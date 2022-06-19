package com.zhou.springboot2022ncov.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zyh
 * @create 2022-06-16 20:47
 */
@Data
@TableName("ncov_data")
public class NcovData {
    private Integer id;
    private String name;
    private Integer value;
}
