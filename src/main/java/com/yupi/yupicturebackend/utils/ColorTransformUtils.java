package com.yupi.yupicturebackend.utils;

import java.awt.*;

/**
 * 颜色转换工具类
 */
public class ColorTransformUtils {

    private ColorTransformUtils() {
        // 工具类不需要实例化
    }

    /**
     * 获取标准颜色（将数据万象的 5 位色值转为 6 位）
     *
     * @param color
     * @return
     */
    public static String getStandardColor(String color) {
        if (color == null || color.isEmpty()) {
            return "#000000"; // 默认颜色
        }
        color = color.trim().toLowerCase().replace("0x", "").replace("#", "");

        if (color.length() == 5) {
            // 补零为 6 位：在最后补充一个 0
            color = color + "0";
        } else if (color.length() == 3) {
            // 如果是 3 位颜色值，扩展为 6 位
            color = "" + color.charAt(0) + color.charAt(0)
                    + color.charAt(1) + color.charAt(1)
                    + color.charAt(2) + color.charAt(2);
        } else if (color.length() != 6) {
            throw new IllegalArgumentException("Invalid color format: " + color);
        }

        return "#" + color;
    }

    public static String toCssColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            return "#000000";
        }
        color = color.trim().toLowerCase();

        // 去掉0x、#前缀
        if (color.startsWith("0x")) {
            color = color.substring(2);
        } else if (color.startsWith("#")) {
            color = color.substring(1);
        }

        color = color.replaceAll("[^0-9a-f]", "");

        // 3位简写转6位
        if (color.length() == 3) {
            color = "" + color.charAt(0) + color.charAt(0)
                    + color.charAt(1) + color.charAt(1)
                    + color.charAt(2) + color.charAt(2);
        }

        // 不足6位左补0，超6位截断
        if (color.length() < 6) {
            color = "000000".substring(0, 6 - color.length()) + color;
        } else if (color.length() > 6) {
            color = color.substring(0, 6);
        }

        return "#" + color;
    }
}
