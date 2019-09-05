package com.ckguys.servlet;

import com.ckguys.pojo.Data;
import com.ckguys.tools.BaseServlet;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 分析数据用的Servlet
 *
 * @author XYQ
 */
@WebServlet(name = "AnalyseServlet", urlPatterns = "/AnalyseServlet")
public class AnalyseServlet extends BaseServlet {

    /**
     * 用来装载最近10次的数据
     */
    private static Queue<Data> dataList = new LinkedList<>();

    /**
     * 队列最大保存的数据数量
     */
    private final static int MAX_CAPACITY = 10;

    /**
     * 保存垃圾桶传递过来的数据
     *
     * @param request request
     */
    public void storeState(HttpServletRequest request, HttpServletResponse response) {
        // 垃圾桶传感器传递过来的数据
        String state = request.getParameter("state");
        // 当前时间
        long time = System.currentTimeMillis();
        // 封装成Data对象
        Data temp = new Data(state, time);
        // 如果容量小于10，则直接把数据加入队列
        if (dataList.size() <= MAX_CAPACITY) {
            dataList.offer(temp);
        } else {
            // 如果容量大于10，则移除最早的数据，再把新数据放入队列尾部
            dataList.poll();
            dataList.offer(temp);
        }
        System.out.println(dataList);
    }

    /**
     * 供前端网页调用的接口
     * 用来获取最新的数据
     *
     * @param response response
     */
    public void getData(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            // 将数据集合转换成json字符串
            String json = gson.toJson(dataList);
            // 把json字符串传回前端
            out.println(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
