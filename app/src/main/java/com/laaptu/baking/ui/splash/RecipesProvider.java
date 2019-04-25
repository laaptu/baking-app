package com.laaptu.baking.ui.splash;

import com.laaptu.baking.data.api.RecipesApiService;
import com.laaptu.baking.data.local.LocalRecipeProvider;
import com.laaptu.baking.data.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

import static com.laaptu.baking.utils.GeneralUtils.isRecipesValid;

public class RecipesProvider {

    private RecipesApiService recipesApiService;
    private LocalRecipeProvider localRecipeProvider;

    private PublishSubject<List<Recipe>> recipesPublisher;

    @Inject
    public RecipesProvider(RecipesApiService recipesApiService, LocalRecipeProvider localRecipeProvider) {
        this.recipesApiService = recipesApiService;
        this.localRecipeProvider = localRecipeProvider;
        recipesPublisher = PublishSubject.create();
    }

    public void fetchRecipes() {
        fetchLocalRecipes()
            .subscribeOn(Schedulers.computation())
            .flatMap(fetchedRecipes -> {
                if (fetchedRecipes.recipes.size() == 0)
                    return fetchRemoteRecipes();
                else
                    return Single.just(fetchedRecipes);
            })
            .doOnSuccess(this::saveRecipes)
            .map(fetchedRecipes -> fetchedRecipes.recipes)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(recipes -> publishRecipes(recipes),
                error -> {
                    Timber.e("Error fetching recipes due to %s", error.getMessage());
                    publishRecipes(new ArrayList<>());
                });
    }

    private void publishRecipes(List<Recipe> recipes) {
        recipesPublisher.onNext(recipes);
    }

    private void saveRecipes(FetchedRecipes fetchedRecipes) {
        if (!fetchedRecipes.isCachedRecipes && isRecipesValid(fetchedRecipes.recipes)) {
            localRecipeProvider.storeAllRecipes(fetchedRecipes.recipes)
                .subscribeOn(Schedulers.computation())
                .subscribe(success ->
                        Timber.d("Successfully save of recipes = %b", success)
                    , error -> Timber.e("Error saving recipes due to : %s", error.getMessage()));
        }
    }

    private Single<FetchedRecipes> fetchRemoteRecipes() {
        return recipesApiService.getAllRecipes()
            .map(recipes -> {
                Timber.d("Successfully fetched remote recipes of size = %d", recipes.size());
                return new FetchedRecipes(false, recipes);
            });
    }

    private Single<FetchedRecipes> fetchLocalRecipes() {
        return localRecipeProvider.getAllRecipes()
            .map(recipes -> {
                Timber.d("Successfully fetched local recipes of size = %d", recipes.size());
                return new FetchedRecipes(true, recipes);
            });
    }

    public Observable<List<Recipe>> getRecipesPublisher() {
        return recipesPublisher;
    }
}
