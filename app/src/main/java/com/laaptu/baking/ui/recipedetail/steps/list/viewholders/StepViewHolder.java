package com.laaptu.baking.ui.recipedetail.steps.list.viewholders;

import android.view.View;
import android.widget.TextView;

import com.laaptu.baking.R;
import com.laaptu.baking.common.ui.BaseViewHolder;
import com.laaptu.baking.ui.recipedetail.steps.list.data.StepClickedItem;
import com.squareup.otto.Bus;

import java.util.Locale;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

public class StepViewHolder extends BaseViewHolder {

    private Bus eventBus;
    private int position;

    @BindView(R.id.txt_step_order)
    TextView txtStepOrder;
    @BindView(R.id.txt_step_name)
    TextView txtStepName;

    public StepViewHolder(@NonNull View itemView, Bus eventBus) {
        super(itemView);
        this.eventBus = eventBus;
    }

    public void bindView(String shortDescription, int position) {
        this.position = position;
        txtStepOrder.setText(String.format(Locale.getDefault(), "%d.", position ));
        txtStepName.setText(shortDescription);
    }

    @OnClick(R.id.step_container)
    public void onContainerClicked() {
        eventBus.post(new StepClickedItem(position));
    }
}
