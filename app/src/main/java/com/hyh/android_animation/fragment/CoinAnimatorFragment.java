package com.hyh.android_animation.fragment;

import android.view.View;
import android.widget.TextView;

import com.hyh.android_animation.R;
import com.hyh.android_animation.customview.GoldCoinsView;
import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;

/**
 * created by curdyhuang on 2019/9/30
 */
@InjectFragment()
public class CoinAnimatorFragment extends BaseFragment {
    @FindViewByIdAno(R.id.btn_start)
    private TextView mTvStart;
    @FindViewByIdAno(R.id.gold_view)
    private GoldCoinsView mGoldCoinsView;

    @Override
    protected int getResId() {
        return R.layout.fragment_coin_animator;
    }

    @Override
    protected void initViews(View rootView) {
        mTvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoldCoinsView.startAnim();
            }
        });
    }
}
