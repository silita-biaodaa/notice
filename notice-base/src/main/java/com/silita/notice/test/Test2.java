package com.silita.notice.test;

import java.util.Arrays;
import java.util.List;

/**
 * 获取评估法
 */
public class Test2 {

    public static void main(String[] args) {
        String pbModes ="综合评估法Ⅰ||综合评估法Ⅱ||固定标价评分法||合理定价抽取法||技术评分最低标价法||合理低价法||经评审最低报价法||百分制综合评分法||其他";



        String[] split = pbModes.split("\\|\\|");



        List<String> list = Arrays.asList(split);
        for (String ss : split) {
            System.out.println("ss::::::"+ss);
        }

       /* System.out.println("split0:"+split[0]);
        System.out.println("split1:"+split[1]);
        System.out.println("split2:"+split[2]);
        System.out.println("split3:"+split[3]);
        System.out.println("split4:"+split[4]);

        for (String s : split) {
            System.out.println(s);
        }*/

        //a1  a2
    }


}

