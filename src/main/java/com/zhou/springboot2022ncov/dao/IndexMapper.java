package com.zhou.springboot2022ncov.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.springboot2022ncov.entity.LineTrend;
import com.zhou.springboot2022ncov.entity.NcovData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zyh
 * @create 2022-06-16 20:44
 */
public interface IndexMapper extends BaseMapper<NcovData> {
    /**
     * 接口: 只有方法定义,写业务逻辑
     * 1. 实现类,写你自己的方法
     * 2. XML  mybatisplus 一种实现
     * 3. @select
     *
     */
    @Select("select * from line_trend order by create_time desc limit 7")
    List<LineTrend> findSevenData();
}
