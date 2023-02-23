package com.atguigu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhijiang.zhao
 * @date: 2023/2/23 8:45
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private String sex;
}
