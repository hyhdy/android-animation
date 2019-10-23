package com.hyh.android_animation.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hyh.android_animation.R;
import com.hyh.android_animation.customview.GoldCoinsAnimView;
import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.hyh.base_lib.utils.SizeUtils;

/**
 * created by curdyhuang on 2019/9/30
 */
@InjectFragment()
public class CoinAnimatorFragment extends BaseFragment {
    @FindViewByIdAno(R.id.fl_root)
    private FrameLayout mFlRoot;
    @FindViewByIdAno(R.id.btn_start)
    private TextView mTvStart;
    @Override
    protected int getResId() {
        return R.layout.fragment_coin_animator;
    }

    @Override
    protected void initViews(View rootView) {
        mTvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final GoldCoinsView goldCoinsView = new GoldCoinsView(getContext());
//                goldCoinsView.setOnAnimListener(new GoldCoinsView.OnAnimListener() {
//                    @Override
//                    public void onAnimStart() {
//
//                    }
//
//                    @Override
//                    public void onAnimEnd() {
//                        mFlRoot.removeView(goldCoinsView);
//                    }
//                });
//                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(SizeUtils.dp2px(300),SizeUtils.dp2px(400));
//                layoutParams.gravity = Gravity.CENTER;
//                mFlRoot.addView(goldCoinsView,layoutParams);
//                mFlRoot.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        goldCoinsView.startAnim();
//                    }
//                });

                final GoldCoinsAnimView goldCoinsView = new GoldCoinsAnimView(getContext());
                goldCoinsView.setOnAnimListener(new GoldCoinsAnimView.OnAnimListener() {
                    @Override
                    public void onAnimStart() {

                    }

                    @Override
                    public void onAnimEnd() {
                        mFlRoot.removeView(goldCoinsView);
                    }
                });
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(SizeUtils.dp2px(300),SizeUtils.dp2px(400));
                layoutParams.gravity = Gravity.CENTER;
                mFlRoot.addView(goldCoinsView,layoutParams);
                mFlRoot.post(new Runnable() {
                    @Override
                    public void run() {
                        goldCoinsView.startAnim();
                    }
                });

            }
        });
    }
}
