package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static tcp.Constant.PORT;

/**
 * @author guho
 */
public class Server {

    private DatagramSocket datagramSocket;

    private void start() throws IOException {
        datagramSocket = new DatagramSocket(PORT);
        this.startStateMachine();
    }

    private void stop() {
        datagramSocket.close();
    }

    private void startStateMachine() throws IOException {

        byte[] buffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            this.datagramSocket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());

            System.out.println(received);
            Integer result = CalculatorService.getResult(received);

            byte[] response = (result+"\n").getBytes();
            DatagramPacket responsePacket = new DatagramPacket(response, response.length, packet.getAddress(), packet.getPort());
            this.datagramSocket.send(responsePacket);
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
