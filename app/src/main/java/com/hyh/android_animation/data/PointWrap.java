package com.hyh.android_animation.data;

import android.graphics.PointF;

public class PointWrap {
    private PointF mPoint;
    private float alpha;
    //emoji旋转角度
    private float mDegree;

    public PointWrap(float alpha) {
        this.alpha = alpha;
    }

    public PointF getPoint() {
        return mPoint;
    }

    public void setPoint(PointF point) {
        mPoint = point;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getDegree() {
        return mDegree;
    }

    public void setDegree(float degree) {
        mDegree = degree;
    }
}
