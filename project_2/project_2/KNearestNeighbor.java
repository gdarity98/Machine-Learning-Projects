package project_2;

import java.util.*;

/*
    1. Load data
    2. Choose a K
    3. For each example e in the data, for query q:
        a. Call getDistance(e, q)
        b. Store the distance and id of example in an array
    4. Sort the array by distance --> O(nlog(n))
    5. Take the K smallest elements
    6.
        a. Return majority class as the classification of q
        b. Return 1/d weighted average of nearest neighbors for regression

    OR

    3b. Put the distance and id of example in k-dimensional tree
    4. Find nearest neighbors in O(log(n)) time

 */
public class KNearestNeighbor {

    public DataC[] data;
    public int numClasses;
    public int k;
   
    /*
        Need to make constructor such that you can give it any of the data sets and
        KNearestNeighbor will work
     */
    public KNearestNeighbor(DataC[] dataIn, int num) {
    	this.data = dataIn;
    	this.numClasses = num;
        this.k = 1;
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
    public String classify(DataC[] dataSet, double[] query) {
        DataC[] nearestNeighbors = new DataC[k];
        double[][] distArray = new double[dataSet.length][2];

        //fill distArray[i][0] w/ distance between query point and point i
        //fill distArray[i][1] w/ id of point i
        double dist;
        int i= 0;
        for (DataC d: dataSet) {
            if (d != null) {
                dist = getDistance(d.getFeatures(), query);
                distArray[i][0] = dist;
                distArray[i++][1] = d.getID();
            }
        }

        //sort distances using this madness to sort 2D array
        //https://stackoverflow.com/questions/4907683/sort-a-two-dimensional-array-based-on-one-column
        Arrays.sort(distArray, new Comparator<double[]>(){
            @Override
            public int compare(double[] o1, double[] o2) {
                Double dist1 = o1[0];
                Double dist2 = o2[0];
                return dist1.compareTo(dist2);
            }
        });

        //fill nearestNeighbors[]
        int j= 0, n= 0;
        while (n < nearestNeighbors.length) {
            if (distArray[j][1] > 0) {
                nearestNeighbors[n++] = data[(int) distArray[j][1]-1];
            }
            j++;
        }

        //print class labels of nearest neighbors
//        for (Data d: nearestNeighbors) {
//            System.out.println(d.getClassLabel());
//        }

        //take majority of nearest neighbor classes as max
        //w/ int vote[] where it increments vote[classNo]
        String cl;
        int[] vote = new int[numClasses+2];
        int max = -1;
        for (DataC nearestNeighbor : nearestNeighbors) {
            int classLabel = Integer.parseInt(nearestNeighbor.getClassLabel());
            vote[classLabel]++;
            if (vote[classLabel] > max) {
                max = classLabel;
            }
        }

//        System.out.println(Arrays.toString(vote));
        cl = String.valueOf(max);

        return cl;
    }

    /*
        L O S S -->
     */
    public double loss(DataC[] dataSet, DataC[] testSet) {
        int count = 0;
        for (DataC d: testSet) {
            if (d != null) {
                String trueClass = d.getClassLabel();
                double[] test = d.getFeatures();
                if (classify(dataSet, test).contentEquals(trueClass)) {
                    count++;
                }
            }
        }

        double accuracy = (double) count / testSet.length;

        return accuracy;
    }

    public double crossValidate() {
        int trainLen = (int)(0.9 * data.length)+1;
        int testLen = data.length - trainLen;
        DataC[] train = new DataC[trainLen];
        DataC[] test;
        DataC[][] split = new DataC[10][testLen];
        double loss= 0;

        //shuffle
        List<DataC> temp = Arrays.asList(data);
        Collections.shuffle(temp);
        DataC[] shuffled = new DataC[data.length];
        temp.toArray(shuffled);

        //split into 10 arrays
        int c= 0;
        for (int i= 0; i< 10; i++) {
            for (int j= 0; j< testLen; j++) {
                if (c >= data.length) {
                    split[i][j] = null;
                }
                else {
                    split[i][j] = shuffled[c++];
                }
            }
        }

        //cross-validate 10x
        for (int fold= 0; fold< 10; fold++) {

            //train fold to (fold+8)%10, test on (fold+9)%10
            List<DataC> list = new ArrayList<>();
            for (int i= 0; i< 9; i++) {
                //add 1/10 of data to list 9 times
                list.addAll(Arrays.asList(split[(i+fold)%10]));
            }

            //fill train[] and test[]
            list.toArray(train);
            test = split[(fold+9)%10];

            //get loss data
            loss += loss(train, test);
        }

        //average loss data
        loss /= 10;

        System.out.println("Accuracy: " + loss);

        return loss;
    }

    public void tune() {
        double max= -1;
        int bestK= 1;
        double accuracy;

        //test k up to 20, take best and set it
        for (int k= 1; k <= 20; k++) {
            this.k = k;

            System.out.println("k = " + k);
            accuracy = crossValidate();
            if (accuracy > max) {
                max = accuracy;
                bestK = k;
            }
        }

        System.out.println("Best k --> " + bestK);
        this.k = bestK;
    }
    
    public static void main(String[] args) {
        String gdFileName = "data-sets/glass.data";
        DataSetUp gdSetUp = new DataSetUp(gdFileName, "end","classification");

        KNearestNeighbor glassKNearestNeighbor = new KNearestNeighbor(gdSetUp.getAllData(), gdSetUp.numClasses());
        glassKNearestNeighbor.tune();
        glassKNearestNeighbor.crossValidate();
//        System.out.println(glassKNearestNeighbor.loss(glassKNearestNeighbor.data, glassKNearestNeighbor.data));
    }
}
