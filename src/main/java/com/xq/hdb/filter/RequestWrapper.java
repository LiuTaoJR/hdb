package com.xq.hdb.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private ByteArrayOutputStream outputStream;
    private volatile Map<String, String[]> parameterMap;
    private boolean isMultipart = false;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        this.isMultipart = this.isMultipart(request);
        if (!this.isMultipart) {
            this.cacheInputStream();
        }

    }

    private boolean isMultipart(HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
    }

    public byte[] getContentAsByteArray() {
        return this.outputStream != null ? this.outputStream.toByteArray() : new byte[0];
    }

    private void cacheParamMap() {
        if (this.isFormPost()) {
            Map<String, List> params = this.getQueryParam();
            String body = new String(this.getContentAsByteArray());
            if (null != body && !"".equals(body)) {
                String[] nameAndVals = body.split("&");
                String[] var4 = nameAndVals;
                int var5 = nameAndVals.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    String nameAndVal = var4[var6];
                    String[] nameVal = nameAndVal.split("=");
                    List existVal = (List) params.get(nameVal[0]);
                    if (existVal != null) {
                        existVal.add(nameVal.length > 1 ? nameVal[1] : "");
                    } else {
                        params.put(nameVal[0], Arrays.asList(nameVal.length > 1 ? this.decode(nameVal[1]) : ""));
                    }
                }
            }

            this.parameterMap = new HashMap();
            Iterator var10 = params.keySet().iterator();

            while (var10.hasNext()) {
                String key = (String) var10.next();
                this.parameterMap.put(key, (String[]) ((String[]) ((List) params.get(key)).toArray()));
            }
        } else {
            this.parameterMap = super.getParameterMap();
        }

    }

    private Map<String, List> getQueryParam() {
        Map<String, List> result = new HashMap();
        String queryString = super.getQueryString();
        if (null != queryString && !"".equals(queryString.trim())) {
            try {
                queryString = URLDecoder.decode(queryString, "UTF-8");
            } catch (UnsupportedEncodingException var10) {
                return result;
            }

            String[] queryParams = queryString.split("&");
            String[] var4 = queryParams;
            int var5 = queryParams.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String queryParam = var4[var6];
                String[] paramAndValue = queryParam.split("=");
                List<String> existVal = (List) result.get(paramAndValue[0]);
                if (existVal != null) {
                    existVal.add(paramAndValue.length > 1 ? paramAndValue[1] : "");
                } else {
                    result.put(paramAndValue[0], Arrays.asList(paramAndValue.length > 1 ? this.decode(paramAndValue[1]) : ""));
                }
            }

            return result;
        } else {
            return result;
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this.isMultipart) {
            return super.getInputStream();
        } else {
            if (this.outputStream == null) {
                this.cacheInputStream();
            }

            return new RequestWrapper.CachedServletInputStream();
        }
    }

    public String getRequestUri() {
        return super.getScheme() + "://" + super.getServerName() + ":" + super.getServerPort() + super.getRequestURI() + (super.getQueryString() != null ? "?" + super.getQueryString() : "");
    }

    private void cacheInputStream() {
        try {
            ServletInputStream in = super.getInputStream();
            this.outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            boolean var3 = false;

            int len;
            while ((len = in.read(bytes)) != -1) {
                this.outputStream.write(bytes, 0, len);
            }
        } catch (IOException var4) {
        }

    }

    @Override
    public String getParameter(String name) {
        if (this.isMultipart) {
            return super.getParameter(name);
        } else {
            if (this.parameterMap == null) {
                this.cacheParamMap();
            }

            String[] values = (String[]) this.parameterMap.get(name);
            return values != null && values.length >= 1 ? ((String[]) this.parameterMap.get(name))[0] : null;
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (this.isMultipart) {
            return super.getParameterMap();
        } else {
            if (this.parameterMap == null) {
                this.cacheParamMap();
            }

            return this.parameterMap;
        }
    }

    @Override
    public Enumeration<String> getParameterNames() {
        if (this.isMultipart) {
            return super.getParameterNames();
        } else {
            if (this.parameterMap == null) {
                this.cacheParamMap();
            }

            return Collections.enumeration(this.parameterMap.keySet());
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        if (this.isMultipart) {
            return super.getParameterValues(name);
        } else {
            if (this.parameterMap == null) {
                this.cacheParamMap();
            }

            return (String[]) this.parameterMap.get(name);
        }
    }

    private boolean isFormPost() {
        String contentType = this.getContentType();
        return contentType != null && contentType.contains("application/x-www-form-urlencoded") && "POST".equalsIgnoreCase(this.getMethod());
    }

    private String decode(String val) {
        String result = null;

        try {
            result = URLDecoder.decode(val, "UTF-8");
        } catch (UnsupportedEncodingException var4) {
            log.error("", var4);
        }

        return result;
    }

    private class CachedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream input;
        private boolean isFinish = false;

        public CachedServletInputStream() {
            this.input = new ByteArrayInputStream(RequestWrapper.this.outputStream.toByteArray());
        }

        public int read() throws IOException {
            int result = this.input.read();
            if (-1 == result) {
                this.isFinish = true;
            }

            return result;
        }

        @Override
        public boolean isFinished() {
            return this.isFinish;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
        }
    }
}
