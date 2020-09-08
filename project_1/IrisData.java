package project_1;

public class IrisData {

    private String allData = " ";
    public int id;
    public String classNo = " ";	//[Iris-setosa, Iris-versicolor, Iris-virginica]
    public double[] features;		//feature array (needs to be binned)
                                    //sepal length, sepal width, petal length, pedal width

    public IrisData(String fullData, int id) {  //there is no id for this data so the line number will have to do

        //This sets up all the variables by splitting the string and assigning the correct value to variables.
        allData = fullData;
        this.id = id;
        String[] tokens = fullData.split(","); //splitting string at commas

        features = new double[4];
        for (int i = 0; i < 4; i++) {
            features[i] = Double.parseDouble(tokens[i]);
        }
        classNo = tokens[4];

    }

    //returns id (line no.)
    public int getID() {
        return id;
    }

    //returns class
    public String getClassNo() {
        return classNo;
    }

    //returns features (NOT BINNED)
    public double[] getFeatures() {
        return features;
    }

    /*
        returns features binned by EQUAL WIDTH BINNING
        sepal len: min- 4.3, max- 7.9, width = 7.9 - 4.3 = 3.6 (SL = 3.6)
        sepal wid: min- 2.0, max- 4.4, SW = 2.4
        pedal len: min- 1.0, max- 6.9, PL = 5.9
        pedal wid: min- 0.1, max- 2.5, PW = 2.4
        (aquired from .names file)
     */
    public int[] getBinnedFeatures() {
        int noBins = 4; //hardwiring bins to 4 but this could be a parameter of this method for tuning
        int[] bins = new int[noBins];

        double SLmin = 4.3;
        double SWmin = 2.0;
        double PLmin = 1.0;
        double PWmin = 0.1;
        double SLBinSize = 3.6 / noBins;
        double SWBinSize = 2.4 / noBins;
        double PLBinSize = 5.9 / noBins;
        double PWBinSize = 2.4 / noBins;

        //PROBLEM: if features[i] = max it gets put in bin 0 (which does not exist)
        //i.e. if pedal width = 2.5 (biggest value in the data) it does not go in bin 4
        //since that bin is not closed on the end [1.9, 2.5) <---
        for (int i= 1; i<= noBins; i++) {
            //place features[0] (sepal len) into a bin [0, noBins]
            //if features[0] is between min and min + bin size,
            if (features[0] >= SLmin && features[0] <= (SLmin + SLBinSize)) {
                bins[0] = i;
            }
            SLmin = SLmin + SLBinSize;

            if (features[1] >= SWmin && features[1] <= (SWmin + SWBinSize)) {
                bins[1] = i;
            }
            SWmin = SWmin + SWBinSize;

            if (features[2] >= PLmin && features[2] <= (PLmin + PLBinSize)) {
                bins[2] = i;
            }
            PLmin = PLmin + PLBinSize;

            if (features[3] >= PWmin && features[3] <= (PWmin + PWBinSize)) {
                bins[3] = i;
            }
            PWmin = PWmin + PWBinSize;
        }

        return bins;
    }

}
