package com.daughter.qinghan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;


@AllArgsConstructor
@Getter
public enum IsOrNoEnum {
    NO(0,"否"),
    YES(1,"是");
    private Integer code;
    private String message;

    public static String getMessage(Integer code){
        if (Objects.isNull(code)){
            return "未知";
        }
        for (IsOrNoEnum value : IsOrNoEnum.values()) {
            if (value.code==code){
                return value.message;
            }
        }
        return "未知";
    }
}
