package com.silita.notice.test;

import org.apache.commons.collections.MapUtils;

public class Test {
    public static void main(String[] args) {
        //String s = "湖南省||邵阳市,长沙市,岳阳市";
        //String s = "湖南省||邵阳市,长沙市";
        /*String s = "湖南省";

        String[] split = s.split("\\||\\,");


        try {
            for (String s1 : split) {
                System.out.println(s1);
            }
            System.out.println(split[2]);

            System.out.println(split[3]);
            if(null != split[4] && "" != split[4]){
                System.out.println(split[4]);
            }else {
                System.out.println("split[4]== null");
            }
        }catch (Exception e){
            System.out.println("split[4]== null   aaaa");
        }*/


        /*System.out.println(split[2]);

        String ss = split[2].toString();

        System.out.println("ss:"+ss);
        System.out.println("ss:length:"+ss.length());
        System.out.println(split[2].length());
        for (String s1 : split) {
            System.out.println(s1);
        }*/









        //String regions = MapUtils.getString(param, "regions");

        String regions = "湖南省";

        String[] split = regions.split("\\||\\,");
        for (String s : split) {
            System.out.println();
        }
        String province = split[0].toString(); // 获取省
        System.out.println("pro:"+province);
        String city1 = "";
        String city2 = "";
        String city3 = "";
        try{
            if(null != split[2] && "" != split[2]){
                city1=split[2].toString();
            }
        }catch (Exception e1){
            System.out.println("city1 == null ");
        }

        try{
            if (null != split[3] && "" != split[3]){
                city2=split[3].toString();
            }
        }catch (Exception e2){
            System.out.println("city2 == null");
        }

        try{

            if (null != split[4] && "" != split[4]){
                city3=split[4].toString();
            }

        }catch (Exception e){
            System.out.println("city3 == null");

        }

        System.out.println("省："+province+"  市1："+city1+"  市2："+city2+"  市3："+city3);

    }
}
