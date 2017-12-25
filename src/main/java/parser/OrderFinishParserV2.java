package parser;

import com.alibaba.fastjson.JSONObject;
import order.Order;

/**
 * Created by jerry on 11/9/17.
 */
public class OrderFinishParserV2 extends BaseOrderParserV2 {
    private Order order;
    private static final String NSQ_TOPIC = "ntc_order_success";

    @Override
    public Order parse(JSONObject jsonObject) {
        order = super.parse(jsonObject);
        order.setNsqTopic(NSQ_TOPIC);
        return order;
    }

}
