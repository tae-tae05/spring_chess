package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.*;
import websocket.messages.ServerMessage;

import java.io.IOException;
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

    public void sendMessage(Session session, String message) throws IOException {
        if (session.isOpen()) {
            session.getRemote().sendString(message);
        }
    }
    public void sendError(Session session, String errorMessage) throws IOException {
        ErrorM error = new ErrorM(errorMessage);
        sendMessage(session, new Gson().toJson(error));
    }

    public void broadcast(String message, Integer gameId, Session out) throws IOException {
        for (Session session : allSessions.get(gameId)) {
            if (session != out) {
                sendMessage(session, message);
            }
        }
    }
    public void empty(){
        allSessions.clear();
    }

}
