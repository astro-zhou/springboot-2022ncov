package com.zhou.springboot2022ncov.vo;

import com.zhou.springboot2022ncov.entity.NcovData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zyh
 * @create 2022-06-18 17:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NcovDataVo extends NcovData {
    private Integer page;
    private Integer limit;

}
