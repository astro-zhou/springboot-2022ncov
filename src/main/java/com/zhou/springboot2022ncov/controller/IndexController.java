package com.zhou.springboot2022ncov.controller;

import com.zhou.springboot2022ncov.entity.LineTrend;
import com.zhou.springboot2022ncov.entity.NcovData;
import com.zhou.springboot2022ncov.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyh
 * @create 2022-06-16 20:43
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping("/query")
    @ResponseBody
    public List<NcovData> queryData(){
        List<NcovData> list = indexService.list();
        return list;
    }

    /**
     * 分组聚合
     * SQL: select count(*) from goods group by type;
     */
    @RequestMapping("/queryPie")
    @ResponseBody
    public List<NcovData> queryPieData(){
        List<NcovData> list = indexService.list();
        return list;
    }

    //跳转柱状图页面
    @RequestMapping("/toBar")
    public String toBar(){
        return "bar";
    }

    @RequestMapping("/queryBar")
    @ResponseBody
    public Map<String,List<Object>> queryBarData(){
        //1. 所有城市数据:数值
        List<NcovData> list = indexService.list();
        //2. 所有的城市数据
        List<String> cityList = new ArrayList<>();
        for (NcovData data : list){
            cityList.add(data.getName());
        }
        //3. 所有疫情数值数据
        List<Integer> dataList = new ArrayList<>();
        for (NcovData data : list){
            dataList.add(data.getValue());
        }
        //4.创建 Map
        Map map = new HashMap();
        map.put("cityList",cityList);
        map.put("dataList",dataList);

        return map;
    }

    //跳转 pie 页面
    @RequestMapping("/toPie")
    public String toPie(){
        return "pie";
    }

    //跳转 line 页面
    @RequestMapping("/toLine")
    public String toLine(){
        return "line";
    }

    /**
     * select * from qushishuju order by create_time limit 7;
     */
    @RequestMapping("/queryLine")
    @ResponseBody
    public Map<String,List<Object>> queryLineData(){
        //1. 查询近7天所有数据
        List<LineTrend> list7Day = indexService.findSevenData();
        //2. 封装所有的确诊人数
        List<Integer> confirmList = new ArrayList<>();
        //3. 封装所有的隔离人数
        List<Integer> isolationList = new ArrayList<>();
        //4. 封装所有的治愈人数
        List<Integer> cureList = new ArrayList<>();
        //5. 封装所有的死亡人数
        List<Integer> deadList = new ArrayList<>();
        //6. 封装所有的疑似人数
        List<Integer> similarList = new ArrayList<>();

        for (LineTrend data : list7Day){
            confirmList.add(data.getConfirm());
            isolationList.add(data.getIsolation());
            cureList.add(data.getCure());
            deadList.add(data.getDead());
            similarList.add(data.getSimilar());
        }
        
        //7.返回时的格式容器 Map
        Map map = new HashMap();
        map.put("confirmList",confirmList);
        map.put("isolationList",isolationList);
        map.put("cureList",cureList);
        map.put("deadList",deadList);
        map.put("similarList",similarList);

        return map;
    }

}
