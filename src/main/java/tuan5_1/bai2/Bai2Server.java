package tuan5_1.bai2;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Bai2Server {
    public static String findInfoIp(String ip){
        String domain_api ="http://ip-api.com/json/";
        String field = "?fields=status,message,continent,country,city";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(domain_api  +  ip  +  field))
                .build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Object obj = JSONValue.parse(response.body().toString());
            JSONObject jsonObject = (JSONObject) obj;
            if(jsonObject.get("status").equals("fail")){
                String mess =  jsonObject.get("message").toString();
                if( mess.equals("invalid query")){
                    return "ip không hợp lệ";
                }
                if(mess.equals("private range")){
                    return "ip private";
                }
            }

            if(jsonObject.get("status").equals("success")){
                IpInfo ipInfo = new IpInfo();
                ipInfo.setChauLuc(jsonObject.get("continent").toString());
                ipInfo.setQuocGia(jsonObject.get("country").toString());
                ipInfo.setThanhPho(jsonObject.get("city").toString());
                return ipInfo.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "fail";
    }
    public static void main(String[] args) {
        Socket socket = null;
        ServerSocket server = null;
        int port =5000;
        BufferedWriter out = null;
        BufferedReader in = null;
        String huongdan = "tìm thông tin ip dùng lệnh req x, trong đó x là địa chỉ ip cần tìm. Dùng lệnh hello để xem public ip và private ip của server.";
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
                String temp = "";
                if(message.equals("hello")){
                    temp = "public ip : "+server.getInetAddress()+" | private ip : "+InetAddress.getLocalHost().getHostAddress();
                }
                else{
                    if(message.split(" ")[0].equals("req") && message.split(" ").length==2){
                        temp=findInfoIp(message.split(" ")[1]);
                    }
                    else {
                        temp=huongdan;
                    }
                }
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
