package parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import junit.framework.TestCase;
import order.Order;

/**
 * Created by jerry on 11/13/17.
 */
public class BaseOrderParserV2Test extends TestCase {
    static final String testStr= "{\n" +
            "  \"activityType\": 1,\n" +
            "  \"activityTypeEnum\": \"NONE\",\n" +
            "  \"bizCategory\": \"COMMON\",\n" +
            "  \"buyerDTO\": {\n" +
            "    \"beneficiary\": \"13942770635\",\n" +
            "    \"buyerId\": 97469701,\n" +
            "    \"fansId\": 1169404454,\n" +
            "    \"fansType\": 1\n" +
            "  },\n" +
            "  \"channelType\": \"YOUZAN\",\n" +
            "  \"closeReason\": \"\",\n" +
            "  \"closeType\": \"NORMAL\",\n" +
            "  \"closeTypeValue\": 0,\n" +
            "  \"consumeStatus\": \"\",\n" +
            "  \"contractId\": 1415019244724688000,\n" +
            "  \"createTime\": 1510554371464,\n" +
            "  \"expiredTime\": 1510561571464,\n" +
            "  \"extra\": {\n" +
            "    \"BUYER_NAME\": \"苗大侠????¹³⁹⁴²⁷⁷⁰⁶³⁵\",\n" +
            "    \"FANS\": \"{\\\"fansId\\\":1169404454,\\\"type\\\":1,\\\"fansNickName\\\":\\\"\\\",\\\"youzanFansId\\\":516991767}\",\n" +
            "    \"FROM_CART\": \"false\",\n" +
            "    \"IS_POINTS_ORDER\": \"0\",\n" +
            "    \"ORDER_TYPE\": \"0\",\n" +
            "    \"TOURIST\": \"crqujevdcgkf0iaa8l3o6rcms2\",\n" +
            "    \"group_key\": \"67d741bb-b49c-4573-87e3-54bcce2efe5c\",\n" +
            "    \"payTool\": \"NOW_PAY,OFFLINE_CASH,PEER_PAY\",\n" +
            "    \"srcEnv\": \"prod\"\n" +
            "  },\n" +
            "  \"goodsType\": 0,\n" +
            "  \"goodsTypeEnum\": \"COMMON\",\n" +
            "  \"id\": 1415019244724688000,\n" +
            "  \"logisticsDTO\": {\n" +
            "    \"address\": {\n" +
            "      \"addressId\": 80478940,\n" +
            "      \"areaCode\": \"211104\",\n" +
            "      \"city\": \"盘锦市\",\n" +
            "      \"detail\": \"芳草路与石油大街交叉口西南侧 荣盛·盛锦花都14号楼1单元603\",\n" +
            "      \"district\": \"大洼区\",\n" +
            "      \"lat\": 41.13437006424559,\n" +
            "      \"lon\": 122.1217032056496,\n" +
            "      \"postalCode\": \"124010\",\n" +
            "      \"province\": \"辽宁省\",\n" +
            "      \"street\": \"\"\n" +
            "    },\n" +
            "    \"logisticsType\": \"NORMAL_EXPRESS\",\n" +
            "    \"receiverName\": \"翟爽\",\n" +
            "    \"receiverTel\": \"13942770635\"\n" +
            "  },\n" +
            "  \"memo\": \"\",\n" +
            "  \"orderItemDTOGroup\": [\n" +
            "    {\n" +
            "      \"extra\": {\n" +
            "        \"BIZ_TRACE_POINT\": \"{\\\"cartCreateTime\\\":0,\\\"cartUpdateTime\\\":0,\\\"pageSource\\\":\\\"\\\",\\\"kdtSessionId\\\":\\\"crqujevdcgkf0iaa8l3o6rcms2\\\"}\",\n" +
            "        \"DELIVER_OPTION\": \"0\",\n" +
            "        \"DELIVER_TIME\": \"0\",\n" +
            "        \"FRONT_PRICE\": \"680\",\n" +
            "        \"POINTS_PRICE\": \"0\",\n" +
            "        \"USED_PRO\": \"{\\\"activityAlias\\\":\\\"\\\",\\\"activityId\\\":0,\\\"activityType\\\":\\\"1\\\"}\"\n" +
            "      },\n" +
            "      \"goodsInfo\": {\n" +
            "        \"alias\": \"35vfotl64ihx9\",\n" +
            "        \"buy_way\": 1,\n" +
            "        \"class1\": 3,\n" +
            "        \"class2\": \"0\",\n" +
            "        \"extra\": {\n" +
            "          \"weight\": 0\n" +
            "        },\n" +
            "        \"goods_id\": 347956050,\n" +
            "        \"goods_no\": \"6185000009\",\n" +
            "        \"img_url\": \"https://img.yzcdn.cn/upload_files/2017/09/01/FiVY0MhToqhBFcqvkp4_u8HewJFO.jpg\",\n" +
            "        \"is_virtual\": 0,\n" +
            "        \"mark\": 0,\n" +
            "        \"points_price\": 0,\n" +
            "        \"quota\": 0,\n" +
            "        \"storeDeliverySet\": [\n" +
            "          \"NORMAL_EXPRESS\"\n" +
            "        ],\n" +
            "        \"supplier_goods_id\": 0,\n" +
            "        \"supplier_kdt_id\": 0,\n" +
            "        \"title\": \"【吴大嫂】450g 生态饺（三鲜）\"\n" +
            "      },\n" +
            "      \"id\": 1415019244724688000,\n" +
            "      \"memo\": \"\",\n" +
            "      \"num\": 2,\n" +
            "      \"quotaNum\": 0,\n" +
            "      \"realPay\": 1360,\n" +
            "      \"skuDTO\": {\n" +
            "        \"currentPrice\": 680,\n" +
            "        \"imageUrl\": \"https://img.yzcdn.cn/upload_files/2017/09/01/FiVY0MhToqhBFcqvkp4_u8HewJFO.jpg\",\n" +
            "        \"name\": \"【吴大嫂】450g 生态饺（三鲜）\",\n" +
            "        \"originPrice\": 680,\n" +
            "        \"skuCode\": \"6185000009\",\n" +
            "        \"skuCompositeId\": {\n" +
            "          \"goodsId\": 347956050,\n" +
            "          \"skuId\": 36164003\n" +
            "        },\n" +
            "        \"skuMap\": \"[{\\\"k\\\":\\\"规格\\\",\\\"k_id\\\":14,\\\"v\\\":\\\"450g/袋\\\",\\\"v_id\\\":38013},{\\\"k\\\":\\\"配送时效\\\",\\\"k_id\\\":1373486,\\\"v\\\":\\\"1小时达\\\",\\\"v_id\\\":12744603},{\\\"k\\\":\\\"起送量\\\",\\\"k_id\\\":1701427,\\\"v\\\":\\\"满10元起送\\\",\\\"v_id\\\":4206938}]\",\n" +
            "        \"type\": \"COMMON\"\n" +
            "      },\n" +
            "      \"snapShot\": \"ca4136719505dd6ac8692286c26f76df\",\n" +
            "      \"tags\": {\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"orderNo\": \"E20171113142611026100007\",\n" +
            "  \"orderStatus\": \"WAIT_PAY\",\n" +
            "  \"orderType\": \"NORMAL\",\n" +
            "  \"outBizNo\": \"E20171113142611026100007\",\n" +
            "  \"payId\": \"\",\n" +
            "  \"payType\": 0,\n" +
            "  \"payTypeEnum\": \"MIXED\",\n" +
            "  \"phase\": 1,\n" +
            "  \"priceDTO\": {\n" +
            "    \"currentPrice\": 1360,\n" +
            "    \"originPrice\": 1360,\n" +
            "    \"postage\": 0,\n" +
            "    \"promotionAmount\": 0,\n" +
            "    \"totalPrice\": 1360\n" +
            "  },\n" +
            "  \"sellerDTO\": {\n" +
            "    \"kdtId\": 13369725,\n" +
            "    \"shopId\": 13369725,\n" +
            "    \"shopName\": \"天鲜到家\",\n" +
            "    \"shopType\": \"NORMAL\",\n" +
            "    \"teamType\": \"WSC\"\n" +
            "  },\n" +
            "  \"sentTime\": 1510554371632,\n" +
            "  \"source\": \"{\\\"appScheme\\\":\\\"\\\",\\\"bookKey\\\":\\\"201711131426085a093b007ffb923543\\\",\\\"clientIp\\\":\\\"123.245.129.190\\\",\\\"fromThirdApp\\\":false,\\\"hasUnvalidGoods\\\":0,\\\"isOnlineOrder\\\":true,\\\"isReceiveMsg\\\":1,\\\"kdtId\\\":13369725,\\\"orderMark\\\":\\\"\\\",\\\"platform\\\":\\\"weixin\\\",\\\"seller\\\":\\\"\\\",\\\"source\\\":\\\"\\\",\\\"track\\\":\\\"\\\",\\\"userAgent\\\":\\\"Mozilla/5.0 (iPhone; CPU iPhone OS 11_0_3 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Mobile/15A432 MicroMessenger/6.5.21 NetType/WIFI Language/zh_CN\\\"}\",\n" +
            "  \"tags\": {\n" +
            "    \"IS_SECURED_TRANSACTIONS\": true,\n" +
            "    \"MESSAGE_NOTIFY\": true,\n" +
            "    \"STOCK_DEDUCTED\": true\n" +
            "  },\n" +
            "  \"updateTime\": 1510554371464\n" +
            "}";


    public void testParse() throws Exception {
        BaseOrderParserV2 baseOrderParserV2 = new BaseOrderParserV2();
        JSONObject jsonObj = JSON.parseObject(testStr);
        Order order = baseOrderParserV2.parse(jsonObj);
        System.out.println(JSON.toJSONString(order, SerializerFeature.PrettyFormat, SerializerFeature.SortField));

    }
}