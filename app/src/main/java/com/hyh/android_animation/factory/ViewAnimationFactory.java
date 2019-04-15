package com.hyh.android_animation.factory;

import com.hyh.android_animation.fragment.ViewAnimationFragment;
import com.hyh.base_lib.factory.BaseFragmentFactory;
import com.hyh.base_lib.fragment.BaseFragment;

public class ViewAnimationFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new ViewAnimationFragment();
    }
}
