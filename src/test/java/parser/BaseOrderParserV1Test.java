package parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import junit.framework.TestCase;
import order.Order;

/**
 * Created by jerry on 11/13/17.
 */
public class BaseOrderParserV1Test extends TestCase {
    private static final String testStr = "{\n" +
            "  \"order\": {\n" +
            "    \"activityType\": 1,\n" +
            "    \"bookTime\": 1510386789,\n" +
            "    \"buyWay\": 0,\n" +
            "    \"buyerId\": 611380052,\n" +
            "    \"buyerPhone\": \"15750933145\",\n" +
            "    \"couponId\": \"\",\n" +
            "    \"couponType\": \"\",\n" +
            "    \"currency\": 1,\n" +
            "    \"customerId\": 3805645705,\n" +
            "    \"customerName\": \"\",\n" +
            "    \"customerType\": 1,\n" +
            "    \"expireTime\": 1510393989,\n" +
            "    \"expressType\": 0,\n" +
            "    \"features\": \"0000000001\",\n" +
            "    \"isBuy\": 0,\n" +
            "    \"isPoints\": 0,\n" +
            "    \"isVirtual\": 0,\n" +
            "    \"kdtId\": 33746,\n" +
            "    \"normal\": 0,\n" +
            "    \"orderNo\": \"E20171111155309085212404\",\n" +
            "    \"orderState\": 3,\n" +
            "    \"orderType\": 0,\n" +
            "    \"shopId\": 33746,\n" +
            "    \"shopType\": 1,\n" +
            "    \"stockState\": 1,\n" +
            "    \"storeId\": 0,\n" +
            "    \"tourist\": \"YZ378692657742110720YZueeuqgh4\",\n" +
            "    \"updateTime\": 1510386789,\n" +
            "    \"youzanFansId\": 1257823851\n" +
            "  },\n" +
            "  \"orderAddress\": {\n" +
            "    \"addressDetail\": \"池店镇泉安北路龙湖嘉天下2期2栋一单元301\",\n" +
            "    \"addressId\": 70776418,\n" +
            "    \"areaCode\": \"350582\",\n" +
            "    \"buyerMsg\": \"\",\n" +
            "    \"city\": \"泉州市\",\n" +
            "    \"community\": \"\",\n" +
            "    \"county\": \"晋江市\",\n" +
            "    \"deliveryTimeSpan\": \"\",\n" +
            "    \"extraInfo\": \"{\\\"lon\\\":118.59030877665472,\\\"lat\\\":24.846697409221882,\\\"deliverOption\\\":\\\"\\\"}\",\n" +
            "    \"kdtId\": 33746,\n" +
            "    \"lat\": 24.846697409221882,\n" +
            "    \"lon\": 118.59030877665472,\n" +
            "    \"orderAddressId\": 70776418,\n" +
            "    \"orderNo\": \"E20171111155309085212404\",\n" +
            "    \"postalCode\": \"362000\",\n" +
            "    \"province\": \"福建省\",\n" +
            "    \"shopId\": 33746,\n" +
            "    \"tel\": \"15750933145\",\n" +
            "    \"userName\": \"王丽容\"\n" +
            "  },\n" +
            "  \"orderItemList\": [\n" +
            "    {\n" +
            "      \"activityAlias\": \"\",\n" +
            "      \"activityExtra\": \"\",\n" +
            "      \"activityId\": 0,\n" +
            "      \"activityType\": 0,\n" +
            "      \"alias\": \"271ht0vvh2dhe\",\n" +
            "      \"cartCreateTime\": 1510386505,\n" +
            "      \"cartUpdateTime\": 1510386505,\n" +
            "      \"created\": 1510386789,\n" +
            "      \"deliverOption\": \"0\",\n" +
            "      \"deliverTime\": 0,\n" +
            "      \"deliveryTemplateId\": 96135,\n" +
            "      \"goodsId\": 354503714,\n" +
            "      \"goodsInfo\": \"{\\\"is_virtual\\\":0,\\\"supplier_kdt_id\\\":1841927,\\\"service_tel\\\":null,\\\"goods_date\\\":null,\\\"goods_no\\\":\\\"YJMZ0687\\\",\\\"buy_way\\\":1,\\\"title\\\":\\\"【品牌直发】Dr.Althea/艾医生 按摩眼部精华霜(自带按摩头) 达人眼霜 抗皱提拉消浮肿淡化黑眼圈\\\",\\\"supplier_goods_id\\\":326948105,\\\"points_price\\\":0,\\\"img_url\\\":\\\"https://img.yzcdn.cn/upload_files/2017/10/16/FuwbtU9_3MzJJOyVnIOhD63Yc3kJ.jpg\\\",\\\"quota\\\":0,\\\"extra\\\":{\\\"is_present\\\":0},\\\"alias\\\":\\\"271ht0vvh2dhe\\\",\\\"storeDeliverySet\\\":null,\\\"mark\\\":0}\",\n" +
            "      \"goodsSnap\": \"dcc31500cfc16154611274103db52c66\",\n" +
            "      \"goodsType\": 10,\n" +
            "      \"id\": 20033909,\n" +
            "      \"imgUrl\": \"https://img.yzcdn.cn/upload_files/2017/10/16/FuwbtU9_3MzJJOyVnIOhD63Yc3kJ.jpg\",\n" +
            "      \"isVirtual\": 0,\n" +
            "      \"itemSource\": \"cart\",\n" +
            "      \"kdtId\": 33746,\n" +
            "      \"kdtSessionId\": \"YZ378692657742110720YZueeuqgh4\",\n" +
            "      \"message\": \"\",\n" +
            "      \"num\": 1,\n" +
            "      \"orderNo\": \"E20171111155309085212404\",\n" +
            "      \"originPrice\": 15900,\n" +
            "      \"pageSource\": \"\",\n" +
            "      \"payPrice\": 15900,\n" +
            "      \"pointsPrice\": 0,\n" +
            "      \"postage\": 0,\n" +
            "      \"price\": 15900,\n" +
            "      \"s1\": 47055,\n" +
            "      \"s2\": 0,\n" +
            "      \"s3\": 0,\n" +
            "      \"s4\": 0,\n" +
            "      \"s5\": 0,\n" +
            "      \"shopId\": 33746,\n" +
            "      \"shopInfo\": \"{\\\"shop_name\\\":\\\"我是大美人\\\"}\",\n" +
            "      \"sku\": \"[{\\\"k\\\":\\\"规格\\\",\\\"k_id\\\":14,\\\"v\\\":\\\"20ml/支\\\",\\\"v_id\\\":47055}]\",\n" +
            "      \"skuCode\": \"YJMZ0687\",\n" +
            "      \"skuId\": 36173577,\n" +
            "      \"supplierGoodsId\": 326948105,\n" +
            "      \"supplierKdtId\": 1841927,\n" +
            "      \"title\": \"【品牌直发】Dr.Althea/艾医生 按摩眼部精华霜(自带按摩头) 达人眼霜 抗皱提拉消浮肿淡化黑眼圈\",\n" +
            "      \"umpSkuId\": 0,\n" +
            "      \"uniqueKey\": \"354503714_36173577\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"activityAlias\": \"\",\n" +
            "      \"activityExtra\": \"\",\n" +
            "      \"activityId\": 0,\n" +
            "      \"activityType\": 0,\n" +
            "      \"alias\": \"2g2n4trlwgdoy\",\n" +
            "      \"cartCreateTime\": 1510386464,\n" +
            "      \"cartUpdateTime\": 1510386464,\n" +
            "      \"created\": 1510386789,\n" +
            "      \"deliverOption\": \"0\",\n" +
            "      \"deliverTime\": 0,\n" +
            "      \"deliveryTemplateId\": 96135,\n" +
            "      \"goodsId\": 349424822,\n" +
            "      \"goodsInfo\": \"{\\\"is_virtual\\\":0,\\\"supplier_kdt_id\\\":1841927,\\\"service_tel\\\":null,\\\"goods_date\\\":null,\\\"goods_no\\\":\\\"\\\",\\\"buy_way\\\":1,\\\"title\\\":\\\"【品牌直发】范冰冰必购·Blackmores澳佳宝 VE面霜50g 冰冰霜 天然维生素E乳霜 澳大利亚（YJHT）\\\",\\\"supplier_goods_id\\\":208447608,\\\"points_price\\\":0,\\\"img_url\\\":\\\"https://img.yzcdn.cn/upload_files/2017/09/22/FsAPn9zhMnjzyFAexxf_tiu2hW1b.jpg\\\",\\\"quota\\\":0,\\\"extra\\\":{\\\"is_present\\\":0},\\\"alias\\\":\\\"2g2n4trlwgdoy\\\",\\\"storeDeliverySet\\\":null,\\\"mark\\\":0}\",\n" +
            "      \"goodsSnap\": \"b36d7e7f64bb1fa21451d1fdb15df261\",\n" +
            "      \"goodsType\": 10,\n" +
            "      \"id\": 20033910,\n" +
            "      \"imgUrl\": \"https://img.yzcdn.cn/upload_files/2017/09/22/FsAPn9zhMnjzyFAexxf_tiu2hW1b.jpg\",\n" +
            "      \"isVirtual\": 0,\n" +
            "      \"itemSource\": \"cart\",\n" +
            "      \"kdtId\": 33746,\n" +
            "      \"kdtSessionId\": \"YZ378692657742110720YZueeuqgh4\",\n" +
            "      \"message\": \"\",\n" +
            "      \"num\": 2,\n" +
            "      \"orderNo\": \"E20171111155309085212404\",\n" +
            "      \"originPrice\": 6900,\n" +
            "      \"pageSource\": \"\",\n" +
            "      \"payPrice\": 6900,\n" +
            "      \"pointsPrice\": 0,\n" +
            "      \"postage\": 0,\n" +
            "      \"price\": 6900,\n" +
            "      \"s1\": 4385,\n" +
            "      \"s2\": 17617,\n" +
            "      \"s3\": 0,\n" +
            "      \"s4\": 0,\n" +
            "      \"s5\": 0,\n" +
            "      \"shopId\": 33746,\n" +
            "      \"shopInfo\": \"{\\\"shop_name\\\":\\\"我是大美人\\\"}\",\n" +
            "      \"sku\": \"[{\\\"k\\\":\\\"产地\\\",\\\"k_id\\\":538,\\\"v\\\":\\\"澳大利亚\\\",\\\"v_id\\\":4385},{\\\"k\\\":\\\"规格\\\",\\\"k_id\\\":14,\\\"v\\\":\\\"50g/支\\\",\\\"v_id\\\":17617}]\",\n" +
            "      \"skuCode\": \"YJMZ0227\",\n" +
            "      \"skuId\": 35913262,\n" +
            "      \"supplierGoodsId\": 208447608,\n" +
            "      \"supplierKdtId\": 1841927,\n" +
            "      \"title\": \"【品牌直发】范冰冰必购·Blackmores澳佳宝 VE面霜50g 冰冰霜 天然维生素E乳霜 澳大利亚（YJHT）\",\n" +
            "      \"umpSkuId\": 0,\n" +
            "      \"uniqueKey\": \"349424822_35913262\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"activityAlias\": \"\",\n" +
            "      \"activityExtra\": \"\",\n" +
            "      \"activityId\": 0,\n" +
            "      \"activityType\": 0,\n" +
            "      \"alias\": \"2fwiyte3fdcvm\",\n" +
            "      \"cartCreateTime\": 1510386451,\n" +
            "      \"cartUpdateTime\": 1510386451,\n" +
            "      \"created\": 1510386789,\n" +
            "      \"deliverOption\": \"0\",\n" +
            "      \"deliverTime\": 0,\n" +
            "      \"deliveryTemplateId\": 424642,\n" +
            "      \"goodsId\": 340457779,\n" +
            "      \"goodsInfo\": \"{\\\"is_virtual\\\":0,\\\"supplier_kdt_id\\\":1841927,\\\"service_tel\\\":null,\\\"goods_date\\\":null,\\\"goods_no\\\":\\\"YJBJ0383\\\",\\\"buy_way\\\":1,\\\"title\\\":\\\"【品牌直发】约会必备神器·澳洲·Breath Pearls 天然清新口气珠 50粒/瓶 草本精华无添加（YJHT ）\\\",\\\"supplier_goods_id\\\":202096841,\\\"points_price\\\":0,\\\"img_url\\\":\\\"https://img.yzcdn.cn/upload_files/2017/04/13/Fq-Lf_Lht3NIaLUOBH6s5z6K0yYU.jpg\\\",\\\"quota\\\":0,\\\"extra\\\":{\\\"is_present\\\":0},\\\"alias\\\":\\\"2fwiyte3fdcvm\\\",\\\"storeDeliverySet\\\":null,\\\"mark\\\":0}\",\n" +
            "      \"goodsSnap\": \"ccc9f54dddfa05700a27aa5c566c1d10\",\n" +
            "      \"goodsType\": 10,\n" +
            "      \"id\": 20033911,\n" +
            "      \"imgUrl\": \"https://img.yzcdn.cn/upload_files/2017/04/13/Fq-Lf_Lht3NIaLUOBH6s5z6K0yYU.jpg\",\n" +
            "      \"isVirtual\": 0,\n" +
            "      \"itemSource\": \"cart\",\n" +
            "      \"kdtId\": 33746,\n" +
            "      \"kdtSessionId\": \"YZ378692657742110720YZueeuqgh4\",\n" +
            "      \"message\": \"\",\n" +
            "      \"num\": 1,\n" +
            "      \"orderNo\": \"E20171111155309085212404\",\n" +
            "      \"originPrice\": 7500,\n" +
            "      \"pageSource\": \"\",\n" +
            "      \"payPrice\": 7500,\n" +
            "      \"pointsPrice\": 0,\n" +
            "      \"postage\": 0,\n" +
            "      \"price\": 7500,\n" +
            "      \"s1\": 1891,\n" +
            "      \"s2\": 394893,\n" +
            "      \"s3\": 0,\n" +
            "      \"s4\": 0,\n" +
            "      \"s5\": 0,\n" +
            "      \"shopId\": 33746,\n" +
            "      \"shopInfo\": \"{\\\"shop_name\\\":\\\"我是大美人\\\"}\",\n" +
            "      \"sku\": \"[{\\\"k\\\":\\\"产地\\\",\\\"k_id\\\":538,\\\"v\\\":\\\"澳洲\\\",\\\"v_id\\\":1891},{\\\"k\\\":\\\"规格\\\",\\\"k_id\\\":14,\\\"v\\\":\\\"50粒/瓶\\\",\\\"v_id\\\":394893}]\",\n" +
            "      \"skuCode\": \"\",\n" +
            "      \"skuId\": 36138959,\n" +
            "      \"supplierGoodsId\": 202096841,\n" +
            "      \"supplierKdtId\": 1841927,\n" +
            "      \"title\": \"【品牌直发】约会必备神器·澳洲·Breath Pearls 天然清新口气珠 50粒/瓶 草本精华无添加（YJHT ）\",\n" +
            "      \"umpSkuId\": 0,\n" +
            "      \"uniqueKey\": \"340457779_36138959\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"activityAlias\": \"\",\n" +
            "      \"activityExtra\": \"\",\n" +
            "      \"activityId\": 0,\n" +
            "      \"activityType\": 0,\n" +
            "      \"alias\": \"3extqrh0ixigi\",\n" +
            "      \"cartCreateTime\": 1510386443,\n" +
            "      \"cartUpdateTime\": 1510386443,\n" +
            "      \"created\": 1510386789,\n" +
            "      \"deliverOption\": \"0\",\n" +
            "      \"deliverTime\": 0,\n" +
            "      \"deliveryTemplateId\": 188250,\n" +
            "      \"goodsId\": 353034040,\n" +
            "      \"goodsInfo\": \"{\\\"is_virtual\\\":0,\\\"supplier_kdt_id\\\":0,\\\"service_tel\\\":null,\\\"goods_date\\\":null,\\\"goods_no\\\":\\\"8809480781117\\\",\\\"buy_way\\\":1,\\\"title\\\":\\\"【最美爆款】眼周新宠,水润双眸·SHANGPREE / 香蒲丽 绿公主眼膜60片/盒 去黑眼圈去眼袋补水淡化细纹 我是大美人精选\\\",\\\"supplier_goods_id\\\":0,\\\"points_price\\\":0,\\\"img_url\\\":\\\"https://img.yzcdn.cn/upload_files/2017/11/05/Fuf9UR1Az3vj4DIj9C3fqgYKSCcZ.jpg\\\",\\\"quota\\\":0,\\\"extra\\\":{\\\"is_present\\\":0},\\\"alias\\\":\\\"3extqrh0ixigi\\\",\\\"storeDeliverySet\\\":null,\\\"mark\\\":0}\",\n" +
            "      \"goodsSnap\": \"05ebcbd277580bb1a0efd560d13f3099\",\n" +
            "      \"goodsType\": 0,\n" +
            "      \"id\": 20033912,\n" +
            "      \"imgUrl\": \"https://img.yzcdn.cn/upload_files/2017/11/05/Fuf9UR1Az3vj4DIj9C3fqgYKSCcZ.jpg\",\n" +
            "      \"isVirtual\": 0,\n" +
            "      \"itemSource\": \"cart\",\n" +
            "      \"kdtId\": 33746,\n" +
            "      \"kdtSessionId\": \"YZ378692657742110720YZueeuqgh4\",\n" +
            "      \"message\": \"\",\n" +
            "      \"num\": 2,\n" +
            "      \"orderNo\": \"E20171111155309085212404\",\n" +
            "      \"originPrice\": 14500,\n" +
            "      \"pageSource\": \"\",\n" +
            "      \"payPrice\": 14500,\n" +
            "      \"pointsPrice\": 0,\n" +
            "      \"postage\": 0,\n" +
            "      \"price\": 14900,\n" +
            "      \"s1\": 0,\n" +
            "      \"s2\": 0,\n" +
            "      \"s3\": 0,\n" +
            "      \"s4\": 0,\n" +
            "      \"s5\": 0,\n" +
            "      \"shopId\": 33746,\n" +
            "      \"shopInfo\": \"{\\\"shop_name\\\":\\\"我是大美人\\\"}\",\n" +
            "      \"sku\": \"[]\",\n" +
            "      \"skuCode\": \"\",\n" +
            "      \"skuId\": 36182761,\n" +
            "      \"title\": \"【最美爆款】眼周新宠,水润双眸·SHANGPREE / 香蒲丽 绿公主眼膜60片/盒 去黑眼圈去眼袋补水淡化细纹 我是大美人精选\",\n" +
            "      \"umpSkuId\": 0,\n" +
            "      \"uniqueKey\": \"353034040_36182761\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"activityAlias\": \"\",\n" +
            "      \"activityExtra\": \"\",\n" +
            "      \"activityId\": 0,\n" +
            "      \"activityType\": 0,\n" +
            "      \"alias\": \"2of630nydao5u\",\n" +
            "      \"cartCreateTime\": 1510386398,\n" +
            "      \"cartUpdateTime\": 1510386398,\n" +
            "      \"created\": 1510386789,\n" +
            "      \"deliverOption\": \"0\",\n" +
            "      \"deliverTime\": 0,\n" +
            "      \"deliveryTemplateId\": 0,\n" +
            "      \"goodsId\": 340647125,\n" +
            "      \"goodsInfo\": \"{\\\"is_virtual\\\":0,\\\"supplier_kdt_id\\\":0,\\\"service_tel\\\":null,\\\"goods_date\\\":null,\\\"goods_no\\\":\\\"8809483660761\\\",\\\"buy_way\\\":1,\\\"title\\\":\\\"【最美爆款·限时包邮】毛孔“吸尘器”·W.Lab 深层洁净清洁撕拉面膜100ml 去皮脂黑头粉刺 我是大美人精选\\\",\\\"supplier_goods_id\\\":0,\\\"points_price\\\":0,\\\"img_url\\\":\\\"https://img.yzcdn.cn/upload_files/2017/11/07/FgkXcmRzQ7HQCQzaKGe7Yu_edUGQ.jpg\\\",\\\"quota\\\":0,\\\"extra\\\":{\\\"is_present\\\":0},\\\"alias\\\":\\\"2of630nydao5u\\\",\\\"storeDeliverySet\\\":null,\\\"mark\\\":0}\",\n" +
            "      \"goodsSnap\": \"a6858ed49596fc03bee80072923035e5\",\n" +
            "      \"goodsType\": 0,\n" +
            "      \"id\": 20033913,\n" +
            "      \"imgUrl\": \"https://img.yzcdn.cn/upload_files/2017/11/07/FgkXcmRzQ7HQCQzaKGe7Yu_edUGQ.jpg\",\n" +
            "      \"isVirtual\": 0,\n" +
            "      \"itemSource\": \"cart\",\n" +
            "      \"kdtId\": 33746,\n" +
            "      \"kdtSessionId\": \"YZ378692657742110720YZueeuqgh4\",\n" +
            "      \"message\": \"\",\n" +
            "      \"num\": 1,\n" +
            "      \"orderNo\": \"E20171111155309085212404\",\n" +
            "      \"originPrice\": 8800,\n" +
            "      \"pageSource\": \"\",\n" +
            "      \"payPrice\": 8800,\n" +
            "      \"pointsPrice\": 0,\n" +
            "      \"postage\": 0,\n" +
            "      \"price\": 9800,\n" +
            "      \"s1\": 0,\n" +
            "      \"s2\": 0,\n" +
            "      \"s3\": 0,\n" +
            "      \"s4\": 0,\n" +
            "      \"s5\": 0,\n" +
            "      \"shopId\": 33746,\n" +
            "      \"shopInfo\": \"{\\\"shop_name\\\":\\\"我是大美人\\\"}\",\n" +
            "      \"sku\": \"[]\",\n" +
            "      \"skuCode\": \"\",\n" +
            "      \"skuId\": 36155145,\n" +
            "      \"title\": \"【最美爆款·限时包邮】毛孔“吸尘器”·W.Lab 深层洁净清洁撕拉面膜100ml 去皮脂黑头粉刺 我是大美人精选\",\n" +
            "      \"umpSkuId\": 0,\n" +
            "      \"uniqueKey\": \"340647125_36155145\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"activityAlias\": \"\",\n" +
            "      \"activityExtra\": \"\",\n" +
            "      \"activityId\": 0,\n" +
            "      \"activityType\": 0,\n" +
            "      \"alias\": \"3nwl5cyak6us2\",\n" +
            "      \"cartCreateTime\": 1510386368,\n" +
            "      \"cartUpdateTime\": 1510386368,\n" +
            "      \"created\": 1510386789,\n" +
            "      \"deliverOption\": \"0\",\n" +
            "      \"deliverTime\": 0,\n" +
            "      \"deliveryTemplateId\": 0,\n" +
            "      \"goodsId\": 323814460,\n" +
            "      \"goodsInfo\": \"{\\\"is_virtual\\\":0,\\\"supplier_kdt_id\\\":0,\\\"service_tel\\\":null,\\\"goods_date\\\":null,\\\"goods_no\\\":\\\"8809326333296\\\",\\\"buy_way\\\":1,\\\"title\\\":\\\"【最美爆款·暖暖推荐】1秒就变白·W.Lab 白雪公主速白霜素颜霜50ml 王丽坤同款 妆前隔离提亮 滋润保湿 我是大美人精选\\\",\\\"supplier_goods_id\\\":0,\\\"points_price\\\":0,\\\"img_url\\\":\\\"https://img.yzcdn.cn/upload_files/2017/11/10/FhtRZ0GbQMTZaBDBrcVSLPca3cgK.jpg\\\",\\\"quota\\\":0,\\\"extra\\\":{\\\"is_present\\\":0},\\\"alias\\\":\\\"3nwl5cyak6us2\\\",\\\"storeDeliverySet\\\":null,\\\"mark\\\":0}\",\n" +
            "      \"goodsSnap\": \"f8dd1bfd59414b0a8e5ee93da07513d5\",\n" +
            "      \"goodsType\": 0,\n" +
            "      \"id\": 20033914,\n" +
            "      \"imgUrl\": \"https://img.yzcdn.cn/upload_files/2017/11/10/FhtRZ0GbQMTZaBDBrcVSLPca3cgK.jpg\",\n" +
            "      \"isVirtual\": 0,\n" +
            "      \"itemSource\": \"cart\",\n" +
            "      \"kdtId\": 33746,\n" +
            "      \"kdtSessionId\": \"YZ378692657742110720YZueeuqgh4\",\n" +
            "      \"message\": \"\",\n" +
            "      \"num\": 1,\n" +
            "      \"orderNo\": \"E20171111155309085212404\",\n" +
            "      \"originPrice\": 14900,\n" +
            "      \"pageSource\": \"\",\n" +
            "      \"payPrice\": 14900,\n" +
            "      \"pointsPrice\": 0,\n" +
            "      \"postage\": 0,\n" +
            "      \"price\": 16900,\n" +
            "      \"s1\": 41080,\n" +
            "      \"s2\": 0,\n" +
            "      \"s3\": 0,\n" +
            "      \"s4\": 0,\n" +
            "      \"s5\": 0,\n" +
            "      \"shopId\": 33746,\n" +
            "      \"shopInfo\": \"{\\\"shop_name\\\":\\\"我是大美人\\\"}\",\n" +
            "      \"sku\": \"[{\\\"k\\\":\\\"规格\\\",\\\"k_id\\\":14,\\\"v\\\":\\\"2支\\\",\\\"v_id\\\":41080}]\",\n" +
            "      \"skuCode\": \"dmr20170503001\",\n" +
            "      \"skuId\": 36180728,\n" +
            "      \"title\": \"【最美爆款·暖暖推荐】1秒就变白·W.Lab 白雪公主速白霜素颜霜50ml 王丽坤同款 妆前隔离提亮 滋润保湿 我是大美人精选\",\n" +
            "      \"umpSkuId\": 0,\n" +
            "      \"uniqueKey\": \"323814460_36180728\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"activityAlias\": \"\",\n" +
            "      \"activityExtra\": \"\",\n" +
            "      \"activityId\": 0,\n" +
            "      \"activityType\": 0,\n" +
            "      \"alias\": \"3659m7btboeea\",\n" +
            "      \"cartCreateTime\": 1504793570,\n" +
            "      \"cartUpdateTime\": 1504793570,\n" +
            "      \"created\": 1510386789,\n" +
            "      \"deliverOption\": \"0\",\n" +
            "      \"deliverTime\": 0,\n" +
            "      \"deliveryTemplateId\": 188250,\n" +
            "      \"goodsId\": 259406314,\n" +
            "      \"goodsInfo\": \"{\\\"is_virtual\\\":0,\\\"supplier_kdt_id\\\":0,\\\"service_tel\\\":null,\\\"goods_date\\\":null,\\\"goods_no\\\":\\\"8809348118086\\\",\\\"buy_way\\\":1,\\\"title\\\":\\\"娜扎同款·可定妆温和防晒·RECIPE 水晶防晒喷雾150ml SPF50+  清爽防水透明保湿防晒 我是大美人精选\\\",\\\"supplier_goods_id\\\":0,\\\"points_price\\\":0,\\\"img_url\\\":\\\"https://img.yzcdn.cn/upload_files/2017/06/12/FkeE9dTpS1wVkqIRH5O6rzhtfa8X.jpg\\\",\\\"quota\\\":0,\\\"extra\\\":{\\\"is_present\\\":0},\\\"alias\\\":\\\"3659m7btboeea\\\",\\\"storeDeliverySet\\\":null,\\\"mark\\\":0}\",\n" +
            "      \"goodsSnap\": \"1f5f2682925a8a9150dedb7527617b20\",\n" +
            "      \"goodsType\": 0,\n" +
            "      \"id\": 20033915,\n" +
            "      \"imgUrl\": \"https://img.yzcdn.cn/upload_files/2017/06/12/FkeE9dTpS1wVkqIRH5O6rzhtfa8X.jpg\",\n" +
            "      \"isVirtual\": 0,\n" +
            "      \"itemSource\": \"cart\",\n" +
            "      \"kdtId\": 33746,\n" +
            "      \"kdtSessionId\": \"YZ378692657742110720YZueeuqgh4\",\n" +
            "      \"message\": \"\",\n" +
            "      \"num\": 1,\n" +
            "      \"orderNo\": \"E20171111155309085212404\",\n" +
            "      \"originPrice\": 6800,\n" +
            "      \"pageSource\": \"75025\",\n" +
            "      \"payPrice\": 6800,\n" +
            "      \"pointsPrice\": 0,\n" +
            "      \"postage\": 0,\n" +
            "      \"price\": 6800,\n" +
            "      \"s1\": 11848,\n" +
            "      \"s2\": 0,\n" +
            "      \"s3\": 0,\n" +
            "      \"s4\": 0,\n" +
            "      \"s5\": 0,\n" +
            "      \"shopId\": 33746,\n" +
            "      \"shopInfo\": \"{\\\"shop_name\\\":\\\"我是大美人\\\"}\",\n" +
            "      \"sku\": \"[{\\\"k\\\":\\\"规格\\\",\\\"k_id\\\":14,\\\"v\\\":\\\"1支\\\",\\\"v_id\\\":11848}]\",\n" +
            "      \"skuCode\": \"8809348118086\",\n" +
            "      \"skuId\": 36157417,\n" +
            "      \"title\": \"娜扎同款·可定妆温和防晒·RECIPE 水晶防晒喷雾150ml SPF50+  清爽防水透明保湿防晒 我是大美人精选\",\n" +
            "      \"umpSkuId\": 0,\n" +
            "      \"uniqueKey\": \"259406314_36157417\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"orderPayment\": {\n" +
            "    \"decrease\": 5000,\n" +
            "    \"isFreePostage\": 1,\n" +
            "    \"itemPay\": 96700,\n" +
            "    \"kdtId\": 33746,\n" +
            "    \"orderNo\": \"E20171111155309085212404\",\n" +
            "    \"pay\": 91700,\n" +
            "    \"postage\": 0,\n" +
            "    \"realPay\": 91700,\n" +
            "    \"shopId\": 33746,\n" +
            "    \"umpActivityResultList\": [\n" +
            "      {\n" +
            "        \"id\": 91528,\n" +
            "        \"name\": \"满减送\",\n" +
            "        \"title\": \"17.6满减（用券需以满减后金额为准）\",\n" +
            "        \"type\": \"meetReduce\",\n" +
            "        \"type_id\": 101\n" +
            "      }\n" +
            "    ],\n" +
            "    \"useUmp\": 1\n" +
            "  },\n" +
            "  \"orderSource\": {\n" +
            "    \"activityId\": 0,\n" +
            "    \"activityType\": 1,\n" +
            "    \"clientIp\": \"183.253.136.102\",\n" +
            "    \"extraInfo\": \"{\\\"userAgent\\\":\\\"Mozilla/5.0 (Linux; Android 5.1.1; vivo Y51A Build/LMY47V; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043622 Safari/537.36 MicroMessenger/6.5.\\\",\\\"bookKey\\\":\\\"201711111552115a06ac2bea22942414\\\"}\",\n" +
            "    \"hasUnvalidGoods\": 0,\n" +
            "    \"kdtId\": 33746,\n" +
            "    \"orderMark\": \"\",\n" +
            "    \"orderNo\": \"E20171111155309085212404\",\n" +
            "    \"seller\": \"\",\n" +
            "    \"shopId\": 33746,\n" +
            "    \"source\": \"weixin11\",\n" +
            "    \"track\": \"\",\n" +
            "    \"userAgent\": \"Mozilla/5.0 (Linux; Android 5.1.1; vivo Y51A Build/LMY47V; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043622 Safari/537.36 MicroMessenger/6.5.13.1100 NetType/WIFI Language/zh_CN\"\n" +
            "  }\n" +
            "}";


    public void testParse() throws Exception {
        BaseOrderParserV1 parserV1 = new BaseOrderParserV1();
        Order order = parserV1.parse(JSON.parseObject(testStr));
        System.out.println(JSON.toJSONString(order, SerializerFeature.SortField, SerializerFeature.PrettyFormat));
    }
}