package com.zhou.springboot2022ncov.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.springboot2022ncov.entity.ChinaTotal;
import org.apache.ibatis.annotations.Select;

/**
 * @author zyh
 * @create 2022-07-02 16:38
 */
public interface ChinaTotalMapper extends BaseMapper<ChinaTotal> {

    @Select("select  max(id) from china_total")
    Integer maxID();
}
