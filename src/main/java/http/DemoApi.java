package http;

import com.github.nezha.httpfetch.Header;
import com.github.nezha.httpfetch.HttpApi;
import com.github.nezha.httpfetch.QueryParam;
import com.github.nezha.httpfetch.resolver.URL;

/**
 * Created by jerry on 12/25/17.
 */
public interface DemoApi {
    @HttpApi(method = "GET", timeout = 1000, headers = {@Header(key = "user-agent", value = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")})
    String getHtml(@URL String url, @QueryParam("content") String content);
}
