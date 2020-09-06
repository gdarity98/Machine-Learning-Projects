package project_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
    As of now, this class only works with Iris data hardwired to 4 bins
 */
public class Train {

    public IrisData[][] data = new IrisData[3][50];
    public IrisData[] alldata = new IrisData[150];
    public double[][] trainedData;


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
        int numFeatures= 4;
        int num= 0;
        for (IrisData data : classSpecificData) {
            for (int i= 0; i< numFeatures; i++) {
                int[] attributeData = data.getBinnedFeatures();
                int attr = attributeData[i];

                for (int j= 0; j< numFeatures; j++) {
                    if (attr == j+1) {
                        count[i][j]++;
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

        this.trainedData = model;

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
            for (int j= 0; j< 4; j++) {
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
        double[] lossData = new double[5];

        //this loops through all irisData's in the test data and fills the confusion matrix
        for (IrisData irisData: testData) {
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

        //Calculating Precision Pmacro and Pmicro for 3 classes
        double Pmacro= 0;
        double Pmicro= 0;
        int TP = 0, TPsum = 0;
        int FP = 0, FPsum= 0;
        int i = 0;
        for (int[] row: confusionMatrix) {
//            System.out.println(Arrays.toString(row));
            TP = row[i];
            FP = row[(i+1)%2] + row[(i+2)%2];

            TPsum += TP;
            FPsum += FP + TP;

            Pmacro += (double)TP / (TP + FP);
            i++;
        }
        Pmacro /= 3.0;
        Pmicro = (double)TPsum / FPsum;

        //Calculating Risk Rmacro and Rmicro for 3 classes
        double Rmacro = 0;
        for (int j= 0; j < 3; j++) {
            Rmacro += (double)confusionMatrix[0][j] / (confusionMatrix[0][j] + confusionMatrix[1][j] + confusionMatrix[2][j]);
        }
        Rmacro /= 3.0;

        double accuracy = (double)count / testData.length;
        double error = 1 - accuracy;

        lossData[0] = accuracy;
        lossData[1] = error;
        lossData[2] = Pmacro;
        lossData[3] = Pmicro;
        lossData[4] = Rmacro;

        return lossData;
    }

    /*
        This method takes all the data as a parameter, then for 10 iterations it
        shuffles it, grabs 5 samples from each class for the testing, uses the rest
        of the samples for training, and tests on the test samples.
     */
    public void crossValidate(IrisData[] data) {
        int fold = 10;  //10-fold cross validation
        /*

             Randomly add 15 samples to a test set, train on everything else, repeat 10x

         */
        IrisData[] train = new IrisData[135], test = new IrisData[15];
        double[] lossData = new double[5];

        //go through 10x, shuffle data each time
        for (int pass= 0; pass < 10; pass++) {
            List<IrisData> temp = Arrays.asList(data);
            Collections.shuffle(temp);
            IrisData[] shuffled = new IrisData[data.length];
            temp.toArray(shuffled);

            //separate data into train[] and test[], with test[] having 5 samples from each class
            int i= 0, j= 0;
            int l= 0, m= 0, n= 0;
            for (IrisData irisData : shuffled) {
                if (i < 15) {
                    if (irisData.getClassNo().contentEquals("Iris-setosa") && l< 5) {
                        test[i++] = irisData;
                        l++;
                    }
                    else if (irisData.getClassNo().contentEquals("Iris-versicolor") && m< 5) {
                        test[i++] = irisData;
                        m++;
                    }
                    else if (irisData.getClassNo().contentEquals("Iris-virginica") && n< 5) {
                        test[i++] = irisData;
                        n++;
                    }
                    else {
                        train[j++] = irisData;
                    }
                }
                else {
                    train[j++] = irisData;
                }
            }

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
            lossData[i++] = d /= 10;
        }

        printLossData(lossData);
    }

    /*
        Takes an array of IrisData and separates it by class into a 2D array
     */
    public IrisData[][] separateByClass(IrisData[] data) {

        IrisData[][] classData= new IrisData[3][data.length / 3];
        int i= 0, j= 0, k= 0;
        for (IrisData irisData: data) {
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

        return classData;
    }

    /*
        Print data from loss()
     */
    public void printLossData(double[] lossData) {
        if (lossData.length < 6) {
            System.out.println("Accuracy: "+ lossData[0]);
            System.out.println("Error:    "+ lossData[1]);
            System.out.println("Pmacro:   "+ lossData[2]);
            System.out.println("Pmicro:   "+ lossData[3]);
            System.out.println("Rmacro:   "+ lossData[4]);
        }
    }

    public static void main(String[] args) {

        Train train= new Train("data-sets/iris.data"); //Model()
        System.out.println("Iris-data --------------------------------> train on all data, test on all data");
        train.printLossData(train.loss(train.alldata, train.alldata));     //-->92%

        System.out.println("\nCross-validate----------------------------------");
        train.crossValidate(train.alldata);

        Train trainNoise = new Train("data-sets/iris-noise.txt");
        System.out.println("\nIris-noise ------------------------------> train on all data, test on all data");
        trainNoise.printLossData(trainNoise.loss(trainNoise.alldata, trainNoise.alldata));    //-->90%

        System.out.println("\nCross-validate-------------------------------------");
        trainNoise.crossValidate(trainNoise.alldata);
        
    }

}
