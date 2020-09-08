package project_1;

public class SoybeanData {

    private String allData = " ";
    public int id;
    public String classNo = " ";	//[D1, D2, D3, D4]
    public int[] features;		//feature array

    public SoybeanData(String fullData, int id) {  //there is no id for this data so the line number will have to do

        //This sets up all the variables by splitting the string and assigning the correct value to variables.
        allData = fullData;
        this.id = id;
        String[] tokens = fullData.split(","); //splitting string at commas

        features = new int[35];
        for (int i = 0; i < 35; i++) {
            features[i] = Integer.parseInt(tokens[i]);
        }
        classNo = tokens[35];

    }

    //returns id (line no.)
    public int getID() {
        return id;
    }

    //returns class
    public String getClassNo() {
        return classNo;
    }

    //returns features
    public int[] getFeatures() {
        return features;
    }
}
