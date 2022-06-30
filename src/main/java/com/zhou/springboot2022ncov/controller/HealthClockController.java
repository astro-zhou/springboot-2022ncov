package com.zhou.springboot2022ncov.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.springboot2022ncov.entity.HealthClock;
import com.zhou.springboot2022ncov.vo.HealthClockVo;
import com.zhou.springboot2022ncov.service.HealthClockService;
import com.zhou.springboot2022ncov.vo.DataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zyh
 * @create 2022-06-23 09:45
 */
@Controller
public class HealthClockController {

    @Autowired
    private HealthClockService healthClockService;

    //跳转页面
    @RequestMapping("/toHealthClock")
    public String toHealthClock() {
        return "admin/healthclock";
    }

    //查询所有的打卡记录 带有模糊查询条件 带有分页
    @RequestMapping("/listHealthClock")
    @ResponseBody
    public DataView listHealthClock(HealthClockVo healthClockVo) {
        //查询所有 带有模糊查询条件 带有分页
        IPage<HealthClock> page = new Page<>(healthClockVo.getPage(), healthClockVo.getLimit());
        QueryWrapper<HealthClock> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(healthClockVo.getUsername() != null, "username", healthClockVo.getUsername());
        queryWrapper.eq(healthClockVo.getPhone() != null, "phone", healthClockVo.getPhone());
        healthClockService.page(page, queryWrapper);

        return new DataView(page.getTotal(), page.getRecords());

    }
}
