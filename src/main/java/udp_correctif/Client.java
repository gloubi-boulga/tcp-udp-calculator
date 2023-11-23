package udp_correctif;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;


public class Client{
    public static void main(String[] args){
        try{
            Client client = new Client(4567);
            client.run();
        }
        catch(UnknownHostException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    DatagramSocket socket;
    InetAddress serverAddress;
    int port;
    Scanner scanner;

    public Client(int port) throws IOException, UnknownHostException{
        this.socket = new DatagramSocket();
        this.serverAddress = InetAddress.getByName("127.0.0.1");
        this.port = port;
        this.scanner = new Scanner(System.in);
    }

    public void run() throws IOException{
        while (true) {
            byte[] number1 = this.askForNumber();
            byte[] number2 = this.askForNumber();
            byte operator = this.askForOperator();

            byte[] request = this.createRequestBuffer(number1, number2, operator);

            DatagramPacket data = new DatagramPacket(request, request.length, this.serverAddress, this.port);

            this.socket.send(data);

            int result = this.getResult();

            System.out.println("Réponse du serveur : "+result);
        }
    }

    public byte[] askForNumber(){
        System.out.println("Veuillez saisir un entier :");
        int n = scanner.nextInt();
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(n);
        buffer.rewind();
        return buffer.array();
    }

    public byte askForOperator(){
        System.out.println("Veuillez saisir un operateur [+ - / *] :");
        char operator = scanner.next().charAt(0);
        return (byte) operator;
    }

    public byte[] createRequestBuffer(byte[] number1, byte[] number2, byte operator){
        byte[] request = new byte[9];
        System.arraycopy(number1, 0, request, 0, 4);
        System.arraycopy(number2, 0, request, 4, 4);
        request[8] = operator;
        return request;
    }

    public int getResult() throws IOException{
        DatagramPacket dataReceived = new DatagramPacket(new byte[4], 4);
        socket.receive(dataReceived);

        byte[] response = dataReceived.getData();
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(response);
        buffer.rewind();
        return buffer.getInt();
    }

}

