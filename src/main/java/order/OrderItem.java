package order;

import lombok.Data;

/**
 * Created by jerry on 11/11/17.
 */
@Data
public class OrderItem {
    private String alias;
    private Long cartCreateTimestamp = 0L;
    private Long goodsId = 0L;
    private String title;
    private Boolean isCart = false;
    private String kdtSessionId = "";
    private Long skuId = 0L;
    private Long dcps = 0L;
    private Long num;
    private Long userId = 0L;
    private String platform = "h5";
    private Long currentPrice = 0L;
    private Long realPay=0L;
}
