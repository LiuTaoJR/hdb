package com.xq.hdb.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;


public class ResponseWrapper extends HttpServletResponseWrapper {
    private ResponseWrapper.OutputStreamWrapper outputStream;
    private ResponseWrapper.PrintWriterWrapper printWriter;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);

        try {
            OutputStream originOutputStream = response.getOutputStream();
            PrintWriter originWriter = new PrintWriter(originOutputStream);
            this.outputStream = new ResponseWrapper.OutputStreamWrapper(originOutputStream);
            this.printWriter = new ResponseWrapper.PrintWriterWrapper(originWriter, this.outputStream.getBranch());
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return this.printWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return this.outputStream;
    }

    public byte[] toByteArray() {
        return this.outputStream.toByteArray();
    }

    @Override
    public ServletResponse getResponse() {
        return this;
    }

    private class PrintWriterWrapper extends PrintWriter {
        private PrintWriter branch;

        public PrintWriterWrapper(Writer out, OutputStream outputStreamBranch) {
            super(out);
            this.branch = new PrintWriter(outputStreamBranch);
        }

        @Override
        public void write(char[] buf, int off, int len) {
            super.write(buf, off, len);
            super.flush();
            this.branch.write(buf, off, len);
            this.branch.flush();
        }

        @Override
        public void write(String s, int off, int len) {
            super.write(s, off, len);
            super.flush();
            this.branch.write(s, off, len);
            this.branch.flush();
        }

        @Override
        public void write(int c) {
            super.write(c);
            super.flush();
            this.branch.write(c);
            this.branch.flush();
        }

        @Override
        public void flush() {
            super.flush();
            this.branch.flush();
        }
    }

    private class OutputStreamWrapper extends ServletOutputStream {
        private OutputStream origin;
        private ByteArrayOutputStream branch;

        public OutputStreamWrapper(OutputStream origin) {
            this.origin = origin;
            this.branch = new ByteArrayOutputStream();
        }

        @Override
        public synchronized void write(int b) throws IOException {
            this.origin.write(b);
            this.branch.write(b);
        }

        @Override
        public void flush() throws IOException {
            super.flush();
            this.branch.flush();
        }

        @Override
        public void close() throws IOException {
            try {
                super.close();
            } finally {
                this.branch.close();
            }

        }

        public ByteArrayOutputStream getBranch() {
            return this.branch;
        }

        public byte[] toByteArray() {
            return this.branch.toByteArray();
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {
        }
    }
}
