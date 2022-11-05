package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {

    @Override
    public Object run() throws ZuulException {
        log.info("**************** printing logs: ");

        /*
        RequestContext holds Request, response, state information and data for ZuulFilters to access and share.
            The requestContext lives for the duration of the request and is ThreadLocal.
            - ZuulFilter는 Pre -> Route -> Post 필터 순서로 Thread가 진행되는데 이때 쓰레드 안에 Local Storage 이용하여 필요한 데이터를 쉽게 전파하기 위하여 RequestContext 구현체를 사용한다.

            extensions of RequestContext can be substituted by setting the contextClass.
            - threadLocal은 현재 contextClass 라는 이름으로 전파하고 있고, 해당 클래스를 상속하는 클래스를 사용하여 전파하려는 데이터를 확장할 수 있다.

            ReqeustContext is an extension of a ConcurrentHashMap.
            - Why ConcurrentHashMap? 아마도 필터가 여러개이고, 레벨도 같을 경우 동시에 해당 필터들을 진행하기 위하여 ConcurrentHashMap을 사용하지 않았나 생각이 든다.

            (전파하는 데이터 종류)
            HttpRequest, Response 관련 : HttpServletRequest, HttpServletResponse, Throwable,
            HashMap<String,Object> : 라우팅정보, Request/Response 데이터 ( 바디부, 헤더부, Input.OutputStream )
         */

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("**************** " + request.getRequestURI());

        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
}
