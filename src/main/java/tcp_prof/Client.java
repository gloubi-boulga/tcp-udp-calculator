package tcp_prof;

import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
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

    Socket socket;
    Scanner scanner;

    public Client(int port) throws IOException, UnknownHostException{
        this.socket = new Socket("127.0.0.1", port);
        this.scanner = new Scanner(System.in);
    }

    public void run() throws IOException{
        OutputStream out = this.socket.getOutputStream();
        InputStream in = this.socket.getInputStream();

        while (true) {
            byte[] number1 = this.askForNumber();
            byte[] number2 = this.askForNumber();
            byte operator = this.askForOperator();

            out.write(number1);
            out.write(number2);
            out.write(operator);

            int result = this.getResult(in);

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

    public int getResult(InputStream in) throws IOException{
        byte[] response = new byte[4];
        in.read(response);
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(response);
        buffer.rewind();
        return buffer.getInt();
    }

}
