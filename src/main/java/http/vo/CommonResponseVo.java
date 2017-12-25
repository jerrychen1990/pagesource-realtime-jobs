package http.vo;

import lombok.Data;

/**
 * Created by jerry on 11/20/17.
 */

@Data
public class CommonResponseVo<T> {
    public Integer code;
    private String message;
    private T data;
    private Boolean result;
}
