package com.example.demo;

import lombok.Data;

/**
 * @author lijinlong
 * @version 1.0
 * @date 2019/6/11 13:55
 */
@Data
public class Result {
    private Object data;

    public Result() {
    }

    public Result(Object data) {
        this.data = data;
    }
}
