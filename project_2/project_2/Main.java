package project_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		String gdFileName = "data-sets/glass.data";
		int gdLength = 0;

		BufferedReader reader; // creates a buffered reader

		try { // Reads in the file and checks for exception
			reader = new BufferedReader(new FileReader(gdFileName));
			// reads the file in line by line
			String line = reader.readLine();
			int lineNo = 0;
			// While we are not at the end of the file do things in the while loop
			while (line != null) {
				// This creates a sData class with the one line from the file
				gdLength++;
				line = reader.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DataSetUp gdSetUp = new DataSetUp(gdFileName, gdLength, "end");
		
        KNearestNeighbor glassKNearestNeighbor = new KNearestNeighbor(gdSetUp.getAllData(), gdSetUp.numClasses());
//      double[] test = {1.51651, 14.38, 0.0, 1.94, 73.61, 0.0, 8.48, 1.57, 0.0};
//      kNearestNeighbor.classify(test);
        glassKNearestNeighbor.loss();    //--> 100% accuracy when test on training data (when k = 1)
                                  //--> 97% when k = 3, but k=1 is so good because each query's nearest neighbor
                                  //--> is itself (probably)
        

		String hvdFileName = "data-sets/house-votes-84.data";
		int hvdLength = 0;

		//BufferedReader reader; // creates a buffered reader

		try { // Reads in the file and checks for exception
			reader = new BufferedReader(new FileReader(hvdFileName));
			// reads the file in line by line
			String line = reader.readLine();
			int lineNo = 0;
			// While we are not at the end of the file do things in the while loop
			while (line != null) {
				// This creates a sData class with the one line from the file
				hvdLength++;
				line = reader.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DataSetUp hvdSetUp = new DataSetUp(hvdFileName, hvdLength, "beg");
		
        KNearestNeighbor houseKNearestNeighbor = new KNearestNeighbor(hvdSetUp.getAllData(), hvdSetUp.numClasses());
//      double[] test = {1.51651, 14.38, 0.0, 1.94, 73.61, 0.0, 8.48, 1.57, 0.0};
//      kNearestNeighbor.classify(test);
        houseKNearestNeighbor.loss();    //--> 100% accuracy when test on training data (when k = 1)
                                  //--> 97% when k = 3, but k=1 is so good because each query's nearest neighbor
                                  //--> is itself (probably)
        
		String sdFileName = "data-sets/segmentation.data";
		int sdLength = 0;

		//BufferedReader reader; // creates a buffered reader

		try { // Reads in the file and checks for exception
			reader = new BufferedReader(new FileReader(sdFileName));
			// reads the file in line by line
			String line = reader.readLine();
			int lineNo = 0;
			// While we are not at the end of the file do things in the while loop
			while (line != null) {
				// This creates a sData class with the one line from the file
				sdLength++;
				line = reader.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DataSetUp sdSetUp = new DataSetUp(sdFileName, sdLength, "beg");
		
        KNearestNeighbor segmentationKNearestNeighbor = new KNearestNeighbor(sdSetUp.getAllData(), sdSetUp.numClasses());
//      double[] test = {1.51651, 14.38, 0.0, 1.94, 73.61, 0.0, 8.48, 1.57, 0.0};
//      kNearestNeighbor.classify(test);
        segmentationKNearestNeighbor.loss();    //--> 100% accuracy when test on training data (when k = 1)
                                  //--> 97% when k = 3, but k=1 is so good because each query's nearest neighbor
                                  //--> is itself (probably)

	}

}
