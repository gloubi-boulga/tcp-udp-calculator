package tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static tcp.CalculatorService.getResult;
import static tcp.Constant.PORT;

/**
 * @author guho
 */
public class Server {

    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private void start() throws IOException {

        LOGGER.info("Starting server on port {}", PORT);
        serverSocket = new ServerSocket(PORT);

        socket = serverSocket.accept();
        if(socket.isConnected()) {
            LOGGER.info("Connection established with {}:{}", socket.getInetAddress(), socket.getLocalPort());
        }

        while (true) {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            byte[] bytes = new byte[11];
            int bytesRead = inputStream.read(bytes);
            String input = new String(bytes, 0, bytesRead, StandardCharsets.UTF_8);
            LOGGER.info("Input received from client [ {} ]", input);

            Integer result = getResult(input);
            outputStream.write(String.valueOf(result).getBytes());
            outputStream.flush();
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
