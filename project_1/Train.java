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


    public Train() {
        BufferedReader reader; //creates a buffered reader
        IrisData iData;
        IrisData[] irisArray= new IrisData[150];

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

    }

    /*
        This method corresponds to part 3 of the algorithm
        F(Aj = ak, C = ci) = ...
     */
    public double train(IrisData[] classSpecificData, int target, int feature) {

        int num= 0;
        for (IrisData data : classSpecificData) {
            int[] attributeData = data.getBinnedFeatures();
            int attr = attributeData[feature];

            if (attr == target) {
                num++;
            }
        }
        System.out.println("Num: "+ num + ", feature: " + feature);

        return (num+1) / 154.0;
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

        //return argmax
        System.out.println(Arrays.toString(C));
        if (C[0] > C[1] && C[0] > C[2]) {
            return "setosa";
        }
        else if (C[1] > C[0] && C[1] > C[2]) {
            return "versicolor";
        }
        else {
            return "virginica";
        }
    }

    public static void main(String[] args) {
        Train train= new Train();
        int[] test= {2, 2, 3, 3};
        System.out.println("Test data: "+ Arrays.toString(test));
        System.out.println(train.classify(test));
    }

}
