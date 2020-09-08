package project_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        	reader2 = new BufferedReader(new FileReader("C:\\Users\\gdari\\Desktop\\Important\\\\School\\CSCI 447\\Projects\\src\\project_1\\data-sets\\house-votes-84.data"));
        	
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
        
        //Glass Data
        BufferedReader reader3; //creates a buffered reader
        GlassData gData; //creates the breast cancer data class
        GlassDataNoise gDataNoise; //creates the breast cancer noise data class

        GlassData[] sA3 = new GlassData[214];
        GlassDataNoise[] sA3Noise = new GlassDataNoise[214]; //I don't think my noise changes 10% each time...maybe it does idk
        
        try { //Reads in the file and checks for exception
        	//The file path would need to be changed based on system.
        	reader3 = new BufferedReader(new FileReader("C:\\Users\\gdari\\Desktop\\Important\\\\School\\CSCI 447\\Projects\\src\\project_1\\data-sets\\glass.data"));
        	
        	//reads the file in line by line
        	String line = reader3.readLine();
        	
        	//While we are not at the end of the file do things in the while loop
        	int i = 0;
        	while(line != null) {
        		//This creates a bData class with the one line from the file
        		gData = new GlassData(line);
        		gDataNoise = new GlassDataNoise(line);
        		
        		sA3[i] = gData;
        		sA3Noise[i] = gDataNoise;
        		i++;
        		line = reader3.readLine();
        	}
        	
        }catch (IOException e) {
        	e.printStackTrace();
        }
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
      
//END OF HOUSE VOTE DATA NOISE
       
//GLASS DATA NORMAL
       //#1., #2., & #3. 
       TrainClassLoss trainGlass = new TrainClassLoss(sA3);
       //END OF #1., #2., & #3. 
      
//END OF GLASS DATA NORMAL
       
//GLASS DATA NOISE
       //#1., #2., & #3. 
       TrainClassLoss glassNoise = new TrainClassLoss(sA3Noise);
       //END OF #1., #2., & #3. 
      
//END OF GLASS DATA NOISE
       
       
//CROSS VALIDATION
       //Breast Cancer Data Normal
       System.out.println("\nBreast Cancer Data Cross Validation:");
       
       TrainClassLoss  Trainer = new TrainClassLoss();
       
       BreastCancerData[] train = new BreastCancerData[630]; //had to make breast cancer data even
       
       BreastCancerData[] test = new BreastCancerData[70];
       
       BreastCancerData[][] dividedData = new BreastCancerData[10][70];
       
       double[] lossData = new double[6];
       
	   List<BreastCancerData> temp = Arrays.asList(sA);
       Collections.shuffle(temp);
       BreastCancerData[] shuffled = new BreastCancerData[sA.length];
       temp.toArray(shuffled);
       
       int i=0,j=0;
       
       int k = 0;
	   for(i = 0; i < 10; i++) {
		   for(j=0;j < 70;j++) {
			   if(k >= sA.length) {
				   dividedData[i][j] = null;
			   }else {
				   dividedData[i][j] = shuffled[k++];
			   }
		   }
	   }

       for(int pass = 0; pass< 10; pass++) {

           //separate data into train[] and test[]
    	   
    	   List<BreastCancerData> list = new ArrayList<>();
    	   for(i = 0; i < 9; i++) {
    		   list.addAll(Arrays.asList(dividedData[(i+pass)%10]));
    	   }
    	   
    	   list.toArray(train);
    	   test = dividedData[(pass+9)%10];
    	   
    	   //System.out.println(Arrays.deepToString(train));
    	  // System.out.println(Arrays.deepToString(test));
 
    	   int malLength = 0;
    	   int benLength = 0;
    	   for(i = 0; i < train.length;i++) {
    		   if(train[i] != null) {
	    		   if(train[i].getCl().equals("2")){
	    			   benLength++;
	    		   }else {
	    			   malLength++;
	    		   }
    		   }
    	   }
	   
    	   BreastCancerData[] mal = new BreastCancerData[malLength];
    	   BreastCancerData[] ben = new BreastCancerData[benLength];
    	   int countBen = 0;
    	   int countMal = 0;

    	   for(i = 0; i < train.length; i++) {
    		   if(train[i] != null) {
	    		   if(train[i].getCl().equals("2")){
	    			   ben[countBen] = train[i];
	    			   countBen++;
	    		   }else{
	    			   mal[countMal] = train[i];
	    			   countMal++;
	    		   }
    		   }
    	   }


    	   //Q(ben)
    	   float xBen = ((float)ben.length/((float)train.length));

    	   //Q(mal)
    	   float xMal = ((float)mal.length/((float)train.length));

    	   double[][] trainDataBen = new double[9][10];
    	   double[][] trainDataMal = new double[9][10];

    	   //All POSSIBLE F(A_j = a_k, C = c_i)
    	   for(int attribute = 0; attribute < ben[0].getFeat().length; attribute++) {
    		   for(int attributeVal = 1; attributeVal<=10; attributeVal++) {
    			   trainDataBen[attribute][attributeVal-1] = Trainer.train(ben, attributeVal, attribute);
    		   }
    	   }

    	   for(int attribute = 0; attribute < mal[0].getFeat().length; attribute++) {
    		   for(int attributeVal = 1; attributeVal<=10; attributeVal++) {
    			   trainDataMal[attribute][attributeVal-1] = Trainer.train(mal, attributeVal, attribute);
    		   }
    	   }

    	   //add lossData from this iteration to lossData[]
    	   int c = 0;
    	   double[] lossTemp = Trainer.loss(test,xBen,xMal,trainDataBen,trainDataMal);
    	   for(double d: lossTemp) {
    		   lossData[c++] =+ d;
    	   }

    	   i = 0;
    	   for(double d: lossData) {
    		   lossData[i++] = d/= 10;
    	   }

    	   Trainer.printLossData(lossData);
      }
     //END Breast Cancer Data Normal
     
     //Breast Cancer Data Noise
       //TrainClassLoss  Trainer = new TrainClassLoss();
       System.out.println("\nBreast Cancer Noise Data Cross Validation:");
       
       BreastCancerDataNoise[] trainN = new BreastCancerDataNoise[630]; //had to make breast cancer data even
       
       BreastCancerDataNoise[] testN = new BreastCancerDataNoise[70];
       
       BreastCancerDataNoise[][] dividedDataN = new BreastCancerDataNoise[10][70];
       
       double[] lossDataN = new double[6];
       
	   List<BreastCancerDataNoise> tempN = Arrays.asList(sANoise);
       Collections.shuffle(tempN);
       BreastCancerDataNoise[] shuffledN = new BreastCancerDataNoise[sANoise.length];
       tempN.toArray(shuffledN);
       
       k=0;
	   for(i = 0; i < 10; i++) {
		   for(j=0;j < 70;j++) {
			   if(k >= sANoise.length) {
				   dividedDataN[i][j] = null;
			   }else {
				   dividedDataN[i][j] = shuffledN[k++];
			   }
		   }
	   }

       for(int pass = 0; pass< 10; pass++) {

           //separate data into train[] and test[]
    	   
    	   List<BreastCancerDataNoise> list = new ArrayList<>();
    	   for(i = 0; i < 9; i++) {
    		   list.addAll(Arrays.asList(dividedDataN[(i+pass)%10]));
    	   }
    	   
    	   list.toArray(trainN);
    	   testN = dividedDataN[(pass+9)%10];
    	   
    	   //System.out.println(Arrays.deepToString(train));
    	  // System.out.println(Arrays.deepToString(test));
 
    	   int malLength = 0;
    	   int benLength = 0;
    	   for(i = 0; i < trainN.length;i++) {
    		   if(trainN[i] != null) {
	    		   if(trainN[i].getCl().equals("2")){
	    			   benLength++;
	    		   }else {
	    			   malLength++;
	    		   }
    		   }
    	   }
	   
    	   BreastCancerDataNoise[] mal = new BreastCancerDataNoise[malLength];
    	   BreastCancerDataNoise[] ben = new BreastCancerDataNoise[benLength];
    	   int countBen = 0;
    	   int countMal = 0;

    	   for(i = 0; i < trainN.length; i++) {
    		   if(trainN[i] != null) {
	    		   if(trainN[i].getCl().equals("2")){
	    			   ben[countBen] = trainN[i];
	    			   countBen++;
	    		   }else{
	    			   mal[countMal] = trainN[i];
	    			   countMal++;
	    		   }
    		   }
    	   }


    	   //Q(ben)
    	   float xBen = ((float)ben.length/((float)trainN.length));

    	   //Q(mal)
    	   float xMal = ((float)mal.length/((float)trainN.length));

    	   double[][] trainDataBen = new double[9][10];
    	   double[][] trainDataMal = new double[9][10];

    	   //All POSSIBLE F(A_j = a_k, C = c_i)
    	   for(int attribute = 0; attribute < ben[0].getFeat().length; attribute++) {
    		   for(int attributeVal = 1; attributeVal<=10; attributeVal++) {
    			   trainDataBen[attribute][attributeVal-1] = Trainer.train(ben, attributeVal, attribute);
    		   }
    	   }

    	   for(int attribute = 0; attribute < mal[0].getFeat().length; attribute++) {
    		   for(int attributeVal = 1; attributeVal<=10; attributeVal++) {
    			   trainDataMal[attribute][attributeVal-1] = Trainer.train(mal, attributeVal, attribute);
    		   }
    	   }

    	   //add lossData from this iteration to lossData[]
    	   int c = 0;
    	   double[] lossTemp = Trainer.loss(testN,xBen,xMal,trainDataBen,trainDataMal);
    	   for(double d: lossTemp) {
    		   lossDataN[c++] =+ d;
    	   }

    	   i = 0;
    	   for(double d: lossDataN) {
    		   lossDataN[i++] = d/= 10;
    	   }

    	   Trainer.printLossData(lossDataN);
      }
     //END Breast Cancer Noise
       
     //House Votes Data Normal
       
       System.out.println("\nHouse Votes Data Cross Validation:");
       
       HouseVotesData[] train2 = new HouseVotesData[400]; //had to make breast cancer data even
 
       HouseVotesData[] test2 = new HouseVotesData[44];
       
       HouseVotesData[][] dividedData2 = new HouseVotesData[10][44];
       
       double[] lossData2 = new double[6];
       
	   List<HouseVotesData> temp2 = Arrays.asList(sA2);
       Collections.shuffle(temp2);
       HouseVotesData[] shuffled2 = new HouseVotesData[sA2.length];
       temp2.toArray(shuffled2);
       
       i=0;
       j=0;
       
       k = 0;
	   for(i = 0; i < 10; i++) {
		   for(j=0;j < 44;j++) {
			   if(k >= sA2.length) {
				   dividedData2[i][j] = null;
			   }else {
				   dividedData2[i][j] = shuffled2[k++];
			   }
		   }
	   }

       for(int pass = 0; pass< 10; pass++) {

           //separate data into train[] and test[]
    	   
    	   List<HouseVotesData> list = new ArrayList<>();
    	   for(i = 0; i < 9; i++) {
    		   list.addAll(Arrays.asList(dividedData2[(i+pass)%10]));
    	   }
    	   
    	   list.toArray(train2);
    	   test2 = dividedData2[(pass+9)%10];
    	   
    	   //System.out.println(Arrays.deepToString(train));
    	  // System.out.println(Arrays.deepToString(test));
    	   
    	   int repubLength = 0;
    	   int demoLength = 0;
    	   for(i = 0; i < train2.length;i++) {
    		   if(train2[i] != null) {
	    		   if(train2[i].getCl().equals("democrat")){
	    			   demoLength++;
	    		   }else {
	    			   repubLength++;
	    		   }
    		   }
    	   }
	   
    	   HouseVotesData[] repub = new HouseVotesData[repubLength];
    	   HouseVotesData[] demo = new HouseVotesData[demoLength];
    	   int countDemo = 0;
    	   int countRepub = 0;

    	   for(i = 0; i < train2.length; i++) {
    		   if(train2[i] != null) {
	    		   if(train2[i].getCl().equals("democrat")){
	    			   demo[countDemo] = train2[i];
	    			   countDemo++;
	    		   }else{
	    			   repub[countRepub] = train2[i];
	    			   countRepub++;
	    		   }
    		   }
    	   }


    	   //Q(demo)
    	   float xDemo = ((float)demo.length/((float)train2.length));

    	   //Q(repub)
    	   float xRepub = ((float)repub.length/((float)train2.length));

    	   double[][] trainDataDemo = new double[16][2];
    	   double[][] trainDataRepub = new double[16][2];

    	   //All POSSIBLE F(A_j = a_k, C = c_i)
           for(int attribute = 0; attribute < demo[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<=1; attributeVal++) {
           		String attVal ="";
            		if(attributeVal == 0) {
            			attVal = Integer.toString(attributeVal);
            			attVal = "n";
            		}else{
            			attVal = "y";
            		}
            		trainDataDemo[attribute][attributeVal] = Trainer.train(demo, attVal, attribute);
            	}
           }

           for(int attribute = 0; attribute < repub[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<=1; attributeVal++) {
           		String attVal ="";
            		if(attributeVal == 0) {
            			attVal = Integer.toString(attributeVal);
            			attVal = "n";
            		}else{
            			attVal = "y";
            		}
            		trainDataRepub[attribute][attributeVal] = Trainer.train(repub, attVal, attribute);
            	
           	}
           }

    	   //add lossData from this iteration to lossData[]
    	   int c = 0;
    	   double[] lossTemp = Trainer.loss(test2,xDemo,xRepub,trainDataDemo,trainDataRepub);
    	   for(double d: lossTemp) {
    		   lossData2[c++] =+ d;
    	   }

    	   i = 0;
    	   for(double d: lossData2) {
    		   lossData2[i++] = d/= 10;
    	   }

    	   Trainer.printLossData(lossData2);
      }   
 
     //END House Votes Normal
       
     //House Votes Data Noise
       
       
       System.out.println("\nHouse Votes Noise Data Cross Validation:");
       
       HouseVotesDataNoise[] train2N = new HouseVotesDataNoise[400]; //had to make breast cancer data even
 
       HouseVotesDataNoise[] test2N = new HouseVotesDataNoise[44];
       
       HouseVotesDataNoise[][] dividedData2N = new HouseVotesDataNoise[10][44];
       
       double[] lossData2N = new double[6];
       
	   List<HouseVotesDataNoise> temp2N = Arrays.asList(sA2Noise);
       Collections.shuffle(temp2N);
       HouseVotesDataNoise[] shuffled2N = new HouseVotesDataNoise[sA2Noise.length];
       temp2N.toArray(shuffled2N);
       
       i=0;
       j=0;
       
       k = 0;
	   for(i = 0; i < 10; i++) {
		   for(j=0;j < 44;j++) {
			   if(k >= sA2Noise.length) {
				   dividedData2N[i][j] = null;
			   }else {
				   dividedData2N[i][j] = shuffled2N[k++];
			   }
		   }
	   }

       for(int pass = 0; pass< 10; pass++) {

           //separate data into train[] and test[]
    	   
    	   List<HouseVotesDataNoise> list = new ArrayList<>();
    	   for(i = 0; i < 9; i++) {
    		   list.addAll(Arrays.asList(dividedData2N[(i+pass)%10]));
    	   }
    	   
    	   list.toArray(train2N);
    	   test2N = dividedData2N[(pass+9)%10];
    	   
    	   //System.out.println(Arrays.deepToString(train));
    	  // System.out.println(Arrays.deepToString(test));
    	   
    	   int repubLength = 0;
    	   int demoLength = 0;
    	   for(i = 0; i < train2N.length;i++) {
    		   if(train2N[i] != null) {
	    		   if(train2N[i].getCl().equals("democrat")){
	    			   demoLength++;
	    		   }else {
	    			   repubLength++;
	    		   }
    		   }
    	   }
	   
    	   HouseVotesDataNoise[] repub = new HouseVotesDataNoise[repubLength];
    	   HouseVotesDataNoise[] demo = new HouseVotesDataNoise[demoLength];
    	   int countDemo = 0;
    	   int countRepub = 0;

    	   for(i = 0; i < train2N.length; i++) {
    		   if(train2N[i] != null) {
	    		   if(train2N[i].getCl().equals("democrat")){
	    			   demo[countDemo] = train2N[i];
	    			   countDemo++;
	    		   }else{
	    			   repub[countRepub] = train2N[i];
	    			   countRepub++;
	    		   }
    		   }
    	   }


    	   //Q(demo)
    	   float xDemo = ((float)demo.length/((float)train2N.length));

    	   //Q(repub)
    	   float xRepub = ((float)repub.length/((float)train2N.length));

    	   double[][] trainDataDemo = new double[16][2];
    	   double[][] trainDataRepub = new double[16][2];

    	   //All POSSIBLE F(A_j = a_k, C = c_i)
           for(int attribute = 0; attribute < demo[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<=1; attributeVal++) {
           		String attVal ="";
            		if(attributeVal == 0) {
            			attVal = Integer.toString(attributeVal);
            			attVal = "n";
            		}else{
            			attVal = "y";
            		}
            		trainDataDemo[attribute][attributeVal] = Trainer.train(demo, attVal, attribute);
            	}
           }

           for(int attribute = 0; attribute < repub[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<=1; attributeVal++) {
           		String attVal ="";
            		if(attributeVal == 0) {
            			attVal = Integer.toString(attributeVal);
            			attVal = "n";
            		}else{
            			attVal = "y";
            		}
            		trainDataRepub[attribute][attributeVal] = Trainer.train(repub, attVal, attribute);
            	
           	}
           }

    	   //add lossData from this iteration to lossData[]
    	   int c = 0;
    	   double[] lossTemp = Trainer.loss(test2N,xDemo,xRepub,trainDataDemo,trainDataRepub);
    	   for(double d: lossTemp) {
    		   lossData2N[c++] =+ d;
    	   }

    	   i = 0;
    	   for(double d: lossData2N) {
    		   lossData2N[i++] = d/= 10;
    	   }

    	   Trainer.printLossData(lossData2N);
      }   
 
       
     //END House Votes Noise
       
     //Glass Data Normal
       
       
       System.out.println("\nGlass Data Cross Validation:");
       
       GlassData[] train3 = new GlassData[200]; //had to make breast cancer data even
 
       GlassData[] test3 = new GlassData[22];
       
       GlassData[][] dividedData3 = new GlassData[10][22];
       
       double[] lossData3 = new double[6];
       
	   List<GlassData> temp3 = Arrays.asList(sA3);
       Collections.shuffle(temp3);
       GlassData[] shuffled3 = new GlassData[sA3.length];
       temp3.toArray(shuffled3);
       
       i=0;
       j=0;
       
       k = 0;
	   for(i = 0; i < 10; i++) {
		   for(j=0;j < 22;j++) {
			   if(k >= sA3.length) {
				   dividedData3[i][j] = null;
			   }else {
				   dividedData3[i][j] = shuffled3[k++];
			   }
		   }
	   }

       for(int pass = 0; pass< 10; pass++) {

           //separate data into train[] and test[]
    	   
    	   List<GlassData> list = new ArrayList<>();
    	   for(i = 0; i < 9; i++) {
    		   list.addAll(Arrays.asList(dividedData3[(i+pass)%10]));
    	   }
    	   
    	   list.toArray(train3);
    	   test3 = dividedData3[(pass+9)%10];
    	   
  		 int lengthBWFP = 0;
  		 int lengthBWNFP = 0;
  		  int lengthVWFP = 0;
  		//public int lengthVWNFP = 0;
  		  int lengthCont = 0;
  		  int lengthTableW = 0;
  		  int lengthHeadL= 0;
  		  
  		for(i = 0; i < train3.length; i++) {
  			if(train3[i] != null) {
	       		if(train3[i].getCl().equals("1")){
	       			
	       			lengthBWFP++;
	       		}else if(train3[i].getCl().equals("2")){
	       			
	       			lengthBWNFP++;
	       		}else if(train3[i].getCl().equals("3")){
	       			
	       			lengthVWFP++;
	       		}else if(train3[i].getCl().equals("5")){
	       			
	       			lengthCont++;
	       		}else if(train3[i].getCl().equals("6")){
	       			
	       			lengthTableW++;
	       		}else {
	       			
	       			lengthHeadL++;
	       		}
  			}
       	}
  		
   	     GlassData[] bWFP = new GlassData[lengthBWFP];
   		 GlassData[] bWNFP = new GlassData[lengthBWNFP];
   		 GlassData[] vWFP = new GlassData[lengthVWFP];
   		// GlassData[] vWNFP = new GlassData[0];
   		 GlassData[] cont = new GlassData[lengthCont];
   		 GlassData[] tableW = new GlassData[lengthTableW];
   		 GlassData[] headL = new GlassData[lengthHeadL];
   		 //GlassData[][] data = {bWFP,bWNFP,vWFP,cont,tableW,headL};
  		 int countBWFP = 0;
  		 int countBWNFP = 0;
  		  int countVWFP = 0;
  		//public int countVWNFP = 0;
  		  int countCont = 0;
  		  int countTableW = 0;
  		  int countHeadL= 0;

    	   for(i = 0; i < train3.length; i++) {
    		if(train3[i] != null) {
	       		if(train3[i].getCl().equals("1")){
	       			bWFP[countBWFP] = train3[i];
	       			countBWFP++;
	       		}else if(train3[i].getCl().equals("2")){
	       			bWNFP[countBWNFP] = train3[i];
	       			countBWNFP++;
	       		}else if(train3[i].getCl().equals("3")){
	       			vWFP[countVWFP] = train3[i];
	       			countVWFP++;
	       		}else if(train3[i].getCl().equals("5")){
	       			cont[countCont] = train3[i];
	       			countCont++;
	       		}else if(train3[i].getCl().equals("6")){
	       			tableW[countTableW] = train3[i];
	       			countTableW++;
	       		}else {
	       			headL[countHeadL] = train3[i];
	       			countHeadL++;
	       		}
    		}
       	}

    	   double[][] trainDataBWFP = new double[9][10];
 		  double[][] trainDataBWNFP = new double[9][10];
 		  double[][] trainDataVWFP = new double[9][10];
 		//public double[][] trainDataVWNFP = new double[9][7];
 		  double[][] trainDataCont = new double[9][10];
 		  double[][] trainDataTableW = new double[9][10];
 		  double[][] trainDataHeadL = new double[9][10];
 		
           
           //Q(of a class)
       	float xBWFP = ((float)bWFP.length/((float)sA.length));
       	float xBWNFP = ((float)bWNFP.length/((float)sA.length));
       	float xVWFP = ((float)vWFP.length/((float)sA.length));
       	//float xVWNFP = ((float)vWNFP.length/((float)sA.length));
       	float xCont = ((float)cont.length/((float)sA.length));
       	float xTableW = ((float)tableW.length/((float)sA.length));
       	float xHeadL = ((float)headL.length/((float)sA.length));
       	
           	
           //All POSSIBLE F(A_j = a_k, C = c_i)
           for(int attribute = 0; attribute < bWFP[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataBWFP[attribute][attributeVal] = Trainer.train(bWFP, attributeVal, attribute);
           	}
           }
           
           for(int attribute = 0; attribute < bWNFP[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataBWNFP[attribute][attributeVal] = Trainer.train(bWNFP, attributeVal, attribute);
           	}
           }
           
           for(int attribute = 0; attribute < vWFP[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataVWFP[attribute][attributeVal] = Trainer.train(vWFP, attributeVal, attribute);
           	}
           }
           
//           for(int attribute = 0; attribute < vWNFP[0].getFeat().length; attribute++) {
//           	for(int attributeVal = 1; attributeVal<=7; attributeVal++) {
//           		trainDataVWNFP[attribute][attributeVal-1] = train(vWNFP, attributeVal, attribute);
//           	}
//           }
           
           for(int attribute = 0; attribute < cont[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataCont[attribute][attributeVal] = Trainer.train(cont, attributeVal, attribute);
           	}
           }
           
           for(int attribute = 0; attribute < tableW[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataTableW[attribute][attributeVal] = Trainer.train(tableW, attributeVal, attribute);
           	}
           }
           
           for(int attribute = 0; attribute < headL[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataHeadL[attribute][attributeVal] = Trainer.train(headL, attributeVal, attribute);
           	}
           }
           
    	   //add lossData from this iteration to lossData[]
    	   int c = 0;
    	   double[] lossTemp = Trainer.loss(test3,xBWFP,xBWNFP,xVWFP,xCont,xTableW,xHeadL,trainDataBWFP,
           		trainDataBWNFP,
           		trainDataVWFP,
           		trainDataCont,
           		trainDataTableW,
           		trainDataHeadL);
    	   for(double d: lossTemp) {
    		   lossData3[c++] =+ d;
    	   }

    	   i = 0;
    	   for(double d: lossData3) {
    		   lossData3[i++] = d/= 10;
    	   }

    	   Trainer.printLossData(lossData3);
      }   
        
       
     //END Glass Noise
       
     //Glass Data Normal
       
       System.out.println("\nGlass Data Cross Validation:");
       
       GlassDataNoise[] train3N = new GlassDataNoise[200]; //had to make breast cancer data even
 
       GlassDataNoise[] test3N = new GlassDataNoise[22];
       
       GlassDataNoise[][] dividedData3N = new GlassDataNoise[10][22];
       
       double[] lossData3N = new double[6];
       
	   List<GlassDataNoise> temp3Noise = Arrays.asList(sA3Noise);
       Collections.shuffle(temp3Noise);
       GlassDataNoise[] shuffled3Noise = new GlassDataNoise[sA3Noise.length];
       temp3Noise.toArray(shuffled3Noise);
       
       i=0;
       j=0;
       
       k = 0;
	   for(i = 0; i < 10; i++) {
		   for(j=0;j < 22;j++) {
			   if(k >= sA3Noise.length) {
				   dividedData3N[i][j] = null;
			   }else {
				   dividedData3N[i][j] = shuffled3Noise[k++];
			   }
		   }
	   }

       for(int pass = 0; pass< 10; pass++) {

           //separate data into train[] and test[]
    	   
    	   List<GlassDataNoise> list = new ArrayList<>();
    	   for(i = 0; i < 9; i++) {
    		   list.addAll(Arrays.asList(dividedData3N[(i+pass)%10]));
    	   }
    	   
    	   list.toArray(train3N);
    	   test3N = dividedData3N[(pass+9)%10];
    	   
  		 int lengthBWFP = 0;
  		 int lengthBWNFP = 0;
  		  int lengthVWFP = 0;
  		//public int lengthVWNFP = 0;
  		  int lengthCont = 0;
  		  int lengthTableW = 0;
  		  int lengthHeadL= 0;
  		  
  		for(i = 0; i < train3N.length; i++) {
  			if(train3N[i] != null) {
	       		if(train3N[i].getCl().equals("1")){
	       			
	       			lengthBWFP++;
	       		}else if(train3N[i].getCl().equals("2")){
	       			
	       			lengthBWNFP++;
	       		}else if(train3N[i].getCl().equals("3")){
	       			
	       			lengthVWFP++;
	       		}else if(train3N[i].getCl().equals("5")){
	       			
	       			lengthCont++;
	       		}else if(train3N[i].getCl().equals("6")){
	       			
	       			lengthTableW++;
	       		}else {
	       			
	       			lengthHeadL++;
	       		}
  			}
       	}
  		
   	     GlassDataNoise[] bWFP = new GlassDataNoise[lengthBWFP];
   		 GlassDataNoise[] bWNFP = new GlassDataNoise[lengthBWNFP];
   		 GlassDataNoise[] vWFP = new GlassDataNoise[lengthVWFP];
   		// GlassDataNoise[] vWNFP = new GlassDataNoise[0];
   		 GlassDataNoise[] cont = new GlassDataNoise[lengthCont];
   		 GlassDataNoise[] tableW = new GlassDataNoise[lengthTableW];
   		 GlassDataNoise[] headL = new GlassDataNoise[lengthHeadL];
   		 //GlassDataNoise[][] data = {bWFP,bWNFP,vWFP,cont,tableW,headL};
  		 int countBWFP = 0;
  		 int countBWNFP = 0;
  		  int countVWFP = 0;
  		//public int countVWNFP = 0;
  		  int countCont = 0;
  		  int countTableW = 0;
  		  int countHeadL= 0;

    	   for(i = 0; i < train3N.length; i++) {
    		if(train3N[i] != null) {
	       		if(train3N[i].getCl().equals("1")){
	       			bWFP[countBWFP] = train3N[i];
	       			countBWFP++;
	       		}else if(train3N[i].getCl().equals("2")){
	       			bWNFP[countBWNFP] = train3N[i];
	       			countBWNFP++;
	       		}else if(train3N[i].getCl().equals("3")){
	       			vWFP[countVWFP] = train3N[i];
	       			countVWFP++;
	       		}else if(train3N[i].getCl().equals("5")){
	       			cont[countCont] = train3N[i];
	       			countCont++;
	       		}else if(train3N[i].getCl().equals("6")){
	       			tableW[countTableW] = train3N[i];
	       			countTableW++;
	       		}else {
	       			headL[countHeadL] = train3N[i];
	       			countHeadL++;
	       		}
    		}
       	}

    	   double[][] trainDataBWFP = new double[9][10];
 		  double[][] trainDataBWNFP = new double[9][10];
 		  double[][] trainDataVWFP = new double[9][10];
 		//public double[][] trainDataVWNFP = new double[9][7];
 		  double[][] trainDataCont = new double[9][10];
 		  double[][] trainDataTableW = new double[9][10];
 		  double[][] trainDataHeadL = new double[9][10];
 		
           
           //Q(of a class)
       	float xBWFP = ((float)bWFP.length/((float)sA.length));
       	float xBWNFP = ((float)bWNFP.length/((float)sA.length));
       	float xVWFP = ((float)vWFP.length/((float)sA.length));
       	//float xVWNFP = ((float)vWNFP.length/((float)sA.length));
       	float xCont = ((float)cont.length/((float)sA.length));
       	float xTableW = ((float)tableW.length/((float)sA.length));
       	float xHeadL = ((float)headL.length/((float)sA.length));
       	
           	
           //All POSSIBLE F(A_j = a_k, C = c_i)
           for(int attribute = 0; attribute < bWFP[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataBWFP[attribute][attributeVal] = Trainer.train(bWFP, attributeVal, attribute);
           	}
           }
           
           for(int attribute = 0; attribute < bWNFP[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataBWNFP[attribute][attributeVal] = Trainer.train(bWNFP, attributeVal, attribute);
           	}
           }
           
           for(int attribute = 0; attribute < vWFP[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataVWFP[attribute][attributeVal] = Trainer.train(vWFP, attributeVal, attribute);
           	}
           }
           
//           for(int attribute = 0; attribute < vWNFP[0].getFeat().length; attribute++) {
//           	for(int attributeVal = 1; attributeVal<=7; attributeVal++) {
//           		trainDataVWNFP[attribute][attributeVal-1] = train(vWNFP, attributeVal, attribute);
//           	}
//           }
           
           for(int attribute = 0; attribute < cont[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataCont[attribute][attributeVal] = Trainer.train(cont, attributeVal, attribute);
           	}
           }
           
           for(int attribute = 0; attribute < tableW[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataTableW[attribute][attributeVal] = Trainer.train(tableW, attributeVal, attribute);
           	}
           }
           
           for(int attribute = 0; attribute < headL[0].getFeat().length; attribute++) {
           	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
           		trainDataHeadL[attribute][attributeVal] = Trainer.train(headL, attributeVal, attribute);
           	}
           }
           
    	   //add lossData from this iteration to lossData[]
    	   int c = 0;
    	   double[] lossTemp = Trainer.loss(test3N,xBWFP,xBWNFP,xVWFP,xCont,xTableW,xHeadL,trainDataBWFP,
           		trainDataBWNFP,
           		trainDataVWFP,
           		trainDataCont,
           		trainDataTableW,
           		trainDataHeadL);
    	   for(double d: lossTemp) {
    		   lossData3N[c++] =+ d;
    	   }

    	   i = 0;
    	   for(double d: lossData3N) {
    		   lossData3N[i++] = d/= 10;
    	   }

    	   Trainer.printLossData(lossData3N);
      }   
        
              

     //END Glass Noise
      
         
//END CROSS VALIDATION
    }
}
