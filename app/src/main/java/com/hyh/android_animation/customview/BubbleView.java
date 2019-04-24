package com.hyh.android_animation.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.hyh.android_animation.data.BubbleValue;
import com.hyh.android_animation.interpolator.SpringInterpolator;
import com.hyh.base_lib.utils.SizeUtils;
import com.hyh.base_lib.utils.TransformUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 冒泡动画效果控件
 * todo 在华为手机上emoji过大会出现闪烁问题
 */
public class BubbleView extends View {
    public static final int DEF_SIZE_TEXT = 40;
    public static final String[] sEmojiList = new String[]{"\uD83D\uDE0D","\uD83D\uDC4D\uD83C\uDFFB"};
    //emoji从0到1的时间
    public static final int TIME_ANIM_START_DURING = 500;
    //emoji保持不动的持续时间
    public static final int TIME_ANIM_KEEP_STILL_DURING = 1000;
    //emoji从1到0的时间
    public static final int TIME_ANIM_END_DURING = 500;
    public static final int ANGEL_RANGE_START = -20;
    public static final int ANGEL_RANGE_END = 20;

    private int mDefTextSize;
    private PointF mCenterPoint = new PointF();
    private Matrix mMatrix;
    private int mWidth;
    private int mHeigh;

    private List<BubbleValue> mBubbleValueList = new ArrayList<>();
    private Paint mPaint;

    public BubbleView(Context context) {
        this(context,null);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mDefTextSize = SizeUtils.sp2px(DEF_SIZE_TEXT);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(w != oldw || h != oldh){
            mWidth = w;
            mHeigh = h;
            mCenterPoint.x = w / 2;
            mCenterPoint.y = h / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i=0;i<mBubbleValueList.size();i++){
            BubbleValue bubbleValue = mBubbleValueList.get(i);
            Log.d("hyh","BubbleView: onDraw: scale="+bubbleValue.getScale());
            if(bubbleValue!=null) {
                mPaint.setTextSize(mDefTextSize * bubbleValue.getScale());
                PointF pointF = getTextCenterPoint(bubbleValue,mPaint);
                mMatrix.reset();
                //绕emoji中心点旋转随机角度
                mMatrix.preRotate(bubbleValue.getRotateDegree(),pointF.x,pointF.y);
                canvas.setMatrix(mMatrix);
                //每个emoji需要在新的图层绘制不然叠加在一起绘制会出现闪烁的问题
                canvas.saveLayer(0,0,canvas.getWidth(),canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
                canvas.drawText(bubbleValue.getEmoji(), bubbleValue.getBasePoint().x, bubbleValue.getBasePoint().y, mPaint);
                canvas.restore();
            }
        }
    }

    public void startAnim(){
        mBubbleValueList.clear();
        int count = 20;

        List<Animator> animatorList = new ArrayList<>();
        long startTime = 0;

        float scale = TransformUtils.getRangeRandomFloat(1,1);
        int index = TransformUtils.getRangeRandomInt(0,2)-1;
        BubbleValue bubbleValue = new BubbleValue(mCenterPoint,sEmojiList[index],scale);
        float degree = TransformUtils.getRangeRandomFloat(ANGEL_RANGE_START,ANGEL_RANGE_END);
        bubbleValue.setRotateDegree(degree);
        ValueAnimator valueAnimator = buildBubbleAnimator(0,startTime,bubbleValue);
        animatorList.add(valueAnimator);
        for(int i=1;i<count;i++){
            double timeInternal = TIME_ANIM_KEEP_STILL_DURING / (count - i);
            if(timeInternal>=250){
                timeInternal = 250;
            }
            startTime+=timeInternal;


            PointF pointF = new PointF(TransformUtils.getRangeRandomInt(0,mWidth),TransformUtils.getRangeRandomInt(0,mHeigh));
            scale = TransformUtils.getRangeRandomFloat(1,1);
            index = TransformUtils.getRangeRandomInt(0,2)-1;
            bubbleValue = new BubbleValue(pointF,sEmojiList[index],scale);
            degree = TransformUtils.getRangeRandomFloat(ANGEL_RANGE_START,ANGEL_RANGE_END);
            bubbleValue.setRotateDegree(degree);
            valueAnimator = buildBubbleAnimator(i,startTime,bubbleValue);
            animatorList.add(valueAnimator);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorList);
        animatorSet.start();
    }

    private ValueAnimator buildBubbleAnimator(final int index, long startTime, final BubbleValue bubbleValue){
        Keyframe kf1 = Keyframe.ofFloat(0,0);
        Keyframe kf2 = Keyframe.ofFloat(0.25f,bubbleValue.getScale());
        //增加弹性效果
        kf2.setInterpolator(new SpringInterpolator(0.4f));
        //静止不动
        Keyframe kf3 = Keyframe.ofFloat(0.75f,bubbleValue.getScale());
        //退出
        Keyframe kf4 = Keyframe.ofFloat(0.8f,bubbleValue.getScale() + 0.3f);
        Keyframe kf5 = Keyframe.ofFloat(1.0f,0);
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("scale",kf1,kf2,kf3,kf4,kf5);
        final ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolder);
        valueAnimator.setStartDelay(startTime);
        valueAnimator.setDuration(TIME_ANIM_START_DURING+TIME_ANIM_KEEP_STILL_DURING+TIME_ANIM_END_DURING);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue("scale");
                bubbleValue.setScale(scale);
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mBubbleValueList.add(bubbleValue);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mBubbleValueList.remove(bubbleValue);
            }
        });

        return valueAnimator;
    }

    private PointF getTextCenterPoint(BubbleValue bubbleValue,Paint paint){
        float width = paint.measureText(bubbleValue.getEmoji());
        float x = bubbleValue.getBasePoint().x + width / 2;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float height = fontMetrics.descent - fontMetrics.ascent;
        float y = bubbleValue.getBasePoint().y + fontMetrics.ascent + height/2;
        return new PointF(x,y);
    }
}
