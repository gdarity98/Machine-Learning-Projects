package project_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		String gdFileName = "data-sets/glass.data";
		DataSetUp gdSetUp = new DataSetUp(gdFileName, "end","classification");
		
        KNearestNeighbor glassKNearestNeighbor = new KNearestNeighbor(gdSetUp.getAllData(), gdSetUp.numClasses());
//      double[] test = {1.51651, 14.38, 0.0, 1.94, 73.61, 0.0, 8.48, 1.57, 0.0};
//      kNearestNeighbor.classify(test);
		System.out.println(glassKNearestNeighbor.loss(glassKNearestNeighbor.data, glassKNearestNeighbor.data));
        							//--> 100% accuracy when test on training data (when k = 1)
                                  //--> 97% when k = 3, but k=1 is so good because each query's nearest neighbor
                                  //--> is itself (probably)
        

		String hvdFileName = "data-sets/house-votes-84.data";
		DataSetUp hvdSetUp = new DataSetUp(hvdFileName, "beg","classification");
		
        KNearestNeighbor houseKNearestNeighbor = new KNearestNeighbor(hvdSetUp.getAllData(), hvdSetUp.numClasses());
//      double[] test = {1.51651, 14.38, 0.0, 1.94, 73.61, 0.0, 8.48, 1.57, 0.0};
//      kNearestNeighbor.classify(test);
		System.out.println(houseKNearestNeighbor.loss(houseKNearestNeighbor.data, houseKNearestNeighbor.data));    //--> 100% accuracy when test on training data (when k = 1)
                                  //--> 97% when k = 3, but k=1 is so good because each query's nearest neighbor
                                  //--> is itself (probably)
        
		String sdFileName = "data-sets/segmentation.data";
		DataSetUp sdSetUp = new DataSetUp(sdFileName, "beg","classification");
		
        KNearestNeighbor segmentationKNearestNeighbor = new KNearestNeighbor(sdSetUp.getAllData(), sdSetUp.numClasses());
//      double[] test = {1.51651, 14.38, 0.0, 1.94, 73.61, 0.0, 8.48, 1.57, 0.0};
//      kNearestNeighbor.classify(test);
		System.out.println(segmentationKNearestNeighbor.loss(segmentationKNearestNeighbor.data, segmentationKNearestNeighbor.data));    //--> 100% accuracy when test on training data (when k = 1)
                                  //--> 97% when k = 3, but k=1 is so good because each query's nearest neighbor
                                  //--> is itself (probably)
        
		String adFileName = "data-sets/abalone.data";
		DataSetUp adSetUp = new DataSetUp(adFileName, "endA","regression");
		
		String ffdFileName = "data-sets/forestfires.data";
		DataSetUp ffdSetUp = new DataSetUp(ffdFileName, "endF","regression");
		
		String mdFileName = "data-sets/machine.data";
		DataSetUp mdSetUp = new DataSetUp(mdFileName, "endM","regression");

	}

}
