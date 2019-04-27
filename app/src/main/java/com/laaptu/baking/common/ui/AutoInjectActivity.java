package com.laaptu.baking.common.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class AutoInjectActivity extends DaggerAppCompatActivity implements
        HasSupportFragmentInjector {

    @Inject DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    public abstract int getLayoutId();

    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public Bus eventBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    public <T> Observable<T> getMainThreadSubscription(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    public void clearDisposable() {
        disposables.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearDisposable();
        eventBus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }
}
