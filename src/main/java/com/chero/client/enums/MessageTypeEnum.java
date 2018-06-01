package com.chero.client.enums;

import lombok.Getter;

/**
 * Created by hxh on 2018/5/31.
 */
@Getter
public enum MessageTypeEnum {
    NOTIFICATION(0, "通知"),
    ANNOUNCEMENT(1, "公告"),
            ;

    private Integer code;

    private String message;

    MessageTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public static String getValue(Integer state) {
        for (MessageTypeEnum myEnum : MessageTypeEnum.values()) {
            if (myEnum.getCode().equals(state)) {
                return myEnum.getMessage();
            }
        }
        return "错误";
    }
}
