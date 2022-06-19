package com.zhou.springboot2022ncov.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zyh
 * @create 2022-06-17 11:08
 */
@Data
@TableName("line_trend")
public class LineTrend {

    private Integer id;
    private Integer confirm;
    private Integer isolation;
    private Integer cure;
    private Integer dead;
    private Integer similar;
    private Date createTime;

}
