package com.laaptu.baking.common.image;

import com.laaptu.baking.R;

public class ImageLoadOptions {

    public String url;
    public int placeHolderDrawableId;
    public int errorDrawableId;

    private ImageLoadOptions(String url, int placeHolderDrawableId, int errorDrawableId) {
        this.url = url;
        this.placeHolderDrawableId = placeHolderDrawableId;
        this.errorDrawableId = errorDrawableId;
    }

    public static class Builder {
        private int errorDrawableId = R.drawable.ic_recipe_list;
        private int placeHolderDrawableId = R.drawable.ic_recipe_list;
        private String url;

        public Builder() {

        }

        public Builder setImageUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setErrorDrawable(int errorDrawableId) {
            this.errorDrawableId = errorDrawableId;
            return this;
        }

        public Builder setPlaceHolderDrawable(int placeHolderDrawableId) {
            this.placeHolderDrawableId = placeHolderDrawableId;
            return this;
        }

        public ImageLoadOptions build() {
            return new ImageLoadOptions(url, placeHolderDrawableId, errorDrawableId);
        }
    }
}
