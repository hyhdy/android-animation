package com.hyh.android_animation.fragment;

import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyh.android_animation.R;
import com.hyh.android_animation.propertyAnimator.PointEvaluator;
import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.hyh.base_lib.annotation.OnClick;

@InjectFragment()
public class ValueAnimatorFragment extends BaseFragment implements View.OnClickListener {
    @FindViewByIdAno(R.id.btn_of_float)
    private Button mBtnOfFloat;
    @FindViewByIdAno(R.id.btn_of_int)
    private Button mBtnOfInt;
    @FindViewByIdAno(R.id.btn_of_object)
    private Button mBtnOfObject;
    @FindViewByIdAno(R.id.btn_of_pvh_float)
    private Button mBtnOfPvhFloat;
    @FindViewByIdAno(R.id.btn_of_pvh_int)
    private Button mBtnOfPvhInt;
    @FindViewByIdAno(R.id.btn_of_pvh_object)
    private Button mBtnOfPvhObject;
    @FindViewByIdAno(R.id.tv_dis)
    private TextView mTvDis;

    @Override
    protected int getResId() {
        return R.layout.fragment_value_animator;
    }

    @Override
    protected void initViews(View rootView) {
    }

    private void onClickOfFloat(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1.0f);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画属性值
                float value = (float) animation.getAnimatedValue();
                mTvDis.setScaleX(value);
                mTvDis.setScaleY(value);
            }
        });
        valueAnimator.start();
    }

    private void onClickOfInt(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画属性值
                int value = (int) animation.getAnimatedValue();
                mTvDis.setTranslationX(value);
                mTvDis.setTranslationY(value);
            }
        });
        valueAnimator.start();
    }

    private void onClickOfObject(){
        Point startPoint = new Point(100,100);
        Point endPoint = new Point(500,500);
        PointEvaluator pointEvaluator = new PointEvaluator();
        ValueAnimator valueAnimator = ValueAnimator.ofObject(pointEvaluator,startPoint,endPoint);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画属性值
                Point value = (Point) animation.getAnimatedValue();
                mTvDis.setX(value.x);
                mTvDis.setY(value.y);
            }
        });
        valueAnimator.start();
    }

    private void onClickOfPvhFloat(){
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("scale",0,1.0f);
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolder);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                mTvDis.setScaleX(scale);
                mTvDis.setScaleY(scale);
            }
        });
        valueAnimator.start();
    }

    private void onClickOfPvhInt(){
        PropertyValuesHolder txPvh = PropertyValuesHolder.ofInt("tx",0,100);
        PropertyValuesHolder tyPvh = PropertyValuesHolder.ofInt("ty",0,200);
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(txPvh,tyPvh);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int tx = (int) animation.getAnimatedValue("tx");
                int ty = (int) animation.getAnimatedValue("ty");
                mTvDis.setTranslationX(tx);
                mTvDis.setTranslationY(ty);
            }
        });
        valueAnimator.start();
    }

    private void onClickOfPvhObject(){
        Point startPoint = new Point(100,100);
        Point endPoint = new Point(500,500);
        PointEvaluator pointEvaluator = new PointEvaluator();
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofObject("point",pointEvaluator,startPoint,endPoint);

        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolder);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画属性值
                Point value = (Point) animation.getAnimatedValue();
                mTvDis.setTranslationX(value.x);
                mTvDis.setTranslationY(value.y);
            }
        });
        valueAnimator.start();
    }

    private void onClickKeyframeFloat(){
        Keyframe scale1 = Keyframe.ofFloat(0,0);
        Keyframe scale2 = Keyframe.ofFloat(0.5f,0.5f);
        Keyframe scale3 = Keyframe.ofFloat(1.0f,1.0f);
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("scale",scale1,scale2,scale3);
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolder);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue("scale");
                mTvDis.setScaleX(scale);
                mTvDis.setScaleY(scale);
            }
        });
        valueAnimator.start();
    }

    private void onClickKeyframeInt(){
        Keyframe tx1 = Keyframe.ofInt(0,0);
        Keyframe tx2 = Keyframe.ofInt(0.5f,50);
        Keyframe tx3 = Keyframe.ofInt(1,100);
        PropertyValuesHolder txPvh = PropertyValuesHolder.ofKeyframe("tx",tx1,tx2,tx3);

        Keyframe ty1 = Keyframe.ofInt(0,0);
        Keyframe ty2 = Keyframe.ofInt(0.5f,100);
        Keyframe ty3 = Keyframe.ofInt(1,200);
        PropertyValuesHolder tyPvh = PropertyValuesHolder.ofKeyframe("ty",ty1,ty2,ty3);

        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(txPvh,tyPvh);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int tx = (int) animation.getAnimatedValue("tx");
                int ty = (int) animation.getAnimatedValue("ty");
                mTvDis.setTranslationX(tx);
                mTvDis.setTranslationY(ty);
            }
        });
        valueAnimator.start();
    }

    private void onClickKeyframeObject(){
        Point startPoint = new Point(100,100);
        Point endPoint = new Point(500,500);
        //估值器
        PointEvaluator pointEvaluator = new PointEvaluator();

        Keyframe p1 = Keyframe.ofObject(0,startPoint);
        Keyframe p2 = Keyframe.ofObject(1,endPoint);
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("point",p1,p2);
        //属性值对象是object类型需要手动设置估值器，不然会出现npe
        propertyValuesHolder.setEvaluator(pointEvaluator);

        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolder);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画属性值
                Point value = (Point) animation.getAnimatedValue();
                mTvDis.setTranslationX(value.x);
                mTvDis.setTranslationY(value.y);
            }
        });
        valueAnimator.start();
    }

    @Override
    @OnClick({R.id.btn_of_float,R.id.btn_of_int,R.id.btn_of_object,
            R.id.btn_of_pvh_float, R.id.btn_of_pvh_int,R.id.btn_of_pvh_object,
            R.id.btn_kf_float,R.id.btn_kf_int, R.id.btn_kf_object})
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_of_float){
            onClickOfFloat();
        }else if(id == R.id.btn_of_int){
            onClickOfInt();
        }else if(id == R.id.btn_of_object){
            onClickOfObject();
        }else if(id == R.id.btn_of_pvh_float){
            onClickOfFloat();
        }else if(id == R.id.btn_of_pvh_int){
            onClickOfPvhInt();
        }else if(id == R.id.btn_of_pvh_object){
            onClickOfPvhObject();
        }else if(id == R.id.btn_kf_float){
            onClickKeyframeFloat();
        }else if(id == R.id.btn_kf_int){
            onClickKeyframeInt();
        }else if(id == R.id.btn_kf_object){
            onClickKeyframeObject();
        }
    }
}
