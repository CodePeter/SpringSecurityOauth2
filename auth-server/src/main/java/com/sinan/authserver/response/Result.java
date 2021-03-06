package com.sinan.authserver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    //是否成功
    private boolean success;

    //返回码
    private int code;

    //返回信息
    private String msg;

    //返回数据
    private Object data;

//    public Result(boolean success, int code, String msg, Object data) {
//        this.success = success;
//        this.code = code;
//        this.msg = msg;
//        this.data = data;
//    }

    public static Result build(Object data) {
        return new Result(true, 200, "操作成功",data);
    }

}
