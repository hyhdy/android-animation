package com.hyh.android_animation.customview;

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
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.hyh.android_animation.R;
import com.hyh.android_animation.data.CoinMoveParam;
import com.hyh.android_animation.data.CoinRotateParam;
import com.hyh.android_animation.evaluator.StraightLineEvaluator;

import java.util.ArrayList;
import java.util.List;

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
    //动画轨迹参数，控制金币运动轨迹
    private List<Pair<CoinMoveParam, CoinRotateParam>> mParamList;
    private List<Pair<PointF,Integer>> mValueList = new ArrayList<>();

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

        for(int i=0;i<mValueList.size();i++){
            Pair<PointF,Integer> pair = mValueList.get(i);
            PointF pointF = pair.first;
            int degree = pair.second;
            
            //step1 移动金币位置
            canvas.translate(pointF.x,pointF.y);

            //setep2 旋转金币
            mMatrix.reset();
            final Camera camera = mCamera;
            //将当前的摄像头位置保存下来，以便变换进行完成后恢复成原位，
            camera.save();
            //会以X轴为中心进行旋转。
            camera.rotateX(degree);
            //将我们刚才定义的一系列变换应用到变换矩阵上面，调用完这句之后，我们就可以将camera的位置恢复了，以便下一次再使用。
            camera.getMatrix(mMatrix);
            //camera位置恢复
            camera.restore();
            //以View的中心点为旋转中心,如果不加这两句，就是以（0,0）点为旋转中心
            mMatrix.preTranslate(-mCoinWidth/2, -mCoinHeight/2);
            mMatrix.postTranslate(mCoinWidth/2, mCoinHeight/2);

            //ste3 绘制图像
            canvas.drawBitmap(mBitmap,mMatrix,mPaint);
        }
    }

    public void startAnim(){
        buildParams();
        for(Pair<CoinMoveParam,CoinRotateParam> pair: mParamList){
            final CoinMoveParam coinMoveParam = pair.first;
            final CoinRotateParam coinRotateParam = pair.second;

            //控制直线运动的属性值
            StraightLineEvaluator straightLineEvaluator = new StraightLineEvaluator();
            PropertyValuesHolder moveProperty = PropertyValuesHolder.ofObject(coinMoveParam.getKey(),straightLineEvaluator,coinMoveParam.getStartPoint(),coinMoveParam.getEndPoint());

            //控制3d旋转运动的属性值
            PropertyValuesHolder rotateProperty = PropertyValuesHolder.ofInt(coinRotateParam.getKey(),coinRotateParam.getStartDegree(),coinRotateParam.getEndDegree());

            ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(moveProperty,rotateProperty);
            valueAnimator.setDuration(coinMoveParam.getDuration());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mValueList.clear();
                    PointF pointF = (PointF) animation.getAnimatedValue(coinMoveParam.getKey());
                    Log.d("hyh","GoldCoinsView: onAnimationUpdate: pointf="+pointF.toString());
                    int degree = (int) animation.getAnimatedValue(coinRotateParam.getKey());
                    Pair<PointF,Integer> value = Pair.create(pointF,degree);
                    mValueList.add(value);
                    invalidate();
                }
            });
            valueAnimator.start();
        }
    }

    /**
     * 构建运动轨迹参数
     * @return
     */
    private void buildParams(){
        if(mParamList == null){
            mParamList = new ArrayList<>();
        }
        mParamList.clear();
        CoinMoveParam coinMoveParam = new CoinMoveParam();
        int x = (mWidth - mCoinWidth)/2;
        int startY = -mCoinHeight;
        PointF start = new PointF(x,startY);
        PointF end = new PointF(x,mHeight);
        coinMoveParam.setStartPoint(start);
        coinMoveParam.setEndPoint(end);
        CoinRotateParam coinRotateParam = new CoinRotateParam();
        mParamList.add(Pair.create(coinMoveParam,coinRotateParam));
    }
}
