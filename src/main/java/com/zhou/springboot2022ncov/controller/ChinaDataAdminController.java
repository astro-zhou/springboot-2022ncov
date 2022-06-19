package com.zhou.springboot2022ncov.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.springboot2022ncov.entity.NcovData;
import com.zhou.springboot2022ncov.service.IndexService;
import com.zhou.springboot2022ncov.vo.DataView;
import com.zhou.springboot2022ncov.vo.NcovDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zyh
 * @create 2022-06-18 17:15
 */
@Controller
public class ChinaDataAdminController {

    @Autowired
    private IndexService indexService;

    //跳转页面
    @RequestMapping("/toChinaManager")
    public String toChinaManager(){
        return "admin/chinadatamanager";
    }

    //模糊查询 带有分页
    @RequestMapping("/listDataByPage")
    @ResponseBody
    public DataView listDataByPage(NcovDataVo ncovDataVo){
        //1. 创建分页的对象 当前页 每页限制大小
        IPage<NcovData> page = new Page<>(ncovDataVo.getPage(),ncovDataVo.getLimit());
        //2. 创建模糊模糊查询条件
        QueryWrapper<NcovData> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!(ncovDataVo.getName() == null), "name", ncovDataVo.getName());
        //3. 疫情数据确诊最多的排在最上面
        queryWrapper.orderByDesc("value");
        //4. 查询数据库
        indexService.page(page, queryWrapper);
        //5. 返回数据封装
        DataView dataView = new DataView(page.getTotal(),page.getRecords());
        return dataView;
    }

    //删除数据 根据id
    @RequestMapping("/china/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        indexService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除中国地图数据成功!");

        return dataView;
    }

    /**
     * 新增或者修改 id
     * id: ncovData 有值修改,没有值就是新增
     * update ncov data set name = "" where id = ?
     * insert into
     *
     * @param ncovData
     * @return
     */
    @RequestMapping("/china/addOrUpdateChina")
    @ResponseBody
    public DataView addOrUpdateChina(NcovData ncovData){
        boolean save = indexService.saveOrUpdate(ncovData);
        DataView dataView = new DataView();
        if (save){
            dataView.setCode(200);
            dataView.setMsg("新增中国地图数据成功!");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("新增中国地图数据失败!");
        return dataView;
    }

}
