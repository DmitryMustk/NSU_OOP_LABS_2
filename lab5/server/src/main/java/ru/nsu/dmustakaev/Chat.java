package ru.nsu.dmustakaev;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import static ru.nsu.dmustakaev.Response.*;

public class Chat {
    private final String chatName;
    private final Long messageCapacity;
    private final Long timeoutInMinutes;

    private final Map<UUID, Session> sessions = new ConcurrentHashMap<>();
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final LinkedList<String> messages = new LinkedList<>();

    private final Lock lock = new ReentrantLock();

    private final Logger logger = Logger.getLogger(Chat.class.getName());

    public Chat(String chatName, Long messageCapacity, Long timeoutInMinutes) {
        this.chatName = chatName;
        this.messageCapacity = messageCapacity;
        this.timeoutInMinutes = timeoutInMinutes;
    }

    public Session getSession(UUID sessionID) {
        if (!sessions.containsKey(sessionID)) {
            throw new RuntimeException("Session not found: " + sessionID);
        }
        return sessions.get(sessionID);
    }

    public Long getTimeoutInMinutes() {
        return timeoutInMinutes;
    }

    public void sendResponseToOnlineUsers(String response) {
        for (Session session : sessions.values()) {
            if (session.getSocket().isConnected()) {
                sendResponse(session.getSocket(), response);
            }
        }
    }

    public void sendResponse(Socket socket, String response) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            byte[] messageBytes = response.getBytes();
            out.writeInt(messageBytes.length);
            out.write(messageBytes);
            out.flush();
        } catch (IOException e) {
            logger.warning("Failed to send response: " + e.getMessage());
        }
    }

    private UUID login(Session session) {
        lock.lock();
        UUID uuid = UUID.randomUUID();
        sessions.put(uuid, session);
        String responseToAll = EVENT_RESPONSE.formatted("userlogin", NAME_RESPONSE.formatted(session.getUsername()));
        String responseToOne = SUCCESS_RESPONSE.formatted("");

        sendResponseToOnlineUsers(responseToAll);
        sendResponse(session.getSocket(), responseToOne);

        lock.unlock();
        return uuid;
    }

    public UUID register(User user, Socket socket) {

        if (!users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
            return login(new Session(socket, user.getUsername()));
        }

        if (!users.get(user.getUsername()).getPassword().equals(user.getPassword())) {
            String response = ERROR_RESPONSE.formatted("Invalid username or password");
            sendResponse(socket, response);
            try {
                socket.close();
            } catch (Exception e) {
                logger.warning("Failed to close socket. " + e.getMessage());
            }
            throw new RuntimeException("Invalid username or password");
        }

        return login(new Session(socket, user.getUsername())); //ТУТ ЕБУТ СОКЕТЫ
    }

    public void logout(UUID sessionID) {
        if (!sessions.containsKey(sessionID)) {
            return;
        }
        lock.lock();
        try {
            Session session = sessions.get(sessionID);
            String responseToAll = EVENT_RESPONSE.formatted("userlogout", NAME_RESPONSE.formatted(session.getUsername()));
            String responseToOne = SUCCESS_RESPONSE.formatted("");

            sendResponseToOnlineUsers(responseToAll);
            sendResponse(session.getSocket(), responseToOne);
            if (session.getSocket().isConnected()) {
                session.getSocket().close();
            }
            sessions.remove(sessionID);
        } catch (IOException e) {
            logger.warning("Failed to close session: " + e.getMessage());
        }
        lock.unlock();
    }

    public void sendMessage(UUID sessionID, String message) {
        if (!sessions.containsKey(sessionID) || !sessions.get(sessionID).getSocket().isConnected()) {
            return;
        }
        String fromName = sessions.get(sessionID).getUsername();
        lock.lock();
        if (messages.size() >= messageCapacity) {
            messages.removeFirst();
        }
        messages.addLast(message);
        String response = EVENT_RESPONSE.formatted(
                "message",
                FROM_RESPONSE.formatted(fromName) + MESSAGE_RESPONSE.formatted(message)
        );
        sendResponseToOnlineUsers(response);
        sessions.get(sessionID).extend();
        lock.unlock();
    }

    public void sendRegisteredUsers(UUID sessionID) {
        if (!sessions.containsKey(sessionID) || !sessions.get(sessionID).getSocket().isConnected()) {
            return;
        }
        String response = SUCCESS_RESPONSE.formatted(String.join("",
                users.values().stream()
                        .map(user -> USER_RESPONSE.formatted(NAME_RESPONSE.formatted(user.getUsername())))
                        .toList()));
        Session session = sessions.get(sessionID);
        sendResponse(session.getSocket(), response);
        session.extend();
    }
}
