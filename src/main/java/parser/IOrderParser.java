package parser;

import com.alibaba.fastjson.JSONObject;
import order.Order;

/**
 * Created by jerry on 11/2/17.
 */
public interface IOrderParser {

    Order parse(JSONObject jsonObject);
}
