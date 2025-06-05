package com.yupi.yupicturebackend.utils;

public class HexColorExpander {

    public static String expandHexColor(String compressed) {
        // 去除可能存在的0x前缀
        String input = compressed.startsWith("0x") ? compressed.substring(2) : compressed;
        int length = input.length();
        
        // 检查输入长度
        if (length != 3 && length != 6) {
            throw new IllegalArgumentException("Invalid color format");
        }
        
        StringBuilder expanded = new StringBuilder();

        // 处理三个颜色分量
        for (int i = 0; i < 3; i++) {
            char current = input.charAt(i);
            if (length == 3) {
                // 如果长度为3，重复当前字符两次
                expanded.append(current).append(current);
            } else {
                // 如果长度为6，直接复制两个字符
                expanded.append(input.charAt(i * 2)).append(input.charAt(i * 2 + 1));
            }
        }
        return "0x" + expanded.toString();
    }
}