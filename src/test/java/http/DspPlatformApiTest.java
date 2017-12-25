package http;

import com.alibaba.fastjson.JSONObject;
import com.github.nezha.httpfetch.HttpApiConfiguration;
import com.github.nezha.httpfetch.HttpApiService;
import com.github.nezha.httpfetch.SourceReader;
import com.github.nezha.httpfetch.XmlReader;
import com.youzan.bigdata.dspplatform.dto.CpsOrderDTO;
import http.vo.CommonResponseVo;
import org.junit.Ignore;
import junit.framework.TestCase;

import java.util.Arrays;


/**
 * Created by jerry on 11/20/17.
 */
public class DspPlatformApiTest extends TestCase {

    private static HttpApiService service;

    @Ignore
    public void testGetCommisionAmount() throws Exception {

        SourceReader xmlReader = new XmlReader(Arrays.asList("http-api.xml"));

        HttpApiConfiguration configuration = new HttpApiConfiguration();
        configuration.setSourceReaders(Arrays.asList(xmlReader));
        configuration.init();

        service = new HttpApiService(configuration);
        service.init();

        DspPlatformApi api = service.getOrCreateService(DspPlatformApi.class);
        CpsOrderDTO vo = new CpsOrderDTO();
        vo.setDcps(111L);
        vo.setKdtId(63077L);
        vo.setOrderNo("1251451245");



        CommonResponseVo<Long> rsVo = api.getCommisionAmount("http://",vo);
        System.out.println(rsVo);

        String content = "";
        JSONObject jobj = JSONObject.parseObject(content);


    }
}