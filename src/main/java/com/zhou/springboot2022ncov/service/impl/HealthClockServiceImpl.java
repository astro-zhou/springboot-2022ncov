package com.zhou.springboot2022ncov.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.springboot2022ncov.entity.HealthClock;
import com.zhou.springboot2022ncov.dao.HealthClockMapper;
import com.zhou.springboot2022ncov.service.HealthClockService;
import org.springframework.stereotype.Service;

/**
 * @author zyh
 * @create 2022-06-23 09:54
 */
@Service
public class HealthClockServiceImpl extends ServiceImpl<HealthClockMapper, HealthClock> implements HealthClockService {
}
