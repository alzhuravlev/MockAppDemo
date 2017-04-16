package com.crane.mockapp.core;

import android.graphics.Bitmap;

/**
 * Created by crane2002 on 2/19/2017.
 */

public interface ImageProvider {
    Bitmap loadImage(String imageFileName, int reqWidth, int reqHeight);
}
