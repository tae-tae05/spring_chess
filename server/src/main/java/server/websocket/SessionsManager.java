package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SessionsManager {
    private final Map<Integer, Set<Session>> allSessions = new ConcurrentHashMap<>();

    public void addSession(Integer gameId, Session session) {
        if(!allSessions.containsKey(gameId)) {
            allSessions.put(gameId, Collections.synchronizedSet(new HashSet<>()));
        }
        allSessions.get(gameId).add(session);
    }
    public void removeSession(Integer gameId, Session session) {
        if(allSessions.containsKey(gameId)){
            allSessions.get(gameId).remove(session);
        }
    }
    public Map<Integer, Set<Session>> getSessions(){
        return allSessions;
    }
    public void empty(){
        allSessions.clear();
    }
}
