package tuan6.bai3;

import java.io.*;
import java.net.Socket;

public class Bai3Client {

    public static void main(String[] args) {
        int port = 5000;
        String domain = "localhost";
        try{
            Socket socket = new Socket(domain,port);
            System.out.println("connecting");
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line= "";
            while (!line.toLowerCase().equals("bye")){
                line = stdIn.readLine();
                out.write(line);
                out.newLine();
                out.flush();
                if(line.equals("bye"))break;
                String info = in.readLine();
                System.out.println("server : "+info);
            }
            stdIn.close();
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e){
            System.out.println("socket is not avalid");
        }

    }
}
