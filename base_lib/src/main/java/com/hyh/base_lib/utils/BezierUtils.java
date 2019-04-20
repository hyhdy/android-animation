package com.hyh.base_lib.utils;

import android.graphics.PointF;
import android.util.Pair;

/**
 * Created by hyh on 2018/8/7 21:17
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class BezierUtils {
    /**
     * 计算通过Catmull-Rom算法绘制曲线的控制点，这里只计算一前一后两个控制点
     * @param start
     * @param end
     * @return
     */
    public static Pair<PointF,PointF> getCatmullRomControlPoint(PointF start, PointF end){
        PointF before = new PointF();
        PointF after = new PointF();

        float bx = 2*start.x-end.x;
        float by = end.y;
        before.set(bx,by);

        float ax = 2*end.x - start.x;
        float ay = start.x;
        after.set(ax,ay);

        return Pair.create(before,after);
    }

    /**
     * 计算通过Catmull-Rom算法绘制曲线时经过的点
     * @param point0
     * @param point1
     * @param point2
     * @param point3
     * @param i
     * @return
     */
    public static PointF getCatmullRomPoint(PointF point0, PointF point1, PointF point2, PointF point3, float i) {
        float u3 = i * i * i;
        float u2 = i * i;
        float f1 = -0.5f * u3 + u2 - 0.5f * i;
        float f2 = 1.5f * u3 - 2.5f * u2 + 1.0f;
        float f3 = -1.5f * u3 + 2.0f * u2 + 0.5f * i;
        float f4 = 0.5f * u3 - 0.5f * u2;
        float x = point0.x * f1 + point1.x * f2 + point2.x * f3 + point3.x * f4;
        float y = point0.y * f1 + point1.y * f2 + point2.y * f3 + point3.y * f4;
        return new PointF(x, y);
    }

    /**
     * 计算三阶贝塞尔曲线的控制点
     * @param start
     * @param end
     * @return
     */
    public static Pair<PointF,PointF> getThreeBezierControlPoint(PointF start, PointF end){
        PointF before = new PointF();
        PointF after = new PointF();

        float bx = start.x + (end.x - start.x) * 0.3f;
        float by = start.y + (end.y - start.y) * 0.3f;

        float ax= end.x - (end.x - start.x) * 0.3f;
        float ay = end.y - (end.y - start.y) * 0.3f;

        before.set(bx,by);
        after.set(ax,ay);

        return Pair.create(before,after);
    }

    /***
     * 计算三阶贝塞尔曲线经过的点
     * @param start
     * @param controlOne
     * @param controlTwo
     * @param end
     * @param t
     * @return
     */
    public static PointF getThreeBezierPoint(PointF start, PointF controlOne, PointF controlTwo, PointF end, float t){
        float x = getThreeBezier(start.x,controlOne.x,controlTwo.x,end.x,t);
        float y = getThreeBezier(start.y,controlOne.y,controlTwo.y,end.y,t);
        return new PointF(x,y);
    }

    private static float getThreeBezier(float start,float controlOne,float controlTwo,float end,float t){
        double result = start * Math.pow((1-t),3) + 3 * controlOne * t * Math.pow((1-t),2) + 3 * controlTwo * Math.pow(t,2) * (1-t) + end * Math.pow(t,3);
        return (float) result;
    }
}
