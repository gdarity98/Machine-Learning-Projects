package project_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Randomize {

    public static void main(String[] args) {

        BufferedReader reader; //creates a buffered reader
        FileWriter writer;

        try { //Reads in the file and checks for exception
            reader = new BufferedReader(new FileReader("data-sets/iris.data"));
            writer = new FileWriter("data-sets/iris-noise.txt");
            DecimalFormat df = new DecimalFormat("##.#");
            //reads the file in line by line
            String line = reader.readLine();
            int rand;
            //While we are not at the end of the file do things in the while loop
            while(line != null) {
                String[] tokens = line.split(",");
                for (int i= 0; i< tokens.length-1; i++) {
                    rand = (int)(Math.random()*10 + 1);
                    if (rand == 7) {
                        if (i == 0) {
                            tokens[i] = String.valueOf(df.format(Math.random()*4.6 + 4.3));
                        }
                        else if (i == 1) {
                            tokens[i]= String.valueOf(df.format(Math.random()*3.4 + 2.0));
                        }
                        else if (i == 2) {
                            tokens[i]= String.valueOf(df.format(Math.random()*6.9 + 1.0));
                        }
                        else if (i == 3) {
                            tokens[i]= String.valueOf(df.format(Math.random()*3.4 + 0.1));
                        }
                    }
                }
                writer.write(String.join(",", tokens));
                writer.write("\n");

                line = reader.readLine();
            }
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
