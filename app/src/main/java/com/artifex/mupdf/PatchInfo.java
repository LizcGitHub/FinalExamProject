package com.artifex.mupdf;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by jcman on 16-5-4.
 */
public class PatchInfo{

    public Bitmap bm;
    public Point patchViewSize;
    public Rect patchArea;
    public PatchInfo(Bitmap aBm, Point aPatchViewSize, Rect aPatchArea) {
        bm = aBm;
        patchViewSize = aPatchViewSize;
        patchArea = aPatchArea;
    }
}
