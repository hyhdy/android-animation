package com.hyh.android_animation.data;

import android.graphics.PointF;

public class BubbleValue {
    private PointF mBasePoint;
    private String mEmoji;
    private float mScale = 1;
    private float mRotateDegree;

    public BubbleValue(PointF basePoint, String emoji, float scale) {
        mBasePoint = basePoint;
        mEmoji = emoji;
        mScale = scale;
    }

    public PointF getBasePoint() {
        return mBasePoint;
    }

    public void setBasePoint(PointF basePoint) {
        mBasePoint = basePoint;
    }

    public String getEmoji() {
        return mEmoji;
    }

    public void setEmoji(String emoji) {
        mEmoji = emoji;
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public float getRotateDegree() {
        return mRotateDegree;
    }

    public void setRotateDegree(float rotateDegree) {
        mRotateDegree = rotateDegree;
    }
}
