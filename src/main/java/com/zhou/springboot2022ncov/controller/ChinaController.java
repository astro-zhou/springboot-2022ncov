package com.zhou.springboot2022ncov.controller;

import com.zhou.springboot2022ncov.service.IndexService;
import com.zhou.springboot2022ncov.vo.DataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 删除数据 根据id
 *
 * @author zyh
 * @create 2022-06-19 20:41
 */
@Controller
@RequestMapping("/china")
public class ChinaController {

    @Autowired
    private IndexService indexService;

    @RequestMapping("/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        indexService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除中国地图数据成功!");

        return dataView;
    }
}
