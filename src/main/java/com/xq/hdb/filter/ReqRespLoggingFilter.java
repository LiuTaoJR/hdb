package com.xq.hdb.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 日志拦截器
 */
@Slf4j
@WebFilter
public class ReqRespLoggingFilter implements Filter, Ordered {

    //是否开启日志
    @Value("${hdb.isOpenLog:false}")
    private  boolean isOpenLog;

    @Value("#{'${hdb.notLogFilerPath}'.split(',')}")
    private List<String> NOT_FILTER_PATH;
    @Override
    public int getOrder() {
        return -1;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //转换
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        if (log.isInfoEnabled()) {

            RequestWrapper requestWrapper = new RequestWrapper(req);
            ResponseWrapper responseWrapper = new ResponseWrapper(resp);
            boolean isFilter = isFilter(requestWrapper.getRequestUri());
            if(isOpenLog && isFilter){
                this.logRequest(requestWrapper);
            }
            chain.doFilter(requestWrapper, responseWrapper);
            if(isOpenLog && isFilter){
                this.logResponse(responseWrapper);
            }

        } else {
            chain.doFilter(req, resp);
        }

    }

    private boolean isFilter(String requestUri) {
        if(!CollectionUtils.isEmpty(NOT_FILTER_PATH)){
            for (String notFilterUrl : NOT_FILTER_PATH) {
                if(requestUri.contains(notFilterUrl)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private void logRequest(RequestWrapper request) {
        try {
            StringBuilder b = new StringBuilder();
            b.append('\n');
            this.printRequestLine(b, request);
            this.printRequestHeaders(b, request);
            this.printRequestBody(b, request);

            log.info("{}\n", b.toString());
        } catch (Exception var3) {
            log.error("{}", var3);
        }

    }

    private void logResponse(ResponseWrapper response) {
        try {
            StringBuilder b = new StringBuilder();
            b.append('\n');
            this.printResponseLine(b, response);
            this.printResponseHeaders(b, response);
            this.printResponseBody(b, response);

            log.info("{}\n", b.toString());
        } catch (Exception var3) {
            log.error("{}", var3);
        }

    }

    private void printRequestLine(StringBuilder b, RequestWrapper request) {
        String requestUri = request.getRequestUri();
        b.append("> * ").append("Server in-bound request");
        b.append("> ").append(request.getMethod()).append(" ").append(requestUri).append('\n');
    }

    private void printResponseLine(StringBuilder b, ResponseWrapper response) {
        b.append("< * ").append("Server out-bound response");
        b.append("< ").append(Integer.toString(response.getStatus())).append('\n');
    }

    private void printRequestHeaders(StringBuilder b, RequestWrapper request) {
        Enumeration headerNameEnum = request.getHeaderNames();

        while(headerNameEnum.hasMoreElements()) {
            String headerName = (String)headerNameEnum.nextElement();
            String headerValue = request.getHeader(headerName);
            b.append("> ").append(headerName).append(": ").append(headerValue).append('\n');
        }

    }

    private void printResponseHeaders(StringBuilder b, ResponseWrapper response) {
        Collection<String> headerNames = response.getHeaderNames();
        Iterator var4 = headerNames.iterator();

        while(var4.hasNext()) {
            String headerName = (String)var4.next();
            b.append("< ").append(headerName).append(": ").append(response.getHeader(headerName)).append('\n');
        }

    }

    private void printRequestBody(StringBuilder b, RequestWrapper request) {
        String body = new String(request.getContentAsByteArray());
        b.append(body).append('\n');
    }

    private void printResponseBody(StringBuilder b, ResponseWrapper response) {
        String body;
        if (null != response.getHeader("Content-Type") && response.getHeader("Content-Type").equals("application/octet-stream")) {
            body = "不显示Content-Type为application/octet-stream类型的body";
        } else {
            body = new String(response.toByteArray());

        }

        b.append(body);
    }
}
