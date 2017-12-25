package dsp;

import com.github.nezha.httpfetch.HttpApiConfiguration;
import com.github.nezha.httpfetch.HttpApiService;
import com.github.nezha.httpfetch.SourceReader;
import com.github.nezha.httpfetch.XmlReader;
import http.DspPlatformApi;
import junit.framework.TestCase;
import org.junit.Ignore;
import lombok.val;
import order.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;


/**
 * Created by jerry on 12/15/17.
 */
public class DspStatisticBizTest extends TestCase {
    private Order order1 = new Order();
    private Order order2 = new Order();
    private Order order3 = new Order();
    private Order order4 = new Order();
    private Order order5 = new Order();

    private DspStatisticBiz dspStatisticBiz;

    {
        order1.setOrderNo("Etest1");
        order1.setBookTimestamp(1513320422L);
        order1.setDcps(111L);
        order1.setKdtId(63077L);
        order1.setRealPay(100L);

        order2.setOrderNo("Etest2");
        order2.setBookTimestamp(1513320422L);
        order2.setDcps(222L);
        order2.setKdtId(63077L);
        order2.setRealPay(200L);

        order3.setOrderNo("Etest3");
        order3.setBookTimestamp(1513320422L);
        order3.setDcps(1111L);
        order3.setKdtId(63078L);
        order3.setRealPay(300L);

        order4.setOrderNo("Etest4");
        order4.setBookTimestamp(1513320422L);
        order4.setDcps(333L);
        order4.setKdtId(63079L);
        order4.setRealPay(400L);

        order5.setOrderNo("Etest5");
        order5.setBookTimestamp(1513320422L);
        order5.setDcps(111L);
        order5.setKdtId(63078L);
        order5.setRealPay(300L);
    }


    @Ignore
    public void testUpdateOrders() throws Exception {


        List<Order> orderList = Arrays.asList(order1, order2, order3, order4, order5);
        SourceReader xmlReader = new XmlReader(Arrays.asList("http-api.xml"));
        HttpApiConfiguration configuration = new HttpApiConfiguration();
        configuration.setSourceReaders(Arrays.asList(xmlReader));
        configuration.init();
        HttpApiService service = new HttpApiService(configuration);
        service.init();
        DspPlatformApi api = service.getOrCreateService(DspPlatformApi.class);
        dspStatisticBiz = new DspStatisticBiz(api);
        dspStatisticBiz.DSP_PLATFORM_HOST = "172.17.10.81";
        dspStatisticBiz.DSP_PLATFORM_PORT = "7003";

        for (Order order : orderList) {
            System.out.println(order.getOrderNo());
            System.out.println(dspStatisticBiz.updateOrders(Arrays.asList(order)));
        }

    }
}