package game24.card;

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
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CardControllerIntegrationTest extends WebsocketTest {

    private static final String WEBSOCKET_TOPIC = "/topic/cards";

    private StompSession session;

    @Before
    public void setUp() throws Exception {
        session = connect(WEBSOCKET_TOPIC);
    }

    @Test
    public void shouldReceiveInitialSetOfCardsUponSubscription() throws Exception {
        String payload = blockingQueue.poll(1, SECONDS);

        assertEquals(toIntArray(payload).length, 4);
    }

    @Test
    public void shouldReceiveNewSetOfCardsUponSubscription() throws Exception {
        String initialPayload = blockingQueue.poll(1, SECONDS);

        session.send("/refresh", null);

        String newPayload = blockingQueue.poll(1, SECONDS);

        assertNotEquals(toIntArray(initialPayload), toIntArray(newPayload));
    }

    @After
    public void tearDown() {
        session.disconnect();
    }
}
