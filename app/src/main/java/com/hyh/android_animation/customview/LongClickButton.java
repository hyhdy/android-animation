package com.hyh.android_animation.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.hyh.base_lib.utils.LongClickHelper;

public class LongClickButton extends android.support.v7.widget.AppCompatButton {
    private LongClickHelper mLongClickHelper;

    public LongClickButton(Context context) {
        this(context,null);
    }

    public LongClickButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLongClickHelper = new LongClickHelper();

    }

    public void setOnClickContinuousCallBack(LongClickHelper.OnClickContinuousCallBack onClickContinuousCallBack) {
        mLongClickHelper.setOnClickContinuousCallBack(onClickContinuousCallBack);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            mLongClickHelper.postMessage();
        } else if(action == MotionEvent.ACTION_UP||action == MotionEvent.ACTION_CANCEL){
            mLongClickHelper.removeMessage();
        }
        return super.onTouchEvent(event);
    }
}
