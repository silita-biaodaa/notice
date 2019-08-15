package com.silita.notice.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RangeCommon {
    // 丁级及以上 ：grade_dj_1553245789183,grade_bj_1553245789155,grade_yj_1553245789137,grade_jj_1553245789116

// 丙级及以上 :	grade_bj_1553245789155,grade_yj_1553245789137,grade_jj_1553245789116

// 乙级及以上 ：grade_yj_1553245789137,grade_jj_1553245789116




// 五级及以上 ：grade_wj_1553476160663,grade_sj_1553476160640,grade_sj_1553245789236,grade_ej_1553245789219,grade_yj_1553245789202,grade_tj_1553497710814

// 四级及以上 ：grade_sj_1553476160640,grade_sj_1553245789236,grade_ej_1553245789219,grade_yj_1553245789202,grade_tj_1553497710814

// 三级及以上 ：grade_sj_1553245789236,grade_ej_1553245789219,grade_yj_1553245789202,grade_tj_1553497710814

// 二级及以上 ：grade_ej_1553245789219,grade_yj_1553245789202,grade_tj_1553497710814

// 一级及以上 ：grade_yj_1553245789202,grade_tj_1553497710814

    public static Map<String,Object> rangeCode = new HashMap<>();

    static {
        rangeCode.put("丁级及以上","grade_dj_1553245789183,grade_bj_1553245789155,grade_yj_1553245789137,grade_jj_1553245789116");
        rangeCode.put("丙级及以上","grade_bj_1553245789155,grade_yj_1553245789137,grade_jj_1553245789116");
        rangeCode.put("乙级及以上","grade_yj_1553245789137,grade_jj_1553245789116");
        rangeCode.put("丁级","grade_dj_1553245789183");
        rangeCode.put("丙级","grade_bj_1553245789155");
        rangeCode.put("乙级","grade_yj_1553245789137");
        rangeCode.put("甲级","grade_jj_1553245789116");
        rangeCode.put("五级及以上","grade_wj_1553476160663,grade_sj_1553476160640,grade_sj_1553245789236,grade_ej_1553245789219,grade_yj_1553245789202,grade_tj_1553497710814");
        rangeCode.put("四级及以上","grade_sj_1553476160640,grade_sj_1553245789236,grade_ej_1553245789219,grade_yj_1553245789202,grade_tj_1553497710814");
        rangeCode.put("三级及以上","grade_sj_1553245789236,grade_ej_1553245789219,grade_yj_1553245789202,grade_tj_1553497710814");
        rangeCode.put("二级及以上","grade_ej_1553245789219,grade_yj_1553245789202,grade_tj_1553497710814");
        rangeCode.put("一级及以上","grade_yj_1553245789202,grade_tj_1553497710814");
        rangeCode.put("五级","grade_wj_1553476160663");
        rangeCode.put("四级","grade_sj_1553476160640");
        rangeCode.put("三级","grade_sj_1553245789236");
        rangeCode.put("二级","grade_ej_1553245789219");
        rangeCode.put("一级","grade_yj_1553245789202");
        rangeCode.put("特级","grade_tj_1553497710814");

    }
}
