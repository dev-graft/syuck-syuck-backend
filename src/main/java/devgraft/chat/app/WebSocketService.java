package devgraft.chat.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


// 흠 그러면 연결할 때 구독할 내용을 모두 세팅해야하나?
//
/**
 * @ServerEndpoint(value="binding_url") 웹소켓 연결을 처리할 클래스를 지정하는 Annotation.
 * 해당 Annotation에 지정되면 웹 소켓 연결마다 인스턴스가 생성된다.
 *
 */

@Slf4j
@Service
@ServerEndpoint(value = "/ws-chat")
public class WebSocketService {

    /**
     * WebSocket 연결
     */
    @OnOpen
    public void onOpen(Session s) {
        log.info("onOpen");
    }

    /**
     * 메세지 수신
     */
    @OnMessage
    public void onMessage(String msg, Session session) throws Exception{
        log.info("onMessage: {}", msg);
    }

    /**
     * WebSocket 종료
     */
    @OnClose
    public void onClose(Session s) {
        log.info("onClose");
    }

    @OnError //의도치 않은 에러 발생
    public void onError(Session session, Throwable throwable) {
    }
}
