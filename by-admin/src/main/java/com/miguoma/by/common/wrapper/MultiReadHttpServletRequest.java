package com.miguoma.by.common.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 可多次读取的HttpServletRequest包装类
 */
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    private final byte[] body;

    public MultiReadHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        // 读取请求体并缓存
        ServletInputStream inputStream = request.getInputStream();
        body = inputStream.readAllBytes();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException("Not implemented");
            }
        };
    }

    public String getBody() {
        return new String(body);
    }
}
