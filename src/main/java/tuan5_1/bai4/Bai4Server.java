package tuan5_1.bai4;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Bai4Server {
    public static String findInfoFromCMND(String cmnd){
        String url="https://masothue.com/Search/?q="+cmnd+"&type=auto";
        String temp="";
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                .post();
            Elements elements = doc.getElementsByClass("copy");
            temp+="cmnd số "+ cmnd+ " . ";
                temp+="họ tên : "+elements.get(0).text()+". ";
            temp+="mã số thuế : "+elements.get(1).text()+". ";
            temp+="Địa chỉ : "+elements.get(2).text()+". ";
            temp+="Ngày hoạt động : "+elements.get(3).text()+". ";
            temp+="Quản lý bởi : "+elements.get(4).text()+".";
            //System.out.println(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (IndexOutOfBoundsException e){
            return "Không có thông tin về cmnd này";
        }
        return temp;
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
                String temp = "";
                temp=findInfoFromCMND(message);
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
        catch (NullPointerException e){

        }
        catch (IOException e){
            System.out.println("Server not valid");
            e.printStackTrace();
        }
    }
}
