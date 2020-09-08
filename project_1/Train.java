package project_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
    As of now, this class only works with Iris data hardwired to 4 bins
 */
public class Train {

    public IrisData[] alldata;
    public int numFeatures = 4;

    public Train(String fileName) {
        BufferedReader reader; //creates a buffered reader
        IrisData iData;
        IrisData[] irisArray= new IrisData[150];

        try { //Reads in the file and checks for exception
            reader = new BufferedReader(new FileReader(fileName));
            //reads the file in line by line
            String line = reader.readLine();
            int lineNo = 0;
            //While we are not at the end of the file do things in the while loop
            while(line != null) {
                //This creates a iData class with the one line from the file
                iData = new IrisData(line, lineNo + 1);
                irisArray[lineNo++] = iData;

//                System.out.println(lineNo + " " + Arrays.toString(iData.getBinnedFeatures()) + " " + iData.getClassNo());

                line = reader.readLine();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        this.alldata = irisArray;

    }

    /*
        This method corresponds to part 3 of the algorithm
        F(Aj = ak, C = ci) = ...

        Need to make it so that this.train(some data) returns a double model[4][4] --> model[i][j] gives
        num of target j in feature i + 1 / length of classData + numFeatures

        [num of 1][][][]
        [num of 2][][][]
        [num of 3][][][]
        [num of 4][][][]
     */
    public double[][] train(IrisData[] classSpecificData) {
        int[][] count = new int[4][4];
        double[][] model = new double[4][4];
        for (IrisData data : classSpecificData) {
            if (data != null) {
                for (int i = 0; i < numFeatures; i++) {
                    int[] attributeData = data.getBinnedFeatures();
                    int attr = attributeData[i];

                    for (int j = 0; j < numFeatures; j++) {
                        if (attr == j + 1) {
                            count[i][j]++;
                        }
                    }
                }
            }
        }

        for (int i= 0; i< 4; i++) {
            for (int j= 0; j< 4; j++) {
                model[i][j] = (double)count[i][j] / (classSpecificData.length + numFeatures);//<-- making denominator smaller = worse accuracy on c3 ?!
            }
//            System.out.println(Arrays.toString(model[i]));
        }

        return model;
    }

    /*
        This method takes a test array and classifies it
     */
    public String classify(IrisData[] trainData, int[] test) {
        double probabilityOfClass = 1.0 / 3.0;
        double[] C = new double[3];
        IrisData[][] train = separateByClass(trainData);

        for (int i= 0; i< C.length; i++) {
            double product= 1;
            double[][] trainedData = train(train[i]);
            for (int j= 0; j< numFeatures; j++) {
                product *= trainedData[j][test[j]-1];
            }
            C[i]= probabilityOfClass * product;
        }

        //return argmax (this could be done cleaner)
        if (C[0] > C[1] && C[0] > C[2]) {
            return "Iris-setosa";
        }
        else if (C[1] > C[0] && C[1] > C[2]) {
            return "Iris-versicolor";
        }
        else {
            return "Iris-virginica";
        }
    }

    /*
        Makes a confusion matrix (FP / FN are in context of c1)
        ----trueClass------
            c1  c2  c3
   |    c1  TP  FP  FP
 guess  c2  FN  TP
   |    c3  FN      TP

        definitely confusing...

        This method goes through the data given in the parameter, classifies it,
        (classifier trained on iris-data) and compares it against the original iris-data. It gives
        *Accuracy = total correct / total samples
        *Error = 1 - accuracy
        *Precision
        *Recall
     */
    public double[] loss(IrisData[] trainData, IrisData[] testData) {
        int count= 0;
        int[][] confusionMatrix = new int[3][3];
        double[] lossData = new double[6];

        //this loops through all irisData's in the test data and fills the confusion matrix
        for (IrisData irisData: testData) {
            if (irisData != null) {
                String trueClass = irisData.getClassNo();
                int[] test = irisData.getBinnedFeatures();
                String guess = classify(trainData, test);

                if (trueClass.contentEquals("Iris-setosa")) {
                    if (guess.contentEquals(trueClass)) {
                        count++;
                        confusionMatrix[0][0]++;
                    }
                    if (guess.contentEquals("Iris-versicolor")) {
                        confusionMatrix[1][0]++;
                    }
                    if (guess.contentEquals("Iris-virginica")) {
                        confusionMatrix[2][0]++;
                    }
                }
                if (trueClass.contentEquals("Iris-versicolor")) {
                    if (guess.contentEquals(trueClass)) {
                        count++;
                        confusionMatrix[1][1]++;
                    }
                    if (guess.contentEquals("Iris-virginica")) {
                        confusionMatrix[2][1]++;
                    }
                    if (guess.contentEquals("Iris-setosa")) {
                        confusionMatrix[0][1]++;
                    }
                }
                if (trueClass.contentEquals("Iris-virginica")) {
                    if (guess.contentEquals(trueClass)) {
                        count++;
                        confusionMatrix[2][2]++;
                    }
                    if (guess.contentEquals("Iris-setosa")) {
                        confusionMatrix[0][2]++;
                    }
                    if (guess.contentEquals("Iris-versicolor")) {
                        confusionMatrix[1][2]++;
                    }
                }
            }
        }

        //Calculating Precision Pmacro and Pmicro for 3 classes
        double Pmacro= 0, Pmicro;
        int TP, TPsum = 0;
        int FP, FPsum= 0;
        int i = 0;
        for (int[] row: confusionMatrix) {
//            System.out.println(Arrays.toString(row));
            TP = row[i];
            FP = row[(i+1)%3] + row[(i+2)%3];

            TPsum += TP;
            FPsum += FP + TP;

            Pmacro += (double)TP / (TP + FP);
            i++;
        }
        Pmacro /= 3.0;
        Pmicro = (double)TPsum / FPsum;

        //Calculating Risk Rmacro and Rmicro for 3 classes
        double Rmacro = 0, Rmicro;
        TPsum= 0;
        int FN, FNsum= 0;
        for (int j= 0; j < 3; j++) {
            TP = confusionMatrix[j][j];
            FN = confusionMatrix[(j+1)%3][j] + confusionMatrix[(j+2)%3][j];
            Rmacro += (double)TP / (TP + FN);

            TPsum += TP;
            FNsum += FN + TP;
        }
        Rmacro /= 3.0;
        Rmicro = (double)TPsum / FNsum;

        double accuracy = (double)count / testData.length;
        double error = 1 - accuracy;

        lossData[0] = accuracy;
        lossData[1] = error;
        lossData[2] = Pmacro;
        lossData[3] = Pmicro;
        lossData[4] = Rmacro;
        lossData[5] = Rmicro;

        return lossData;
    }

    /*
        This method takes all the data as a parameter, then for 10 iterations it
        shuffles it, grabs 5 samples from each class for the testing, uses the rest
        of the samples for training, and tests on the test samples.
     */
    public void crossValidate(IrisData[] data) {
        IrisData[] train = new IrisData[135], test;
        IrisData[][] split= new IrisData[10][15];
        double[] lossData = new double[6];

        //shuffle
        List<IrisData> temp = Arrays.asList(data);
        Collections.shuffle(temp);
        IrisData[] shuffled = new IrisData[data.length];
        temp.toArray(shuffled);

        //split into 10 arrays
        int k= 0;
        for (int i= 0; i< 10; i++) {
            for (int j= 0; j< 15; j++) {
                if (k >= data.length) {
                    split[i][j] = null;
                }
                else {
                    split[i][j] = shuffled[k++];
                }
            }
        }

        //go through 10x
        for (int pass= 0; pass < 10; pass++) {

            //train pass to (pass+9)%10, test on (pass+10)%10
            List<IrisData> list = new ArrayList<>();
            for (int i= 0; i< 9; i++) {
                list.addAll(Arrays.asList(split[(i+pass)%10]));
            }

            list.toArray(train);
            test = split[(pass+9)%10];

            //add lossData from this iteration to lossData[]
            int c= 0;
            double[] lossTemp = loss(train, test);
            for (double d: lossTemp) {
                lossData[c++] += d;
            }
        }

        //average lossData
        int i= 0;
        for (double d: lossData) {
            lossData[i++] = d / 10;
        }

        printLossData(lossData);
    }

    /*
        Takes an array of IrisData and separates it by class into a 2D array
     */
    public IrisData[][] separateByClass(IrisData[] data) {

        IrisData[][] classData= new IrisData[3][50];
        int i= 0, j= 0, k= 0;
        for (IrisData irisData: data) {
            if (irisData != null) {
                if (irisData.getClassNo().contentEquals("Iris-setosa")) {
                    classData[0][i++] = irisData;
                }
                if (irisData.getClassNo().contentEquals("Iris-versicolor")) {
                    classData[1][j++] = irisData;
                }
                if (irisData.getClassNo().contentEquals("Iris-virginica")) {
                    classData[2][k++] = irisData;
                }
            }
        }

        return classData;
    }

    /*
        Print data from loss()
     */
    public void printLossData(double[] lossData) {
        if (lossData.length < 7) {
            System.out.println("Accuracy: "+ lossData[0]);
            System.out.println("Error:    "+ lossData[1]);
            System.out.println("Pmacro:   "+ lossData[2]);
            System.out.println("Pmicro:   "+ lossData[3]);
            System.out.println("Rmacro:   "+ lossData[4]);
            System.out.println("Rmicro:   "+ lossData[5]);
        }
    }

    public static void main(String[] args) {

        Train train= new Train("data-sets/iris.data"); //Model()
        Train trainNoise = new Train("data-sets/iris-noise.txt");

        System.out.println("Iris-data --------------------------------> train on all data, test on all data");
        train.printLossData(train.loss(train.alldata, train.alldata));     //-->92%

        System.out.println("\nCross-validate---------------");
        System.out.println("\n\tRegular data");
        train.crossValidate(train.alldata);
        System.out.println("\n\tNoise data");
        train.crossValidate(trainNoise.alldata);

        System.out.println("\nIris-noise ------------------------------> train on all data, test on all data");
        trainNoise.printLossData(trainNoise.loss(trainNoise.alldata, trainNoise.alldata));    //-->90%

        System.out.println("\nCross-validate--------------");
        System.out.println("\n\tRegular data");
        trainNoise.crossValidate(train.alldata);
        System.out.println("\n\tNoise data");
        trainNoise.crossValidate(trainNoise.alldata);
        
    }

}
