package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static tcp.Constant.PORT;

/**
 * @author guho
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private void start() throws IOException {

        serverSocket = new ServerSocket(PORT);
        socket = serverSocket.accept();

        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        this.startStateMachine();
    }

    private void stop() throws IOException {
        serverSocket.close();
        socket.close();
    }

    private void startStateMachine() throws IOException {

        while (true) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String input = bufferedReader.readLine();

            System.out.println(input);
            Integer result = CalculatorService.getResult(input);

            outputStream.write((result+"\n").getBytes());
        }
    }

    public static void main(String[] args) {
        try {
            final Server server = new Server();
            server.start();
        } catch (IOException e) {
            System.exit(-1);
        }
    }
}
