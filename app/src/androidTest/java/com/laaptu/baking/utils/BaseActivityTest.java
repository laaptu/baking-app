package com.laaptu.baking.utils;

import com.laaptu.baking.ui.splash.SplashActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import java.util.concurrent.TimeUnit;

import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

public abstract class BaseActivityTest {

    public CustomIdlingResource idlingResource;

    public static final int TIMEOUT_ESPRESSO = 30;
    public static final int TIMEOUT_FETCH = 8;
    public static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;

    @Rule
    public ActivityScenarioRule<SplashActivity> splashActivityTestRule =
            new ActivityScenarioRule<>(SplashActivity.class);

    @Before
    public void initIdlingResource() {
        idlingResource = new CustomIdlingResource();
        IdlingPolicies.setMasterPolicyTimeout(TIMEOUT_ESPRESSO, TIMEOUT_UNIT);
        IdlingPolicies.setIdlingResourceTimeout(TIMEOUT_FETCH, TIMEOUT_UNIT);
    }

    public void registerIdlingResource(long countDown) {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().register(idlingResource);
            idlingResource.startCountdown(countDown, TIMEOUT_UNIT);
        }
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
            idlingResource = null;
        }
    }
}
