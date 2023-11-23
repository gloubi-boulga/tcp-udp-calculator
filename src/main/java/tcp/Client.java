package tcp;

import java.io.*;
import java.net.Socket;

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

        this.startStateMachine();
    }

    private void stop() throws IOException {
        socket.close();
    }

    private void startStateMachine() throws IOException {

        while (true) {
            String firstNumber = CalculatorService.getFromScanner();
            String operator = CalculatorService.getFromScanner();
            String secondNumber = CalculatorService.getFromScanner();

            outputStream.write(("GHP:" + firstNumber).getBytes());
            outputStream.write(("GHP:" + operator).getBytes());
            outputStream.write(("GHP:" + secondNumber+ "\n").getBytes());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println(bufferedReader.readLine());
        }

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
    }
}
