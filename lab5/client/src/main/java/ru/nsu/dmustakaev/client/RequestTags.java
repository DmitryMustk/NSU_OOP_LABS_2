package ru.nsu.dmustakaev.client;

public interface RequestTags {
    String COMMAND_REQUEST = "<command name=\"%s\">%s</command>";
    String USERNAME_REQUEST = "<username>%s</username>";
    String PASSWORD_REQUEST = "<password>%s</password>";
    String MESSAGE_REQUEST = "<message>%s</message>";
}