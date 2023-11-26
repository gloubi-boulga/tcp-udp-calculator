package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static tcp.CalculatorService.getFromScanner;
import static tcp.Constant.HOST;
import static tcp.Constant.PORT;

/**
 * @author guho
 */
public class Client {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private void start() throws IOException {

        socket = new Socket(HOST, PORT);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        while (true) {
            String firstNumber = getFromScanner("Enter first number : ");
            String secondNumber = getFromScanner("Enter second number : ");
            String operator = getFromScanner("Enter operator : ");

            outputStream.write((firstNumber + ":" + secondNumber + ":" + operator).getBytes());
            outputStream.flush();

            byte[] bytes = new byte[1000];
            int bytesRead = inputStream.read(bytes);
            String input = new String(bytes, 0, bytesRead, StandardCharsets.UTF_8);

            System.out.println(input);
        }
    }

    public static void main(String[] args) {
        try {
            final Client client = new Client();
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
