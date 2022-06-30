package com.zhou.springboot2022ncov.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.springboot2022ncov.entity.NcovData;
import com.zhou.springboot2022ncov.service.IndexService;
import com.zhou.springboot2022ncov.vo.DataView;
import com.zhou.springboot2022ncov.vo.NcovDataVo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    public String toChinaManager() {
        return "admin/chinadatamanager";
    }

    //模糊查询 带有分页
    @RequestMapping("/listDataByPage")
    @ResponseBody
    public DataView listDataByPage(NcovDataVo ncovDataVo) {
        //1. 创建分页的对象 当前页 每页限制大小
        IPage<NcovData> page = new Page<>(ncovDataVo.getPage(), ncovDataVo.getLimit());
        //2. 创建模糊模糊查询条件
        QueryWrapper<NcovData> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!(ncovDataVo.getName() == null), "name", ncovDataVo.getName());
        //3. 疫情数据确诊最多的排在最上面
        queryWrapper.orderByDesc("value");
        //4. 查询数据库
        indexService.page(page, queryWrapper);
        //5. 返回数据封装
        DataView dataView = new DataView(page.getTotal(), page.getRecords());
        return dataView;
    }

    //删除数据 根据id
    @RequestMapping("/china/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id) {
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
    public DataView addOrUpdateChina(NcovData ncovData) {
        boolean save = indexService.saveOrUpdate(ncovData);
        DataView dataView = new DataView();
        if (save) {
            dataView.setCode(200);
            dataView.setMsg("新增中国地图数据成功!");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("新增中国地图数据失败!");
        return dataView;
    }

    /**
     * Excel 的拖拽或点击上传
     * <p>
     * 1. 前台页面发送一个请求,上传文件 MultipartFile HTTP
     * 2. Controller,上传文件 MultipartFile 参数
     * 3. POI 解析文件,里面的数据一行一行全部解析出来
     * 4. 每一条数据插入数据库
     */
    @RequestMapping("/excelImportChina")
    @ResponseBody
    public DataView excelImportChina(@RequestParam("file") MultipartFile file) throws Exception {
        DataView dataView = new DataView();

        //1.文件不能为空
        if (file.isEmpty()) {
            dataView.setMsg("文件为空,不能上传!");
        }
        //2. POI获取 Excel 解析数据
        XSSFWorkbook sheets = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = sheets.getSheetAt(0);
        //3. 定义一个程序集合 接收文件中的数据
        List<NcovData> list = new ArrayList<>();
        XSSFRow row = null;
        //4. 解析数据,装到集合里面
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            //4.1 定义实体
            NcovData ncovData = new NcovData();
            //4.2 每一行的数据 放到实体类里面
            row = sheet.getRow(i);
            //4.3 解析数据
            ncovData.setName(row.getCell(0).getStringCellValue());
            ncovData.setValue((int) row.getCell(1).getNumericCellValue());
            //5. 添加 list 集合
            list.add(ncovData);
        }
        //6. 插入数据库
        indexService.saveBatch(list);
        dataView.setCode(200);
        dataView.setMsg("Excel文件已经被你插入成功!");
        return dataView;
    }

    /**
     * 导出 Excel 数据 中国疫情数据
     * 1. 查询数据库
     * 2. 建立 Excel 对象,封装数据
     * 3. 建立输出流,输出文件
     */
    @RequestMapping("/excelOutPortChina")
    @ResponseBody
    public void excelOutPortChina(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        List<NcovData> list = indexService.list();
        HSSFWorkbook wb = new HSSFWorkbook();
        //2.创建sheet页,设置sheet页的名字
        HSSFSheet sheet = wb.createSheet("中国数据表");
        //3.创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("城市名称");
        titleRow.createCell(1).setCellValue("确诊数量");
        //4.遍历将数据集合将数据放到对应的列中
        for (NcovData data : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(data.getName());
            if(data.getValue() == null){
                dataRow.createCell(1).setCellValue(0);
            }
            else{
                dataRow.createCell(1).setCellValue(data.getValue());
            }
        }
        // 5.建立输出
        OutputStream os = null;
        try {
            //6.设置Excel的名称
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("中国疫情数据表".getBytes(), "iso-8859-1") + ".xls");
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
