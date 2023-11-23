package tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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

        while (true) {

            socket = serverSocket.accept();
            if(socket.isConnected()) {
                LOGGER.info("Connection established with {}:{}", socket.getInetAddress(), socket.getLocalPort());
            }

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String input = bufferedReader.readLine();

            LOGGER.info("Input received from client [ {} ]", input);

            Integer result = getResult(input);
            outputStream.write((result+"\n").getBytes());
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
