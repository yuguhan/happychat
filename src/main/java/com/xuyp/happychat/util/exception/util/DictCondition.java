package com.xuyp.happychat.util.exception.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DictCondition implements InitializingBean {

    private Map<String, String> condition = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    private void init(){
        condition.put("userName", "用户名");
        condition.put("password", "密码");
        condition.put("cId", "手机唯一标识符");
        condition.put("userId", "用户id");
        condition.put("nickName", "昵称");

    }

    public String get(String key) {
        String v = condition.get(key);
        return v == null ? "" : v;
    }

}
