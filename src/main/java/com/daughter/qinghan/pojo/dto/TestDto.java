package com.daughter.qinghan.pojo.dto;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhangkezhen
 * @email 528525313@qq.com
 * @time 2021/8/13 17:50
 */
@Data
@AllArgsConstructor
public class TestDto {
    private Integer age;
    private String name;
    private String sex;
    public static void main(String[] args) {
        ArrayList<TestDto> testDtos = Lists.newArrayList(
                new TestDto(1, "张飞", "男"),
                new TestDto(2, "赵云", "男"),
                new TestDto(5, "孙二娘", "女"),
                new TestDto(6, "武松", "男"),
                new TestDto(3, "孙尚香", "女"),
                new TestDto(4, "刘备", "男"),
                new TestDto(8, "孙权", "男")
        );
        //通过主键去分组
        Map<Integer, TestDto> map = testDtos.stream().collect(Collectors.toMap(TestDto::getAge, Function.identity()));

        //通过性别分组
        Map<String, List<TestDto>> collect = testDtos.stream().collect(Collectors.groupingBy(TestDto::getSex));
        System.out.println(map.get(1));
        System.out.println(collect);
    }

}
