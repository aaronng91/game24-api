package game24.player;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlayerServiceTest {

    private PlayerService service = new PlayerService();

    @Test
    public void shouldReturnNextAvailablePlayerId() throws Exception {
        service.addSession("abc");
        service.addSession("qwe");
        assertThat(service.getPlayerId("abc"), equalTo(1));
        assertThat(service.getPlayerId("qwe"), equalTo(2));
    }

    @Test
    public void shouldUseAFreedUpPlayerIdIfPossible() throws Exception {
        service.addSession("abc");
        service.addSession("qwe");
        service.addSession("zxc");
        service.removeSession("qwe");
        service.addSession("iop");

        assertThat(service.getPlayerId("iop"), equalTo(2));
    }
}