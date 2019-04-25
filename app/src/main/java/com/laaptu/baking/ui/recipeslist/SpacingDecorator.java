package com.laaptu.baking.ui.recipeslist;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingDecorator extends RecyclerView.ItemDecoration {

    public enum Arrangement {
        VERTICAL,
        GRID,
    }

    private final int spacing;
    private Arrangement arrangement;


    public SpacingDecorator(int spacing, Arrangement arrangement) {
        this.spacing = spacing;
        this.arrangement = arrangement;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildViewHolder(view).getAdapterPosition();
        int itemCount = state.getItemCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        setSpacing(outRect, layoutManager, position, itemCount);
    }

    private void setSpacing(Rect outRect,
                            RecyclerView.LayoutManager layoutManager,
                            int position,
                            int itemCount) {
        switch (arrangement) {
            case VERTICAL:
                outRect.left = spacing;
                outRect.right = spacing;
                outRect.top = spacing;
                outRect.bottom = position == itemCount - 1 ? spacing : 0;
                break;
            case GRID:
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    int cols = gridLayoutManager.getSpanCount();
                    int rows = itemCount / cols;

                    outRect.left = spacing;
                    outRect.right = position % cols == cols - 1 ? spacing : 0;
                    outRect.top = spacing;
                    outRect.bottom = position / cols == rows - 1 ? spacing : 0;
                }
                break;
        }
    }

}
