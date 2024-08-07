package ru.nsu.dmustakaev.client;

import static ru.nsu.dmustakaev.client.RequestTags.*;

public class CommandBuilder {

    public String createMessageCommand(String message) {
        return COMMAND_REQUEST.formatted("message", MESSAGE_REQUEST.formatted(message));
    }

    public String createLoginCommand(String username, String password) {
        return COMMAND_REQUEST.formatted(
                "login", USERNAME_REQUEST.formatted(username) + PASSWORD_REQUEST.formatted(password));
    }

    public String createListCommand() {
        return COMMAND_REQUEST.formatted("list", "");
    }

    public String createLogoutCommand() {
        return COMMAND_REQUEST.formatted("logout", "");
    }
}
