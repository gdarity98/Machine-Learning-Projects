package project_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import project_1.BreastCancerData;
import project_1.BinarySearchTree;
import project_1.Queue;

public class DataMain {
    public static void main(String[] args) {
//        BufferedReader reader; //creates a buffered reader
//        BreastCancerData bData; //creates the breast cancer data class
//        BinarySearchTree<String, BreastCancerData> st; //Creates a BST that will be full of breast cancer data
//        st = new BinarySearchTree();
//        try { //Reads in the file and checks for exception
//        	//The file path would need to be changed based on system.
//        	reader = new BufferedReader(new FileReader("data-sets/breast-cancer-wisconsin.data"));
//        	//reads the file in line by line
//        	String line = reader.readLine();
//        	//While we are not at the end of the file do things in the while loop
//        	while(line != null) {
//        		//This creates a bData class with the one line from the file
//        		bData = new BreastCancerData(line);
//
//        		st.put(bData.getID(), bData);
//        		line = reader.readLine();
//        	}
//
//        }catch (IOException e) {
//        	e.printStackTrace();
//        }
//
//        //Prints everything in tree
//        st.keys();  //This is a changed keys method to print depthFirst
//
//        //This is how we would get to specific data
//        BreastCancerData oneData = st.get(Integer.toString(95719));
//        System.out.println(oneData.getFullInfo());
        
        //To get to large amounts of data you would want to copy and change the .keys function or 
        //two other functions in order to get access to stuff while the tree is going through everything
        //For whatever reason if I wanted to sum the id numbers I would need to go into keys
        //then while it is going through add everything up while it does recursive calls
        //I'm sure we can figure it all out.


        //------------------------------------------------------------------------------------------testing
        BufferedReader reader1; //creates a buffered reader
        IrisData iData; //creates the breast cancer data class
        IrisData[] irisArray= new IrisData[150];

        try { //Reads in the file and checks for exception
            //The file path would need to be changed based on system.
            reader1 = new BufferedReader(new FileReader("data-sets/iris.data"));
            //reads the file in line by line
            String line1 = reader1.readLine();
            int lineNo = 0;
            //While we are not at the end of the file do things in the while loop
            while(line1 != null) {
                //This creates a bData class with the one line from the file
                iData = new IrisData(line1, ++lineNo);
                irisArray[lineNo - 1] = iData;

                System.out.println(lineNo + " " + Arrays.toString(iData.getBinnedFeatures()) + " " + iData.getClassNo());

                line1 = reader1.readLine();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        
      
    }
    
//Finding what I changed!
    // I put this above methods that I changed in Binary Search Tree
//********************************************************************************************//   
//                              I CHANGED THIS METHOD                                         //
//********************************************************************************************//  
    // I did play around with the enqueue and dequeue methods but ended up reverting them back to what they were
    // Originally.
}
