package com.hyh.android_animation.evaluator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.hyh.android_animation.data.ExplodeValue;
import com.hyh.base_lib.utils.BezierUtils;

/**
 * 爆种动画估值器
 */
public class ExplodeEvaluator implements TypeEvaluator<PointF> {
    private ExplodeValue mExplodePoint;

    public ExplodeEvaluator(ExplodeValue explodePoint) {
        mExplodePoint = explodePoint;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        PointF startPoint = mExplodePoint.getStartPoint();
        PointF midPoint = mExplodePoint.getMidPoint();
        PointF endpoint = mExplodePoint.getEndPoint();

        if(startValue == startPoint){
            //第一阶段做直线运动
            float x = startValue.x + fraction * (endValue.x - startValue.x);
            float y = startValue.y + fraction * (endValue.y - startValue.y);
            PointF point = new PointF(x, y);
            return point;
        }else if(startValue == midPoint && endValue == midPoint){
            //第二阶段静止不动
            return startValue;
        }else if(endValue == endpoint){
            //最后消失阶段做贝塞尔曲线运动
            PointF pointF = BezierUtils.getThreeBezierPoint(startValue,mExplodePoint.getControlOne(),
                    mExplodePoint.getControlTwo(),endValue,fraction);
            return pointF;
        }
        return null;
    }
}
