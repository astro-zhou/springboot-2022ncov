package com.zhou.springboot2022ncov.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zyh
 * @create 2022-06-16 20:47
 */
@Data
@TableName("ncov_data")
public class NcovData {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer value;
}
