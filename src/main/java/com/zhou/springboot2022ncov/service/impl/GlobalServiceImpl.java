package com.zhou.springboot2022ncov.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.springboot2022ncov.dao.GlobalDataMapper;
import com.zhou.springboot2022ncov.entity.NcovGlobalData;
import com.zhou.springboot2022ncov.service.GlobalService;
import org.springframework.stereotype.Service;

/**
 * @author zyh
 * @create 2022-06-18 16:31
 */
@Service
public class GlobalServiceImpl extends ServiceImpl<GlobalDataMapper, NcovGlobalData> implements GlobalService{
}
