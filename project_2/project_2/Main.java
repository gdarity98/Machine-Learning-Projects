package project_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {

//-------------------------------------------GLASS
		System.out.println("GLASS-------------------------");
		String gdFileName = "data-sets/glass.data";
		DataSetUp gdSetUp = new DataSetUp(gdFileName, "end","classification");
		
        KNearestNeighbor glassKNearestNeighbor = new KNearestNeighbor(gdSetUp.getAllData(), gdSetUp.numClasses(), true);
		System.out.println(glassKNearestNeighbor.loss(glassKNearestNeighbor.data, glassKNearestNeighbor.data));
        
//-------------------------------------------HOUSE VOTES
		System.out.println("HOUSE VOTES-------------------------");
		String hvdFileName = "data-sets/house-votes-84.data";
		DataSetUp hvdSetUp = new DataSetUp(hvdFileName, "beg","classification");
		
        KNearestNeighbor houseKNearestNeighbor = new KNearestNeighbor(hvdSetUp.getAllData(), hvdSetUp.numClasses(), true);
		System.out.println(houseKNearestNeighbor.loss(houseKNearestNeighbor.data, houseKNearestNeighbor.data));

//-------------------------------------------SEGMENTATION
		System.out.println("SEGMENTATION-------------------------");
		String sdFileName = "data-sets/segmentation.data";
		DataSetUp sdSetUp = new DataSetUp(sdFileName, "beg","classification");
		
        KNearestNeighbor segmentationKNearestNeighbor = new KNearestNeighbor(sdSetUp.getAllData(), sdSetUp.numClasses(), true);
		System.out.println(segmentationKNearestNeighbor.loss(segmentationKNearestNeighbor.data, segmentationKNearestNeighbor.data));

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
		
		//I just set it to 5 cause I don't know what k needs to be at the moment
		
		double[][] gMeans = clustering.kMeansCluster(gdSetUp.getAllData(), (int) Math.sqrt(gdSetUp.getAllData().length));
		//Printer gPrint = new Printer (gMeans);
		
		double[][] hvMeans = clustering.kMeansCluster(hvdSetUp.getAllData(), (int) Math.sqrt(hvdSetUp.getAllData().length));		
		//Printer hvPrint = new Printer (hvMeans);
		
		double[][] sMeans = clustering.kMeansCluster(sdSetUp.getAllData(), (int) Math.sqrt(sdSetUp.getAllData().length));		
//		Printer sPrint = new Printer(sMeans);
		
//		double[][] aMeans = clustering.kMeansCluster(adSetUp.getAllData(), (int) Math.sqrt(adSetUp.getAllData().length));		
//		Printer aPrint = new Printer(aMeans);
		
		double[][] ffMeans = clustering.kMeansCluster(ffdSetUp.getAllData(), (int) Math.sqrt(ffdSetUp.getAllData().length));		
//		Printer ffPrint = new Printer(ffMeans);
		
		double[][] mMeans = clustering.kMeansCluster(mdSetUp.getAllData(), (int) Math.sqrt(mdSetUp.getAllData().length));		
		//Printer Print = new Printer(mMeans);
		
		
	}

}
