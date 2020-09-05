package project_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import project_1.BreastCancerData;
import project_1.BreastCancerDataNoise;

public class DataMain {
    public static void main(String[] args) {
//SETTING UP ALL THE DATA
    	//Breast Cancer Data
        BufferedReader reader; //creates a buffered reader
        BreastCancerData bData; //creates the breast cancer data class
        BreastCancerDataNoise bDataNoise; //creates the breast cancer noise data class

        BreastCancerData[] sA = new BreastCancerData[699];
        BreastCancerDataNoise[] sANoise = new BreastCancerDataNoise[699]; //I don't think my noise changes 10% each time...maybe it does idk
        
        try { //Reads in the file and checks for exception
        	//The file path would need to be changed based on system.
        	reader = new BufferedReader(new FileReader("C:\\Users\\gdari\\Desktop\\Important\\School\\CSCI 447\\Projects\\src\\project_1\\data-sets\\breast-cancer-wisconsin.data"));
        	
        	//reads the file in line by line
        	String line = reader.readLine();
        	
        	//While we are not at the end of the file do things in the while loop
        	int i = 0;
        	while(line != null) {
        		//This creates a bData class with the one line from the file
        		bData = new BreastCancerData(line);
        		bDataNoise = new BreastCancerDataNoise(line);
        		
        		sA[i] = bData;
        		sANoise[i] = bDataNoise;
        		i++;
        		line = reader.readLine();
        	}
        	
        }catch (IOException e) {
        	e.printStackTrace();
        }
        
        //House Votes Data
        BufferedReader reader2; //creates a buffered reader
        HouseVotesData hvData; //creates the breast cancer data class
        HouseVotesDataNoise hvDataNoise; //creates the breast cancer noise data class

        HouseVotesData[] sA2 = new HouseVotesData[435];
        HouseVotesDataNoise[] sA2Noise = new HouseVotesDataNoise[435]; //I don't think my noise changes 10% each time...maybe it does idk
        
        try { //Reads in the file and checks for exception
        	//The file path would need to be changed based on system.
        	reader2 = new BufferedReader(new FileReader("C:\\Users\\gdari\\Desktop\\Important\\School\\CSCI 447\\Projects\\src\\project_1\\data-sets\\house-votes-84.data"));
        	
        	//reads the file in line by line
        	String line = reader2.readLine();
        	
        	//While we are not at the end of the file do things in the while loop
        	int i = 0;
        	while(line != null) {
        		//This creates a bData class with the one line from the file
        		hvData = new HouseVotesData(line);
        		hvDataNoise = new HouseVotesDataNoise(line);
        		
        		sA2[i] = hvData;
        		sA2Noise[i] = hvDataNoise;
        		i++;
        		line = reader2.readLine();
        	}
        	
        }catch (IOException e) {
        	e.printStackTrace();
        }
        
        //Glass Data?
//END SETTING UP ALL THE DATA
        
//BREAST CANCER DATA NORMAL 
     //#1., #2., & #3.       
       
        TrainClassLoss trainBenMal = new TrainClassLoss(sA);

     //END OF #1., #2., & #3. 
        
//END OF BREAST CANCER DATA NORMAL
        
//BREAST CANCER DATA NOISE
        //#1., #2., & #3. 
        
        TrainClassLoss trainBenMalNoise = new TrainClassLoss(sANoise);

       //END OF  #1., #2., & #3. 
          
//END OF BREAST CANCER DATA NOISE

        
//HOUSE VOTE DATA NORMAL 
       //#1., #2., & #3. 
       TrainClassLoss trainHouseVotes = new TrainClassLoss(sA2);
    
       //END OF #1., #2., & #3. 
        
//END OF HOUSE VOTE DATA NORMAL
	

//HOUSE VOTE DATA NOISE
       //#1., #2., & #3. 
       TrainClassLoss trainHouseVotesNoise = new TrainClassLoss(sA2Noise);
       //END OF #1., #2., & #3. 
      
////END OF HOUSE VOTE DATA NOISE
  
    }
    
}
