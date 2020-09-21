package project_2;

import java.util.Random;

/*
    A generalized Data object to replace GlassData.java, HouseVotesData.java, etc.
    Data must be in the form [id, feat_1, feat_2, ..., feat_n, class label]
 */
public class DataC {

    private String allData;
    private double[] features;
    private String classLabel;
    private int id;
    String classLoc;

    //could add a boolean id flag to constructor so that we can work w/ the data sets as they are now
    //id=true --> skip tokens[0] when filling feature vector
    //id=false --> don't skip tokens[0]
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
	    	}else if (classLoc.equals("beg")) {
		        this.allData = fullData;
		        String[] tokens = fullData.split(",");
		
		        features = new double[tokens.length-1];
		        
		        //This is for when we read in the Classification House Votes Data This also Randomizes Missing Attributes
		        for(int i = 1; i < tokens.length; i++) {
		        	if(tokens[i].equals("n")) {
		        		tokens[i] = Integer.toString(0);
		        	}else if (tokens[i].equals("y")) {
		        		tokens[i] = Integer.toString(1);
		        	}else if (tokens[i].equals("?")) {
		        		Random random = new Random();
						int randomNum = 0;
						while(true) {
							randomNum = random.nextInt(3);
							if (randomNum != 0) break;
						}
						if(randomNum == 1) {
							tokens[i] = Integer.toString(1);
						}else {
							tokens[i] = Integer.toString(0);
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
		        if(classLabel.equals("BRICKFACE")) {
		        	classLabel = "1";
		        }else if (classLabel.equals("SKY")) {
		        	classLabel = "2";
		        }else if (classLabel.equals("FOLIAGE")) {
		        	classLabel = "3";
		        }else if (classLabel.equals("CEMENT")) {
		        	classLabel = "4";
		        }else if (classLabel.equals("WINDOW")) {
		        	classLabel = "5";
		        }else if (classLabel.equals("PATH")) {
		        	classLabel = "6";
		        }else if (classLabel.equals("GRASS")) {
		        	classLabel = "7";
		        }
		        
		        this.id = id;
	    	}
    	}
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
