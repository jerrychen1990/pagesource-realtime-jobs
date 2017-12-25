package parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import order.Order;
import order.OrderItem;
import util.CommonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jerry on 11/13/17.
 */
public abstract class AbstractOrderParser implements IOrderParser, Serializable {
    protected Order order ;

    @Override
    public Order parse(JSONObject jsonObject) {
        order = new Order();
        parseOrderInfo(jsonObject);
        JSONArray orderItemJsonArray = getOrderItemListJson(jsonObject);
        parseOrderItemList(orderItemJsonArray);
        return order;
    }

    protected abstract void parseOrderInfo(JSONObject jsonObject );

    protected abstract JSONArray getOrderItemListJson(JSONObject jsonObject);

    protected abstract OrderItem parseOrderItem(JSONObject jsonObject);

    protected void parseOrderItemList(JSONArray orderItemJsonList) {
        Boolean isCart = false;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < orderItemJsonList.size(); i++) {
            JSONObject jsonObject = orderItemJsonList.getJSONObject(i);
            OrderItem orderItem = parseOrderItem(jsonObject);
            orderItemList.add(orderItem);
            if (orderItem.getIsCart()) {
                isCart = true;
            }
        }

        order.setOrderItemList(orderItemList);
        order.setIsCart(isCart);
        List<OrderItem> dcpsOrderItemList = orderItemList.stream()
                .filter(e -> !CommonUtil.isBlank(e.getDcps())).collect(Collectors.toList());
        if (isCart) {
            dcpsOrderItemList.sort((o1, o2) -> o2.getCartCreateTimestamp().intValue() - o1.getCartCreateTimestamp().intValue());
        }

        OrderItem theOrderItem = orderItemList.get(0);
        if(!CommonUtil.isBlank(dcpsOrderItemList)){
            theOrderItem = dcpsOrderItemList.get(0);
        }

        order.setGoodsId(theOrderItem.getGoodsId());
        order.setSkuId(theOrderItem.getSkuId());
        order.setUserId(theOrderItem.getUserId());
        order.setPlatform(theOrderItem.getPlatform());
        order.setDcps(theOrderItem.getDcps());
        order.setPageSource(theOrderItem.getDcps().toString());
        order.setKdtSessionId(theOrderItem.getKdtSessionId());
    }


}
