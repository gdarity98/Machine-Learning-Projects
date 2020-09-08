package project_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SoybeanModel {

    public SoybeanData[] alldata;
    public double[] probOfClass;
    public int numFeatures = 35;

    public SoybeanModel(String fileName) {
        BufferedReader reader; //creates a buffered reader
        SoybeanData sData;
        SoybeanData[] soyArray= new SoybeanData[47];

        try { //Reads in the file and checks for exception
            reader = new BufferedReader(new FileReader(fileName));
            //reads the file in line by line
            String line = reader.readLine();
            int lineNo = 0;
            //While we are not at the end of the file do things in the while loop
            while(line != null) {
                //This creates a sData class with the one line from the file
                sData = new SoybeanData(line, lineNo + 1);
                soyArray[lineNo++] = sData;

//                System.out.println(lineNo + " " + Arrays.toString(sData.getFeatures()) + " " + sData.getClassNo());

                line = reader.readLine();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        this.alldata = soyArray;
        this.probOfClass = new double[4];
        this.probOfClass[0] = 10 / 47.0;
        this.probOfClass[1] = 10 / 47.0;
        this.probOfClass[2] = 10 / 47.0;
        this.probOfClass[3] = 17 / 47.0;
    }

    /*
        This method takes an array of class specific training data and
        runs the training algorithm on it
     */
    public double[][] train(SoybeanData[] classSpecificData) {
        int[][] count = new int[35][7];
        double[][] model = new double[35][7];
        for (SoybeanData data : classSpecificData) {
            if (data != null) {
                for (int i = 0; i < numFeatures; i++) {
                    int[] attributeData = data.getFeatures();
                    int attr = attributeData[i];

                    for (int j = 0; j < numFeatures; j++) {
                        if (attr == j) {
                            count[i][j]++;
                        }
                    }
                }
            }
        }
        for (int i= 0; i< numFeatures; i++) {
            for (int j= 0; j< 7; j++) {
                model[i][j] = (double)count[i][j] / (classSpecificData.length + numFeatures);//<-- making denominator smaller = worse accuracy on c3 ?!
            }
//            System.out.println(Arrays.toString(model[i]));
        }

        return model;
    }

    /*
    This method takes a test array and classifies it
 */
    public String classify(SoybeanData[] trainData, int[] test) {
        double[] C = new double[4];
        SoybeanData[][] train = separateByClass(trainData);

        for (int i= 0; i< C.length; i++) {
            double product= 1;
            double[][] trainedData = train(train[i]);
            for (int j= 0; j< numFeatures; j++) {
                product *= trainedData[j][test[j]];
            }
            C[i]= probOfClass[i] * product;
        }

        //return argmax (this could be done cleaner)
        if (C[0] > C[1] && C[0] > C[2] && C[0] > C[3]) {
            return "D1";
        }
        else if (C[1] > C[0] && C[1] > C[2] && C[1] > C[3]) {
            return "D2";
        }
        else if (C[2] > C[0] && C[2] > C[1] && C[2] > C[3]) {
            return "D3";
        }
        else {
            return "D4";
        }
    }

    /*
    Makes a confusion matrix (FP / FN are in context of c1)
    ----trueClass------
           c1  c2  c3  c4
  |    c1  TP  FP  FP  FP
guess  c2  FN  TP
  |    c3  FN      TP
  |    c4  FN          TP
    definitely confusing...

    This method goes through the test data given in the parameter, classifies it,
    and compares it against the correct class. It gives
    *Accuracy = total correct / total samples
    *Error = 1 - accuracy
    *Precision
    *Recall
 */
    public double[] loss(SoybeanData[] trainData, SoybeanData[] testData) {
        int count= 0;
        int[][] confusionMatrix = new int[4][4];
        double[] lossData = new double[6];

        //this loops through all irisData's in the test data and fills the confusion matrix
        for (SoybeanData soyData: testData) {
            if (soyData != null) {
                String trueClass = soyData.getClassNo();
                int[] test = soyData.getFeatures();
                String guess = classify(trainData, test);

                if (trueClass.contentEquals("D1")) {
                    if (guess.contentEquals(trueClass)) {
                        count++;
                        confusionMatrix[0][0]++;
                    }
                    if (guess.contentEquals("D2")) {
                        confusionMatrix[1][0]++;
                    }
                    if (guess.contentEquals("D3")) {
                        confusionMatrix[2][0]++;
                    }
                    if (guess.contentEquals("D4")) {
                        confusionMatrix[3][0]++;
                    }
                }
                if (trueClass.contentEquals("D2")) {
                    if (guess.contentEquals(trueClass)) {
                        count++;
                        confusionMatrix[1][1]++;
                    }
                    if (guess.contentEquals("D1")) {
                        confusionMatrix[0][1]++;
                    }
                    if (guess.contentEquals("D3")) {
                        confusionMatrix[2][1]++;
                    }
                    if (guess.contentEquals("D4")) {
                        confusionMatrix[3][1]++;
                    }
                }
                if (trueClass.contentEquals("D3")) {
                    if (guess.contentEquals(trueClass)) {
                        count++;
                        confusionMatrix[2][2]++;
                    }
                    if (guess.contentEquals("D1")) {
                        confusionMatrix[0][2]++;
                    }
                    if (guess.contentEquals("D2")) {
                        confusionMatrix[1][2]++;
                    }
                    if (guess.contentEquals("D4")) {
                        confusionMatrix[3][2]++;
                    }
                }
                if (trueClass.contentEquals("D4")) {
                    if (guess.contentEquals(trueClass)) {
                        count++;
                        confusionMatrix[3][3]++;
                    }
                    if (guess.contentEquals("D1")) {
                        confusionMatrix[0][3]++;
                    }
                    if (guess.contentEquals("D2")) {
                        confusionMatrix[1][3]++;
                    }
                    if (guess.contentEquals("D3")) {
                        confusionMatrix[2][3]++;
                    }
                }
            }

        }

        //Calculating Precision Pmacro and Pmicro for 4 classes
        double Pmacro= 0, Pmicro;
        int TP, TPsum = 0;
        int FP, FPsum= 0;
        int i = 0;
        for (int[] row: confusionMatrix) {
//            System.out.println(Arrays.toString(row));
            TP = row[i];
            FP = row[(i+1)%4] + row[(i+2)%4] + row[(i+3)%4];

            TPsum += TP;
            FPsum += FP + TP;

            Pmacro += (double)TP / (TP + FP);
            i++;
        }
        Pmacro /= 4.0;
        Pmicro = (double)TPsum / FPsum;

        //Calculating Risk Rmacro and Rmicro for 4 classes
        double Rmacro = 0, Rmicro;
        TPsum = 0;
        int FN, FNsum= 0;
        for (int j= 0; j < 4; j++) {
            TP = confusionMatrix[j][j];
            FN = confusionMatrix[(j+1)%4][j] + confusionMatrix[(j+2)%4][j] + confusionMatrix[(j+3)%4][j];
            Rmacro += (double)TP / (TP + FN);

            TPsum += TP;
            FNsum += FN + TP;
        }
        Rmacro /= 4.0;
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
        Takes data, shuffles, and splits into 10 same size arrays.
        Trains on 1st 9 arrays, tests on last, then rotate and repeat.
     */
    public void crossValidate(SoybeanData[] data) {
        SoybeanData[] train = new SoybeanData[45], test;
        SoybeanData[][] split= new SoybeanData[10][5];
        double[] lossData = new double[6];

        //shuffle
        List<SoybeanData> temp = Arrays.asList(data);
        Collections.shuffle(temp);
        SoybeanData[] shuffled = new SoybeanData[data.length];
        temp.toArray(shuffled);

        //split into 10 arrays
        int k= 0;
        for (int i= 0; i< 10; i++) {
            for (int j= 0; j< 5; j++) {
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
            List<SoybeanData> list = new ArrayList<>();
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
        Takes an array of SoybeanData and separates it by class into a 2D array
    */
    public SoybeanData[][] separateByClass(SoybeanData[] data) {

        SoybeanData[][] classData= new SoybeanData[4][17];
        int i= 0, j= 0, k= 0, l= 0;
        for (SoybeanData soyData: data) {
            if (soyData != null) {
                if (soyData.getClassNo().contentEquals("D1")) {
                    classData[0][i++] = soyData;
                } else if (soyData.getClassNo().contentEquals("D2")) {
                    classData[1][j++] = soyData;
                } else if (soyData.getClassNo().contentEquals("D3")) {
                    classData[2][k++] = soyData;
                } else if (soyData.getClassNo().contentEquals("D4")) {
                    classData[3][l++] = soyData;
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
        SoybeanModel soy = new SoybeanModel("data-sets/soybean-small.data");
        SoybeanModel soyNoise = new SoybeanModel("data-sets/soybean-noise.txt");

        System.out.println("Soybean-data------------------->train on all, test on all");
        soy.printLossData(soy.loss(soy.alldata, soy.alldata));

        System.out.println("\nCross-validate-----------");
        System.out.println("\n\tRegular data");
        soy.crossValidate(soy.alldata);
        System.out.println("\n\tNoise data");
        soy.crossValidate(soyNoise.alldata);

        System.out.println("\nSoybean-noise------------------>train on all, test on all");
        soyNoise.printLossData(soyNoise.loss(soyNoise.alldata, soyNoise.alldata));

        System.out.println("\nCross-validate-----------");
        System.out.println("\n\tRegular data");
        soyNoise.crossValidate(soy.alldata);
        System.out.println("\n\tNoise data");
        soyNoise.crossValidate(soyNoise.alldata);
    }
}
