package project_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Clustering {
	
	public Clustering(){
		//constructor
	}
	
	public double[][] kMeansCluster(DataC[] dataSet, int k) {
		//k is the number of clusters not the k from KNN
		//double[] means =  new double[k];
		double[][] means = new double[k][dataSet[0].getFeatures().length];
		double[][] newMeans =  new double[k][dataSet[0].getFeatures().length];
		
		double min = Double.POSITIVE_INFINITY;
		double max = 0;
		for(DataC data : dataSet) {
			double[] features = data.getFeatures();
			for(double d : features) {
				if(d < min) {
					min = d;
				}else if(d > max) {
					max = d;
				}
			}
		}
		
		for(int i = 0; i < means.length; i++) {
			//randomly pick means from the "data space" not the data set... whatever that means
			//Unformed sampling
			for(int j = 0; j < dataSet[0].numFeatures(); j++) {
				Random r = new Random();
				double ranNum = (double)(r.nextInt((int)((max-min)+1)) + min);
				means[i][j] = (double) ranNum;
			}
		}
		
		boolean first = true;
		int numTimes = 0;
		while(isDif(means, newMeans, dataSet[0].numFeatures()) || numTimes < 100) {
			if(!first) {
				//set means = newMeans after the first time through
				for(int i = 0; i < means.length; i++) {
					for(int j = 0; j < dataSet[0].numFeatures(); j++) {
						means[i][j] = newMeans[i][j];
					}
				}
			}
			
			for(DataC data : dataSet) {
				// take data find which of the means/centroids is closest to that data
				// assign that data to that mean/centroid which makes it a part of that cluster
				
				
				double[][] meansDistances = new double[k][dataSet[0].numFeatures()];
				
				//gets all the distances for all the means
				for(int i = 0; i < means.length; i++) {
					meansDistances[i] = dist(data.getFeatures(), means[i]);
				}
				
				//makes a minimum mean dist array to hold the min numbers is one of the meansDistances randomly to start
				Random r = new Random();
				int ranNum = r.nextInt(meansDistances.length);
				double[] minMeanDist = meansDistances[ranNum];
				
				//this is an array that will hold the number of min features a mean has
				int[] finalNumMin = new int[k];
				
				//this walks through and checks each feature for each mean. If the feature is less then all other features from all means then
				// update the smallest number and increases the number of min feature up by one. IDK If what I have works c:
				for(int i = 0; i < dataSet[0].numFeatures(); i++) {
					int[] numMin = new int[k];
					for(int j = 0; j < means.length; j++) {
						if(meansDistances[j][i] < minMeanDist[i]) {
							minMeanDist[i] = meansDistances[j][i];
							//Arrays.fill(numMin, 0);
							numMin[j]++;
						}else if(meansDistances[j][i] == minMeanDist[i]) {
							numMin[j]++;
						}
					}
					for(int g = 0; g < finalNumMin.length; g++) {
						finalNumMin[g] += numMin[g];
					}
				}
				
				//Finds the highest number of changed features 
				int minNumMean = 0;
				int indexMinMean = 0;
				for(int i = 0; i < finalNumMin.length; i++) {
					if(finalNumMin[i] > minNumMean) {
						indexMinMean = i;
						minNumMean = finalNumMin[i];
					}
				}
				
				//counts how many means have that number of changed features
				int numMax = 0;
				for(int i = 0; i < finalNumMin.length; i++) {
					if(finalNumMin[i] == minNumMean) {
						numMax++;
					}
				}
				
				//If there is more than one mean that has the number of changed features then
				//find which means, then randomly select one of the means to get the data
				// otherwise the mean that has the most changes(when not tied) gets that data
				if(numMax > 1) {
					int[] indexes = new int[numMax];
					int i = 0;
					for(int j = 0; j < finalNumMin.length; j++) {
						if(finalNumMin[j] == minNumMean) {
							indexes[i] = j;
							i++;
						}
					}
					
					
					Random r2 = new Random();
					int randomIndex = r2.nextInt(numMax);
					indexMinMean = indexes[randomIndex];
				}
				
				//set the data to the cluster
				data.setClusterID((double) indexMinMean); 
			}
			
			int[] numFeatAddedToMean = new int[newMeans.length];
			for(int i = 0; i < dataSet[0].getFeatures().length; i++) {
				//now calculate newMeans based on all the data in the new clusters.
				//this sums all the individual features within all that data up if they are in a specific mean.
				//keeps track of how many data examples are added to a mean.
				int numData = 0;
				double[] featureAvgM = new double[dataSet[0].getFeatures().length];
				for(int j = 0; j < newMeans.length; j++) {

					for(DataC data : dataSet) {
						if(data.getClusterID() == (double) j) {
							double[] features = data.getFeatures();
							newMeans[j][i] += features[i];
							numFeatAddedToMean[j]++;
						}
					}
				
				}
			}
			
			for(int i = 0; i < dataSet[0].getFeatures().length; i++) {
				//now calculate newMeans based on all the data in the new clusters
				//This calculates the average for all the features in each mean.
				for(int j = 0; j < newMeans.length; j++) {
					if(numFeatAddedToMean[j] != 0) {
						newMeans[j] [i] /= numFeatAddedToMean[j];
					}
				}
			}
			
			first = false;
			numTimes++;
		}
		System.out.println(numTimes);
		return means;
	}
	
	//checks if the arrays are different
	public boolean isDif(double[][] base, double[][] comp, int featL) {
		int numFeatEq = 0;
		
		for(int i = 0; i < base.length; i++) {
			for(int j = 0; j < featL; j++) {
				if(base[i][j] == comp[i][j]) {
					numFeatEq++;
				}
				else {
					return true;
				}
			}
		}
		
		if(numFeatEq == base.length * featL) {
			return false;
		}
		
		return true;
	}
	
	//Calculates the distance
	public double[] dist(double[] feat, double[] mean) {
		double[] distance = new double[mean.length];
		for(int i = 0; i < mean.length ; i++) {
			distance[i] = (Math.abs(feat[i] - mean[i]));
		}
		return distance; //CHANGE THIS LATER
	}
}
