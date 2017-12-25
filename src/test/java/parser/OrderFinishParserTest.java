package parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import junit.framework.TestCase;
import order.Order;

/**
 * Created by jerry on 11/9/17.
 */
public class OrderFinishParserTest extends TestCase {
    private static final String srcStr = "{\n" +
            "  \"activityType\": 1,\n" +
            "  \"activityTypeEnum\": \"NONE\",\n" +
            "  \"bizCategory\": \"COMMON\",\n" +
            "  \"buyerDTO\": {\n" +
            "    \"beneficiary\": \"13613216362\",\n" +
            "    \"buyerId\": 275724654,\n" +
            "    \"fansId\": 4312388231,\n" +
            "    \"fansType\": 1\n" +
            "  },\n" +
            "  \"channelType\": \"YOUZAN\",\n" +
            "  \"closeReason\": \"\",\n" +
            "  \"closeType\": \"NORMAL\",\n" +
            "  \"closeTypeValue\": 0,\n" +
            "  \"consumeStatus\": \"selffetch\",\n" +
            "  \"contractId\": 1412604369352789921,\n" +
            "  \"createTime\": 1509429857592,\n" +
            "  \"expiredTime\": 1509433457592,\n" +
            "  \"extra\": {\n" +
            "    \"FANS\": \"{\\\"fansId\\\":4312388231,\\\"type\\\":1,\\\"fansNickName\\\":\\\"\\\",\\\"youzanFansId\\\":1875694971}\",\n" +
            "    \"SELF_FETCH\": \"{\\\"kdt_id\\\":\\\"19248829\\\",\\\"id\\\":\\\"20976474\\\",\\\"name\\\":\\\"\\\\u76c8\\\\u00b7\\\\u5065\\\\u5eb7\\\\u751f\\\\u6d3b\\\\u9986\\\\uff08\\\\u7f8e\\\\u4e3d\\\\u534e\\\\u5e97\\\\uff09\\\",\\\"is_valid\\\":\\\"1\\\",\\\"created_time\\\":\\\"2017-10-04 13:34:51\\\",\\\"updated_time\\\":\\\"2017-10-29 00:09:46\\\",\\\"is_super_store\\\":\\\"0\\\",\\\"distance\\\":\\\"558\\\",\\\"province\\\":\\\"\\\\u6cb3\\\\u5317\\\\u7701\\\",\\\"city\\\":\\\"\\\\u77f3\\\\u5bb6\\\\u5e84\\\\u5e02\\\",\\\"area\\\":\\\"\\\\u957f\\\\u5b89\\\\u533a\\\",\\\"county\\\":\\\"\\\\u957f\\\\u5b89\\\\u533a\\\",\\\"county_id\\\":\\\"130102\\\",\\\"address\\\":\\\"\\\\u7f8e\\\\u4e3d\\\\u534e\\\\u9152\\\\u5e971\\\\u697c\\\",\\\"lng\\\":\\\"114.53846\\\",\\\"lat\\\":\\\"38.055856\\\",\\\"phone1\\\":\\\"\\\",\\\"phone2\\\":\\\"18633028505\\\",\\\"is_optional_self_fetch_time\\\":\\\"0\\\",\\\"offline_business_hours\\\":[{\\\"open_time\\\":\\\"09:30\\\",\\\"close_time\\\":\\\"20:00\\\",\\\"weekdays\\\":[\\\"\\\\u5468\\\\u4e00\\\",\\\"\\\\u5468\\\\u4e8c\\\",\\\"\\\\u5468\\\\u4e09\\\",\\\"\\\\u5468\\\\u56db\\\",\\\"\\\\u5468\\\\u4e94\\\",\\\"\\\\u5468\\\\u516d\\\"]}],\\\"tel\\\":\\\"18633028505\\\",\\\"address_detail\\\":\\\"\\\\u7f8e\\\\u4e3d\\\\u534e\\\\u9152\\\\u5e971\\\\u697c\\\",\\\"dfcode\\\":\\\"130102\\\",\\\"postal_code\\\":\\\"\\\",\\\"area_code\\\":\\\"\\\",\\\"user_name\\\":\\\"\\\\u5f20\\\\u6d77\\\\u971e\\\",\\\"community\\\":\\\"\\\",\\\"lon\\\":\\\"\\\",\\\"needCommunityInfo\\\":\\\"false\\\",\\\"needUpgrade\\\":\\\"false\\\",\\\"user_time\\\":\\\"\\\\u8bf7\\\\u57282017-11-01\\\\u540e\\\\u5230\\\\u5e97\\\\u81ea\\\\u63d0\\\",\\\"user_tel\\\":\\\"13613216362\\\",\\\"is_header\\\":\\\"false\\\",\\\"id_card_number\\\":\\\"\\\"}\",\n" +
            "    \"weAppPrepayId\": \"wx20171031140421415fb53ac80281806109\",\n" +
            "    \"ORDER_TYPE\": \"0\",\n" +
            "    \"payTool\": \"NOW_PAY,OFFLINE_CASH,PEER_PAY\",\n" +
            "    \"FROM_CART\": \"false\",\n" +
            "    \"OUTER_TRANSACTION_NO\": \"4200000006201710311465674092\",\n" +
            "    \"srcEnv\": \"prod\",\n" +
            "    \"IS_POINTS_ORDER\": \"0\",\n" +
            "    \"INNER_TRANSACTION_NO\": \"171031140421303560\",\n" +
            "    \"AUTO_RECEIVE_TIME\": \"1510214713742\",\n" +
            "    \"TOURIST\": \"YZgadosottcin30c0hcsg19l46o128dab6c39368b7c0350\",\n" +
            "    \"group_key\": \"511c362a-62c7-400b-9656-82bca491713f\"\n" +
            "  },\n" +
            "  \"goodsType\": 0,\n" +
            "  \"goodsTypeEnum\": \"COMMON\",\n" +
            "  \"id\": 1412604369352789921,\n" +
            "  \"logisticsDTO\": {\n" +
            "    \"address\": {\n" +
            "      \"addressId\": 0,\n" +
            "      \"city\": \"石家庄市\",\n" +
            "      \"country\": \"\",\n" +
            "      \"detail\": \"美丽华酒店1楼\",\n" +
            "      \"district\": \"长安区\",\n" +
            "      \"postalCode\": \"\",\n" +
            "      \"province\": \"河北省\"\n" +
            "    },\n" +
            "    \"logisticsType\": \"SELF_TAKE\",\n" +
            "    \"receiverName\": \"张海霞\",\n" +
            "    \"receiverTel\": \"13613216362\"\n" +
            "  },\n" +
            "  \"memo\": \"\",\n" +
            "  \"orderItemDTOGroup\": [\n" +
            "    {\n" +
            "      \"extra\": {\n" +
            "        \"USED_PRO\": \"{\\\"activityAlias\\\":\\\"\\\",\\\"activityId\\\":0,\\\"activityType\\\":\\\"1\\\"}\",\n" +
            "        \"BIZ_TRACE_POINT\": \"{\\\"cartCreateTime\\\":0,\\\"cartUpdateTime\\\":0,\\\"pageSource\\\":\\\"\\\",\\\"kdtSessionId\\\":\\\"YZgadosottcin30c0hcsg19l46o128dab6c39368b7c0350\\\",\\\"extension\\\":{}}\",\n" +
            "        \"FRONT_PRICE\": \"1\",\n" +
            "        \"POINTS_PRICE\": \"0\"\n" +
            "      },\n" +
            "      \"goodsInfo\": {\n" +
            "        \"alias\": \"35vgsdbz7r365\",\n" +
            "        \"buy_way\": 1,\n" +
            "        \"etd_start\": 1509465600,\n" +
            "        \"etd_type\": 0,\n" +
            "        \"extra\": {\n" +
            "          \"weight\": 0.0\n" +
            "        },\n" +
            "        \"goods_id\": 352980156,\n" +
            "        \"goods_no\": \"\",\n" +
            "        \"img_url\": \"https://img.yzcdn.cn/upload_files/2017/10/28/FoO5j9szE9Noq8W5zuTW5cS2UkYw.jpg\",\n" +
            "        \"is_virtual\": 0,\n" +
            "        \"mark\": 0,\n" +
            "        \"points_price\": 0,\n" +
            "        \"pre_sale\": 1,\n" +
            "        \"quota\": 0,\n" +
            "        \"relevanceState\": false,\n" +
            "        \"stockSyncState\": false,\n" +
            "        \"supplier_goods_id\": 0,\n" +
            "        \"supplier_kdt_id\": 0,\n" +
            "        \"title\": \"华北制药VC咀嚼片\"\n" +
            "      },\n" +
            "      \"id\": 1412604369352789921,\n" +
            "      \"memo\": \"\",\n" +
            "      \"num\": 1,\n" +
            "      \"quotaNum\": 0,\n" +
            "      \"realPay\": 0,\n" +
            "      \"skuDTO\": {\n" +
            "        \"currentPrice\": 1,\n" +
            "        \"imageUrl\": \"https://img.yzcdn.cn/upload_files/2017/10/28/FoO5j9szE9Noq8W5zuTW5cS2UkYw.jpg\",\n" +
            "        \"name\": \"华北制药VC咀嚼片\",\n" +
            "        \"originPrice\": 4800,\n" +
            "        \"skuCode\": \"\",\n" +
            "        \"skuCompositeId\": {\n" +
            "          \"goodsId\": 352980156,\n" +
            "          \"skuId\": 36164507\n" +
            "        },\n" +
            "        \"skuMap\": \"[{\\\"k\\\":\\\"规格\\\",\\\"k_id\\\":14,\\\"v\\\":\\\"56g（70片）\\\",\\\"v_id\\\":21852512}]\",\n" +
            "        \"type\": \"COMMON\"\n" +
            "      },\n" +
            "      \"snapShot\": \"40c4843d623dcebde275973bce8d2c9c\",\n" +
            "      \"tags\": {\n" +
            "        \"STOCK_DEDUCTED\": true\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"orderNo\": \"E20171031140417036600006\",\n" +
            "  \"orderStatus\": \"SUCCESS\",\n" +
            "  \"orderType\": \"NORMAL\",\n" +
            "  \"outBizNo\": \"E20171031140417036600006\",\n" +
            "  \"payId\": \"171031140418145633\",\n" +
            "  \"payTime\": 1509429868000,\n" +
            "  \"payType\": 2,\n" +
            "  \"payTypeEnum\": \"NOW_PAY\",\n" +
            "  \"payWay\": \"WXPAY_DAIXIAO\",\n" +
            "  \"phase\": 1,\n" +
            "  \"priceDTO\": {\n" +
            "    \"currentPrice\": 1,\n" +
            "    \"originPrice\": 4800,\n" +
            "    \"postage\": 0,\n" +
            "    \"promotionAmount\": 0,\n" +
            "    \"totalPrice\": 1\n" +
            "  },\n" +
            "  \"sellerDTO\": {\n" +
            "    \"kdtId\": 19248829,\n" +
            "    \"shopId\": 20976474,\n" +
            "    \"shopName\": \"华北制药盈健康生活馆\",\n" +
            "    \"shopType\": \"MULTI\",\n" +
            "    \"teamType\": \"CJMD\"\n" +
            "  },\n" +
            "  \"sentTime\": 1510214716784,\n" +
            "  \"shipTime\": 1509609913742,\n" +
            "  \"source\": \"{\\\"bookKey\\\":\\\"2017103114033459f812363c95c25845\\\",\\\"clientIp\\\":\\\"223.104.13.42\\\",\\\"hasUnvalidGoods\\\":0,\\\"isOnlineOrder\\\":true,\\\"isReceiveMsg\\\":1,\\\"orderMark\\\":\\\"\\\",\\\"platform\\\":\\\"weixin\\\",\\\"seller\\\":\\\"\\\",\\\"source\\\":\\\"\\\",\\\"track\\\":\\\"\\\",\\\"userAgent\\\":\\\"Mozilla/5.0 (Linux; Android 5.1; m1 metal Build/LMY47I; wv) AppleWebKit/537.36 (KHTML, like Gecko) V\\\"}\",\n" +
            "  \"successTime\": 1510214716769,\n" +
            "  \"tags\": {\n" +
            "    \"MESSAGE_NOTIFY\": true,\n" +
            "    \"IS_MULTI_STORE_ENABLED\": true,\n" +
            "    \"IS_SECURED_TRANSACTIONS\": true,\n" +
            "    \"STOCK_DEDUCTED\": true,\n" +
            "    \"IS_PAYED\": true\n" +
            "  },\n" +
            "  \"updateTime\": 1509609913738\n" +
            "}";


    public void testParse() throws Exception {
        OrderFinishParserV2 parser = new OrderFinishParserV2();
        Order order = parser.parse(JSON.parseObject(srcStr));
        System.out.println(JSON.toJSONString(order, SerializerFeature.PrettyFormat));


    }

    public static void main(String args[]) {

        String tmp = "{\"aaa\": 123}";
        JSONObject jsonObject = JSON.parseObject(tmp);
        System.out.println(jsonObject);
        Long tl = jsonObject.getLong("bbb");
        tl = Long.valueOf(jsonObject.getOrDefault("aaa","0").toString());


        System.out.println(tl);


    }
}