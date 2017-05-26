package com.artifex.mupdf;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by jcman on 16-5-4.
 */
public class OpaqueImageView extends ImageView {
    public OpaqueImageView(Context context){
        super(context);
    }

    @Override
    public boolean isOpaque() {
        return true;
    }
}
