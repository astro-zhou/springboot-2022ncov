package com.zhou.springboot2022ncov.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zyh
 * @create 2022-06-18 16:12
 */
@Data
@TableName("ncov_global_data")
public class NcovGlobalData {
    //    private Integer id;
    private String name;
    private Integer value;
}
