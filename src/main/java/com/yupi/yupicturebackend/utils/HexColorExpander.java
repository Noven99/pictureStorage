package com.yupi.yupicturebackend.utils;

public class HexColorExpander {

    public static String expandHexColor(String compressed) {
        if (compressed == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }

        String input = compressed.trim()
                .replaceFirst("^#", "")     // 去掉#前缀
                .replaceFirst("^0x", "");   // 去掉0x前缀

        if (input.length() == 3) {
            // 检查合法性
            if (!input.matches("[0-9a-fA-F]{3}")) {
                throw new IllegalArgumentException("Invalid color format");
            }
            // #abc -> #aabbcc
            StringBuilder expanded = new StringBuilder();
            for (char c : input.toCharArray()) {
                expanded.append(c).append(c);
            }
            return "#" + expanded.toString();
        } else if (input.length() == 6) {
            if (!input.matches("[0-9a-fA-F]{6}")) {
                throw new IllegalArgumentException("Invalid color format");
            }
            return "#" + input.toLowerCase();
        } else {
            throw new IllegalArgumentException("Invalid color format");
        }
    }
}