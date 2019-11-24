package com.ckguys.websocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * WebSocket服务器
 *
 * @author XYQ
 */
@ServerEndpoint("/ws/websocketServer/{canId}")
public class WebSocketServer {

    public static Map<String, Set<Session>> sessionMap = new ConcurrentHashMap<>();

    /**
     * 存储各个垃圾桶数据的map
     */
    public static Map<String, String> datas = new ConcurrentHashMap<>();
    /**
     * 用来创建"向客户端发送消息"任务的线程池
     */
    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
    /**
     * 通过session来向客户端发送消息
     */
    private Session session;
    /**
     * websocket连接断开时，需要使用future.cancel()方法来取消任务
     */
    private ScheduledFuture<?> future;

    private String canId;

    @OnOpen
    public void onOpen(Session session, @PathParam("canId") String canId) {
        this.canId = canId;
        this.session = session;
        Set<Session> tempSession = sessionMap.get(canId);
        if (tempSession == null) {
            Set<Session> temp = new HashSet<>();
            sessionMap.put(canId, temp);
        }
        assert tempSession != null;
        tempSession.add(session);
        sessionMap.put(canId, tempSession);
        // 开启任务，每隔1s向客户端发送信息
        this.future = service.scheduleAtFixedRate(() -> {
            String state = datas.get(canId);
            if (state != null) {
                sendMessage(state);
            } else {
                sendMessage("0");
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @OnClose
    public void onClose() {
        // 取消任务
        future.cancel(true);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 向客户端发送消息
     *
     * @param message 需要发送的消息
     */
    private void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException("发送消息失败！");
        }
    }
}
