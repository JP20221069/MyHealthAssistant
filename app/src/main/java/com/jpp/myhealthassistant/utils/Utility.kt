package com.jpp.myhealthassistant.utils

import android.content.Context
import android.graphics.drawable.Drawable



class Utility {
    fun GetImage(c: Context, ImageName: String?): Drawable {
        return c.getResources()
            .getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()))
    }
}