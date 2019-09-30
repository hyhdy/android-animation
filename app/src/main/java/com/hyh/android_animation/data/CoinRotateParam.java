package com.hyh.android_animation.data;

import android.graphics.PointF;

/**
 * created by curdyhuang on 2019/9/30
 * 金币3d旋转轨迹参数
 */
public class CoinRotateParam {
    /**
     * 属性名
     */
    private String mKey = "coin_rotate";
    /**
     * 金币做自由3d旋转的开始角度
     */
    private int mStartDegree = 0;
    /**
     * 金币做自由3d旋转的结束角度，默认转三圈
     */
    private int mEndDegree = 360 * 3;
    /**
     * 动画总时长，默认2秒
     */
    private int mDuration = 2000;

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

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }
}
