package com.silita.notice.test;

import java.util.Arrays;
import java.util.List;

/**
 * 獲取地址
 */
public class Test4 {
    public static void main(String[] args) {

        //String s = "湖南省||邵阳市,长沙市,岳阳市";
        String s = "湖南省||邵阳市";
        //String s = "湖南省";

   /*     String[] split = s.split("\\|\\|");
       *//* for (String s1 : split) {
            System.out.println("s1:"+s1);
        }*//*
        String pro = split[0].toString();


        System.out.println("pro:"+pro);
        try{
            System.out.println("split[1]="+split[1]);
            String addrs = split[1].toString();
            System.out.println("addrs:"+addrs);

            String[] split1 = addrs.split(",");

            List<String> list =
                    Arrays.asList(split1);
            for (String s1 : list) {
                System.out.println("s1:"+s1);
            }
        }catch (Exception e){
            System.out.println("split[1] = null");
        }

*/

    }
}
