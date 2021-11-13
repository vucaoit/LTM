package tuan5_1.bai3;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import tuan5_1.bai2.IpInfo;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bai3Server {
    public static double TinhPi(double n){
        int i;
        double pi=0;
        int numberThread=12;
        double circle_points=0; double square_points=0;
        double rand_x, rand_y;
        for ( i = 0; i < (n); i++) {
            rand_x = new Random().nextDouble();
            rand_y = new Random().nextDouble();
            if (rand_x * rand_x + rand_y * rand_y <= 1) {
                circle_points++;
            }
            square_points++;
        }
        pi = (4.0 * circle_points) / square_points*1.0;
//
//        //dung thread
//        for (i = 0; i < numberThread; i++) {
//            CountPointThread countPointThread = new CountPointThread(n/numberThread);
//            countPointThread.start();
//        }
//        double sum_cirle=0;
//        double sum_square=0;
//        for(double x : EXAMPLE.circle){
//            sum_cirle+=x;
//        }
//        for(double x : EXAMPLE.square)sum_square+=x;
//        pi = (4.0 * sum_cirle) / sum_square*1.0;

        return pi;
        // Final Estimated Value
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
                long start = System.currentTimeMillis();
                String temp = "";
                double n=0;
                if(message.equals("bye"))break;
                try{
                    n=Double.parseDouble(message);
                    if(n<1000000){
                        temp = "n phải lớn hơn 1.000.000";
                    }
                    else {
                        double pi = TinhPi(n);
                        long end = System.currentTimeMillis();
                        long elapsedTime = end - start;
                        temp="Số pi là : "+pi+". Thời gian tính hêt "+ (elapsedTime*0.001)+"s";
                    }
                }
                catch (NumberFormatException e){
                    temp="Định dạng số không đúng";
                }
                out.write(temp);
                out.newLine();
                out.flush();
            }
            in.close();
            out.close();
            socket.close();
            server.close();
            System.out.println("disconnected");

        }
        catch (IOException e){
            System.out.println("Server not valid");
        }
    }
}
