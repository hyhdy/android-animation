package com.hyh.android_animation;

import com.hyh.android_animation.factory.ViewAnimationFactory;

import java.util.ArrayList;
import java.util.List;

public class FragmentRepotity {
    public static List<Class> sDataList = new ArrayList<>();
    static {
        sDataList.add(ViewAnimationFactory.class);
    }
}
