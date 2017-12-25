package parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import order.OrderItem;
import util.CommonUtil;

/**
 * Created by jerry on 11/2/17.
 */
public class BaseOrderParserV1 extends AbstractOrderParser {

    @Override
    protected void parseOrderInfo(JSONObject jsonObject) {
        JSONObject orderObject = jsonObject.getJSONObject("order");
        parseOrderObject(orderObject);
        JSONObject orderPayObject = jsonObject.getJSONObject("orderPayment");
        parseOrderPayObject(orderPayObject);
        JSONObject orderSourceObject = jsonObject.getJSONObject("orderSource");
        parseOrderSourceObject(orderSourceObject);
    }

    @Override
    protected JSONArray getOrderItemListJson(JSONObject jsonObject) {
        JSONArray orderItemList = jsonObject.getJSONArray("orderItemList");
        return orderItemList;
    }

    @Override
    protected OrderItem parseOrderItem(JSONObject jsonObject) {
        OrderItem orderItem = new OrderItem();
        Long cartCreateTime = Long.valueOf(jsonObject.getOrDefault("cartCreateTime", "0").toString());
        orderItem.setCartCreateTimestamp(cartCreateTime);
        String alias = jsonObject.getString("alias");
        orderItem.setAlias(alias);
        Long goodsId = jsonObject.getLong("goodsId");
        orderItem.setGoodsId(goodsId);
        String title = jsonObject.getString("title");
        orderItem.setTitle(title);
        String itemSource = jsonObject.getOrDefault("itemSource", "").toString();
        orderItem.setIsCart("cart".equals(itemSource));
        String kdtSessionId = jsonObject.getOrDefault("kdtSessionId", "").toString();
        orderItem.setKdtSessionId(kdtSessionId);
        Long skuId = Long.valueOf(jsonObject.getLong("skuId").toString());
        orderItem.setSkuId(skuId);
        Long num = jsonObject.getLong("num");
        orderItem.setNum(num);
        Long price = jsonObject.getLong("price");
        orderItem.setCurrentPrice(price);
        Long payPrice = jsonObject.getLong("payPrice");
        orderItem.setRealPay(payPrice);



        if (jsonObject.containsKey("extendSource")) {
            JSONObject extendJsonObj = jsonObject.getJSONObject("extendSource");
            String platform = extendJsonObj.getOrDefault("platform", "h5").toString();
            orderItem.setPlatform(platform);
            Long userId = Long.valueOf(extendJsonObj.getOrDefault("userId", "0").toString());
            orderItem.setUserId(userId);
            Long dcps = Long.valueOf(extendJsonObj.getOrDefault("dc_ps", "0").toString());
            orderItem.setDcps(dcps);
        }
        Long dcps = Long.valueOf(jsonObject.getOrDefault("dc_ps", "0").toString());
        if (!CommonUtil.isBlank(dcps)) {
            orderItem.setDcps(dcps);
        }
        return orderItem;
    }



    private void parseOrderObject(JSONObject orderObject) {
        String orderNo = orderObject.getString("orderNo");
        order.setOrderNo(orderNo);
        Long kdtId = orderObject.getLong("kdtId");
        order.setKdtId(kdtId);
        Long bookTimeStamp = orderObject.getLong("bookTime");
        order.setBookTime(bookTimeStamp);
        order.setBookTimestamp(bookTimeStamp);
        Long buyerId = Long.valueOf(orderObject.getOrDefault("buyerId", "0").toString());
        order.setBuyerId(buyerId);
        Long customerId = Long.valueOf(orderObject.getOrDefault("customerId", "0").toString());
        order.setCustomerId(customerId);
        order.setFansId(customerId);
        Long youzanFansId = Long.valueOf(orderObject.getOrDefault("youzanFansId", "0").toString());
        order.setYouzanFansId(youzanFansId);
    }

    private void parseOrderPayObject(JSONObject orderPayObject) {
        Long realPay = orderPayObject.getLong("realPay");
        order.setRealPay(realPay);
        Long pay = orderPayObject.getLong("pay");
        order.setCurrentPrice(pay);
        Long postage = orderPayObject.getLong("postage");
        order.setPostage(postage);

    }

    private void parseOrderSourceObject(JSONObject orderSourceObject) {
        String agentStr = orderSourceObject.getOrDefault("userAgent", "").toString();
        if (agentStr.contains("MicroMessenger")) {
            order.setUserClient("MicroMessenger");
        } else {
            order.setUserClient("others");
        }
    }



}
