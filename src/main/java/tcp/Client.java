package tcp;

import java.io.*;
import java.net.Socket;

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

            outputStream.write((firstNumber + ":").getBytes());
            outputStream.write((secondNumber + ":").getBytes());
            outputStream.write((operator + "\n").getBytes());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println(bufferedReader.readLine());
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
