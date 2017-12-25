package parser;


import com.alibaba.fastjson.JSONObject;
import order.Order;

/**
 * Created by jerry on 11/6/17.
 */
public class OrderCreateParserV2 extends BaseOrderParserV2 {
    private Order order;
    private static final String NSQ_TOPIC = "ntc_order_create";


    @Override
    public Order parse(JSONObject jsonObject) {
        order = super.parse(jsonObject);
        order.setNsqTopic(NSQ_TOPIC);
        return order;
    }


}
