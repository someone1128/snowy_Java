package vip.xiaonuo.common.websocket;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created with IntelliJ IDEA.
 *
 * @ Description:
 * @ ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 		注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@Component
@ServerEndpoint(value = "/ws/{userId}/{type}")
@Slf4j
@NoArgsConstructor
@Data
public class WebSocketService {

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
     */
    private static final CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();
    //当前在线连接数
    private static int onlineCount = 0;
    private Session session;

    private String userId = "";

    /**
     * webSocket 类型
     */
    private String type = "";

    {
        System.out.println("webscoket成功初始化");
    }

    /**
     * 群发自定义消息
     */
    public static WebSocketService sendInfo(String message, String userId, String type) {
        log.info("推送消息到窗口" + userId + "，推送内容:" + message);
        for (WebSocketService item : webSocketSet) {
            try {
                // 为null则全部推送
                if (userId == null) {
                    item.sendMessage(message);
                } else if (item.userId.equals(userId) && item.type.equals(type)) {
                    item.sendMessage(message);
                    return item;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

    public static CopyOnWriteArraySet<WebSocketService> getWebSocketSet() {
        return webSocketSet;
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        log.info("释放的userId为：" + userId);
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId, @PathParam("type") String type) {
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        this.userId = userId;
        this.type = type;
        //在线数加1
        addOnlineCount();
        try {
            sendMessage("isOnlineSucc");
            log.info("有新窗口开始监听:" + userId + ",当前在线人数为:" + getOnlineCount());
            System.out.println("this = " + this);
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket 发生错误", error);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("接受到的消息：" + message);
        sendInfo(message, this.userId, "WxPay");
    }
}
