package parser;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.api.scripting.JSObject;
import order.Order;

/**
 * Created by jerry on 11/9/17.
 */
public class OrderCreateParserV1 extends BaseOrderParserV1 {
    private Order order;
    private static final String NSQ_TOPIC = "trade_order_bill_create";


    @Override
    public Order parse(JSONObject jsonObject) {
        order = super.parse(jsonObject);
        order.setNsqTopic(NSQ_TOPIC);
        return order;
    }


}
