package com.zhou.springboot2022ncov.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zyh
 * @create 2022-06-18 17:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataView {
    private Integer code = 0;
    private String msg = "";
    private Long count = 0L;
    private Object data;

    public DataView(Long count, Object data){
        this.count = count;
        this.data = data;
    }

    public DataView(Object data) {
        this.data = data;
    }
}
