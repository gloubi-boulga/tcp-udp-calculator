package tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static tcp.CalculatorService.getResult;

public class ClientHandler implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);

    private final Socket socket;

    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            byte[] bytes = new byte[11];
            int bytesRead = inputStream.read(bytes);
            String input = new String(bytes, 0, bytesRead, StandardCharsets.UTF_8);
            LOGGER.info("Input received from client [ {} ]", input);

            Integer result = getResult(input);
            outputStream.write(String.valueOf(result).getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
