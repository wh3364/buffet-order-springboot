package com.fch.buffetorder.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: BuffetOrder
 * @description: websocket通信
 * @CreatedBy: fch
 * @create: 2022-10-30 11:33
 **/

@Component
@ServerEndpoint("/WebSocket")
@Slf4j
public class WebSocket {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        //最大链接数1
//        if (webSocketSet.size() > 0){
//            return;
//        }
        webSocketSet.add(this); //加入set中
        log.info("【WebSocket消息】有新的连接，总数:{}",webSocketSet.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);//从set中删除
        log.info("【WebSocket消息】连接断开,总数:{}",webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message ){
        log.info("【WebSocket消息】收到客户端发来的消息:{}",message);
    }

    public void sendMessage(String message){
        for (WebSocket webSocket:webSocketSet) {
            log.info("【webSocket消息】广播消息,message={}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
    }
}