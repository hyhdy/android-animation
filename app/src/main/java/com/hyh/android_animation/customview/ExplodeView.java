package com.hyh.android_animation.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;

import com.hyh.android_animation.data.PointWrap;
import com.hyh.android_animation.evaluator.ExplodeEvaluator;
import com.hyh.android_animation.data.ExplodePoint;
import com.hyh.android_animation.interpolator.SpringInterpolator;
import com.hyh.base_lib.utils.BezierUtils;
import com.hyh.base_lib.utils.CoordinateUtils;
import com.hyh.base_lib.utils.SizeUtils;
import com.hyh.base_lib.utils.TransformUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现爆种动画的自定义view
 */
public class ExplodeView extends View {
    public static final String sEmoji = "\uD83D\uDE0D";
    public static final int SIZE_TEXT = 30;
    public static final int RADIUS_EXPLODE = 80;
    //爆种动画的个数
    public static final int NUM_EXPLODE = 6;
    public static final int ANGEL_RANGE_START = -20;
    public static final int ANGEL_RANGE_END = 20;

    private SparseArray<List<PointWrap>> mPointFArray = new SparseArray<>();
    private PointF mCenterPoint = new PointF();

    private Paint mPaint;
    private Paint.FontMetrics mFontMetrics;
    private float mWidth;
    private float mHeight;
    private float mRadius;
    private int mIndex;

    public ExplodeView(Context context) {
        this(context,null);
    }

    public ExplodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(SizeUtils.sp2px(SIZE_TEXT));
        mWidth = mPaint.measureText(sEmoji);
        mFontMetrics = mPaint.getFontMetrics();
        mHeight = mFontMetrics.descent - mFontMetrics.ascent;
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
        for(int i =0;i<mPointFArray.size();i++){
            List<PointWrap> pointFList = mPointFArray.valueAt(i);
            for(PointWrap pointWrap: pointFList){
                int x = getTextBaseLineX(pointWrap.getPoint());
                int y = getTextBaseLineY(pointWrap.getPoint());
                mPaint.setAlpha((int) (255 * pointWrap.getAlpha()));
                canvas.drawText(sEmoji,x,y,mPaint);
            }
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
        mIndex++;
        final int index = mIndex;

        final List<ExplodePoint> explodePointList = buildExplodePoint();
        PropertyValuesHolder[] propertyValuesHolderArray = new PropertyValuesHolder[explodePointList.size()+1];
        Keyframe kfAlpha1 = Keyframe.ofFloat(0,1);
        Keyframe kfAlpha2 = Keyframe.ofFloat(0.8f,1);
        Keyframe kfAlpha3 = Keyframe.ofFloat(1.0f,0);
        PropertyValuesHolder alphaValueHolder = PropertyValuesHolder.ofKeyframe("alpha",kfAlpha1,kfAlpha2,kfAlpha3);
        propertyValuesHolderArray[0] = alphaValueHolder;
        for(int i =0 ;i<explodePointList.size();i++){
            ExplodePoint explodePoint = explodePointList.get(i);
            ExplodeEvaluator explodeEvaluator = new ExplodeEvaluator(explodePoint);
            Keyframe startPoint = Keyframe.ofObject(0,explodePoint.getStartPoint());
            Keyframe mid1 = Keyframe.ofObject(0.6f,explodePoint.getMidPoint());
            //在第一阶段做直线运动时需要实现弹性效果，所以需要在第二个Keyframe加上一个Spring差值器
            mid1.setInterpolator(new SpringInterpolator());
            //中间停留一会儿
            Keyframe mid2 = Keyframe.ofObject(0.8f,explodePoint.getMidPoint());
            Keyframe endPoint = Keyframe.ofObject(1.0f,explodePoint.getEndPoint());
            PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe(explodePoint.getPropertyName(),startPoint,mid1,mid2,endPoint);
            propertyValuesHolder.setEvaluator(explodeEvaluator);

            propertyValuesHolderArray[i+1]=propertyValuesHolder;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolderArray);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPointFArray.remove(index);
                List<PointWrap> pointWrapList = new ArrayList<>();
                mPointFArray.put(index,pointWrapList);
                float alpha = (float) animation.getAnimatedValue("alpha");
                for(int i =0;i<explodePointList.size();i++){
                    PointWrap pointWrap = new PointWrap(alpha);
                    PointF pointF = (PointF) animation.getAnimatedValue(explodePointList.get(i).getPropertyName());
                    pointWrap.setPoint(pointF);
                    pointWrapList.add(pointWrap);
                }
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPointFArray.remove(index);
            }
        });
        //只需要一个动画就能实现爆种效果
        valueAnimator.start();
    }
}
