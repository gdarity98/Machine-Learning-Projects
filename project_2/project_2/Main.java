package project_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

//-------------------------------------------GLASS
		System.out.println("GLASS-------------------------");
		String gdFileName = "data-sets/glass.data";
		DataSetUp gdSetUp = new DataSetUp(gdFileName, "end","classification");
		
        KNearestNeighbor glassKNearestNeighbor = new KNearestNeighbor(gdSetUp.getAllData(), gdSetUp.numClasses(), true);
//		System.out.println(glassKNearestNeighbor.loss(glassKNearestNeighbor.data, glassKNearestNeighbor.data));
        
//-------------------------------------------HOUSE VOTES
		System.out.println("HOUSE VOTES-------------------------");
		String hvdFileName = "data-sets/house-votes-84.data";
		DataSetUp hvdSetUp = new DataSetUp(hvdFileName, "beg","classification");
		
        KNearestNeighbor houseKNearestNeighbor = new KNearestNeighbor(hvdSetUp.getAllData(), hvdSetUp.numClasses(), true);
//		System.out.println(houseKNearestNeighbor.loss(houseKNearestNeighbor.data, houseKNearestNeighbor.data));

//-------------------------------------------SEGMENTATION
		System.out.println("SEGMENTATION-------------------------");
		String sdFileName = "data-sets/segmentation.data";
		DataSetUp sdSetUp = new DataSetUp(sdFileName, "beg","classification");
		
        KNearestNeighbor segmentationKNearestNeighbor = new KNearestNeighbor(sdSetUp.getAllData(), sdSetUp.numClasses(), true);
//		System.out.println(segmentationKNearestNeighbor.loss(segmentationKNearestNeighbor.data, segmentationKNearestNeighbor.data));

//-------------------------------------------ABALONE
		System.out.println("ABALONE-------------------------");
		String adFileName = "data-sets/abalone.data";
		DataSetUp adSetUp = new DataSetUp(adFileName, "endA","regression");

		KNearestNeighbor abaloneKNN = new KNearestNeighbor(adSetUp.getAllData(), adSetUp.numClasses(), false);
		System.out.println(abaloneKNN.loss(abaloneKNN.data, abaloneKNN.data));

//------------------------------------------FOREST FIRES
		System.out.println("FOREST FIRES-------------------------");
		String ffdFileName = "data-sets/forestfires.data";
		DataSetUp ffdSetUp = new DataSetUp(ffdFileName, "endF","regression");

		KNearestNeighbor forestFireKNN = new KNearestNeighbor(ffdSetUp.getAllData(), ffdSetUp.numClasses(), false);
		System.out.println(forestFireKNN.loss(forestFireKNN.data, forestFireKNN.data));

//------------------------------------------MACHINE
		System.out.println("MACHINE-------------------------");
		String mdFileName = "data-sets/machine.data";
		DataSetUp mdSetUp = new DataSetUp(mdFileName, "endM","regression");

		KNearestNeighbor machineKNN = new KNearestNeighbor(mdSetUp.getAllData(), mdSetUp.numClasses(), false);
		System.out.println(machineKNN.loss(machineKNN.data, machineKNN.data));
		
//------------------------------------------Clustering
		System.out.println("Clustering-------------------------");
		//Testing stuff
		Clustering clustering = new Clustering();
		ConvertToData convertMeans = new ConvertToData();
		
		System.out.println("KMeans-------------------------\n");
//		double[][] gMeans = clustering.kMeansCluster(gdSetUp.getAllData(), 7);
//		Printer gPrint = new Printer (gMeans);
//		DataC[] gMeansDataC = convertMeans.convertToData(gMeans);
		
//		KNearestNeighbor gMeanKNN = new KNearestNeighbor(gMeansDataC,7, true);
//		System.out.println(gMeanKNN.loss(gMeanKNN.data, glassKNearestNeighbor.data));

		Cluster cluster = new Cluster();
//		DataC[] centroids = cluster.kMeansClusters(gdSetUp.getAllData(), 1);
//		for (DataC d: centroids) {
//			System.out.println(Arrays.toString(d.getFeatures()));
//			System.out.println(d.getClusterID());
//		}
//		KNearestNeighbor gMeanKNN = new KNearestNeighbor(centroids, 7, true);
//		System.out.println(gMeanKNN.loss(centroids, glassKNearestNeighbor.data));

//		DataC[] centroids = cluster.kMeansClusters(hvdSetUp.getAllData(), 2);
//		KNearestNeighbor hvKNN = new KNearestNeighbor(centroids, 2, true);
//		System.out.println(hvKNN.loss(hvKNN.data, houseKNearestNeighbor.data));

//		DataC[] centroids = cluster.kMeansClusters(ffdSetUp.getAllData(), (int) Math.sqrt(ffdSetUp.getAllData().length));
//		KNearestNeighbor ffKNN = new KNearestNeighbor(centroids, 7, false);
//		System.out.println(ffKNN.loss(ffKNN.data, forestFireKNN.data));

		
		//double[][] hvMeans = clustering.kMeansCluster(hvdSetUp.getAllData(), 2);		
		//Printer hvPrint = new Printer (hvMeans);
		
		//double[][] sMeans = clustering.kMeansCluster(sdSetUp.getAllData(), 7);		
		//Printer sPrint = new Printer(sMeans);
		
		//double[][] aMeans = clustering.kMeansCluster(adSetUp.getAllData(), (int) Math.sqrt(adSetUp.getAllData().length));		
		//Printer aPrint = new Printer(aMeans);
		
		//double[][] ffMeans = clustering.kMeansCluster(ffdSetUp.getAllData(), (int) Math.sqrt(ffdSetUp.getAllData().length));		
		//Printer ffPrint = new Printer(ffMeans);
		
		//double[][] mMeans = clustering.kMeansCluster(mdSetUp.getAllData(), (int) Math.sqrt(mdSetUp.getAllData().length));		
		//Printer Print = new Printer(mMeans);
		
		System.out.println("KMedoids-------------------------\n");
		//double[][] gMedoids = clustering.kMedoidsCluster(gdSetUp.getAllData(), 7);
		//Printer gPrint = new Printer(gMedoids);
		
		//double[][] hvMedoids = clustering.kMedoidsCluster(hvdSetUp.getAllData(), 2);		
		//Printer hvPrint = new Printer (hvMedoids);
		
		//double[][] sMedoids = clustering.kMedoidsCluster(sdSetUp.getAllData(), 7);	//SOME OF THE CLUSTER DATA POINTS ARE WEIRD	JUST FYI
		//Printer sPrint = new Printer(sMedoids);
		
		//double[][] aMedoids = clustering.kMedoidsCluster(adSetUp.getAllData(), (int) Math.sqrt(adSetUp.getAllData().length));	//THIS ONE GOES INF? To large of dataset?	
		//Printer aPrint = new Printer(aMedoids);
		
		//double[][] ffMedoids = clustering.kMedoidsCluster(ffdSetUp.getAllData(), (int) Math.sqrt(ffdSetUp.getAllData().length)); //Runs on a lower NumTimes	
		//Printer ffPrint = new Printer(ffMedoids);
		
		//double[][] mMedoids = clustering.kMedoidsCluster(mdSetUp.getAllData(), (int) Math.sqrt(mdSetUp.getAllData().length));	//This one completed at numTimes 1000 after awhile	
		//Printer Print = new Printer(mMedoids);
		
	}

}
