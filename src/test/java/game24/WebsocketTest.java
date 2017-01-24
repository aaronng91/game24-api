package game24;

import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

public class WebsocketTest {

    @LocalServerPort
    private int port;

    protected BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();

    protected StompSession connect(String topic) throws Exception {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(
                asList(new WebSocketTransport(new StandardWebSocketClient()))));

        StompSession session = stompClient
                .connect("ws://localhost:{port}/", new StompSessionHandlerAdapter() {}, this.port)
                .get(1, SECONDS);
        session.subscribe(topic, new DefaultStompFrameHandler());

        return session;
    }

    protected int[] toIntArray(String payload) {
        return Arrays.stream(payload.substring(1, payload.length()-1).split(","))
                .map(String::trim).mapToInt(Integer::parseInt).toArray();
    }

    public class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}
