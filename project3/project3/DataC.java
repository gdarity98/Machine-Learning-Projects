package project3;

import java.util.Random;

/*
    A generalized Data object to replace GlassData.java, HouseVotesData.java, etc.
    Data must be in the form [id, feat_1, feat_2, ..., feat_n, class label]
 */
public class DataC {

    private String allData;
    private double[] features;
    private double[] normalizedFeatures;
    private String classLabel;
    private int id;
    private String classLoc;
    private double clusterID;
    

    public DataC(String fullData, int id, String classLoc, String clOrReg) { 
    	if(clOrReg.equals("classification")){
	    	if(classLoc.equals("end")) {
		        this.allData = fullData;
		        String[] tokens = fullData.split(",");
		
		        features = new double[tokens.length - 2];
		
		        for (int i= 1; i< tokens.length -1; i++) {
		            features[i-1] = Double.parseDouble(tokens[i]);
		        }
		
		        classLabel = tokens[tokens.length-1];
		        this.id = id;
	    	} else if (classLoc.contentEquals("endS")) {
	    		this.allData = fullData;
	    		String[] tokens = fullData.split(",");

	    		features = new double[tokens.length - 2];

	    		for (int i= 1; i< tokens.length-1; i++) {
	    			features[i-1] = Double.parseDouble(tokens[i]);
				}

	    		classLabel = tokens[tokens.length-1].split("D")[1];
			} else if (classLoc.contentEquals("endB")) {
	    		this.allData = fullData;
	    		String[] tokens = fullData.split(",");

	    		features = new double[tokens.length - 2];

	    		for (int i= 1; i< tokens.length-1; i++) {
	    			if (tokens[i].contentEquals("?")) {
	    				tokens[i] = String.valueOf((int)(Math.random()*5) + 1);
					}
	    			features[i-1] = Double.parseDouble(tokens[i]);
				}

	    		if (tokens[tokens.length-1].contentEquals("2")) {
	    			classLabel = "1";
				}
	    		else if (tokens[tokens.length-1].contentEquals("4")){
	    			classLabel = "2";
				}
			}
	    	else if (classLoc.equals("beg")) {
		        this.allData = fullData;
		        String[] tokens = fullData.split(",");
		
		        features = new double[tokens.length-1];
		        
		        //This is for when we read in the Classification House Votes Data This also Randomizes Missing Attributes
		        for(int i = 1; i < tokens.length; i++) {
					switch (tokens[i]) {
						case "n" -> tokens[i] = Integer.toString(0);
						case "y" -> tokens[i] = Integer.toString(1);
						case "?" -> {
							Random random = new Random();
							int randomNum = 0;
							while (true) {
								randomNum = random.nextInt(3);
								if (randomNum != 0) break;
							}
							if (randomNum == 1) {
								tokens[i] = Integer.toString(1);
							} else {
								tokens[i] = Integer.toString(0);
							}
						}
					}
		        }
		        
		        for (int i= 1; i< tokens.length; i++) {
		            features[i-1] = Double.parseDouble(tokens[i]);
		        }
		
		        classLabel = tokens[0];
		        
		        //This edits the classLabel to be a number version rather than a string for House Votes Data
		        if(classLabel.equals("democrat")) {
		        	classLabel = "1";
		        }else if (classLabel.equals("republican")) {
		        	classLabel = "2";
		        }
		        
		        //This edits the classLabel to be a number version rather than a string for Segmentation Data
				switch (classLabel) {
					case "BRICKFACE" -> classLabel = "1";
					case "SKY" -> classLabel = "2";
					case "FOLIAGE" -> classLabel = "3";
					case "CEMENT" -> classLabel = "4";
					case "WINDOW" -> classLabel = "5";
					case "PATH" -> classLabel = "6";
					case "GRASS" -> classLabel = "7";
				}
		        
		        this.id = id;
	    	}
    	}
    	if(clOrReg.equals("regression")){
    		//This is for FOREST FIRES DATA
    		if(classLoc.equals("endF")) {
			 	this.allData = fullData;
		        String[] tokens = fullData.split(",");
		
		        features = new double[tokens.length-1];
		        
		       //This is for when we read in the Regression Forest Fires Data to change categorical data into numbers
		        for(int i = 1; i < tokens.length; i++) {
					switch (tokens[i]) {
						case "jan" -> tokens[i] = Integer.toString(0);
						case "feb" -> tokens[i] = Integer.toString(1);
						case "mar" -> tokens[i] = Integer.toString(2);
						case "apr" -> tokens[i] = Integer.toString(3);
						case "may" -> tokens[i] = Integer.toString(4);
						case "jun" -> tokens[i] = Integer.toString(5);
						case "jul" -> tokens[i] = Integer.toString(6);
						case "aug" -> tokens[i] = Integer.toString(7);
						case "sep" -> tokens[i] = Integer.toString(8);
						case "oct" -> tokens[i] = Integer.toString(9);
						case "nov" -> tokens[i] = Integer.toString(10);
						case "dec" -> tokens[i] = Integer.toString(11);
					}
		        }
		        
		      //This is for when we read in the Regression Forest Fires Data to change categorical data into numbers
		        for(int i = 1; i < tokens.length; i++) {
					switch (tokens[i]) {
						case "mon" -> tokens[i] = Integer.toString(0);
						case "tue" -> tokens[i] = Integer.toString(1);
						case "wed" -> tokens[i] = Integer.toString(2);
						case "thu" -> tokens[i] = Integer.toString(3);
						case "fri" -> tokens[i] = Integer.toString(4);
						case "sat" -> tokens[i] = Integer.toString(5);
						case "sun" -> tokens[i] = Integer.toString(6);
					}
		        }
		        
		        for (int i= 0; i< tokens.length -1; i++) {
		            features[i] = Double.parseDouble(tokens[i]);
		        }
		
		        classLabel = tokens[tokens.length-1];
		        this.id = id;
		    //THIS IS FOR ABALONE DATA   
    		}else if(classLoc.equals("endA")) {
    			
			 	this.allData = fullData;
		        String[] tokens = fullData.split(",");
		
		        features = new double[tokens.length-1];

		        switch(tokens[0]) {
		        	case "M" -> features[0] = 1.0;
		        	case "F" -> features[0] = 2.0;
		        	case "I" -> features[0] = 3.0;
				}

		        for (int i= 1; i< tokens.length-1; i++) {
		            features[i] = Double.parseDouble(tokens[i]);
		        }
		
		        classLabel = tokens[tokens.length-1];
		        this.id = id;
		    //THIS IS FOR MACHINE DATA    
    		}else {
    			
			 	this.allData = fullData;
		        String[] tokens = fullData.split(",");
		
		        features = new double[tokens.length-4];

		        for (int i= 2; i< tokens.length-2; i++) {
		            features[i-2] = Double.parseDouble(tokens[i]);
		        }
		
		        classLabel = tokens[tokens.length-2];
		        this.id = id;
    		}
    	}
    }
    

    public DataC(double[] meanFeatures, int clusterID) {
    	this.classLabel = String.valueOf(clusterID);
    	this.id = clusterID;
    	features = meanFeatures;
	}


	public String getAllData() { return allData; }

    public double[] getFeatures() {
        return features;
    }

    public void setNormFeatures(double[] normFeatures) { this.normalizedFeatures = normFeatures; }

    public double[] getNormalizedFeatures() { return this.normalizedFeatures; }

    public int numFeatures() {
        return features.length;
    }

    public String getClassLabel() {
        return classLabel;
    }

    public void setClassLabel(String cl) {
    	this.classLabel = cl;
	}

    public int getID() {
        return id;
    }

    public void setID(int id) { this.id = id; }
    
    public double getClusterID() {
    	return clusterID;
    }
    
    public void setClusterID(double cID) {
    	clusterID = cID;
    }


}
