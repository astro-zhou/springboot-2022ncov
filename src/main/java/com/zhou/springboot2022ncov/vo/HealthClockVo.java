package com.zhou.springboot2022ncov.vo;

import com.zhou.springboot2022ncov.entity.HealthClock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zyh
 * @create 2022-06-23 10:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HealthClockVo extends HealthClock {
    private Integer page;
    private Integer limit;
}
