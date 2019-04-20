package com.hyh.android_animation.explode;

import android.graphics.PointF;

/**
 * 封装爆种动画关键坐标点信息
 * 爆种动画从开始坐标点到中间坐标点将做直线运动
 * 从中间坐标点到结束坐标点做贝塞尔曲线运动
 */
public class ExplodePoint {
    //属性名
    private String propertyName;
    //起始坐标点
    private PointF mStartPoint;
    //中间坐标点
    private PointF mMidPoint;
    //结束坐标点
    private PointF mEndPoint;

    //控制点，做贝塞尔曲线运动用到
    private PointF mControlOne;
    private PointF mControlTwo;

    public ExplodePoint(String propertyName) {
        this.propertyName = propertyName;
    }

    public ExplodePoint() {
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public PointF getStartPoint() {
        return mStartPoint;
    }

    public void setStartPoint(PointF startPoint) {
        mStartPoint = startPoint;
    }

    public PointF getMidPoint() {
        return mMidPoint;
    }

    public void setMidPoint(PointF midPoint) {
        mMidPoint = midPoint;
    }

    public PointF getEndPoint() {
        return mEndPoint;
    }

    public void setEndPoint(PointF endPoint) {
        mEndPoint = endPoint;
    }

    public PointF getControlOne() {
        return mControlOne;
    }

    public void setControlOne(PointF controlOne) {
        mControlOne = controlOne;
    }

    public PointF getControlTwo() {
        return mControlTwo;
    }

    public void setControlTwo(PointF controlTwo) {
        mControlTwo = controlTwo;
    }
}
