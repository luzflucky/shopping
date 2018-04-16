package com.maomi.util;

import java.util.Map;

public class PageServiceUtil {
    public PageServiceUtil() {
    }

    public static void checkPageParams(Map params) {
        if(params != null && !params.isEmpty()) {
            if((!params.containsKey("pageNum") || !params.containsKey("pageSize")) && (!params.containsKey("offset") || !params.containsKey("limit"))) {
                throw new IllegalArgumentException("分页参数不正确");
            } else {
                if(!params.containsKey("pageNum") || !params.containsKey("pageSize")) {
                    int offset = Integer.parseInt(params.get("offset").toString());
                    int limit = Integer.parseInt(params.get("limit").toString());
                    int pageNum = offset / limit + 1;
                    params.put("pageNum", Integer.valueOf(pageNum));
                    params.put("pageSize", Integer.valueOf(limit));
                }

            }
        } else {
            throw new IllegalArgumentException("分页参数不正确");
        }
    }
}