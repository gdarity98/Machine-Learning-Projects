package project_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSetUp {
	private DataC[] data;
	private Boolean clOrReg = false;
    
	public DataSetUp(String fileName, String classPos, String clOrReg, boolean doVDM) {
	 	
		if(clOrReg.equals("classification")) {
			this.clOrReg = true;
		}else if(clOrReg.equals("regression")) {
			this.clOrReg = false;
		}
		
		BufferedReader reader; //creates a buffered reader
        DataC data;
        
        int length = 0;
        
        try { // Reads in the file and checks for exception
			reader = new BufferedReader(new FileReader(fileName));
			// reads the file in line by line
			String line = reader.readLine();
			int lineNo = 0;
			// While we are not at the end of the file do things in the while loop
			while (line != null) {
				// This creates a sData class with the one line from the file
				length++;
				line = reader.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        DataC[] dataArray= new DataC[length];

        try { //Reads in the file and checks for exception
            reader = new BufferedReader(new FileReader(fileName));
            //reads the file in line by line
            String line = reader.readLine();
            int lineNo = 0;
            //While we are not at the end of the file do things in the while loop
            while(line != null) {
                //This creates a sData class with the one line from the file
                data = new DataC(line, lineNo+1, classPos, clOrReg);
                dataArray[lineNo++] = data;

                line = reader.readLine();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        
        this.data = dataArray;
	        
	}

	public DataC[] getAllData() {
		return data;
	}
	
	public Boolean getClassOrReg() {
		return clOrReg;
	}

	public static double[][] constructVDM(DataC[] dataSet) {
		//count how many times each feature occurs
		int featLength = dataSet[0].getFeatures().length;
		int[] c_i = new int[featLength];
		int[][] c_ij = new int[featLength][featLength];

		//count how many different features
		for (int i= 0; i< featLength; i++) {
			double prevFeat = 0;
			for (DataC d : dataSet) {
				if (d.getFeatures()[i] != prevFeat) {
					c_i[i]++;
				}
			}
		}

		return null;
	}

}
