package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import static tcp.Constant.*;
import static tcp.Service.getFromScanner;
import static tcp.Service.getStringFromBytes;

/**
 * @author guho
 */
public class Client {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Integer messageCount = 0;

    private void start() throws IOException {

        socket = new Socket(HOST, PORT);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        Thread getNewMessagesThread = new Thread(() -> {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        outputStream.write("DATA".getBytes());
                        outputStream.flush();

                        String input = getStringFromBytes(inputStream);

                        if(input.startsWith("DATABACK") && !input.equals("DATABACK")) {

                            List<String> messages = Arrays.stream(input.split("DATABACK")[1].split(":")).collect(Collectors.toList());

                            if(messageCount < messages.size()) {
                                // It means new messages are in the pool
                                int newMessageCount = messages.size() - messageCount;
                                messageCount = messages.size();
                                List<String> newMessages = messages.subList(messages.size()-newMessageCount, messages.size());
                                for (String newMessage : newMessages) {
                                    System.out.println("[] " + newMessage);
                                }
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, 0, DATA_REFRESH_TIME);
        });

        Thread postNewMessageThread = new Thread(() -> {
            while (true) {
                try {
                    String message = getFromScanner("Enter message : ");
                    outputStream.write(message.getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        getNewMessagesThread.start();
        postNewMessageThread.start();
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
