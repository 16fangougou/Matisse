/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhihu.matisse.engine.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.shizhefei.view.largeimage.LargeImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhihu.matisse.engine.ImageEngine;
import com.zhihu.matisse.ui.MatisseActivity;

/**
 * {@link ImageEngine} implementation using Picasso.
 */

public class PicassoEngine implements ImageEngine {

    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        Picasso.with(context).load(uri).placeholder(placeholder)
                .resize(resize, resize)
                .centerCrop()
                .tag(MatisseActivity.LOAD_TAG)
                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
                                 Uri uri) {
        loadThumbnail(context, resize, placeholder, imageView, uri);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, final LargeImageView imageView, Uri uri) {
        Picasso.with(context).load(uri).resize(resizeX, resizeY).priority(Picasso.Priority.HIGH)
                .centerInside().into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImage(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                imageView.setImage(errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                imageView.setImage(placeHolderDrawable);
            }
        });
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
            Picasso.with(context).load(uri).resize(resizeX, resizeY).priority(Picasso.Priority.HIGH)
                    .centerInside().into(imageView);
    }

    @Override
    public void pauseLoad(Context context, String tag) {
        Picasso.with(context).pauseTag(tag);
    }

    @Override
    public void resumeLoad(Context context, String tag) {
        Picasso.with(context).resumeTag(tag);
    }

    @Override
    public boolean supportAnimatedGif() {
        return false;
    }
}
