package http;

import com.github.nezha.httpfetch.HttpApi;
import com.github.nezha.httpfetch.Header;
import com.github.nezha.httpfetch.resolver.RequestBody;
import com.github.nezha.httpfetch.resolver.URL;
import com.youzan.bigdata.dspplatform.CpsCommisionDTO;
import com.youzan.bigdata.dspplatform.dto.CpsOrderDTO;
import com.youzan.bigdata.dspplatform.dto.OrderInfoDTO;
import http.vo.CommonResponseVo;
import java.util.List;

/**
 * Created by jerry on 11/20/17.
 */
public interface DspPlatformApi {
    @HttpApi(method = "POST", timeout = 1000, headers = {@Header(key = "Content-type", value = "application/json;charset=UTF-8")})
    CommonResponseVo<Long> getCommisionAmount(@URL String url, @RequestBody CpsOrderDTO cpsOrderDTO);

    @HttpApi(method = "POST", timeout = 1000, headers = {@Header(key = "Content-type", value = "application/json;charset=UTF-8"), @Header(key = "X-Service-Chain", value = "{\"name\": \"prj565\"}")})
    CommonResponseVo<Boolean> doCommision(@URL String url, @RequestBody CpsCommisionDTO cpsCommisionDTO);

    @HttpApi(method = "POST", timeout = 1000, headers = {@Header(key = "Content-type", value = "application/json;charset=UTF-8")})
    CommonResponseVo<Boolean> realtimeOrder(@URL String url, @RequestBody List<OrderInfoDTO> orderInfoDTOList);

}
