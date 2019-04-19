package com.hyh.android_animation.fragment;

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
    @FindViewByIdAno(R.id.btn_of_pvh)
    private Button mBtnOfPvh;
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
                mTvDis.setTranslationX(value.x);
                mTvDis.setTranslationY(value.y);
            }
        });
        valueAnimator.start();
    }

    private void onClickOfPvh(){

    }

    @Override
    @OnClick({R.id.btn_of_float,R.id.btn_of_int,R.id.btn_of_object,R.id.btn_of_pvh})
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_of_float){
            onClickOfFloat();
        }else if(id == R.id.btn_of_int){
            onClickOfInt();
        }else if(id == R.id.btn_of_object){
            onClickOfObject();
        }else if(id == R.id.btn_of_pvh){
            onClickOfPvh();
        }
    }
}
