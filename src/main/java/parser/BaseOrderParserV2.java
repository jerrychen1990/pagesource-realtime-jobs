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
 * Created by jerry on 11/9/17.
 */
public class BaseOrderParserV2 extends AbstractOrderParser {


    @Override
    protected void parseOrderInfo(JSONObject jsonObject) {
        parseMainObj(jsonObject);
        JSONObject buyerObject = jsonObject.getJSONObject("buyerDTO");
        parseBuyerObj(buyerObject);
        JSONObject extraObject = jsonObject.getJSONObject("extra");
        parseExtraObj(extraObject);
        JSONObject priceObject = jsonObject.getJSONObject("priceDTO");
        parsePriceObj(priceObject);
        JSONObject sellerObject = jsonObject.getJSONObject("sellerDTO");
        parseSellerObj(sellerObject);
        JSONObject sourceObject = JSON.parseObject(jsonObject.getOrDefault("source", "").toString());
        parseSourceObj(sourceObject);
    }

    @Override
    protected JSONArray getOrderItemListJson(JSONObject jsonObject) {
        JSONArray itemListObject = jsonObject.getJSONArray("orderItemDTOGroup");
        return itemListObject;
    }

    @Override
    protected OrderItem parseOrderItem(JSONObject jsonObject) {
        OrderItem orderItem = new OrderItem();
        Long goodsId = jsonObject.getLong("goods_id");
        orderItem.setGoodsId(goodsId);
        Long num = jsonObject.getLong("num");
        orderItem.setNum(num);
        Long realPay = jsonObject.getLong("realPay");
        orderItem.setRealPay(realPay);

        JSONObject goodsInfoObj = jsonObject.getJSONObject("goodsInfo");
        String alias = goodsInfoObj.getString("alias");
        orderItem.setAlias(alias);
        String title = goodsInfoObj.getString("title");
        orderItem.setTitle(title);


        JSONObject skuInfoObj = jsonObject.getJSONObject("skuDTO");

        JSONObject skuObj = JSON.parseObject(skuInfoObj.getOrDefault("skuCompositeId", "{}").toString());
        Long skuId = Long.valueOf(skuObj.getOrDefault("skuId", "0").toString());
        orderItem.setSkuId(skuId);
        Long currentPrice = skuInfoObj.getLong("currentPrice");
        orderItem.setCurrentPrice(currentPrice);


        if (jsonObject.containsKey("extra")) {
            JSONObject extraObj = jsonObject.getJSONObject("extra");
            String bizTraceObjStr = extraObj.getOrDefault("BIZ_TRACE_POINT", "{}").toString();
            JSONObject bizTraceObj = JSON.parseObject(bizTraceObjStr);
            Long cartCreateTime = Long.valueOf(bizTraceObj.getOrDefault("cartCreateTime", "0").toString());
            orderItem.setCartCreateTimestamp(cartCreateTime);
            String dcpsStr = bizTraceObj.getString("pageSource");
            if (!CommonUtil.isBlank(dcpsStr)) {
                orderItem.setDcps(Long.valueOf(dcpsStr));
            }
            String kdtSessionId = bizTraceObj.getOrDefault("kdtSessionId", "").toString();
            orderItem.setKdtSessionId(kdtSessionId);

        }


        return orderItem;
    }


    private void parseMainObj(JSONObject obj) {
        String orderNo = obj.getString("orderNo");
        order.setOrderNo(orderNo);
        Long bookTime = obj.getLong("createTime");
        bookTime /= 1000;
        order.setBookTime(bookTime);
        order.setBookTimestamp(bookTime);
    }

    private void parseBuyerObj(JSONObject obj) {
        Long fansId = Long.valueOf(obj.getOrDefault("fansId", "0").toString());
        order.setFansId(fansId);
        order.setCustomerId(fansId);
        Long buyerId = Long.valueOf(obj.getOrDefault("buyerId", "0").toString());
        order.setBuyerId(buyerId);
    }

    private void parseExtraObj(JSONObject obj) {
        JSONObject fansObject = JSONObject.parseObject(obj.getOrDefault("FANS", new JSONObject()).toString());
        Long youzanFansId = Long.valueOf(fansObject.getOrDefault("youzanFansId", "0").toString());
        order.setYouzanFansId(youzanFansId);
        String isCartStr = obj.getOrDefault("FROM_CART", "").toString();
        Boolean isCart = "true".equals(isCartStr);
        order.setIsCart(isCart);
    }

    private void parsePriceObj(JSONObject obj) {
        Long realPay = obj.getLong("totalPrice");
        Long currentPrice = obj.getLong("currentPrice");
        Long postage = obj.getLong("postage");

        order.setRealPay(realPay);
        order.setCurrentPrice(currentPrice);
        order.setPostage(postage);
    }

    private void parseSellerObj(JSONObject obj) {
        Long kdtId = obj.getLong("kdtId");
        order.setKdtId(kdtId);
    }

    private void parseSourceObj(JSONObject obj) {
        String userClientStr = obj.getOrDefault("userAgent", "").toString();
        if (userClientStr.contains("MicroMessenger")) {
            order.setUserClient("MicroMessenger");
        } else {
            order.setUserClient("others");
        }
    }


    private Long extractDcps(JSONObject itemObject) {
        try {
            Object obj = extractItemExtra(itemObject, "pageSource");
            Long pageSource = Long.valueOf(obj.toString());
            return pageSource;
        } catch (Exception e) {
            return 0L;
        }
    }

    private Object extractItemExtra(JSONObject itemObject, String key) {
        String tracePointStr = itemObject.getJSONObject("extra").getString("BIZ_TRACE_POINT");
        Object rsObj = JSON.parseObject(tracePointStr).get(key);
        return rsObj;
    }

    private int extractCartCreateTime(JSONObject itemObject) {
        try {
            Object obj = extractItemExtra(itemObject, "cartCreateTime");
            int cartCreateTime = Integer.valueOf(obj.toString());
            return cartCreateTime;
        } catch (Exception e) {
            return 0;
        }

    }


    private String extractKdtSessionId(JSONObject itemObject) {
        try {
            Object obj = extractItemExtra(itemObject, "kdtSessionId");
            return obj.toString();
        } catch (Exception e) {
            return "";
        }

    }


}
