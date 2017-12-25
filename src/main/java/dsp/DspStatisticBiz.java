package dsp;

import com.youzan.bigdata.dspplatform.dto.OrderInfoDTO;
import http.DspPlatformApi;
import http.vo.CommonResponseVo;
import order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CommonUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by jerry on 12/15/17.
 */
public class DspStatisticBiz {

    private static final Logger logger = LoggerFactory.getLogger(DspStatisticBiz.class);
    private DspPlatformApi dspPlatformApi;
    private static final int DSP_API_SUCCESS_CODE = 0;
    public String DSP_PLATFORM_HOST;
    public String DSP_PLATFORM_PORT;
    public static String URL_PATH ="/dspplatform/statistic/realtimeOrder";




    public DspStatisticBiz(DspPlatformApi api) {
        this.dspPlatformApi = api;
    }


    public Boolean updateOrders(List<Order> orderList) {
        List<OrderInfoDTO> orderInfoDTOList = orderList.stream().
                map(e -> getOrderDetailInfo(e)).collect(Collectors.toList());
        String url = "http://" + this.DSP_PLATFORM_HOST + ":" + this.DSP_PLATFORM_PORT + URL_PATH;
        CommonResponseVo<Boolean> responseVo =  dspPlatformApi.realtimeOrder(url, orderInfoDTOList);
        if(null == responseVo || DSP_API_SUCCESS_CODE != responseVo.getCode()){
            logger.info("requesting to dsp realtime order failed!");
            return false;
        }
        return true;
    }


    private OrderInfoDTO getOrderDetailInfo(Order order) {
        if (null == order) return null;

        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setOrderNo(order.getOrderNo());
        orderInfoDTO.setDcps(order.getDcps());
        orderInfoDTO.setKdtId(order.getKdtId());
        orderInfoDTO.setLastViewTime(CommonUtil.timestamp2Date(order.getBookTimestamp()));
        orderInfoDTO.setBookTime(CommonUtil.timestamp2Date(order.getBookTimestamp()));
        orderInfoDTO.setOrderAmount(order.getRealPay());
        orderInfoDTO.setPayAmount(0L);

        return orderInfoDTO;

    }


}
