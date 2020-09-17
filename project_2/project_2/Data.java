package project_2;

/*
    A generalized Data object to replace GlassData.java, HouseVotesData.java, etc.
    Data must be in the form [id, feat_1, feat_2, ..., feat_n, class label]
 */
public class Data {

    private String allData;
    private double[] features;
    private String classLabel;
    private int id;

    //could add a boolean id flag to constructor so that we can work w/ the data sets as they are now
    //id=true --> skip tokens[0] when filling feature vector
    //id=false --> don't skip tokens[0]
    public Data(String fullData, int id) {
        this.allData = fullData;
        String[] tokens = fullData.split(",");

        features = new double[tokens.length - 2];

        for (int i= 1; i< tokens.length -1; i++) {
            features[i-1] = Double.parseDouble(tokens[i]);
        }

        classLabel = tokens[tokens.length-1];
        this.id = id;
    }

    public String getAllData() { return allData; }

    public double[] getFeatures() {
        return features;
    }

    public int numFeatures() {
        return features.length;
    }

    public String getClassLabel() {
        return classLabel;
    }

    public int getID() {
        return id;
    }


}
