package com.tibafit.util;

public class ValidationUtil {
    /**
     * 验证姓名格式
     * @param name 待验证的姓名
     * @return 验证通过返回null，否则返回错误信息
     */
    public static String validateName(String name) {
        // 非空验证（如果需要强制必填，可在此处添加）
        if (name == null || name.trim().isEmpty()) {
            return null; // 允许空值，由业务逻辑决定是否必填
        }
        
        // 长度验证
        if (name.length() < 2 || name.length() > 20) {
            return "姓名长度必须介于 2 到 20 个字符之间";
        }
        
        // 字符类型验证（中英文，去掉数字允许）
        String nameRegex = "^[a-zA-Z\u4e00-\u9fa5]+$";
        if (!name.matches(nameRegex)) {
            return "姓名只能包含中英文";
        }
        
        return null; // 验证通过
    }
}

