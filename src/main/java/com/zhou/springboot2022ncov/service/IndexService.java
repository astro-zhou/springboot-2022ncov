package com.zhou.springboot2022ncov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.springboot2022ncov.entity.LineTrend;
import com.zhou.springboot2022ncov.entity.NcovData;

import java.util.List;

/**
 * @author zyh
 * @create 2022-06-16 20:43
 */
public interface IndexService extends IService<NcovData> {
    List<LineTrend> findSevenData();
}
