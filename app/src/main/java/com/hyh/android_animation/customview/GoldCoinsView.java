package com.hyh.android_animation.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;

import com.hyh.android_animation.R;
import com.hyh.android_animation.data.CoinFrameData;
import com.hyh.android_animation.data.CoinMoveParam;
import com.hyh.android_animation.data.CoinRotateParam;
import com.hyh.android_animation.evaluator.StraightLineEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * created by curdyhuang on 2019/9/30
 */
public class GoldCoinsView extends View {
    private Paint mPaint;
    private Camera mCamera;
    private Matrix mMatrix;
    private Bitmap mBitmap;
    private int mWidth;//控件宽度
    private int mHeight;//控件高度
    private int mCoinWidth;//金币宽度
    private int mCoinHeight;//金币高度
    private Interpolator mInterPolator;
    private SparseArray<List<CoinFrameData>> mFrameArray = new SparseArray<>();

    public GoldCoinsView(Context context) {
        this(context,null);
    }

    public GoldCoinsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mMatrix = new Matrix();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gold_coin_icon);
        mCoinWidth = mBitmap.getWidth();
        mCoinHeight = mBitmap.getHeight();
        Log.d("hyh","GoldCoinsView: init: mCoinWidth="+mCoinWidth+" ,mCoinHeight="+mCoinHeight);
        mCamera = new Camera();
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

        for(int i =0;i<mFrameArray.size();i++){
            List<CoinFrameData> frameList = mFrameArray.valueAt(i);
            for(CoinFrameData frameData: frameList){
                //保存当前图层以便恢复
                canvas.save();

                //step1 移动金币位置
                canvas.translate(frameData.pointF.x,frameData.pointF.y);

                //setep2 旋转金币
                mMatrix.reset();
                final Camera camera = mCamera;
                //将当前的摄像头位置保存下来，以便变换进行完成后恢复成原位
                camera.save();
                //以X轴为中心进行旋转
                camera.rotateX(frameData.degree);
                //将刚才定义的一系列变换应用到变换矩阵上面，调用完这句之后，我们就可以将camera的位置恢复了，以便下一次再使用
                camera.getMatrix(mMatrix);
                //camera位置恢复
                camera.restore();
                //以金币的中心点为旋转中心,如果不加这两句，就是以左上角顶点为旋转中心
                mMatrix.preTranslate(-mCoinWidth/2, -mCoinHeight/2);
                mMatrix.postTranslate(mCoinWidth/2, mCoinHeight/2);

                //ste3 绘制图像
                canvas.drawBitmap(mBitmap,mMatrix,mPaint);

                //恢复图层
                canvas.restore();
            }
        }
    }

    public void startAnim(){
        List<Animator> animatorList = new ArrayList<>();
        for(int i = 0; i<10; i++) {
            final int position = i;
            final List<Pair<CoinMoveParam, CoinRotateParam>> paramsList = buildAnimParams(i);
            final PropertyValuesHolder[] propertyValuesHolderArray = new PropertyValuesHolder[paramsList.size()*2];
            int index = 0;
            for (Pair<CoinMoveParam, CoinRotateParam> pair: paramsList) {
                final CoinMoveParam coinMoveParam = pair.first;
                final CoinRotateParam coinRotateParam = pair.second;

                //控制直线运动的属性值
                StraightLineEvaluator straightLineEvaluator = new StraightLineEvaluator();
                PropertyValuesHolder moveProperty = PropertyValuesHolder.ofObject(coinMoveParam.getKey(), straightLineEvaluator, coinMoveParam.getStartPoint(), coinMoveParam.getEndPoint());
                propertyValuesHolderArray[index++]=moveProperty;

                //控制3d旋转运动的属性值
                PropertyValuesHolder rotateProperty = PropertyValuesHolder.ofInt(coinRotateParam.getKey(), coinRotateParam.getStartDegree(), coinRotateParam.getEndDegree());
                propertyValuesHolderArray[index++]=rotateProperty;
            }

            ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolderArray);
            valueAnimator.setInterpolator(mInterPolator);
            valueAnimator.setDuration(1500+200*i);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mFrameArray.remove(position);
                    List<CoinFrameData> coinFrameDataList = new ArrayList<>();
                    mFrameArray.put(position, coinFrameDataList);
                    for (int i = 0; i < paramsList.size(); i++) {
                        Pair<CoinMoveParam, CoinRotateParam> pair = paramsList.get(i);
                        final CoinMoveParam coinMoveParam = pair.first;
                        final CoinRotateParam coinRotateParam = pair.second;
                        CoinFrameData coinFrameData = new CoinFrameData();
                        coinFrameData.pointF = (PointF) animation.getAnimatedValue(coinMoveParam.getKey());
                        coinFrameData.degree = (int) animation.getAnimatedValue(coinRotateParam.getKey());
                        coinFrameDataList.add(coinFrameData);
                    }
                    invalidate();
                }
            });
            animatorList.add(valueAnimator);
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorList);
        animatorSet.start();
    }

    /**
     * 构建运动轨迹参数
     * @return
     */
    @NonNull
    private List<Pair<CoinMoveParam, CoinRotateParam>> buildAnimParams(int index){
        List<Pair<CoinMoveParam, CoinRotateParam>> list = new ArrayList<>();

        int xRangeStart = (int) (mWidth * 0.1f);
        int xRangeEnd = (int) (mWidth * 0.9f - mCoinWidth);
        for(int i=0;i<3;i++){
            CoinMoveParam coinMoveParam = new CoinMoveParam(String.format("%s%s",CoinMoveParam.KEY_PREFIX,i));
            //在控件宽度指定范围内随机取一个位置作为起始点x坐标
            int x = getRangeRandomInt(xRangeStart,xRangeEnd);
            //起始点y坐标适当错开位置以便调整每个金币的距离
            int startY = -(mCoinHeight*2 + mHeight*i/4 + mHeight/8*index);
            PointF start = new PointF(x,startY);
            PointF end = new PointF(x,mHeight);
            coinMoveParam.setStartPoint(start);
            coinMoveParam.setEndPoint(end);
            CoinRotateParam coinRotateParam = new CoinRotateParam(String.format("%s%s",CoinRotateParam.KEY_PREFIX,i));
            list.add(Pair.create(coinMoveParam,coinRotateParam));
        }

        return list;
    }

    /**
     * 获取指定区间的随机数（整数）
     * @param start
     * @param end
     * @return
     */
    public int getRangeRandomInt(int start, int end) {
        if (start >= end) {
            return 1;
        }
        Random random = new Random();
        int result = random.nextInt(end - start) + start + 1;
        return result;
    }
}
