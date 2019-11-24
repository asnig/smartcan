package com.ckguys.servlet;

import com.ckguys.pojo.Data;
import com.ckguys.tools.BaseServlet;
import com.ckguys.websocket.WebSocketServer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.util.Map;
import java.util.Set;

/**
 * 分析数据用的Servlet
 *
 * @author XYQ
 */
@WebServlet(name = "AnalyseServlet", urlPatterns = "/AnalyseServlet")
public class AnalyseServlet extends BaseServlet {


    /**
     * 保存垃圾桶传递过来的数据
     *
     * @param request request
     */
    public void storeState(HttpServletRequest request, HttpServletResponse response) {
        // 传送数据的垃圾桶的id
        String canId = request.getParameter("t");
        // 垃圾桶传感器传递过来的数据
        String state = request.getParameter("state");
        // 当前时间
        long time = System.currentTimeMillis();
        // 封装成Data对象 暂时没用
        Data temp = new Data(canId, state, time);
        WebSocketServer.datas.put(canId, state);
        WebSocketServer.sessionMap.get(canId);

    }

    public void broadCast(Map<String, Set<Session>> map) {

    }
}
