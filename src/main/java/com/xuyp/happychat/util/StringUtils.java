package com.xuyp.happychat.util;


public class StringUtils {

    /**
     * 批量判断是否为空
     * @param strings
     * @return
     */
    public static boolean isNotBlank(String... strings) {

        for (String string : strings) {
            if (org.apache.commons.lang3.StringUtils.isBlank(string)) {
                return false;
            }
        }
        return true;
    }

}
