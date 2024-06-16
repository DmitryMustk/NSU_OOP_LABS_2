package ru.nsu.dmustakaev;

import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Session {
    private ZonedDateTime lastActionTime = ZonedDateTime.now();
    private final String username;
    private final Socket socket;

    public Session(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isTimeout(long timeoutInMinutes) {
        return lastActionTime.plusMinutes(timeoutInMinutes).isBefore(ZonedDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (Session) o;
        return Objects.equals(this.username, that.username) && Objects.equals(this.socket, that.socket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, socket);
    }

    @Override
    public String toString() {
        return "Session[username=" + username + ", socket=" + socket + "]";
    }
}
