package com.hyh.android_animation.fragment;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.hyh.android_animation.R;
import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;

@InjectFragment()
public class ViewAnimationFragment extends BaseFragment {
    @FindViewByIdAno(R.id.tv_dis)
    private TextView mTvDis;
    @FindViewByIdAno(R.id.btn_start_anim)
    private Button mBtnStart;

    @Override
    protected int getResId() {
        return R.layout.fragment_view_animation;
    }

    @Override
    protected void initViews(View rootView) {
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xml加载动画
//                Animation animation = AnimationUtils
//                        .loadAnimation(getContext(), R.anim.scale_animation);
                //mTvDis.startAnimation(animation);

                //java代码加载动画
                Animation animation = new ScaleAnimation(0,1,0,1,Animation.ABSOLUTE,50,Animation.ABSOLUTE,50);
                animation.setDuration(500);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        Log.d("hyh","ViewAnimationFragment: onAnimationStart: ");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.d("hyh","ViewAnimationFragment: onAnimationEnd: ");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        Log.d("hyh","ViewAnimationFragment: onAnimationRepeat: ");
                    }
                });
                mTvDis.startAnimation(animation);
            }
        });
    }
}
