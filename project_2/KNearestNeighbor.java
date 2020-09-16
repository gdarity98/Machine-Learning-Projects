package project_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/*
    1. Load data
    2. Choose a K
    3. For each example e in the data, for query q:
        a. Call getDistance(e, q)
        b. Store the distance and id of example in an array
    4. Sort the array by distance
    5. Take the K smallest elements
    6. Return majority class as the classification of q

 */
public class KNearestNeighbor {

    public GlassData[] data;

    public KNearestNeighbor(String fileName) {
        BufferedReader reader; //creates a buffered reader
        GlassData gData;
        GlassData[] glassArray= new GlassData[214];

        try { //Reads in the file and checks for exception
            reader = new BufferedReader(new FileReader(fileName));
            //reads the file in line by line
            String line = reader.readLine();
            int lineNo = 0;
            //While we are not at the end of the file do things in the while loop
            while(line != null) {
                //This creates a sData class with the one line from the file
                gData = new GlassData(line);
                glassArray[lineNo++] = gData;

                System.out.println(lineNo + " " + Arrays.toString(gData.getFeat()) + " " + gData.getCl());

                line = reader.readLine();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        data = glassArray;

    }

    /*
        Gets euclidean distance between 2 feature vectors x and y
     */
    public double getDistance(double[] x, double[] y) {
        //error checking - x and y need to have same dimensionality
        if (x.length != y.length) {
            return -1;
        }

        double sum = 0;
        for (int i = 0; i< x.length; i++) {
            sum += Math.pow((x[i] - y[i]), 2);
        }

        return Math.pow(sum, 0.5);
    }

    /*
        Need to a way to sort distArray such that we can get the ID
        of the k smallest distances
     */
    public void classify(double[] query) {
        int k = 1;

        double[][] distArray = new double[data.length][2];
        double[] temp = new double[2];
        int i= 0;
        for (GlassData gData: data) {
            temp[0] = getDistance(gData.getFeat(), query);
            temp[1] = Double.parseDouble(gData.getID());
            distArray[i++] = temp;
        }

        Arrays.sort(distArray);
        GlassData[] nearestNeighbors = new GlassData[k];
        for (int j = 0; j < k; j++) {
            nearestNeighbors[j] = data[(int) distArray[j][1]];
        }

        for (GlassData gData: nearestNeighbors) {
            System.out.println(gData.getCl());
        }

    }


    public static void main(String[] args) {
        KNearestNeighbor kNearestNeighbor = new KNearestNeighbor("data-sets/glass.data");
        double[] test = {1.51711,14.23,0.00,2.08,73.36,0.00,8.62,1.67,0.00};
        kNearestNeighbor.classify(test);
    }

}
