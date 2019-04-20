package com.hyh.android_animation.evaluator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.hyh.base_lib.utils.BezierUtils;

/**
 * Created by hyh on 2018/8/7 18:12
 * E-Mail Address：fjnuhyh122@gmail.com
 * 按照贝塞尔曲线轨迹移动的动画估值器
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF mControlPointOne;
    private PointF mControlPointTwo;

    public BezierEvaluator(PointF controlPointOne, PointF controlPointTwo) {
        mControlPointOne = controlPointOne;
        mControlPointTwo = controlPointTwo;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        //Log.d("hyh", "BezierEvaluator: evaluate: start="+startValue.toString()+" ,end="+endValue.toString());
        PointF pointF = BezierUtils.getThreeBezierPoint(startValue,mControlPointOne,mControlPointTwo,endValue,fraction);
        //Log.d("hyh", "BezierEvaluator: evaluate: pointf="+pointF.toString());

        return pointF;
    }
}
