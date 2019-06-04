package com.silita.notice.test;

/**
 *
 */
public class Test3 {

    public static void main(String[] args){

        String string ="综合评估法Ⅰ||综合评估法Ⅱ||固定标价评分法||合理定价抽取法||技术评分最低标价法||合理低价法||经评审最低报价法||百分制综合评分法||其他";

       /* String string = "A1B2C3D4E5F6G7H8";
        int[] num =new int[8];
        String[] num_string = string.split("\\D");
        System.out.println(num_string[0]);
        System.out.println("提取数字后的数组元素为：");
        for(int i=0;i<num_string.length-1;i++){
            num[i] = Integer.parseInt(num_string[i+1]);
            System.out.print(num[i]);
        }*/

        //String string = "A1B2C3D4E5F6G7H8";
        String[] num =new String[80];
        String[] num_string = string.split("\\||");
        //System.out.println(num_string[0]);
        System.out.println("提取数字后的数组元素为：");
        for(int i=0;i<num_string.length-1;i++){
            num[i] = num_string[i+1];
            System.out.print(num[i]);
        }

    }
}
