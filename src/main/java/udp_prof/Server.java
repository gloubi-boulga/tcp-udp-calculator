package udp_prof;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Server{
    public static void main(String[] args){
        try{
            Server server = new Server(4567);
            server.run();
        }
        catch(UnknownHostException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    DatagramSocket socket;

    public Server(int port) throws IOException, UnknownHostException{
        this.socket = new DatagramSocket(port);
    }

    public void run() throws IOException{
        byte[] buffer = new byte[9]; // 8 bytes for 2 numbers and 1 byte for operator
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);

        while (true){
            this.socket.receive(data);
            byte[] dataBytes = data.getData();

            byte[] number1Bytes = Arrays.copyOfRange(dataBytes, 0, 4);
            byte[] number2Bytes = Arrays.copyOfRange(dataBytes, 4, 8);

            int number1 = this.byteToInt(number1Bytes);
            int number2 = this.byteToInt(number2Bytes);
            char operator = (char) dataBytes[8];

            System.out.println("Client Request : "+number1+" "+operator+" "+number2);

            int result = this.compute(number1, number2, operator);
            System.out.println(result);
            this.sendResponse(data, result);
        }
    }

    public int byteToInt(byte[] bytes){
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.rewind();
        return buffer.getInt();
    }

    public int compute(int number1, int number2, int operator){
        int result;
        switch(operator){
            case '+':
                result = number1+number2;
                break;
            case '-':
                result = number1-number2;
                break;
            case '*':
                result = number1*number2;
                break;
            case '/':
                result = number1/number2;
                break;
            default:
                System.out.println("Operateur non supporté ..");
                result =-1;
        }
        return result;
    }

    public void sendResponse(DatagramPacket data, int result) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(result);
        buffer.rewind();
        byte[] resultBytes = buffer.array();

        DatagramPacket response = new DatagramPacket(resultBytes, resultBytes.length, data.getAddress(), data.getPort());
        this.socket.send(response);
    }
}

