package project_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/*
    As of now, this class only works with Iris data hardwired to 4 bins
 */
public class Train {

    public IrisData[][] data = new IrisData[3][50];
    public IrisData[] alldata = new IrisData[150];
    public IrisData[] noiseData = new IrisData[150];


    public Train() {
        BufferedReader reader; //creates a buffered reader
        IrisData iData;
        IrisData[] irisArray= new IrisData[150];
        IrisData[] irisArrayNoise = new IrisData[150];

        try { //Reads in the file and checks for exception
            reader = new BufferedReader(new FileReader("data-sets/iris.data"));
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

            //reads in the iris-noisy.txt and fills irisArrayNoise =
            reader= new BufferedReader(new FileReader("data-sets/iris-noise.txt"));
            line = reader.readLine();
            lineNo = 0;

            while (line != null) {
                iData = new IrisData(line, lineNo + 1);
                irisArrayNoise[lineNo++] = iData;

                line= reader.readLine();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        //split the data up by class
        IrisData[] setosa = new IrisData[50];
        IrisData[] versicolor = new IrisData[50];
        IrisData[] virginica = new IrisData[50];

        int j= 0, k= 0, l= 0;
        for (IrisData irisData : irisArray) {
            if (irisData.getClassNo().contentEquals("Iris-setosa")) {
                setosa[j++] = irisData;
            } else if (irisData.getClassNo().contentEquals("Iris-versicolor")) {
                versicolor[k++] = irisData;
            } else {
                virginica[l++] = irisData;
            }
        }

        this.data[0] = setosa;
        this.data[1] = versicolor;
        this.data[2] = virginica;
        this.alldata = irisArray;
        this.noiseData = irisArrayNoise;

    }

    /*
        This method corresponds to part 3 of the algorithm
        F(Aj = ak, C = ci) = ...
     */
    public double train(IrisData[] classSpecificData, int target, int feature) {
        int numFeatures= 4;
        int num= 0;
        for (IrisData data : classSpecificData) {
            int[] attributeData = data.getBinnedFeatures();
            int attr = attributeData[feature];

            if (attr == target) {
                num++;
            }
        }

        return (double)(num+1) / classSpecificData.length + numFeatures; //<-- making denominator smaller = worse accuracy on c3 ?!
    }

    /*
        This method takes a test array and classifies it
     */
    public String classify(int[] test) {
        double probabilityOfClass = 1.0 / 3.0;
        double[] C = new double[3];

        for (int i= 0; i< C.length; i++) {
            double product= 1;
            for (int j= 0; j< 4; j++) {
                product *= train(data[i], test[j], j);
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
    public void loss(IrisData[] testData) {
        int count= 0;
        int[][] confusionMatrix = new int[3][3];

        //this loops through all irisData's in the test data and fills the confusion matrix
        for (IrisData irisData: testData) {
            String trueClass = irisData.getClassNo();
            int[] test = irisData.getBinnedFeatures();
            String guess = classify(test);

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

        //Calculating Precision Pmacro and Pmicro for 3 classes
        double Pmacro= 0;
        double Pmicro= 0;
        int TP = 0, TPsum = 0;
        int FP = 0, FPsum= 0;
        int i = 0;
        for (int[] row: confusionMatrix) {
            System.out.println(Arrays.toString(row));
            TP = row[i];
            FP = row[(i+1)%2] + row[(i+2)%2];

            TPsum += TP;
            FPsum += FP + TP;

            Pmacro += (double)TP / (TP + FP);
            i++;
        }
        Pmacro /= 3.0;
        Pmicro = (double)TPsum / FPsum;
        System.out.println("Pmacro: "+ Pmacro);
        System.out.println("Pmicro: "+ Pmicro);

        //Calculating Risk Rmacro and Rmicro for 3 classes
        double Rmacro = 0;
        for (int j= 0; j < 3; j++) {
            Rmacro += (double)confusionMatrix[0][j] / (confusionMatrix[0][j] + confusionMatrix[1][j] + confusionMatrix[2][j]);
        }
        Rmacro /= 3.0;
        System.out.println("Rmacro: "+ Rmacro);

        double accuracy = count / 150.0;
        System.out.println("Accuracy: "+ accuracy);
        double error = 1 - accuracy;
        System.out.println("Error: "+ error);

    }

    public double crossValidate(IrisData[] data) {
        int fold = 10;  //10-fold cross validation


        return 0;
    }

    public static void main(String[] args) {
        Train train= new Train(); //Model()

//        int[] test= {2, 2, 3, 3};
//        System.out.println("Test data: "+ Arrays.toString(test));
//        System.out.println(train.classify(test));

        System.out.println("Iris-data -----------------------------------------");
        train.loss(train.alldata);     //-->92%

        System.out.println("\nIris-noise ----------------------------------------");
        train.loss(train.noiseData);    //-->90%


    }

}
