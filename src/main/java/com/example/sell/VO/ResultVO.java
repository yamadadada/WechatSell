package com.example.sell.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * Http请求返回的最外层对象
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 3938626101358237007L;

    /**错误码. */
    private Integer code;

    /**提示信息. */
    private String msg;

    /**具体内容. */
    private T data;

}
