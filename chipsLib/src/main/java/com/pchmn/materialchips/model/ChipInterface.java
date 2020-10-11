package com.pchmn.materialchips.model;


import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.Nullable;

public interface ChipInterface {

    @Nullable Object getId();
    @Nullable Uri getAvatarUri();
    Drawable getAvatarDrawable();
    String getLabel();
    String getInfo();
    Object getObject();

    void setSelected(boolean isSelected);
    boolean isSelected();
}
