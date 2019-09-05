package com.ckguys.servlet;

import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XYQ
 */
@ServerEndpoint("/ws/testWebsocket")
public class WebSocketTest {

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("-----------------建立连接成功！-----------------");
    }

    @OnClose
    public void onClose() {
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        Gson gson = new Gson();
        List<String> list = new ArrayList<>();
        list.add("testtest1");
        list.add("testtest2");
        String json = gson.toJson(list);
        System.out.println(message);
        session.getBasicRemote().sendText(json);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
