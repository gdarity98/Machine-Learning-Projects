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
    private String classLoc;
    private double clusterID;
    

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
    	if(clOrReg.equals("regression")){
    		//This is for FOREST FIRES DATA
    		if(classLoc.equals("endF")) {
			 	this.allData = fullData;
		        String[] tokens = fullData.split(",");
		
		        features = new double[tokens.length-1];
		        
		       //This is for when we read in the Regression Forest Fires Data to change categorical data into numbers
		        for(int i = 1; i < tokens.length; i++) {
		        	if(tokens[i].equals("jan")) {
		        		tokens[i] = Integer.toString(0);
		        	}else if (tokens[i].equals("feb")) {
		        		tokens[i] = Integer.toString(1);
		        	}else if (tokens[i].equals("mar")) {
		        		tokens[i] = Integer.toString(2);
		        	}else if (tokens[i].equals("apr")) {
		        		tokens[i] = Integer.toString(3);
		        	}else if (tokens[i].equals("may")) {
		        		tokens[i] = Integer.toString(4);
		        	}else if (tokens[i].equals("jun")) {
		        		tokens[i] = Integer.toString(5);
		        	}else if (tokens[i].equals("jul")) {
		        		tokens[i] = Integer.toString(6);
		        	}else if (tokens[i].equals("aug")) {
		        		tokens[i] = Integer.toString(7);
		        	}else if (tokens[i].equals("sep")) {
		        		tokens[i] = Integer.toString(8);
		        	}else if (tokens[i].equals("oct")) {
		        		tokens[i] = Integer.toString(9);
		        	}else if (tokens[i].equals("nov")) {
		        		tokens[i] = Integer.toString(10);
		        	}else if (tokens[i].equals("dec")) {
		        		tokens[i] = Integer.toString(11);
		        	}
		        }
		        
		      //This is for when we read in the Regression Forest Fires Data to change categorical data into numbers
		        for(int i = 1; i < tokens.length; i++) {
		        	if(tokens[i].equals("mon")) {
		        		tokens[i] = Integer.toString(0);
		        	}else if (tokens[i].equals("tue")) {
		        		tokens[i] = Integer.toString(1);
		        	}else if (tokens[i].equals("wed")) {
		        		tokens[i] = Integer.toString(2);
		        	}else if (tokens[i].equals("thu")) {
		        		tokens[i] = Integer.toString(3);
		        	}else if (tokens[i].equals("fri")) {
		        		tokens[i] = Integer.toString(4);
		        	}else if (tokens[i].equals("sat")) {
		        		tokens[i] = Integer.toString(5);
		        	}else if (tokens[i].equals("sun")) {
		        		tokens[i] = Integer.toString(6);
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
		
		        features = new double[tokens.length-2];

		        for (int i= 1; i< tokens.length-1; i++) {
		            features[i-1] = Double.parseDouble(tokens[i]);
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
    
    public double getClusterID() {
    	return clusterID;
    }
    
    public void setClusterID(double cID) {
    	clusterID = cID;
    }


}
