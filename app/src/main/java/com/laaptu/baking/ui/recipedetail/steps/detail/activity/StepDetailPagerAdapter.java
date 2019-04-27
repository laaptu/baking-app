package com.laaptu.baking.ui.recipedetail.steps.detail.activity;

import android.content.Context;

import com.laaptu.baking.R;
import com.laaptu.baking.data.models.Step;
import com.laaptu.baking.ui.recipedetail.steps.detail.StepDetailFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class StepDetailPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Step> steps;

    public StepDetailPagerAdapter(Context context, List<Step> steps,
                                  FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
        this.steps = steps;
    }

    @NonNull @Override public Fragment getItem(int position) {
        Step step = steps.get(position);
        return StepDetailFragment.getInstance(step);
    }

    @Override public int getCount() {
        return steps.size();
    }

    @Nullable @Override public CharSequence getPageTitle(int position) {
        return String.format(context.getString(R.string.step), position);
    }
}
