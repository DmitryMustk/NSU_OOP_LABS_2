package ru.nsu.dmustakaev;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.logging.Logger;

public class Connection extends Thread {
    private final Logger logger = Logger.getLogger(Connection.class.getName());
    private final XmlMapper mapper = new XmlMapper();

    private final Chat chat;
    private final UUID sessionID;
    private final Session session;
    private final Socket socket;

    public Connection(Chat chat, Socket socket) throws IOException {
        this.chat = chat;
        this.socket = socket;
        logger.info("Creating new connection... ");
        DataInputStream in = new DataInputStream(socket.getInputStream());
        Command command = readCommand(in);
        if (!command.getCommandName().equals("login") || command.getUsername().isEmpty() || command.getPassword().isEmpty()) {
            throw new RuntimeException("Invalid command=%s".formatted(command));
        }
        this.sessionID = chat.register(new User(command.getUsername(), command.getPassword()), socket);
        this.session = chat.getSession(sessionID);
        logger.info("User connected to sessionID=%s".formatted(sessionID));
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            while (socket.isConnected()) {
                if (session.isTimeout(chat.getTimeoutInMinutes())) {
                    chat.logout(sessionID);
                    return;
                }
                Command command = readCommand(in);
                logger.info(command.toString());
                switch (command.getCommandName()) {
                    case "logout" -> chat.logout(sessionID);
                    default -> logger.warning("Unknown command=%s".formatted(command));
                }
            }
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    private Command readCommand(DataInputStream in) throws IOException {
        int messageLength = in.readInt();
        logger.info("Message length=%d".formatted(messageLength));
        byte[] messageBytes = new byte[messageLength];
        in.readFully(messageBytes);
        String message = new String(messageBytes, "UTF-8");
        logger.info("Message accepted: length=%d, message=%s".formatted(messageLength, message));
        return mapper.readValue(message, Command.class);
    }
}
