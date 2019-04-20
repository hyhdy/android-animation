package com.hyh.android_animation.customview;

import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import com.hyh.android_animation.evaluator.ExplodeEvaluator;
import com.hyh.android_animation.explode.ExplodePoint;
import com.hyh.base_lib.utils.BezierUtils;
import com.hyh.base_lib.utils.CoordinateUtils;
import com.hyh.base_lib.utils.SizeUtils;
import com.hyh.base_lib.utils.TransformUtils;

import java.security.KeyFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现爆种动画的自定义view
 */
public class ExplodeView extends View {
    public static final String sEmoji = "\uD83D\uDC4D\uD83C\uDFFB";
    public static final int SIZE_TEXT = 20;
    public static final int RADIUS_EXPLODE = 30;
    //爆种动画的个数
    public static final int NUM_EXPLODE = 6;
    public static final int ANGEL_RANGE_START = -20;
    public static final int ANGEL_RANGE_END = 20;

    private List<PointF> mPointFList = new ArrayList<>();
    private PointF mCenterPoint = new PointF();

    private Paint mPaint;
    private Paint.FontMetrics mFontMetrics;
    private float mWidth;
    private float mHeight;
    private float mRadius;

    public ExplodeView(Context context) {
        this(context,null);
    }

    public ExplodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(SizeUtils.sp2px(SIZE_TEXT));
        mWidth = mPaint.measureText(sEmoji);
        mHeight = mPaint.measureText(sEmoji);
        mFontMetrics = mPaint.getFontMetrics();
        mRadius = SizeUtils.dp2px(RADIUS_EXPLODE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(w != oldw || h != oldh){
            mCenterPoint.x = w / 2;
            mCenterPoint.y = w / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(PointF pointF: mPointFList){
            int x = getTextBaseLineX(pointF);
            int y = getTextBaseLineY(pointF);
            canvas.drawText(sEmoji,x,y,mPaint);
        }
    }

    /**
     * 获取文本基线的x坐标
     * @param point
     * @return
     */
    private int getTextBaseLineX(PointF point){
        return (int) (point.x - mWidth / 2);
    }

    /**
     * 获取文本基线的y坐标
     * @param point
     * @return
     */
    private int getTextBaseLineY(PointF point){
        return (int) (point.y - mHeight/2 - mFontMetrics.top);
    }

    /**
     * 构建爆种动画关键帧的坐标点
     * @return
     */
    private List<ExplodePoint> buildExplodePoint(){
        List<ExplodePoint> explodePointList = new ArrayList<>();

        PathMeasure pathMeasure = CoordinateUtils.getPathMeasure(mCenterPoint,mRadius);
        List<PointF> pointfListStart = CoordinateUtils.calculateRoundItemPositions(pathMeasure,
                NUM_EXPLODE, 0);
        float endOffsetAngel = (float) TransformUtils.getRangeRandomDouble(ANGEL_RANGE_START,ANGEL_RANGE_END);
        List<PointF> pointfListEnd = CoordinateUtils.calculateRoundItemPositions(pathMeasure,
                NUM_EXPLODE, endOffsetAngel);

        for(int i=0;i<NUM_EXPLODE;i++){
            ExplodePoint explodePoint = new ExplodePoint(String.format("%s%d","point",i));
            explodePoint.setStartPoint(mCenterPoint);
            explodePoint.setMidPoint(pointfListStart.get(i));
            explodePoint.setEndPoint(pointfListEnd.get(i));
            //贝塞尔计算器 传入三阶公式的必要参数 （由于起点和终点已确定，这里控制曲线则由中间2个点来控制 ）
            Pair<PointF, PointF> pair = BezierUtils.getThreeBezierControlPoint(explodePoint.getMidPoint(), explodePoint.getEndPoint());
            explodePoint.setControlOne(pair.first);
            explodePoint.setControlTwo(pair.second);
            explodePointList.add(explodePoint);
        }

        return explodePointList;
    }

    public void startAnim(){
        final List<ExplodePoint> explodePointList = buildExplodePoint();
        PropertyValuesHolder[] propertyValuesHolderArray = new PropertyValuesHolder[explodePointList.size()];
        for(int i =0 ;i<explodePointList.size();i++){
            ExplodePoint explodePoint = explodePointList.get(i);
            ExplodeEvaluator explodeEvaluator = new ExplodeEvaluator(explodePoint);
            Keyframe startPoint = Keyframe.ofObject(0,explodePoint.getStartPoint());
            Keyframe mid1 = Keyframe.ofObject(0.6f,explodePoint.getMidPoint());
            //中间停留一会儿
            Keyframe mid2 = Keyframe.ofObject(0.8f,explodePoint.getMidPoint());
            Keyframe endPoint = Keyframe.ofObject(1.0f,explodePoint.getEndPoint());
            PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe(explodePoint.getPropertyName(),startPoint,mid1,mid2,endPoint);
            propertyValuesHolder.setEvaluator(explodeEvaluator);

            propertyValuesHolderArray[i]=propertyValuesHolder;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolderArray);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPointFList.clear();
                for(int i =0;i<explodePointList.size();i++){
                    PointF pointF = (PointF) animation.getAnimatedValue(explodePointList.get(i).getPropertyName());
                    mPointFList.add(pointF);
                }
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
