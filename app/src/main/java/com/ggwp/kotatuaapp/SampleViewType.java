package com.ggwp.kotatuaapp;

/**
 * Created by akmaljun on 6/26/2016.
 */

import jp.satorufujiwara.binder.ViewType;

public enum SampleViewType implements ViewType {
    TEXT;

    @Override
    public int viewType() {
        return ordinal();
    }
}