package com.hyh.base_lib.utils;

import java.util.Random;

public class TransformUtils {
    /**
     * 获取指定区间的随机数（整数）
     * @param start
     * @param end
     * @return
     */
    public static int getRangeRandomInt(int start, int end) {
        if (start >= end) {
            return 1;
        }
        Random random = new Random();
        int result = random.nextInt(end - start) + start + 1;
        return result;
    }

    /**
     * 获取指定区间的随机数（可以是小数）
     * @param start
     * @param end
     * @return
     */
    public static float getRangeRandomFloat(int start, int end) {
        if (start >= end) {
            return start;
        }
        Random random = new Random();
        int result = random.nextInt(end - start) + start;
        float resultFloat = random.nextFloat()+result;
        return resultFloat;
    }

    /**
     * 获取指定区间的随机数（可以是小数）
     * @param start
     * @param end
     * @return
     */
    public static float getRangeRandomFloat(float start, float end) {
        if (start >= end) {
            return 0;
        }
        Random random = new Random();
        float resultFloat = random.nextFloat()+start;
        return resultFloat;
    }
}
