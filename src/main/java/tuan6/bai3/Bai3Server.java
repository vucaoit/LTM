package tuan6.bai3;

import bsh.EvalError;
import bsh.Interpreter;

import java.beans.Expression;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bai3Server {
    public static String Tinh(String expression){
        expression=expression.trim();
        String reg = "^([-])*([0-9]+[+\\-*\\/])+([0-9])+$";
        Pattern pattern = Pattern.compile(reg);
        ArrayList<String> num = null;
        ArrayList<String> pt = null;
        if(pattern.matcher(expression).matches()){
                String[] number = expression.replaceAll("(\\+|-|\\*|/)s?"," ").trim().split(" ");
                if(expression.charAt(0)=='-'){
                    number[0]="-"+number[0];
                }
                num = new ArrayList<>(Arrays.asList(number));
                String[] pheptinh=null;
                if(expression.charAt(0)=='-'){
                    pheptinh = expression.substring(1,expression.length()-1).replaceAll("[0-9]{0,}"," ").replaceAll("\\s+"," ").trim().split(" ");
                }
                else{
                    pheptinh = expression.substring(0,expression.length()-1).replaceAll("[0-9]{0,}"," ").replaceAll("\\s+"," ").trim().split(" " );
                }
                pt = new ArrayList<>(Arrays.asList(pheptinh));
            while (pt.indexOf("*")>-1||pt.indexOf("/")>-1) {
                int nhan=pt.indexOf("*");
                int chia=pt.indexOf("/");
                if(nhan==-1&&chia!=-1)nhan=chia+1;
                if(chia==-1&&nhan!=-1)chia=nhan+1;
                if (nhan<chia ) {
                    num.set(nhan, "" + (Double.parseDouble(num.get(nhan)) * Double.parseDouble(num.get(nhan+ 1))));
                    num.remove(nhan+ 1);
                    pt.remove(nhan);
                }
                else {
                    num.set(chia, "" + (Double.parseDouble(num.get(chia)) / Double.parseDouble(num.get(chia + 1))));
                    num.remove(chia + 1);
                    pt.remove(chia);
                }
            }
            while(pt.size()>0){
                if(pt.get(0).equals("+")){
                    num.set(1,(Double.parseDouble(num.get(0))+Double.parseDouble(num.get(1)))+"");
                    pt.remove(0);
                    num.remove(0);
                }
                else{
                    num.set(1,(Double.parseDouble(num.get(0))-Double.parseDouble(num.get(1)))+"");
                    pt.remove(0);
                    num.remove(0);
                }
            }
            return num.get(num.size()-1);
        }
        else{
            return "Biểu thức không đúng format";
        }
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
                if(message.equals("bye"))break;
                String temp = Tinh(message);
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
            e.printStackTrace();
        }
    }
}
