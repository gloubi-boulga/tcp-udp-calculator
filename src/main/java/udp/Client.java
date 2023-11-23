package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static tcp.Constant.HOST;
import static tcp.Constant.PORT;

/**
 * @author guho
 */
public class Client {

    private DatagramSocket datagramSocket;

    private void start() throws IOException {
        datagramSocket = new DatagramSocket();
        this.startStateMachine();
    }

    private void stop() {
        datagramSocket.close();
    }

    private void startStateMachine() throws IOException {

        while (true) {
            String firstNumber = CalculatorService.getFromScanner();
            String operator = CalculatorService.getFromScanner();
            String secondNumber = CalculatorService.getFromScanner();

            String dataToSend = (("GHP:" + firstNumber) + ("GHP:" + operator) + ("GHP:" + secondNumber));
            byte[] bytes = dataToSend.getBytes();

            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(HOST), PORT);

            this.datagramSocket.send(packet);

            DatagramPacket receivedPacket = new DatagramPacket(new byte[4], 4);
            datagramSocket.receive(receivedPacket);
            String received = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println(received);
        }

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
    }
}
