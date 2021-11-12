package tuan6.bai2;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Bai2Server {
    public static String checkID(String link){
        String temp="";
        String id="";
        int index = link.indexOf(".html");
        int indexStart = 0;
        try{
            for(int i=index;;i--){
                if(link.charAt(i)=='p'){
                    indexStart=i+1;
                    break;
                }
            }
        }
        catch (StringIndexOutOfBoundsException e){
            return "";
        }
        return link.substring(indexStart,index);
    }
    public static String TraCuuThongTinSanPham(String link){
        String product_id="";
        product_id = checkID(link);
        String url="https://tiki.vn/api/v2/products/"+product_id;
        int limitReview=10;
        String api = "https://tiki.vn/api/v2/reviews?limit="+limitReview+"&product_id="+product_id;
        Document doc = null;
        Document docReview = null;
        String result="";
        try {
            Product prodcut = new Product();
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36")
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .ignoreContentType(true)
                    .get();
            docReview  = Jsoup.connect(api)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36")
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .ignoreContentType(true)
                    .get();
            try {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(doc.body().text());
                JSONObject object = (JSONObject) new JSONParser().parse(docReview.body().text());
                JSONArray arr = (JSONArray) object.get("data");

                prodcut.setName(jsonObject.get("name").toString());
                prodcut.setPrice(jsonObject.get("price").toString());

                ArrayList<String> map = new ArrayList<>();

                for(int i=0;i<arr.size();i++){
                    JSONObject temp = (JSONObject) arr.get(i);
                    map.add(temp.get("content").toString());
                }
                prodcut.setMap(map);
                Gson gson = new Gson();
                result = gson.toJson(prodcut);
                return result;
            } catch (ParseException e) {

            }
        } catch (IOException e) {
            result="link sản phẩm không tìm thấy";
            return  result;
        }
        return result;
    }
    public static void main(String[] args) {
        BufferedWriter out = null;
        BufferedReader in = null;
        ServerSocket server = null;
        Socket socket = null;
        int port = 5000;
        Jsoup jsoup = null;
        String message = "";
        try{
            server = new ServerSocket(port);
            System.out.println("server starting...");
            socket = server.accept();
            System.out.println("connecting...");
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!message.equals("bye")){
                message = in.readLine();
                String temp = TraCuuThongTinSanPham(message);
                out.write(temp);
                out.newLine();
                out.flush();
            }
            out.close();
            in.close();
            socket.close();
            server.close();
        }
        catch (IOException e){

        }
    }
}
