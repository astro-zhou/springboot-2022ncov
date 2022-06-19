package com.zhou.springboot2022ncov.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.springboot2022ncov.dao.IndexMapper;
import com.zhou.springboot2022ncov.entity.LineTrend;
import com.zhou.springboot2022ncov.entity.NcovData;
import com.zhou.springboot2022ncov.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zyh
 * @create 2022-06-16 20:44
 */
@Service
public class IndexServiceImpl extends ServiceImpl<IndexMapper, NcovData> implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Override
    public List<LineTrend> findSevenData() {
        List<LineTrend> list = indexMapper.findSevenData();
        return list;
    }
}
