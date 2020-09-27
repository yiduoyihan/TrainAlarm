package com.leidi.trainalarm.bean;

import android.os.Parcel;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author 阎
 * @date 2020/9/8
 * @description
 */
public class HomeCardBean implements MultiItemEntity {
    public static final int ITEM_UP = 1;
    public static final int ITEM_DOWN = 2;
    private int itemType;

    private float startWeight;
    private float endWeight;

    public float getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(float startWeight) {
        this.startWeight = startWeight;
    }

    public float getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(float endWeight) {
        this.endWeight = endWeight;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }


}
