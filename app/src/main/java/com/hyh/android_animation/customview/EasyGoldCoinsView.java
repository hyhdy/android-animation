package com.hyh.android_animation.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import com.hyh.android_animation.R;
import com.hyh.android_animation.data.CoinFrameData;
import com.hyh.android_animation.data.CoinMoveParam;
import com.hyh.android_animation.evaluator.StraightLineEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * created by curdyhuang on 2019/9/30
 */
public class EasyGoldCoinsView extends View {
    private Paint mPaint;
    private Bitmap mCoin1;
    private Bitmap mCoin2;
    private Bitmap mCoin3;
    private int mCoinWidth;
    private int mWidth;//控件宽度
    private int mHeight;//控件高度
    private Interpolator mInterPolator;
    private List<CoinFrameData> mFrameList = new ArrayList<>();
    private OnAnimListener mOnAnimListener;

    public EasyGoldCoinsView(Context context) {
        this(context,null);
    }

    public EasyGoldCoinsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mCoin1 = BitmapFactory.decodeResource(getResources(), R.drawable.gold_icon_1);
        mCoin2 = BitmapFactory.decodeResource(getResources(), R.drawable.gold_icon_2);
        mCoin3 = BitmapFactory.decodeResource(getResources(), R.drawable.gold_icon_3);
        mCoinWidth = mCoin3.getWidth();
        mInterPolator = new AccelerateInterpolator();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(w != oldw || h != oldh){
            mWidth = w;
            mHeight = h;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i< mFrameList.size(); i++){
            CoinFrameData frameData = mFrameList.get(i);
            //绘制图像
            canvas.drawBitmap(frameData.coinIcon,frameData.pointF.x,frameData.pointF.y,mPaint);
        }
    }

    public void startAnim(){
            final List<CoinMoveParam> paramsList = buildAnimParams();
            final PropertyValuesHolder[] propertyValuesHolderArray = new PropertyValuesHolder[paramsList.size()];
            int index = 0;
            for (CoinMoveParam coinMoveParam: paramsList) {
                //控制直线运动的属性值
                StraightLineEvaluator straightLineEvaluator = new StraightLineEvaluator();
                PropertyValuesHolder moveProperty = PropertyValuesHolder.ofObject(coinMoveParam.getKey(), straightLineEvaluator, coinMoveParam.getStartPoint(), coinMoveParam.getEndPoint());
                propertyValuesHolderArray[index++]= moveProperty;
            }

            ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolderArray);
            valueAnimator.setInterpolator(mInterPolator);
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mFrameList.clear();
                    for (int i = 0; i < paramsList.size(); i++) {
                        CoinMoveParam coinMoveParam = paramsList.get(i);
                        CoinFrameData coinFrameData = new CoinFrameData();
                        coinFrameData.coinIcon = coinMoveParam.getCoinIcon();
                        coinFrameData.pointF = (PointF) animation.getAnimatedValue(coinMoveParam.getKey());
                        mFrameList.add(coinFrameData);
                    }
                    invalidate();
                }
            });

            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if(mOnAnimListener!=null){
                        mOnAnimListener.onAnimEnd();
                    }
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    if(mOnAnimListener!=null){
                        mOnAnimListener.onAnimStart();
                    }
                }
            });

            valueAnimator.start();
    }

    /**
     * 构建运动轨迹参数
     * @return
     */
    @NonNull
    private List<CoinMoveParam> buildAnimParams(){
        List<CoinMoveParam> list = new ArrayList<>();

        int xRangeStart = (mWidth - mCoinWidth * 4)/2;
        int yTopRangeStart;
        int yTopRangeEnd;
        int yBottomRangeStart = (int) (mHeight * 0.9f);
        int yBottomRangeEnd = mHeight;
        for(int i=0;i<12;i++){
            CoinMoveParam coinMoveParam = new CoinMoveParam(String.format("%s%s",CoinMoveParam.KEY_PREFIX,i));
            //起始点x坐标适当错开位置以便调整每个金币的距离
            int x = xRangeStart + mCoinWidth*(i%4);
            //起始点y坐标适当错开位置以便调整每个金币的距离
            if(i<4){
                yTopRangeStart = 0;
                yTopRangeEnd = (int) (mHeight * 0.1f);
            }else if(i<8){
                yTopRangeStart = (int) (mHeight * 0.1f);
                yTopRangeEnd = (int) (mHeight * 0.2f);
            }else{
                yTopRangeStart = (int) (mHeight * 0.2f);
                yTopRangeEnd = (int) (mHeight * 0.3f);
            }
            int startY = getRangeRandomInt(yTopRangeStart,yTopRangeEnd);
            int endY = getRangeRandomInt(yBottomRangeStart,yBottomRangeEnd);
            PointF start = new PointF(x,startY);
            PointF end = new PointF(x,endY);
            coinMoveParam.setStartPoint(start);
            coinMoveParam.setEndPoint(end);
            if(i == 0){
                coinMoveParam.setCoinIcon(mCoin1);
            }else if(i == 8){
                coinMoveParam.setCoinIcon(mCoin2);
            }else{
                coinMoveParam.setCoinIcon(mCoin3);
            }
            list.add(coinMoveParam);
        }

        return list;
    }

    /**
     * 获取指定区间的随机数（整数）
     * @param start
     * @param end
     * @return
     */
    private int getRangeRandomInt(int start, int end) {
        if (start >= end) {
            return 1;
        }
        Random random = new Random();
        return random.nextInt(end - start) + start + 1;
    }

    public void setOnAnimListener(OnAnimListener onAnimListener) {
        mOnAnimListener = onAnimListener;
    }

    public interface OnAnimListener{
        void onAnimStart();
        void onAnimEnd();
    }
}
