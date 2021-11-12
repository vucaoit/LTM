package tuan6.bai2;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Bai2Client {

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
                if(line.equals("bye"))break;
                out.write(line);
                out.newLine();
                out.flush();
                String info = in.readLine();
                System.out.println("server : ");
                try{
                    Product prodcut = new Product();
                    Gson gson = new Gson();
                    prodcut = gson.fromJson(info, Product.class);
                    System.out.println("tên : "+prodcut.getName());
                    System.out.println("giá : "+prodcut.getPrice());
                    ArrayList<String> review = new ArrayList<>();
                    review = prodcut.getMap();
                    for(int i=0;i<review.size();i++){
                        System.out.println("review "+i+" : \n"+review.get(i));
                    }
                }
                catch (JsonSyntaxException e){
                    System.out.println("Không tìm kiếm được thông tin sản phẩm từ link");
                }
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
