package com.hyh.android_animation.fragment;

import android.view.View;
import android.widget.Button;

import com.hyh.android_animation.R;
import com.hyh.android_animation.customview.ExplodeView;
import com.hyh.android_animation.customview.LongClickButton;
import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.hyh.base_lib.annotation.OnClick;
import com.hyh.base_lib.utils.LongClickHelper;

@InjectFragment()
public class ExplodeAnimatorFragment extends BaseFragment {
    @FindViewByIdAno(R.id.explode_view)
    private ExplodeView mExplodeView;
    @FindViewByIdAno(R.id.btn_start)
    private LongClickButton mBtnStart;

    @Override
    protected int getResId() {
        return R.layout.fragment_explode_animator;
    }

    @Override
    protected void initViews(View rootView) {
        mBtnStart.setOnClickContinuousCallBack(new LongClickHelper.OnClickContinuousCallBack() {
            @Override
            public void onClickContinuous() {
                mExplodeView.startAnim();
            }
        });
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExplodeView.startAnim();
            }
        });
    }
}
