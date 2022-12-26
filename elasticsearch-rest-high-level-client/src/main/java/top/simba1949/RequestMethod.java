package top.simba1949;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author anthony
 * @date 2022/12/26
 */
@Getter
@AllArgsConstructor
public enum RequestMethod {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete"),
    ;
    private final String method;
}
