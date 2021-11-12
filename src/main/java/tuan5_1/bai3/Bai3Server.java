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
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bai3Server {
    public static double TinhPi(double n){
        int i;
        double INTERVAL = n;
        double origin_dist, pi=0;
        double circle_points = 0, square_points = 0;
        // Total Random numbers generated = possible x
        // values * possible y values
        for (i = 0; i < 10; i++) {
            CountPointThread countPointThread = new CountPointThread(n/10);
            countPointThread.run(circle_points,square_points);
        }
        pi = (4.0 * circle_points) / square_points*1.0;
        // Final Estimated Value
        return pi;
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
            Date date = new Date();
            long timeStart = date.getTime();
            System.out.println("connecting...");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (!message.equals("bye")){
                message = in.readLine();
                String temp = "";
                double n=0;
                if(message.equals("bye"))break;
                try{
                    n=Double.parseDouble(message);
                    if(n<1000000){
                        temp = "n phải lớn hơn 1.000.000";
                    }
                    else {
                        long milisecond = new Date().getTime()-timeStart;
                            String time = String.format("%02d phút, %02d giây",
                                    TimeUnit.MILLISECONDS.toMinutes(milisecond),
                                    TimeUnit.MILLISECONDS.toSeconds(milisecond) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milisecond)));
                        temp="Số pi là : "+TinhPi(n)+". Thời gian tính hêt "+ time;
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
