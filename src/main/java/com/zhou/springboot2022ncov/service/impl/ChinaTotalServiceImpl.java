package com.zhou.springboot2022ncov.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.springboot2022ncov.dao.ChinaTotalMapper;
import com.zhou.springboot2022ncov.entity.ChinaTotal;
import com.zhou.springboot2022ncov.service.ChinaTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zyh
 * @create 2022-07-02 16:39
 */
@Service
public class ChinaTotalServiceImpl extends ServiceImpl<ChinaTotalMapper, ChinaTotal> implements ChinaTotalService {

    @Autowired
    private ChinaTotalMapper chinaTotalMapper;


    @Override
    public Integer maxID() {
        return chinaTotalMapper.maxID();
    }
}
