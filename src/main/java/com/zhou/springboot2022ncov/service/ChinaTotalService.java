package com.zhou.springboot2022ncov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.springboot2022ncov.entity.ChinaTotal;

/**
 * @author zyh
 * @create 2022-07-02 16:38
 */
public interface ChinaTotalService extends IService<ChinaTotal> {

    Integer maxID();

}
