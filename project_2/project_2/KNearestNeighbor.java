package project_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

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

    public Data[] data;

    /*
        Need to make constructor such that you can give it any of the data sets and
        KNearestNeighbor will work
     */
    public KNearestNeighbor(String fileName) {
        BufferedReader reader; //creates a buffered reader
        Data data;
        Data[] dataArray= new Data[214];

        try { //Reads in the file and checks for exception
            reader = new BufferedReader(new FileReader(fileName));
            //reads the file in line by line
            String line = reader.readLine();
            int lineNo = 0;
            //While we are not at the end of the file do things in the while loop
            while(line != null) {
                //This creates a sData class with the one line from the file
                data = new Data(line, lineNo+1);
                dataArray[lineNo++] = data;

                System.out.println(lineNo + " " + Arrays.toString(data.getFeatures()) + " " + data.getClassLabel());

                line = reader.readLine();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        this.data = dataArray;

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
        int k = 3;
        Data[] nearestNeighbors = new Data[k];
        double[][] distArray = new double[data.length][2];

        //fill distArray[i][0] w/ distance between query point and point i
        //fill distArray[i][1] w/ id of point i
        double dist;
        int i= 0;
        for (Data d: data) {
            dist = getDistance(d.getFeatures(), query);
            distArray[i][0] = dist;
            distArray[i++][1] = d.getID();
        }

        //sort distances using this trash to sort 2D array
        //https://stackoverflow.com/questions/4907683/sort-a-two-dimensional-array-based-on-one-column
        Arrays.sort(distArray, new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2) {
                Double dist1 = o1[0];
                Double dist2 = o2[0];
                return dist1.compareTo(dist2);
            }
        });

        //fill nearestNeighbors[]
        for (int j= 0; j< nearestNeighbors.length; j++) {
            nearestNeighbors[j] = data[(int) distArray[j][1]-1];
        }

        //print class labels of nearest neighbors
//        for (Data d: nearestNeighbors) {
//            System.out.println(d.getClassLabel());
//        }

        //take majority of nearest neighbor classes as cl
        String cl;
        boolean same = true;
        for (int j= 0; j< nearestNeighbors.length-1; j++) {
            //if they are not all the same
            if (!nearestNeighbors[j].getClassLabel().contentEquals(nearestNeighbors[j+1].getClassLabel())) {
                same = false;
            }
        }

        //need to figure out a good way to get majority vote
        cl = nearestNeighbors[0].getClassLabel();

        return cl;
    }

    /*
        L O S S --> test on whole data set
     */
    public void loss() {
        int count = 0;
        for (Data d: data) {
            if (classify(d.getFeatures()).contentEquals(d.getClassLabel())) {
                count++;
            }
        }

        double accuracy = (double) count / data.length;

        System.out.println("Accuracy: " + accuracy);
    }


    public static void main(String[] args) {
        KNearestNeighbor kNearestNeighbor = new KNearestNeighbor("data-sets/glass.data");
//        double[] test = {1.51761,12.81,3.54,1.23,73.24,0.58,8.39,0.00,0.00};
//        kNearestNeighbor.classify(test);
        kNearestNeighbor.loss();    //--> 100% accuracy when test on training data
    }

}
