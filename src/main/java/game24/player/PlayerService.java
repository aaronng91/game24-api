package game24.player;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlayerService {
    private ArrayList<String> playerList = new ArrayList<>();

    public void addSession(String sessionId) {
        if(playerList.contains(null)) {
            playerList.set(playerList.indexOf(null), sessionId);
        } else {
            playerList.add(sessionId);
        }
    }

    public void removeSession(String sessionId) {
        playerList.set(playerList.indexOf(sessionId), null);
    }

    int getPlayerId(String sessionId) {
        return playerList.indexOf(sessionId) + 1;
    }
}
