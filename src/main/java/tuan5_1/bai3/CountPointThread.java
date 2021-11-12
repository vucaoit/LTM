package tuan5_1.bai3;

import java.util.Random;

public class CountPointThread extends Thread{
    double end=0;
    public CountPointThread(double end){
        this.end=end;
    }
    public void run(double circle_points, double square_points) {
        System.out.println("start");
        double rand_x, rand_y;
        for (double i = 0; i < (end); i++) {
            rand_x = new Random().nextDouble();
            rand_y = new Random().nextDouble();
            if (rand_x * rand_x + rand_y * rand_y <= 1) {
                circle_points++;
            }
            square_points++;
        }
    }
}
