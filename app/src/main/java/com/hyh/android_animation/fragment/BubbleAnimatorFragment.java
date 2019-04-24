package com.hyh.android_animation.fragment;

import android.view.View;
import android.widget.Button;

import com.hyh.android_animation.R;
import com.hyh.android_animation.customview.BubbleView;
import com.hyh.android_animation.customview.ExplodeView;
import com.hyh.android_animation.customview.LongClickButton;
import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;

@InjectFragment()
public class BubbleAnimatorFragment extends BaseFragment {
    @FindViewByIdAno(R.id.bubble_view)
    private BubbleView mBubbleView;
    @FindViewByIdAno(R.id.btn_start)
    private Button mBtnStart;
    @Override
    protected int getResId() {
        return R.layout.fragment_bubble_animator;
    }

    @Override
    protected void initViews(View rootView) {
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBubbleView.startAnim();
            }
        });
    }
}
