package game24.tap;

import org.springframework.stereotype.Service;

@Service
public class TapService {

    private boolean received;

    public boolean isEndOfRound() {
        return received;
    }

    public void endRound() {
        received = true;
    }

    public void restartGame() {
        received = false;
    }
}
