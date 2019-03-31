package com.xuyp.happychat.util.exception.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class DictConditionDis {
    private static final String DEFAULT = "";
    @Autowired
    DictCondition dictCondition;

    public  String getConditionStr(String key,String condition) {
        if (!StringUtils.isEmpty(condition)) {
            if (condition.contains(Dict.NOT_NULL.getKey())) {
                return Dict.NOT_NULL.getInfo(dictCondition.get(key));
            } else if (condition.contains(Dict.MAX.getKey())) {
                return Dict.MAX.getInfo(dictCondition.get(key), getCondition(condition));
            } else if (condition.contains(Dict.MIN.getKey())) {
                return Dict.MIN.getInfo(dictCondition.get(key), getCondition(condition));
            }
        }



        return DEFAULT;
    }

    private String getCondition(String condition) {
        if (!StringUtils.isEmpty(condition)) {
            String[] conditions = condition.split(":");
            return conditions[1];
        }
        return "";
    }

}
