package ru.nsu.dmustakaev;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import java.util.logging.Logger;

public class Connection extends Thread {
    private final Logger logger = Logger.getLogger(Connection.class.getName());
    private final XmlMapper mapper = new XmlMapper();

    private final Chat chat;
    private final UUID sessionID;
    private final Session session;
    private final Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    public Connection(Chat chat, Socket socket) throws IOException {
        this.chat = chat;
        this.socket = socket;
        logger.info("Connection init" + socket.isClosed());
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        Command command = readCommand(in);
        if (!command.getCommandName().equals("login") || command.getUsername().isEmpty() || command.getPassword().isEmpty()) {
            throw new RuntimeException("Invalid command=%s".formatted(command));
        }
        this.sessionID = chat.register(new User(command.getUsername(), command.getPassword()), socket); //Эта хуйня вырубала мне сокеты блять
        this.session = chat.getSession(sessionID);
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            if (session.isTimeout(chat.getTimeoutInMinutes())) {
                chat.logout(sessionID);
                return;
            }
            try {
                Command command = readCommand(in);
                if (command == null) {
                    continue;
                }
                logger.info("Command=%s.".formatted(command));
                switch (command.getCommandName()) {
                    case "message" -> chat.sendMessage(sessionID, command.getMessage());
                    case "list" -> chat.sendRegisteredUsers(sessionID);
                    case "logout" -> chat.logout(sessionID);
                    default -> logger.warning("Unknown command=%s".formatted(command));
                }
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        }

    }

    private Command readCommand(DataInputStream in) throws IOException {
        int messageLength = in.readInt();
        if (messageLength <= 0) {
            return null;
        }
        byte[] messageBytes = new byte[messageLength];
        in.readFully(messageBytes);
        String message = new String(messageBytes, "UTF-8");
        return mapper.readValue(message, Command.class);
    }
}
