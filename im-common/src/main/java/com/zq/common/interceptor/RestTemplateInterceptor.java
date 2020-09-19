package com.zq.common.interceptor;

import com.zq.common.utils.TokenUtil;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author dzeb
 * @version 1.0
 * @Description set account to http request header
 * @createTime 2020/9/19 21:43
 */
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().add(TokenUtil.REQUEST_TOKEN_HEADER, TokenUtil.getLoginUser().getAccount());
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
