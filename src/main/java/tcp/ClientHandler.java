package tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import static tcp.Service.*;
import java.util.List;

public class ClientHandler implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);

    private final Socket socket;

    private InputStream inputStream;
    private OutputStream outputStream;

    private List<String> conversations;

    public ClientHandler(Socket socket, List<String> conversations) {
        this.socket = socket;
        this.conversations = conversations;
    }

    @Override
    public void run() {
        try {
            while(true) {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                String input = getStringFromBytes(inputStream);
                LOGGER.info("Input received from client [ {} ]", input);

                if("DATA".equals(input)) {
                    LOGGER.info("Input is a command, processing it and sending response");
                    String joined = String.join(":", conversations);
                    outputStream.write(("DATABACK" + joined).getBytes());
                    outputStream.flush();
                } else {
                    LOGGER.info("Input is a message, adding it to the conversation [ {} ]", conversations.size());
                    conversations.add(input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
