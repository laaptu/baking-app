package com.laaptu.baking.ui.splash;

import com.laaptu.baking.R;
import com.laaptu.baking.common.ui.AutoInjectActivity;
import com.laaptu.baking.data.models.Recipe;
import com.laaptu.baking.ui.recipeslist.RecipesListActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static com.laaptu.baking.utils.GeneralUtils.isRecipesValid;

public class SplashActivity extends AutoInjectActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Inject
    RecipesProvider recipesProvider;

    @Override
    protected void onResume() {
        super.onResume();
        Disposable disposable = getMainThreadSubscription(
            recipesProvider.getRecipesPublisher()
        ).subscribe(this::validateRecipes);
        addDisposable(disposable);
        recipesProvider.fetchRecipes();
    }

    private void validateRecipes(List<Recipe> recipes) {
        if (isRecipesValid(recipes)) {
            startActivity(RecipesListActivity.getLaunchingIntent(this, recipes));
        } else {
            showToast(getString(R.string.error_fetching_recipes));
        }
        finish();
    }
}
