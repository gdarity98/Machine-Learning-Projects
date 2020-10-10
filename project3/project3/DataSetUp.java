package project3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSetUp {
	private final DataC[] data;
	private final int length;
    
	public DataSetUp(String fileName, String classPos, String clOrReg) {
		
		BufferedReader reader; //creates a buffered reader
        DataC data;
        
        List<DataC> list = new ArrayList<>();

        try { //Reads in the file and checks for exception
            reader = new BufferedReader(new FileReader(fileName));
            //reads the file in line by line
            String line = reader.readLine();
            int lineNo = 0;
            //While we are not at the end of the file do things in the while loop
            while(line != null) {
                data = new DataC(line, lineNo+1, classPos, clOrReg);
                list.add(data);

                line = reader.readLine();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        this.data = new DataC[list.size()];
        this.length = list.size();
		list.toArray(this.data);
	        
	}

	public DataC[] getAllData() {
		return data;
	}

	public void zScoreNormalize() {
		//get means of all features
		//get SD of all features
		//change features
		double[] means = new double[data[0].getFeatures().length];
		for (DataC d: data) {
			double[] features = d.getFeatures();

			for (int i= 0; i< features.length; i++) {
				means[i] += features[i];
			}
		}

		for (int i= 0; i< means.length; i++) {
			means[i] /= length;
		}

		//get SDs
		double[] SDs = new double[data[0].getFeatures().length];
		for (DataC d: data) {
			double[] features = d.getFeatures();

			for (int i= 0; i< features.length; i++) {
				SDs[i] += Math.pow((features[i] - means[i]), 2);
			}
		}

		for (int i= 0; i< SDs.length; i++) {
			SDs[i] = Math.pow((SDs[i] / length), 0.5);
		}

		//normalize data
		double[] normFeatures = new double[data[0].getFeatures().length];
		for (DataC d: data) {
			double[] features = d.getFeatures();

			for (int i= 0; i< features.length; i++) {
				normFeatures[i] = (features[i] - means[i]) / SDs[i];
			}
			d.setNormFeatures(normFeatures);
		}

	}

	public static void main(String[] args) {
		DataSetUp glass = new DataSetUp("data-sets/glass.data", "end", "classification");
		glass.zScoreNormalize();
	}

}
