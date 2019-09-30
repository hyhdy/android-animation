package com.hyh.android_animation.evaluator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.util.Log;

/**
 * Created by hyh on 2018/8/7 18:08
 * E-Mail Address：fjnuhyh122@gmail.com
 * 直线平移动画估值器
 */
public class StraightLineEvaluator implements TypeEvaluator<PointF> {
    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float x = startValue.x + fraction * (endValue.x - startValue.x);
        float y = startValue.y + fraction * (endValue.y - startValue.y);
        PointF point = new PointF(x, y);
        return point;
    }
}
