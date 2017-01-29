package game24.tap;

import game24.WebsocketTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TapControllerIntegrationTest extends WebsocketTest {

    private static final String WEBSOCKET_TOPIC = "/topic/gotIt";
    private static final String GOT_IT_PATH = "/gotIt";
    private static final String REFRESH_PATH = "/refresh";

    private StompSession session, otherSession;

    @Before
    public void setUp() throws Exception {
        session = connect(WEBSOCKET_TOPIC);
        otherSession = connect(WEBSOCKET_TOPIC);
    }

    @Test
    public void shouldOnlyReceivePlayerIdWhoTappedFirst() throws Exception {
        otherSession.send(GOT_IT_PATH, null);
        String payload = blockingQueue.poll(1, SECONDS);
        String newPayload = blockingQueue.poll(1, SECONDS);

        assertEquals(Integer.parseInt(payload), 2);
        assertEquals(Integer.parseInt(newPayload), 2);

        // Should not receive anymore player IDs after receiving that from the first player
        session.send(GOT_IT_PATH, null);

        payload = blockingQueue.poll(1, SECONDS);

        assertNull(payload);
    }

    @Test
    public void shouldRestartGameAfterRefresh() throws Exception {
        otherSession.send(GOT_IT_PATH, null);
        blockingQueue.poll(1, SECONDS);
        blockingQueue.poll(1, SECONDS);

        session.send(REFRESH_PATH, null);
        session.send(GOT_IT_PATH, null);

        String payload = blockingQueue.poll(1, SECONDS);

        assertEquals(Integer.parseInt(payload), 1);
    }

    @After
    public void tearDown() {
        session.disconnect();
        otherSession.disconnect();
    }
}
