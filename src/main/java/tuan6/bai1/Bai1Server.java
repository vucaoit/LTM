package tuan6.bai1;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Bai1Server {
    public static ArrayList<String> TraCuuDiemSinhVien(String mssv){
        String url="http://thongtindaotao.sgu.edu.vn/Default.aspx?page=xemdiemthi&id="+mssv;
        ArrayList<String> list = new ArrayList<>();
        String domain = "http://thongtindaotao.sgu.edu.vn/";
        try {
            String userAgent ="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36";
            String urlLogin = domain+"default.aspx?page="+mssv+"&flag=XemDiemThi";
            Connection.Response  response = Jsoup.connect(urlLogin).method(Connection.Method.GET).execute();
            Document loginpage = response.parse();
            response = Jsoup.connect("http://thongtindaotao.sgu.edu.vn/Default.aspx?page=xemdiemthi&id=3118410037")
                    .userAgent(userAgent)
                    .followRedirects(true)
                    .cookies(response.cookies())
                    .data("__VIEWSTATE",loginpage.getElementById("__VIEWSTATE").val())
                    .data("__VIEWSTATEGENERATOR",loginpage.getElementById("__VIEWSTATEGENERATOR").val())
                    .data("ctl00$ContentPlaceHolder1$ctl00$txtChonHK","20191")
                    .data("ctl00$ContentPlaceHolder1$ctl00$btnChonHK","xem")
                    .data("__EVENTTARGET","")
                    .data("__EVENTARGUMENT","")
                    .method(Connection.Method.POST)
                    .execute();
            loginpage = response.parse();
           // Element element = doc.getElementById("ctl00_ContentPlaceHolder1_ctl00_div1").children().get(0).children().get(0);
            System.out.println(loginpage.body().getElementById("ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblMaSinhVien"));
            System.out.println(loginpage.body().getElementById("ctl00_ContentPlaceHolder1_ctl00_div1"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        TraCuuDiemSinhVien("3118410037");
//        BufferedWriter out = null;
//        BufferedReader in = null;
//        ServerSocket server = null;
//        Socket socket = null;
//        int port = 5000;
//        Jsoup jsoup = null;
//        String message = null;
//        try{
//            server = new ServerSocket(port);
//            socket = server.accept();
//            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            while (!message.equals("bye")){
//                message = in.readLine();
//
//            }
//        }
//        catch (IOException e){
//
//        }
    }
}
