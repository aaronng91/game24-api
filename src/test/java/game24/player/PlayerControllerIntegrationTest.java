package game24.player;

import game24.WebsocketTest;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerControllerIntegrationTest extends WebsocketTest {

    private static final String WEBSOCKET_TOPIC = "/topic/playerId";

    private StompSession session, otherSession;
    
    @Test
    public void shouldReceiveUniquePlayerIdUponSubscription() throws Exception {
        session = connect(WEBSOCKET_TOPIC);
        String payload = blockingQueue.poll(1, SECONDS);

        otherSession = connect(WEBSOCKET_TOPIC);
        String newPayload = blockingQueue.poll(1, SECONDS);

        assertEquals(Integer.parseInt(payload), 1);
        assertEquals(Integer.parseInt(newPayload), 2);
    }

    @After
    public void tearDown() {
        session.disconnect();
        otherSession.disconnect();
    }
}
