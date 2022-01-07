package com.zt.socket;

import com.base.lib.net.L;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.nio.ByteBuffer;

/**
 * WebSocket客户端
 *
 * @author 刘鹏
 * @date 2021-04-20
 */
public class JWebSocketClient extends WebSocketClient {

    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        L.e("JWebSocketClient","JWebSocketClient  onOpen");
    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        L.e("JWebSocketClient","JWebSocketClient  onClose");
    }

    @Override
    public void onError(Exception ex) {
        L.e("JWebSocketClient","JWebSocketClient  onError  "+ex.getMessage());
    }

}