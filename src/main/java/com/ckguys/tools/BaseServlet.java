package com.ckguys.tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;


/**
 * @author 10727
 */
@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 处理响应编码
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String methodName = request.getParameter("method");
        Method method = null;

        try {
            method = this.getClass().getMethod(methodName,
                    HttpServletRequest.class, HttpServletResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("您要调用的方法：" + methodName + "它不存在！", e);
        }

        ResourceBundle bundle = ResourceBundle.getBundle("applicationContext");
        // 文件夹前缀
        String pathPrefix = bundle.getString("PREFIX");
        // 文件后缀名
        String pathSuffix = bundle.getString("SUFFIX");

        try {
            String result = (String) method.invoke(this, request, response);
            if (result != null && !result.trim().isEmpty()) {
                int index = result.indexOf(":");
                // 如果没有冒号，使用转发
                if (index == -1) {
                    request.getRequestDispatcher(result).forward(request, response);
                } else {// 如果存在冒号
                    // 获取前缀（f或者r）
                    String prefix = result.substring(0, index);
                    // 视图名
                    String viewName = result.substring(index + 1);
                    String forwardText = "f";
                    String redirectText = "r";
                    // 最终路径
                    String finalPath = pathPrefix + viewName + pathSuffix;
                    // 前缀为f表示转发
                    if (forwardText.equals(prefix)) {
                        request.getRequestDispatcher(finalPath).forward(request, response);
                        // 前缀为r表示重定向
                    } else if (redirectText.equals(prefix)) {
                        response.sendRedirect(request.getContextPath() + finalPath);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
