package tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static tcp.Constant.PORT;

/**
 * @author guho
 */
public class Server {

    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private ServerSocket serverSocket;
    private Socket socket;

    private void start() throws IOException {

        LOGGER.info("Starting server on port {}", PORT);
        serverSocket = new ServerSocket(PORT);

        while (true) {
            socket = serverSocket.accept();
            if(socket.isConnected()) {
                LOGGER.info("Connection established with {}:{}", socket.getInetAddress(), socket.getLocalPort());
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();
            }
        }
    }

    public static void main(String[] args) {
        try {
            final Server server = new Server();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
