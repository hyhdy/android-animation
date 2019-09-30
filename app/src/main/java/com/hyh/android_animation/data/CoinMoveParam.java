package com.hyh.android_animation.data;

import android.graphics.PointF;

/**
 * created by curdyhuang on 2019/9/30
 * 金币移动轨迹参数
 */
public class CoinMoveParam {
    /**
     * 属性名
     */
    private String mKey = "coin_move";
    /**
     * 金币下落起始点
     */
    private PointF mStartPoint;
    /**
     * 金币下落结束点
     */
    private PointF mEndPoint;
    /**
     * 动画总时长，默认2秒
     */
    private int mDuration = 2000;

    public PointF getStartPoint() {
        return mStartPoint;
    }

    public void setStartPoint(PointF startPoint) {
        mStartPoint = startPoint;
    }

    public PointF getEndPoint() {
        return mEndPoint;
    }

    public void setEndPoint(PointF endPoint) {
        mEndPoint = endPoint;
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
