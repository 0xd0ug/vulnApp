package com.example.vulnapp;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    private static final Logger logger = (Logger) LogManager.getLogger(HelloServlet.class);

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String param;
        String header;
        out.println("<html><body>");

        // Print and log all parameters

        Map<String, String[]> paramsMap = request.getParameterMap();
        out.println("Parameter count: " + paramsMap.size() + "<BR>");
        for(String paramName: paramsMap.keySet()) {
            param = paramName + ":" + paramsMap.get(paramName)[0];
            out.println(param + "<BR>");
            logger.error(param);
        }

        // Print and log all headers

        for(String headerName: Collections.list(request.getHeaderNames())) {
            header = headerName + ": " + request.getHeader(headerName);
            out.println(header + "<BR>");
            logger.error(header);
        }

        out.println("Java version " + System.getProperty("java.version"));
        out.println("<BR>Log4j version " + org.apache.logging.log4j.LogManager.class.getPackage().getImplementationVersion());
        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }


    public void destroy() {
    }
}