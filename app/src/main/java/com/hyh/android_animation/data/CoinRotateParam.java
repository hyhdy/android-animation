package com.hyh.android_animation.data;

import android.graphics.PointF;

/**
 * created by curdyhuang on 2019/9/30
 * 金币3d旋转轨迹参数
 */
public class CoinRotateParam {
    public static final String KEY_PREFIX = "coin_rotate_";
    /**
     * 属性名
     */
    private String mKey;
    /**
     * 金币做自由3d旋转的开始角度
     */
    private int mStartDegree = 0;
    /**
     * 金币做自由3d旋转的结束角度，默认转三圈
     */
    private int mEndDegree = 360 * 3;

    public CoinRotateParam(String mKey) {
        this.mKey = mKey;
    }

    public int getStartDegree() {
        return mStartDegree;
    }

    public void setStartDegree(int startDegree) {
        mStartDegree = startDegree;
    }

    public int getEndDegree() {
        return mEndDegree;
    }

    public void setEndDegree(int endDegree) {
        mEndDegree = endDegree;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }
}
