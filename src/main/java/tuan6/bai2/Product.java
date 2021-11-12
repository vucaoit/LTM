package tuan6.bai2;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Product {
    String name=null,price=null;
    ArrayList<String> map = new ArrayList<>();

    public Product(String name, String price, ArrayList<String> map) {
        this.name = name;
        this.price = price;
        this.map = map;
    }

    public ArrayList<String> getMap() {
        return map;
    }

    public void setMap(ArrayList<String> map) {
        this.map = map;
    }

    public Product(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        String temp = "";
        for(int i=0;i<map.size();i++){
            temp+="{'review':'"+map.get(i)+"'},";
        }
        String result= "{ 'name':'" + name + '\'' +
                ", 'price':'" + price + '\'' +
                ", 'map':[" + temp.substring(0,temp.length()-1)+
                "]}";
        return result.replaceAll("'", "\"");
    }
}
