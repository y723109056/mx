package com.mx.util;

import java.util.Random;

/**
 * Created by zhusw on 2017/6/22.
 */
public class RandomUtil {

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param size 随机数个数
     */
    public static int[] randomCommon(int size,int n){
        if(size>0) {
            int[] result = new int[n];
            for(int i=0;i<n;i++)        //初始为-1;
                result[i] = -1;
            int count = 0;
            while (count < n) {
                int num = new Random().nextInt(size);
                boolean flag = true;
                for (int j = 0; j < n; j++) {
                    if (num == result[j]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    result[count] = num;
                    count++;
                }
            }
            return result;
        }
        return new int[0];
    }

}
