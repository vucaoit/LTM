package tuan5_1.bai1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Bai1Server {
    public static String findWord(String word){
        BufferedReader reader;
        boolean isValid = false;
        try{
            reader = new BufferedReader(new FileReader(
                    "D:\\tudien.txt"));
            String line;
            while ((line =reader.readLine()) != null) {
                String[] temp = line.split(";");
                if(word.toLowerCase().equals(temp[0].toLowerCase())){
                    return temp[1];
                }
                else{
                    if(word.toLowerCase().equals(temp[1].toLowerCase())){
                        return temp[0];
                    }
                }
            }
            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "Từ này không có trong từ điển";
    }
    public static void main(String[] args) {
        Socket socket = null;
        ServerSocket server = null;
        int port =5000;
        BufferedWriter out = null;
        BufferedReader in = null;
        try{
            server = new ServerSocket(port);
            System.out.println("server starting...");
            String message ="";
            socket = server.accept();
            System.out.println("connecting...");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (!message.equals("bye")){
                message = in.readLine();
                String temp = findWord(message);
                out.write(temp);
                out.newLine();
                out.flush();
            }
            in.close();
            out.close();
            message="";
            socket.close();
            server.close();
            System.out.println("disconnected");

        }
        catch (IOException e){
            System.out.println("Server not valid");
        }
    }
}
