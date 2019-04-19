package com.hyh.android_animation.propertyAnimator;

import android.animation.TypeEvaluator;
import android.graphics.Point;

public class PointEvaluator implements TypeEvaluator<Point> {

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        Point point = new Point();
        point.x = (int) ((endValue.x - startValue.x) * fraction);
        point.y = (int) ((endValue.x - startValue.x) * fraction);
        return point;
    }
}
