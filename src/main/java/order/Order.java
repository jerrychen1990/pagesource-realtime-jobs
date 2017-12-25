package order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import util.CommonUtil;
import java.util.List;


/**
 * Created by jerry on 11/2/17.
 */
@Data
public class Order {

    private String orderNo;
    private Long kdtId;
    private Long dcps = 0L;
    private Long buyerId = 0L;
    private Long customerId = 0L;
    private Long fansId = 0L;
    private Long userId = 0L;
    private Long currentPrice;
    private Long realPay;
    private String userClient = "others";
    private Long youzanFansId = 0L;
    private Long goodsId = 0L;
    private String pageSource = "";
    private Long skuId = 0L;
    private Long bookTime;
    private Long bookTimestamp;
    private String  platform = "";
    private Boolean isCart = false;
    private String nsqTopic;
    private String kdtSessionId = "";
    private Long postage = 0L;
    private List<OrderItem> orderItemList;


    public Long getDcps(){
        return this.dcps;
    }

    public Long getKdtId() {return this.kdtId;}

    @JSONField(name = "userTrackId")
    public String getUserTrackId() {
        if (!CommonUtil.isBlank(userId)) {
            return userId.toString();
        }
        if (!CommonUtil.isBlank(youzanFansId)) {
            return youzanFansId.toString();
        }
        if (!StringUtils.isBlank(kdtSessionId)) {
            return kdtSessionId.toString();
        }
        if (!CommonUtil.isBlank(customerId)) {
            return customerId.toString();
        }
        return "";
    }

    @JSONField(serialize = false)
    public boolean isValid() {
        return orderNo != null && kdtId != null && realPay != null && bookTime != null;
    }

    @JSONField(serialize = false)

    public String getInfo() {
        return orderNo;
    }


}


