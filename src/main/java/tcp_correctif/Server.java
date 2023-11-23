package tcp_correctif;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

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

    ServerSocket server;

    public Server(int port) throws IOException, UnknownHostException{
        this.server = new ServerSocket(port);
    }

    public void run() throws IOException{
        Socket client = server.accept();
        InputStream in = client.getInputStream();
        OutputStream out = client.getOutputStream();
        while (true){
            int number1 = this.readNumber(in);
            int number2 = this.readNumber(in);
            char operator = this.readOperator(in);

            System.out.println("Client Request : "+number1+" "+operator+" "+number2);

            int result = this.compute(number1, number2, operator);
            System.out.println(result);
            this.sendResponse(out, result);
        }
    }

    public int readNumber(InputStream in) throws IOException{
        byte[] bytes = new byte[4];
        in.read(bytes);
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.rewind();
        return buffer.getInt();
    }

    public char readOperator(InputStream in) throws IOException{
        return (char) in.read();
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

    public void sendResponse(OutputStream out, int result) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(result);
        buffer.rewind();
        out.write(buffer.array());
    }
}
