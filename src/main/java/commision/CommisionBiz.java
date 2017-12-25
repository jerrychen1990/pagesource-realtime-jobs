package commision;

import com.youzan.bigdata.dspplatform.CpsCommisionDTO;
import com.youzan.bigdata.dspplatform.dto.CpsOrderDTO;
import com.youzan.bigdata.dspplatform.dto.CpsOrderItemDTO;
import http.DspPlatformApi;
import http.vo.CommonResponseVo;
import order.Order;
import order.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CommonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jerry on 11/21/17.
 */
public class CommisionBiz {
    private static final Logger logger = LoggerFactory.getLogger(CommisionBiz.class);
    private DspPlatformApi dspPlatformApi;
    private static final int DSP_API_SUCCESS_CODE = 0;
    public String COMMISION_HOST;
    public String COMMISION_PORT;
    public static String AMOUNT_URL_PATH = "/dspplatform/cps/amount";
    public static String COMMISION_URL_PATH = "/dspplatform/cps/commision";


    public CommisionBiz(DspPlatformApi api) {
        this.dspPlatformApi = api;
    }


    public Long getCommisionAmount(Order order) {
        CpsOrderDTO dto = order2Dto(order);
        String url = "http://" + COMMISION_HOST + ":" + COMMISION_PORT + AMOUNT_URL_PATH;
        CommonResponseVo<Long> responseVo = dspPlatformApi.getCommisionAmount(url, dto);
        if (null == responseVo || responseVo.getCode() != DSP_API_SUCCESS_CODE) {
            logger.error("get commision amount request failed with dto:{}", dto);
            return 0L;
        }
        Long amount = responseVo.getData();
        return amount;
    }

    public Boolean doCommision(Order order, Long amount) {
        CpsCommisionDTO cpsCommisionDTO = getCommisionDto(order, amount);
        String url = "http://" + COMMISION_HOST + ":" + COMMISION_PORT  + COMMISION_URL_PATH;
        CommonResponseVo<Boolean> responseVo = dspPlatformApi.doCommision(url, cpsCommisionDTO);
        if (responseVo.getCode() != DSP_API_SUCCESS_CODE) {
            logger.error("request failed with dto:{}", cpsCommisionDTO);
            return false;
        }
        Boolean rs = responseVo.getData();
        if (!rs) {
            String message = responseVo.getMessage();
            logger.error("commision failed with message:{}", message);
        }
        return rs;
    }


    private CpsCommisionDTO getCommisionDto(Order order, Long amount) {
        CpsCommisionDTO cpsCommisionDTO = new CpsCommisionDTO();
        cpsCommisionDTO.setCommisionAmount(amount);
        CpsOrderDTO cpsOrderDTO = order2Dto(order);
        cpsCommisionDTO.setCpsOrderDTO(cpsOrderDTO);
        return cpsCommisionDTO;
    }


    private CpsOrderDTO order2Dto(Order order) {
        CpsOrderDTO cpsOrderDTO = new CpsOrderDTO();
        cpsOrderDTO.setDcps(order.getDcps());
        cpsOrderDTO.setKdtId(order.getKdtId());
        cpsOrderDTO.setOrderNo(order.getOrderNo());
        cpsOrderDTO.setRealPay(order.getRealPay());
        cpsOrderDTO.setOrderAmount(order.getCurrentPrice());
        cpsOrderDTO.setPostage(order.getPostage());
        cpsOrderDTO.setOrderTime(CommonUtil.timestamp2Date(order.getBookTimestamp()));


        List<CpsOrderItemDTO> cpsOrderItemDTOList = order.getOrderItemList().stream().
                map(e -> orderItem2Dto(e)).collect(Collectors.toList());
        cpsOrderDTO.setItemList(cpsOrderItemDTOList);
        return cpsOrderDTO;
    }

    private CpsOrderItemDTO orderItem2Dto(OrderItem orderItem) {
        CpsOrderItemDTO cpsOrderItemDTO = new CpsOrderItemDTO();
        cpsOrderItemDTO.setDcps(orderItem.getDcps());
        cpsOrderItemDTO.setGoodsId(orderItem.getGoodsId());
        cpsOrderItemDTO.setNum(orderItem.getNum());
        cpsOrderItemDTO.setSkuId(orderItem.getSkuId());
        cpsOrderItemDTO.setTitle(orderItem.getTitle());
        cpsOrderItemDTO.setOriginPrice(orderItem.getCurrentPrice());
        cpsOrderItemDTO.setRealPay(orderItem.getRealPay());
        return cpsOrderItemDTO;
    }

    public static void main(String[] args) {
//        Long st = 1513067332L * 1000;
//        Date d = new Date(st);
//        System.out.println(d);

        String[] strArr = new String[]{"123", "234"};

    }


}
