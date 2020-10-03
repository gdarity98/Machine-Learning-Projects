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
		
		boolean notFirst = false;
		int numTimes = 0;
		while(isDif(means, newMeans, dataSet[0].numFeatures()) && numTimes < 1000) {
			if(notFirst) {
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
			
			notFirst = true;
			numTimes++;
		}
		//System.out.println(numTimes);
		return means;
	}
	
	public double[][] kMedoidsCluster(DataC[] dataSet, int k) {
		//k is the number of clusters not the k from KNN
		double[][] medoids = new double[k][dataSet[0].getFeatures().length];
		double[][] newMedoids =  new double[k][dataSet[0].getFeatures().length];
		
		
		for(int i = 0; i < medoids.length; i++) {
			//randomly pick medoids from the data set
			//Unformed sampling
			Random r = new Random();
			int ranNum = r.nextInt(dataSet.length);
			medoids[i] = dataSet[ranNum].getFeatures();
		}
		
		boolean notFirst = false;
		int numTimes = 0;
		while(isDif(medoids, newMedoids, dataSet[0].numFeatures()) && numTimes < 1) {
			
//			if(notFirst) {
//				//set means = newMeans after the first time through
//				for(int i = 0; i < means.length; i++) {
//					for(int j = 0; j < dataSet[0].numFeatures(); j++) {
//						means[i][j] = newMeans[i][j];
//					}
//				}
//			}
			
			for(DataC data : dataSet) {
				// take data find which of the medoid/centroids is closest to that data
				// assign that data to that medoid/centroid which makes it a part of that cluster
				
				
				double[][] medoidDistances = new double[k][dataSet[0].numFeatures()];
				
				//gets all the distances for all the medoid
				for(int i = 0; i < medoids.length; i++) {
					medoidDistances[i] = dist(data.getFeatures(), medoids[i]);
				}
				
				//makes a minimum medoid dist array to hold the min numbers is one of the medoidDistances randomly to start
				Random r = new Random();
				int ranNum = r.nextInt(medoidDistances.length);
				double[] minMedoidDist = medoidDistances[ranNum];
				
				//this is an array that will hold the number of min features a medoid has
				int[] finalNumMin = new int[k];
				
				//this walks through and checks each feature for each medoid. If the feature is less then all other features from all medoid then
				// update the smallest number and increases the number of min feature up by one. IDK If what I have works c:
				for(int i = 0; i < dataSet[0].numFeatures(); i++) {
					int[] numMin = new int[k];
					for(int j = 0; j < medoids.length; j++) {
						if(medoidDistances[j][i] < minMedoidDist[i]) {
							minMedoidDist[i] = medoidDistances[j][i];
							//Arrays.fill(numMin, 0);
							numMin[j]++;
						}else if(medoidDistances[j][i] == minMedoidDist[i]) {
							numMin[j]++;
						}
					}
					for(int g = 0; g < finalNumMin.length; g++) {
						finalNumMin[g] += numMin[g];
					}
				}
				
				//Finds the highest number of changed features 
				int minNumMedoid = 0;
				int indexMinMedoid = 0;
				for(int i = 0; i < finalNumMin.length; i++) {
					if(finalNumMin[i] > minNumMedoid) {
						indexMinMedoid = i;
						minNumMedoid = finalNumMin[i];
					}
				}
				
				//counts how many medoid have that number of changed features
				int numMax = 0;
				for(int i = 0; i < finalNumMin.length; i++) {
					if(finalNumMin[i] == minNumMedoid) {
						numMax++;
					}
				}
				
				//If there is more than one mean that has the number of changed features then
				//find which medoid, then randomly select one of the medoid to get the data
				// otherwise the medoid that has the most changes(when not tied) gets that data
				if(numMax > 1) {
					int[] indexes = new int[numMax];
					int i = 0;
					for(int j = 0; j < finalNumMin.length; j++) {
						if(finalNumMin[j] == minNumMedoid) {
							indexes[i] = j;
							i++;
						}
					}
					
					
					Random r2 = new Random();
					int randomIndex = r2.nextInt(numMax);
					indexMinMedoid = indexes[randomIndex];
				}
				
				//set the data to the cluster
				data.setClusterID((double) indexMinMedoid); 
			}
			
//THIS IS WHERE THINGS CHANGE FROM kMeans
			//make the distorted Medoid
			double[] distortedMedoid = distortion(dataSet, medoids);
			
			//go through each medoid and each data point that is not in the cluster
			//swap them. Make a new distortedMedoid.
			//If that new distortedMedoid is larger (worse) than the previous medoid
			//swap back
			for(int i = 0; i < medoids.length; i++) {
				for(int j = 0; j < dataSet.length; j++) {
					if(!(dataSet[j].getClusterID() == (double) i)) {
						double[] feat = dataSet[j].getFeatures();
						double[] originalM = new double[feat.length];
						
						for(int l=0; l<feat.length-2; l++) { //for some reason -2??
							originalM[l] = medoids[i][l];
						}
						
						swap(medoids[i], feat);
						double[] newDistortedMedoid = distortion(dataSet,medoids);
						
						if(isLessMedoid(distortedMedoid, newDistortedMedoid)) {
							swap(medoids[i],feat);
						}
					}
				}
			}
			notFirst = true;
			numTimes++;
		}
		//System.out.println(numTimes);
		return medoids;
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
			distance[i] = Math.pow((Math.abs(feat[i] - mean[i])),2);
		}
		return distance; //CHANGE THIS LATER
	}
	
	public double[] distortion(DataC[] dataSet, double[][] medoids) {
		double[] distortedMedoid = new double[dataSet[0].getFeatures().length];
		for(int i = 0; i < dataSet.length; i++) {
			for(int j = 0; j < dataSet[0].getFeatures().length-2; j++) { //for some reason -2?
				for(int k = 0; k < medoids.length; k++) {
					if(dataSet[i].getClusterID() == (double) k) {
						double[] feat = dataSet[i].getFeatures();
						distortedMedoid[j] += Math.pow((feat[j] - medoids[k][j]),2);
					}
				}
			}
		}
		return distortedMedoid;
	}
	
	public void swap(double[] m_i, double[] x_j) {
		//make copy of original m_i
		double[] originalM = new double[m_i.length];
		
		for(int l=0; l<x_j.length; l++) {
			originalM[l] = m_i[l];
		}
		
		//switch m_i to be the x_j
		for(int i = 0; i<m_i.length;i++) {
			m_i[i] = x_j[i];
		}
		
		//switch the x_j to be the original m_i
		for(int j = 0; j<m_i.length;j++) {
			x_j[j] = originalM[j];
		}
	}
	
	public boolean isLessMedoid(double[] original, double[] newest) {
		for(int i = 0; i < original.length; i++) {
			if(original[i] <= newest[i]) {
				return true;
			}
		}
		return false;
	}
}
