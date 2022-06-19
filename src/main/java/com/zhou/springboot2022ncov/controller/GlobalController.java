package com.zhou.springboot2022ncov.controller;

import com.zhou.springboot2022ncov.entity.NcovData;
import com.zhou.springboot2022ncov.entity.NcovGlobalData;
import com.zhou.springboot2022ncov.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zyh
 * @create 2022-06-18 16:35
 */
@Controller
public class GlobalController {

    @Autowired
    private GlobalService globalService;

    /*跳转页面*/
    @RequestMapping("/toGlobal")
    public String toGlobal(){
        return "global";
    }

    @RequestMapping("/queryGlobal")
    @ResponseBody
    public List<NcovGlobalData> queryGlobal(){
        List<NcovGlobalData> list = globalService.list();
        return list;
    }
}
