package com.hyh.android_animation.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyh.android_animation.R;
import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.hyh.base_lib.annotation.OnClick;

@InjectFragment()
public class AnimatorFragment extends BaseFragment implements View.OnClickListener {
    @FindViewByIdAno(R.id.tv_dis)
    private TextView mTvDis;
    @FindViewByIdAno(R.id.btn_object_animator)
    private Button mBtnObject;
    @FindViewByIdAno(R.id.btn_value_animator)
    private Button mBtnValue;

    @Override
    protected int getResId() {
        return R.layout.fragment_object_animator;
    }

    @Override
    protected void initViews(View rootView) {
    }

    private void onClickObjectAnimator(){
        //设置控件缩放的锚点（动画起始点）
        mTvDis.setPivotX(50);
        mTvDis.setPivotY(50);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mTvDis,"ScaleX",0,1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mTvDis,"ScaleY",0,1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(scaleXAnimator,scaleYAnimator);
        animatorSet.start();

//        //优化，上述代码实现需要创建两个ObjectAnimator才能实现控件同时对x和y轴缩放，其实一个动画就能实现的。
//        PropertyValuesHolder xValue = PropertyValuesHolder.ofFloat("ScaleX",0,1.0f);
//        PropertyValuesHolder yValue = PropertyValuesHolder.ofFloat("ScaleY",0,1.0f);
//        //只创建一个属性动画，但是对两个属性进行改变
//        ValueAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(xValue,yValue);
//        objectAnimator.setDuration(500);
//        objectAnimator.start();
    }

    private void onClickValueAnimator(){
        //设置属性动画每帧绘制的间隔时间，这个方法在某些机型可能无效
        ValueAnimator.setFrameDelay(500);
        //设置控件缩放的锚点（动画起始点）
        mTvDis.setPivotX(50);
        mTvDis.setPivotY(50);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,0.1f,0.11f,0.12f,0.13f,1.0f);
        valueAnimator.setDuration(500);
        //重复次数
        valueAnimator.setRepeatCount(0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                Log.d("hyh","AnimatorFragment: onAnimationUpdate: fraction="+fraction);
                float rate = (float) animation.getAnimatedValue();
                mTvDis.setScaleX(rate);
                mTvDis.setScaleY(rate);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("hyh","AnimatorFragment: onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("hyh","AnimatorFragment: onAnimationEnd: ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("hyh","AnimatorFragment: onAnimationCancel: ");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("hyh","AnimatorFragment: onAnimationRepeat: ");
            }
        });
        valueAnimator.start();
    }

    @Override
    @OnClick({R.id.btn_object_animator,R.id.btn_value_animator})
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_object_animator){
            onClickObjectAnimator();
        }else if(id == R.id.btn_value_animator){
            onClickValueAnimator();
        }
    }
}
