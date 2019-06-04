package com.silita.notice.test;

import java.util.ArrayList;
import java.util.List;

public class TestzzType {

    public static void main(String[] args) {
        String zzType="A/-1,B/-2,B/-3";


        String[] split = zzType.split("\\,");
        for (String s : split) {
            System.out.println(s);
        }
        String zz1 = split[0].toString();
        String zz2 = split[1].toString();
        String zz3 = split[2].toString();



        //资质 1
        String[] spdj = zz1.split("\\/");


        String zzdj1 = spdj[0].toString();
        String zzdj2 = spdj[1].toString();


        // 资质 2
        String[] zz2dj = zz2.split("\\/");

        String zz2dj1 = spdj[0].toString();
        String zz2dj2 = spdj[1].toString();


        // 资质 3
        String[] zz3dj = zz3.split("\\/");

        String zz3dj1 = spdj[0].toString();
        String zz3dj2 = spdj[1].toString();


        System.out.println("zzdj1: "+zzdj1+"   zzdj2: "+zzdj2);


        System.out.println("zz2dj1:"+zz2dj1+"   zz2dj2:"+zz2dj2);


        System.out.println("zz3dj1:"+zz3dj1+"   zz3dj2:"+zz3dj2);









       /* String[] split = zzType.split("\\,");
        for (String s : split) {
            System.out.println(s);
        }

        String s = split.toString();

        String[] split1 = s.split("\\,");
        for (String s1 : split1) {
            System.out.println(s1);
        }*/
    }
}
