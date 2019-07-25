package com.silita.notice.common;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class IsNullCommon {
    public static void isNull(Map<String, Object> param) {
        if (StringUtils.isEmpty(MapUtils.getString(param, "title"))) {
            param.put("title", "");
        }
        if (StringUtils.isEmpty(MapUtils.getString(param, "projectType"))) {
            param.put("projectType", "");
        }
        if (StringUtils.isEmpty(MapUtils.getString(param, "comName"))) {
            param.put("comName", "");
        }
        if (StringUtils.isEmpty(MapUtils.getString(param, "rangeType"))) {
            param.put("rangeType", "");
        }
        if (StringUtils.isEmpty(MapUtils.getString(param, "pbModes"))) {
            param.put("pbModes", "");
        }
        if (StringUtils.isEmpty(MapUtils.getString(param, "zzType"))) {
            param.put("zzType", "");
        }
        if (StringUtils.isEmpty(MapUtils.getString(param, "type"))) {
            param.put("type", "");
        }
        if (StringUtils.isEmpty(MapUtils.getString(param, "projSumStart"))) {
            param.put("projSumStart", "");
        }
        if (StringUtils.isEmpty(MapUtils.getString(param, "projSumEnd"))) {
            param.put("projSumEnd", "");
        }
        if (StringUtils.isEmpty(MapUtils.getString(param, "isVip"))) {
            param.put("isVip", "0");
        }

    }
}
