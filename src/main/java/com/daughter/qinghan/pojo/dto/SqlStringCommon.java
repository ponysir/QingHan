package com.daughter.qinghan.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author: wenji.song
 * @Date: 2021/1/11 10:22
 * @Version 1.0
 */
@Data
public class SqlStringCommon implements Serializable {
    private String encryptedData;

    private String sign;

    private String sql;

    private Integer status;

    public SqlStringCommon(String encryptedData, String sign, String sql) {
        this.encryptedData = encryptedData;
        this.sign = sign;
        this.sql = sql;
    }

    public SqlStringCommon() {
    }


    public static void main(String[] args) {
        SqlStringCommon selectSql = new SqlStringCommon("mQ7Ea6S8QkmvJKOictZ6wA==", "22", "");
        System.out.println("11");
    }
}
