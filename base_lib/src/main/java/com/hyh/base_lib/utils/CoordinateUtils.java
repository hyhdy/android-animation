package com.hyh.base_lib.utils;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh on 2018/7/19 21:52
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class CoordinateUtils {
    private static final double DEFAULT_WIDTH = 750;
    private static final double DEFAULT_HEIGHT = 1334;

    public static Pair<Double, Double> generateStorageCoordinate(int widgetWidth, int widgetHeight, double x, double y) {

        double resultX = x / widgetWidth * DEFAULT_WIDTH;
        double resultY = y / widgetHeight * DEFAULT_HEIGHT;

        return Pair.create(resultX, resultY);
    }

    public static Pair<Integer, Integer> generateDisplayCoordinate(int widgetWidth, int widgetHeight, double x, double y) {

        double resultX = x / DEFAULT_WIDTH * widgetWidth;
        double resultY = y / DEFAULT_HEIGHT * widgetHeight;

        int resultIntX = (int) resultX;
        int resultIntY = (int) resultY;

        return Pair.create(resultIntX, resultIntY);
    }

    /**
     * 计算将圆分成n等份后每个圆弧上点的坐标
     * @param centerPoint：圆形中心店坐标
     * @param radius：圆半径
     * @param divisor：将圆n等分
     * @param offset：偏移量
     * @return
     */
    public static List<PointF> calculateRoundItemPositions(Point centerPoint, float radius, int divisor, float offset) {
        List<PointF> pointfList = new ArrayList<>();

        //以圆点坐标（x，y）为中心画一个矩形RectF
        RectF area = new RectF(centerPoint.x - radius, centerPoint.y - radius, centerPoint.x + radius, centerPoint.y + radius);
        Path orbit = new Path();
        //通过Path类画一个内切圆弧路径
        orbit.addArc(area, 0, 360);

        PathMeasure measure = new PathMeasure(orbit, false);
        for(int i=0; i<6; i++) {
            float[] coords = new float[] {0f, 0f};
            //利用PathMeasure分别测量出各个点的坐标值coords
            measure.getPosTan((i) * measure.getLength() / divisor + offset, coords, null);
            PointF pointf = new PointF(coords[0],coords[1]);
            Log.d("hyh", "PraiseView: calculateItemPositions: pointf="+pointf.toString());
            pointfList.add(pointf);
        }

        return pointfList;
    }

    public static PathMeasure getPathMeasure(PointF centerPoint, float radius){
        //以centerPoint为中心，2 * radius为宽高，画一个矩形RectF
        RectF area = new RectF(centerPoint.x - radius, centerPoint.y - radius, centerPoint.x + radius, centerPoint.y + radius);
        Path orbit = new Path();
        //通过Path类画一个内切圆弧路径
        orbit.addArc(area, 0, 360);
        PathMeasure measure = new PathMeasure(orbit, false);
        return measure;
    }

    public static List<PointF> calculateRoundItemPositions(PathMeasure pathMeasure, int divisor, float offsetAngel) {
        List<PointF> pointfList = new ArrayList<>();

        float length = pathMeasure.getLength();
        float oneAngelLength = length / 360;//一个角度的长度
        float offsetLength = offsetAngel * oneAngelLength;

        for(int i=0; i<divisor; i++) {
            float[] coords = new float[] {0f, 0f};
            //利用PathMeasure分别测量出各个点的坐标值coords
            pathMeasure.getPosTan(((i) * length / divisor + offsetLength)%length, coords, null);
            PointF pointf = new PointF(coords[0],coords[1]);
            pointfList.add(pointf);
        }

        return pointfList;
    }

    /**
     * 获取两个点x,y坐标的距离
     * @param start
     * @param end
     * @return
     */
    public static PointF getPointFDistance(PointF start, PointF end){
        float width = Math.abs(end.x-start.x);
        float height = Math.abs((end.y-start.y));
        return new PointF(width,height);
    }
}
