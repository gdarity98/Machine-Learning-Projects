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
    4. Sort the array by distance --> O(nlog(n))
    5. Take the K smallest elements
    6. Return majority class as the classification of q

    OR

    3b. Put the distance and id of example in k-dimensional tree
    4. Find nearest neighbors in O(log(n)) time

 */
public class KNearestNeighbor {

    public GlassData[] data;

    /*
        Need to make constructor such that you can give it any of the data sets and
        KNearestNeighbor will work
     */
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
        Takes a query point (feature vector) and gets its distance between every other point
        in the data set, then finds its k nearest neighbors and classifies it based on a plurality
        vote from the neighbors' classes. (Working (I think))
     */
    public String classify(double[] query) {
        int k = 1;
        GlassData[] nearestNeighbors = new GlassData[k];

        double min = Double.POSITIVE_INFINITY;
        double dist;
        int i= 0;
        for (GlassData gData: data) {
            dist = getDistance(gData.getFeat(), query);
            if (dist < min) {
                min = dist;
                nearestNeighbors[0] = gData;
            }
        }

        String cl = nearestNeighbors[0].getCl();

        return cl;
    }

    /*

     */
    public void loss() {
        int count = 0;
        for (GlassData gData: data) {
            if (classify(gData.getFeat()).contentEquals(gData.getCl())) {
                count++;
            }
        }

        double accuracy = (double) count / data.length;

        System.out.println("Accuracy: " + accuracy);
    }


    public static void main(String[] args) {
        KNearestNeighbor kNearestNeighbor = new KNearestNeighbor("data-sets/glass.data");

        kNearestNeighbor.loss();    //--> 100% accuracy when test on training data
    }

}
