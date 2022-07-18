package com.zhou.springboot2022ncov.tengxunapi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhou.springboot2022ncov.entity.ChinaTotal;
import com.zhou.springboot2022ncov.entity.NcovData;
import com.zhou.springboot2022ncov.service.ChinaTotalService;
import com.zhou.springboot2022ncov.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * @author zyh
 * @create 2022-07-02 15:21
 */
@Component
public class ChinaTotalScheduleTask {

    @Autowired
    private ChinaTotalService chinaTotalService;
    @Autowired
    private IndexService indexService;

    /**
     * 每小时执行一次 更新全国疫情数据情况
     *
     * @throws Exception
     */
    @Scheduled(fixedDelay = 100000)  //每 100s 执行一次
//    @Scheduled(cron="0 0 8,9,10,11,12,13")    //每到整点执行一次,例如 8 点...
    public void updateChinaTotalToDB() throws Exception {
        HttpUtils httpUtils = new HttpUtils();
        String string = httpUtils.getData();
        System.out.println("原始数据:" + string);

        //1. 所有数据的 alibaba 格式
        JSONObject jsonObject = JSONObject.parseObject(string);
        Object data = jsonObject.get("data");
        System.out.println("data:" + data);
        //2. data
        JSONObject jsonObjectData = JSONObject.parseObject(data.toString());
        Object chinaTotal = jsonObjectData.get("chinaTotal");
        Object lastUpdateTime = jsonObjectData.get("overseaLastUpdateTime");
        System.out.println("chinaTotal:" + chinaTotal);
        //3. total 全中国整体疫情数据
        JSONObject jsonObjectTotal = JSONObject.parseObject(chinaTotal.toString());
        Object total = jsonObjectTotal.get("total");
        System.out.println("total:" + total);
        //4. 全国数据 total
        JSONObject totalData = JSONObject.parseObject(total.toString());
        Object confirm = totalData.get("confirm");
        Object input = totalData.get("input");
        Object severe = totalData.get("severe");
        Object heal = totalData.get("heal");
        Object dead = totalData.get("dead");
        Object suspect = totalData.get("suspect");
        //5. 为程序实体赋值
        ChinaTotal dataEntity = new ChinaTotal();
        dataEntity.setConfirm((Integer) confirm);
        dataEntity.setInput((Integer) input);
        dataEntity.setSevere((Integer) severe);
        dataEntity.setHeal((Integer) heal);
        dataEntity.setDead((Integer) dead);
        dataEntity.setSuspect((Integer) suspect);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        dataEntity.setUpdateTime(format.parse(String.valueOf(lastUpdateTime)));
        //6. 插入数据库
        chinaTotalService.save(dataEntity);

        /**
         * 中国各个省份数据的自动更新
         */
        // 拿到 areaTree
        JSONArray areaTree = jsonObjectData.getJSONArray("areaTree");
        Object[] objects = areaTree.toArray();
        // 遍历所有国家的数据
//        for (int i = 0; i < objects.length; i++) {
//            JSONObject jsonObject1 = jsonObject.parseObject(objects[i].toString());
//            Object name = jsonObject1.get("name");
//            System.out.println(name);

        //拿到中国的数据
        JSONObject jsonObject1 = jsonObject.parseObject(objects[2].toString());
        JSONArray children = jsonObject1.getJSONArray("children");
        Object[] objects1 = children.toArray(); //各个省份

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<NcovData> list = new ArrayList<>();
        for (int i = 0; i < objects1.length; i++) {
            NcovData ncovData = new NcovData();
            JSONObject jsonObject2 = JSONObject.parseObject(objects1[i].toString());
            Object name = jsonObject2.get("name");//省份名字
            Object timePro = jsonObject2.get("lastUpdateTime");//省份更新数据时间
            Object total1 = jsonObject2.get("total");
            JSONObject jsonObject3 = JSONObject.parseObject(total1.toString()); //total
            Object confirm1 = jsonObject3.get("confirm");   //累计确诊数量

            //获取累计死亡人数 治愈人数
            Object heal1 = jsonObject3.get("heal");   //累计确诊数量
            Object dead1 = jsonObject3.get("dead");   //累计确诊数量

            //现存确诊
            int xianConfirm = Integer.parseInt(confirm1.toString()) - Integer.parseInt(heal1.toString()) - Integer.parseInt(dead1.toString());


//            System.out.println("省份 -> " + name + ":" + confirm1 + "人");
            //赋值
            ncovData.setName(name.toString());
            ncovData.setValue(xianConfirm);

            if (timePro == null){
                ncovData.setUpdateTime(new Date());
            }else {
                ncovData.setUpdateTime(format1.parse(String.valueOf(timePro)));
            }

            list.add(ncovData);

        }
        //各个省份的数据插入数据库
        indexService.saveBatch(list);
    }
}

