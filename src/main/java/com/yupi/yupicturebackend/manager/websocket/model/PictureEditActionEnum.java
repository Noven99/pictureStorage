package com.yupi.yupicturebackend.manager.websocket.model;

import lombok.Getter;

@Getter
public enum PictureEditActionEnum {

    ZOOM_IN("Zoom in operation", "ZOOM_IN"),
    ZOOM_OUT("Zoom out operation", "ZOOM_OUT"),
    ROTATE_LEFT("Left rotation operation", "ROTATE_LEFT"),
    ROTATE_RIGHT("Right rotation operation", "ROTATE_RIGHT");

    private final String text;
    private final String value;

    PictureEditActionEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     */
    public static PictureEditActionEnum getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (PictureEditActionEnum actionEnum : PictureEditActionEnum.values()) {
            if (actionEnum.value.equals(value)) {
                return actionEnum;
            }
        }
        return null;
    }
}
