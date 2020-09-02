package project_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import project_1.BreastCancerData;

public class DataMain {
    public static void main(String[] args) {
        BufferedReader reader; //creates a buffered reader
        BreastCancerData bData; //creates the breast cancer data class

        BreastCancerData[] st = new BreastCancerData[699];
        
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
        		
        		st[i] = bData;
        		i++;
        		line = reader.readLine();
        	}
        	
        }catch (IOException e) {
        	e.printStackTrace();
        }
        
        
     //#1. & #2.
      //split data up by class
        BreastCancerData[] mal = new BreastCancerData[241];
    	BreastCancerData[] ben = new BreastCancerData[458];
    	int countBen = 0;
    	int countMal = 0;
    	
    	
    	for(int i = 0; i < st.length; i++) {
    		if(st[i].getCl().equals("2")){
    			ben[countBen] = st[i];
    			countBen++;
    		}else{
    			mal[countMal] = st[i];
    			countMal++;
    		}
    	}

        
        //Q(ben)
        float xBen = ((float)ben.length/((float)st.length));
        
        //Q(mal)
        float xMal = ((float)mal.length/((float)st.length));
     //END OF #1. & #2.
        
     //#3.
        //the id numbers with attributes 2-10 are instances of that attribute with 11 showing the class attribute
        //TRAINING from TrainBCD
  
        TrainBCD trainBen = new TrainBCD();
        double[] trainDataBen = new double[9];
        for(int attribute = 0; attribute < ben[0].getFeat().length; attribute++) {
        	for(int attributeVal = 1; attributeVal<=10; attributeVal++) {
        		trainDataBen[attribute] = trainBen.train(ben, attributeVal, attribute);
        	}
        }
        
        TrainBCD trainMal = new TrainBCD();
        double[] trainDataMal = new double[9];
        for(int attribute = 0; attribute < mal[0].getFeat().length; attribute++) {
        	for(int attributeVal = 0; attributeVal<=10; attributeVal++) {
        		trainDataMal[attribute] = trainMal.train(mal, attributeVal, attribute);
        	}
        }
        
//      //CLASSIFING
        double piNotation = 1;
        for(double trainNum : trainDataBen) {
        	piNotation *= trainNum;
        }
        
        double c1 = xBen*piNotation;
        
        piNotation = 1;
        for(double trainNum : trainDataMal) {
        	piNotation *= trainNum;
        }
        
        double c2 = xMal*piNotation;
        
        for(double x : trainDataBen) {
        	System.out.println(x);
        }
        
        System.out.println("");
        
        for(double x : trainDataMal) {
        	System.out.println(x);
        }
        
        System.out.println("");
        
        System.out.println(c1 + " and " + c2);
        System.out.println("Mal is higher");
        
     //END OF #3.   
      
    }
    
//Finding what I changed!
    // I put this above methods that I changed in Binary Search Tree
//********************************************************************************************//   
//                              I CHANGED THIS METHOD                                         //
//********************************************************************************************//  
    // I did play around with the enqueue and dequeue methods but ended up reverting them back to what they were
    // Originally.
}
