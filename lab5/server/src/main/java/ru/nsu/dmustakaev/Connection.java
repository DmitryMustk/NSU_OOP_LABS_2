package ru.nsu.dmustakaev;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.logging.Logger;

public class Connection extends Thread {
    private final Logger logger = Logger.getLogger(Connection.class.getName());
    private final UUID sessionID;

    public Connection(Socket socket) throws IOException {
        try (DataInputStream in = new DataInputStream(socket.getInputStream())){
            String command = readCommand(in);
            if(!command.contains("login")) {
                throw new RuntimeException("Invalid command=%s".formatted(command));
            }
            this.sessionID = UUID.randomUUID();
            logger.info("User connected to sessionID=%s".formatted(sessionID));
        }
    }

    private String readCommand(DataInputStream in) throws IOException {
        StringJoiner joiner = new StringJoiner("");
        try {
            int messageLength = in.readInt();
            while (messageLength > joiner.length()) {
                joiner.add(in.readUTF());
            }
            return joiner.toString();
        } finally {
            logger.info("User logout");
        }
    }
}
