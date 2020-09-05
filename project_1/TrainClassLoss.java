package project_1;

import java.util.Arrays;

public class TrainClassLoss{
	public BreastCancerData[] mal = new BreastCancerData[241];
	public BreastCancerData[] ben = new BreastCancerData[458];
	public int countBen = 0;
	public int countMal = 0;
	float xBen = 0;
	float xMal = 0;
	public double[][] trainDataBen = new double[9][10];
    public double[][] trainDataMal = new double[9][10];

    
	public BreastCancerDataNoise[] malNoise = new BreastCancerDataNoise[241];
	public BreastCancerDataNoise[] benNoise = new BreastCancerDataNoise[458];
	public int countBenNoise = 0;
	public int countMalNoise = 0;
	float xBenNoise = 0;
	float xMalNoise = 0;
	public double[][] trainDataBenNoise = new double[9][10];
    public double[][] trainDataMalNoise = new double[9][10];

    
    public HouseVotesData[] demo = new HouseVotesData[267];
 	public HouseVotesData[] repub = new HouseVotesData[168];
	public int countDemo = 0;
	public int countRepub = 0;
	float xDemo = 0;
	float xRepub = 0;
	public double[][] trainDataDemo = new double[16][2];
    public double[][] trainDataRepub = new double[16][2];

    
    public HouseVotesDataNoise[] demoNoise = new HouseVotesDataNoise[267];
 	public HouseVotesDataNoise[] repubNoise = new HouseVotesDataNoise[168];
	public int countDemoNoise = 0;
	public int countRepubNoise = 0;
	float xDemoNoise = 0;
	float xRepubNoise = 0;
	public double[][] trainDataDemoNoise = new double[16][2];
    public double[][] trainDataRepubNoise = new double[16][2];

    

	public TrainClassLoss(BreastCancerData[] sA) {
       
    	for(int i = 0; i < sA.length; i++) {
    		if(sA[i].getCl().equals("2")){
    			ben[countBen] = sA[i];
    			countBen++;
    		}else{
    			mal[countMal] = sA[i];
    			countMal++;
    		}
    	}

        
        //Q(ben)
        float xBen = ((float)ben.length/((float)sA.length));
        
        //Q(mal)
        float xMal = ((float)mal.length/((float)sA.length));
        
        //All POSSIBLE F(A_j = a_k, C = c_i)
        for(int attribute = 0; attribute < ben[0].getFeat().length; attribute++) {
        	for(int attributeVal = 1; attributeVal<=10; attributeVal++) {
        		trainDataBen[attribute][attributeVal-1] = train(ben, attributeVal, attribute);
        	}
        }
        
        for(int attribute = 0; attribute < mal[0].getFeat().length; attribute++) {
        	for(int attributeVal = 1; attributeVal<=10; attributeVal++) {
        		trainDataMal[attribute][attributeVal-1] = train(mal, attributeVal, attribute);
        	}
        }
        
        //Classifying one set of data for one set of data
        	//double c1 = xBen*(trainDataBen[0][4] * trainDataBen[1][0] * trainDataBen[2][0] * trainDataBen[3][0] * trainDataBen[4][1] * trainDataBen[5][0] * trainDataBen[6][1] * trainDataBen[7][0] * trainDataBen[8][0]);
        	//double c2 = xMal*(trainDataMal[0][4] * trainDataMal[1][0] * trainDataMal[2][0] * trainDataMal[3][0] * trainDataMal[4][1] * trainDataMal[5][0] * trainDataMal[6][1] * trainDataMal[7][0] * trainDataMal[8][0]);
        loss(sA,xBen,xMal,trainDataBen,trainDataMal);
        
	}
		
	public float train(BreastCancerData[] classSpecificData, int targetInstance, int feature) {

        float num=0;
        for (BreastCancerData data : classSpecificData) {
            String[] attributeData = data.getFeat();
            String attr = attributeData[feature];
            
            if (attr.equals(Integer.toString(targetInstance))) {
                num++;
                //System.out.println("I made it in here");
            }

        }
//        System.out.println("Num: "+ num + ", feature: " + feature);
        
        int numOfAttributes = classSpecificData[0].getFeat().length; //not counting class attribute?
        		
        return (num+1)/((float)classSpecificData.length+numOfAttributes);
	}
	
	/*
	 * 
		Makes a confusion matrix (FP / FN are in context of c1)
		----trueClass------
	  		c1  c2  c3
	|    c1  TP  FP  FP
	guessc2  FN  TP
	|    c3  FN      TP
	definitely confusing...
	This method goes through the data given in the parameter, classifies it,
	(classifier trained on iris-data) and compares it against the original iris-data. It gives
	*Accuracy = total correct / total samples
	*Error = 1 - accuracy
	*Precision
	*Recall
	*
	*/
	public void loss(BreastCancerData[] testData, float probC1, float probC2, double[][] allFC1, double[][] allFC2) {
	    int count= 0;
	    int[][] confusionMatrix = new int[2][2];
	
	    //this loops through all irisData's in the test data and fills the confusion matrix
	    for (BreastCancerData bcD: testData) {
	        String trueClass = bcD.getCl();
	        String[] test = bcD.getFeat();
	        String guess = classify(test,allFC1,allFC2,probC1,probC2); //this is what I did outside this function
	
	        if (trueClass.contentEquals("2")) {
	            if (guess.contentEquals(trueClass)) {
	                count++;
	                confusionMatrix[0][0]++;
	            }
	            if (guess.contentEquals("4")) {
	                confusionMatrix[1][0]++;
	            }
	        }
	        if (trueClass.contentEquals("4")) {
	            if (guess.contentEquals(trueClass)) {
	                count++;
	                confusionMatrix[1][1]++;
	            }
	            if (guess.contentEquals("2")) {
	                confusionMatrix[0][1]++;
	            }
	        }
	
	    }

	    //Calculating Precision Pmacro and Pmicro for 3 classes
	    System.out.println("\nBreast Cancer Data:");
	    double Pmacro= 0;
	    double Pmicro= 0;
	    int TP = 0, TPsum = 0;
	    int FP = 0, FPsum= 0;
	    int i = 0;
	    for (int[] row: confusionMatrix) {
	        System.out.println(Arrays.toString(row));
	        TP = row[i];
	        FP = row[(i+1)%2] + row[(i+2)%2];

	        TPsum += TP;
	        FPsum += FP + TP;

	        Pmacro += (double)TP / (TP + FP);
	        i++;
	    }
	    Pmacro /= 3.0;
	    Pmicro = (double)TPsum / FPsum;
	    System.out.println("Pmacro: "+ Pmacro);
	    System.out.println("Pmicro: "+ Pmicro);

	    //Calculating Risk Rmacro and Rmicro for 3 classes
	    double Rmacro = 0;
	    for (int j= 0; j < 2; j++) {
	        Rmacro += (double)confusionMatrix[0][j] / (confusionMatrix[0][j] + confusionMatrix[1][j]);
	    }
	    Rmacro /= 3.0;
	    System.out.println("Rmacro: "+ Rmacro);

	    double accuracy = count / 150.0;
	    System.out.println("Accuracy: "+ accuracy);
	    double error = 1 - accuracy;
	    System.out.println("Error: "+ error);

	}

	public TrainClassLoss(BreastCancerDataNoise[] sA) {
	       
    	for(int i = 0; i < sA.length; i++) {
    		if(sA[i].getCl().equals("2")){
    			benNoise[countBenNoise] = sA[i];
    			countBenNoise++;
    		}else{
    			malNoise[countMalNoise] = sA[i];
    			countMalNoise++;
    		}
    	}

        
        //Q(ben)
        float xBenNoise = ((float)benNoise.length/((float)sA.length));
        
        //Q(mal)
        float xMalNoise = ((float)malNoise.length/((float)sA.length));
        
        //All POSSIBLE F(A_j = a_k, C = c_i)
        for(int attribute = 0; attribute < benNoise[0].getFeat().length; attribute++) {
        	for(int attributeVal = 1; attributeVal<=10; attributeVal++) {
        		trainDataBenNoise[attribute][attributeVal-1] = train(benNoise, attributeVal, attribute);
        	}
        }
        
        for(int attribute = 0; attribute < malNoise[0].getFeat().length; attribute++) {
        	for(int attributeVal = 1; attributeVal<=10; attributeVal++) {
        		trainDataMalNoise[attribute][attributeVal-1] = train(malNoise, attributeVal, attribute);
        	}
        }
        
        //Classifying one set of data for one set of data
        	//double c1 = xBen*(trainDataBen[0][4] * trainDataBen[1][0] * trainDataBen[2][0] * trainDataBen[3][0] * trainDataBen[4][1] * trainDataBen[5][0] * trainDataBen[6][1] * trainDataBen[7][0] * trainDataBen[8][0]);
        	//double c2 = xMal*(trainDataMal[0][4] * trainDataMal[1][0] * trainDataMal[2][0] * trainDataMal[3][0] * trainDataMal[4][1] * trainDataMal[5][0] * trainDataMal[6][1] * trainDataMal[7][0] * trainDataMal[8][0]);
        loss(sA,xBenNoise,xMalNoise,trainDataBenNoise,trainDataMalNoise);
        
	}
	
	public float train(BreastCancerDataNoise[] classSpecificData, int targetInstance, int feature) {

        float num=0;
        for (BreastCancerDataNoise data : classSpecificData) {
            String[] attributeData = data.getFeat();
            String attr = attributeData[feature];
            
            if (attr.equals(Integer.toString(targetInstance))) {
                num++;
                //System.out.println("I made it in here");
            }

        }
//        System.out.println("Num: "+ num + ", feature: " + feature);
        
        int numOfAttributes = classSpecificData[0].getFeat().length; //not counting class attribute?
        		
        return (num+1)/((float)classSpecificData.length+numOfAttributes);
	}
	
	public void loss(BreastCancerDataNoise[] testData, float probC1, float probC2, double[][] allFC1, double[][] allFC2) {
	    int count= 0;
	    int[][] confusionMatrix = new int[2][2];
	
	    //this loops through all irisData's in the test data and fills the confusion matrix
	    for (BreastCancerDataNoise bcD: testData) {
	        String trueClass = bcD.getCl();
	        String[] test = bcD.getFeat();
	        String guess = classify(test,allFC1,allFC2,probC1,probC2); //this is what I did outside this function
	
	        if (trueClass.contentEquals("2")) {
	            if (guess.contentEquals(trueClass)) {
	                count++;
	                confusionMatrix[0][0]++;
	            }
	            if (guess.contentEquals("4")) {
	                confusionMatrix[1][0]++;
	            }
	        }
	        if (trueClass.contentEquals("4")) {
	            if (guess.contentEquals(trueClass)) {
	                count++;
	                confusionMatrix[1][1]++;
	            }
	            if (guess.contentEquals("2")) {
	                confusionMatrix[0][1]++;
	            }
	        }
	
	    }

	    //Calculating Precision Pmacro and Pmicro for 3 classes
	    System.out.println("\nBreast Cancer Noise Data:");
	    double Pmacro= 0;
	    double Pmicro= 0;
	    int TP = 0, TPsum = 0;
	    int FP = 0, FPsum= 0;
	    int i = 0;
	    for (int[] row: confusionMatrix) {
	        System.out.println(Arrays.toString(row));
	        TP = row[i];
	        FP = row[(i+1)%2] + row[(i+2)%2];

	        TPsum += TP;
	        FPsum += FP + TP;

	        Pmacro += (double)TP / (TP + FP);
	        i++;
	    }
	    Pmacro /= 3.0;
	    Pmicro = (double)TPsum / FPsum;
	    System.out.println("Pmacro: "+ Pmacro);
	    System.out.println("Pmicro: "+ Pmicro);

	    //Calculating Risk Rmacro and Rmicro for 3 classes
	    double Rmacro = 0;
	    for (int j= 0; j < 2; j++) {
	        Rmacro += (double)confusionMatrix[0][j] / (confusionMatrix[0][j] + confusionMatrix[1][j]);
	    }
	    Rmacro /= 3.0;
	    System.out.println("Rmacro: "+ Rmacro);

	    double accuracy = count / 150.0;
	    System.out.println("Accuracy: "+ accuracy);
	    double error = 1 - accuracy;
	    System.out.println("Error: "+ error);

	}

	public TrainClassLoss(HouseVotesData[] sA) {
	       
    	for(int i = 0; i < sA.length; i++) {
    		if(sA[i].getCl().equals("democrat")){
    			demo[countDemo] = sA[i];
    			countDemo++;
    		}else{
    			repub[countRepub] = sA[i];
    			countRepub++;
    		}
    	}

        
        //Q(Demo)
        float xDemo = ((float)demo.length/((float)sA.length));
        
        //Q(repub)
        float xRepub = ((float)repub.length/((float)sA.length));
        
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
         		trainDataDemo[attribute][attributeVal] = train(demo, attVal, attribute);
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
         		trainDataRepub[attribute][attributeVal] = train(repub, attVal, attribute);
         	
        	}
        }
        
        loss(sA,xDemo,xRepub,trainDataDemo,trainDataRepub);
        
	}
			
	public float train(HouseVotesData[] classSpecificData, String targetInstance, int feature) {

        float num=0;
        for (HouseVotesData data : classSpecificData) {
            String[] attributeData = data.getFeat();
            String attr = attributeData[feature];
            
            if (attr.equals(targetInstance)) {
                num++;
                //System.out.println("I made it in here");
            }

        }
//        System.out.println("Num: "+ num + ", feature: " + feature);
        
        int numOfAttributes = classSpecificData[0].getFeat().length; //not counting class attribute?
        		
        return (num+1)/((float)classSpecificData.length+numOfAttributes);
	}
	
	public void loss(HouseVotesData[] testData, float probC1, float probC2, double[][] allFC1, double[][] allFC2) {
	    int count= 0;
	    int[][] confusionMatrix = new int[2][2];
	
	    //this loops through all irisData's in the test data and fills the confusion matrix
	    for (HouseVotesData hvD: testData) {
	        String trueClass = hvD.getCl();
	        String[] test = hvD.getFeat();
	        String guess = classifyHV(test,allFC1,allFC2,probC1,probC2); //this is what I did outside this function
	
	        if (trueClass.contentEquals("democrat")) {
	            if (guess.contentEquals(trueClass)) {
	                count++;
	                confusionMatrix[0][0]++;
	            }
	            if (guess.contentEquals("republican")) {
	                confusionMatrix[1][0]++;
	            }
	        }
	        if (trueClass.contentEquals("republican")) {
	            if (guess.contentEquals(trueClass)) {
	                count++;
	                confusionMatrix[1][1]++;
	            }
	            if (guess.contentEquals("democrat")) {
	                confusionMatrix[0][1]++;
	            }
	        }
	
	    }

	    //Calculating Precision Pmacro and Pmicro for 3 classes
	    System.out.println("\nHouse Votes Data:");
	    double Pmacro= 0;
	    double Pmicro= 0;
	    int TP = 0, TPsum = 0;
	    int FP = 0, FPsum= 0;
	    int i = 0;
	    for (int[] row: confusionMatrix) {
	        System.out.println(Arrays.toString(row));
	        TP = row[i];
	        FP = row[(i+1)%2] + row[(i+2)%2];

	        TPsum += TP;
	        FPsum += FP + TP;

	        Pmacro += (double)TP / (TP + FP);
	        i++;
	    }
	    Pmacro /= 3.0;
	    Pmicro = (double)TPsum / FPsum;
	    System.out.println("Pmacro: "+ Pmacro);
	    System.out.println("Pmicro: "+ Pmicro);

	    //Calculating Risk Rmacro and Rmicro for 3 classes
	    double Rmacro = 0;
	    for (int j= 0; j < 2; j++) {
	        Rmacro += (double)confusionMatrix[0][j] / (confusionMatrix[0][j] + confusionMatrix[1][j]);
	    }
	    Rmacro /= 3.0;
	    System.out.println("Rmacro: "+ Rmacro);

	    double accuracy = count / 150.0;
	    System.out.println("Accuracy: "+ accuracy);
	    double error = 1 - accuracy;
	    System.out.println("Error: "+ error);

	}

	public TrainClassLoss(HouseVotesDataNoise[] sA) {
	       
    	for(int i = 0; i < sA.length; i++) {
    		if(sA[i].getCl().equals("democrat")){
    			demoNoise[countDemoNoise] = sA[i];
    			countDemoNoise++;
    		}else{
    			repubNoise[countRepubNoise] = sA[i];
    			countRepubNoise++;
    		}
    	}

        
        //Q(Demo)
        float xDemoNoise = ((float)demoNoise.length/((float)sA.length));
        
        //Q(repub)
        float xRepubNoise = ((float)repubNoise.length/((float)sA.length));
        
        //All POSSIBLE F(A_j = a_k, C = c_i)
        for(int attribute = 0; attribute < demoNoise[0].getFeat().length; attribute++) {
        	for(int attributeVal = 0; attributeVal<=1; attributeVal++) {
        		String attVal ="";
         		if(attributeVal == 0) {
         			attVal = Integer.toString(attributeVal);
         			attVal = "n";
         		}else{
         			attVal = "y";
         		}
         		trainDataDemoNoise[attribute][attributeVal] = train(demoNoise, attVal, attribute);
         	}
        }
        
        for(int attribute = 0; attribute < repubNoise[0].getFeat().length; attribute++) {
        	for(int attributeVal = 0; attributeVal<=1; attributeVal++) {
        		String attVal ="";
         		if(attributeVal == 0) {
         			attVal = Integer.toString(attributeVal);
         			attVal = "n";
         		}else{
         			attVal = "y";
         		}
         		trainDataRepubNoise[attribute][attributeVal] = train(repubNoise, attVal, attribute);
         	
        	}
        }
        
        loss(sA,xDemoNoise,xRepubNoise,trainDataDemoNoise,trainDataRepubNoise);
        
	}
	
	public float train(HouseVotesDataNoise[] classSpecificData, String targetInstance, int feature) {

        float num=0;
        for (HouseVotesDataNoise data : classSpecificData) {
            String[] attributeData = data.getFeat();
            String attr = attributeData[feature];
            
            if (attr.equals(targetInstance)) {
                num++;
                //System.out.println("I made it in here");
            }

        }
//        System.out.println("Num: "+ num + ", feature: " + feature);
        
        int numOfAttributes = classSpecificData[0].getFeat().length; //not counting class attribute?
        		
        return (num+1)/((float)classSpecificData.length+numOfAttributes);
	}

	public void loss(HouseVotesDataNoise[] testData, float probC1, float probC2, double[][] allFC1, double[][] allFC2) {
	    int count= 0;
	    int[][] confusionMatrix = new int[2][2];
	
	    //this loops through all irisData's in the test data and fills the confusion matrix
	    for (HouseVotesDataNoise hvD: testData) {
	        String trueClass = hvD.getCl();
	        String[] test = hvD.getFeat();
	        String guess = classifyHV(test,allFC1,allFC2,probC1,probC2); //this is what I did outside this function
	
	        if (trueClass.contentEquals("democrat")) {
	            if (guess.contentEquals(trueClass)) {
	                count++;
	                confusionMatrix[0][0]++;
	            }
	            if (guess.contentEquals("republican")) {
	                confusionMatrix[1][0]++;
	            }
	        }
	        if (trueClass.contentEquals("republican")) {
	            if (guess.contentEquals(trueClass)) {
	                count++;
	                confusionMatrix[1][1]++;
	            }
	            if (guess.contentEquals("democrat")) {
	                confusionMatrix[0][1]++;
	            }
	        }
	
	    }

	    //Calculating Precision Pmacro and Pmicro for 3 classes
	    System.out.println("\nHouse Votes Noise Data:");
	    double Pmacro= 0;
	    double Pmicro= 0;
	    int TP = 0, TPsum = 0;
	    int FP = 0, FPsum= 0;
	    int i = 0;
	    for (int[] row: confusionMatrix) {
	        System.out.println(Arrays.toString(row));
	        TP = row[i];
	        FP = row[(i+1)%2] + row[(i+2)%2];

	        TPsum += TP;
	        FPsum += FP + TP;

	        Pmacro += (double)TP / (TP + FP);
	        i++;
	    }
	    Pmacro /= 3.0;
	    Pmicro = (double)TPsum / FPsum;
	    System.out.println("Pmacro: "+ Pmacro);
	    System.out.println("Pmicro: "+ Pmicro);

	    //Calculating Risk Rmacro and Rmicro for 3 classes
	    double Rmacro = 0;
	    for (int j= 0; j < 2; j++) {
	        Rmacro += (double)confusionMatrix[0][j] / (confusionMatrix[0][j] + confusionMatrix[1][j]);
	    }
	    Rmacro /= 3.0;
	    System.out.println("Rmacro: "+ Rmacro);

	    double accuracy = count / 150.0;
	    System.out.println("Accuracy: "+ accuracy);
	    double error = 1 - accuracy;
	    System.out.println("Error: "+ error);

	}
		
	public String classify(String[] test, double[][] allFC1, double[][] allFC2,float probC1,float probC2) {
		//trainingSet = test
        float productOfTrainingC1 = 1;
        for(int i=0;i<test.length;i++) {
       	 for(int j=0;j<allFC1.length;j++) {
       		 String looking = test[i];
       		 int arrayLooking = Integer.valueOf(looking);
       		 productOfTrainingC1 *= allFC1[i][arrayLooking-1];
       	 }
        }
        
        float productOfTrainingC2 = 1;
        for(int i=0;i<test.length;i++) {
        	for(int j=0;j<allFC2.length;j++) {
        		String looking = test[i];
        		int arrayLooking = Integer.valueOf(looking);
        		productOfTrainingC2 *= allFC2[i][arrayLooking-1];
        	}
        }
		
        double chanceC1 = probC1*productOfTrainingC1;
        double chanceC2 = probC2*productOfTrainingC2;
        
        String classifiedAs = "";
        
        if(chanceC1 > chanceC2){
        	classifiedAs = "2";
        }else {
        	classifiedAs = "4";
        }
        
        return classifiedAs;
        
	}
	
	public String classifyHV(String[] test, double[][] allFC1, double[][] allFC2,float probC1,float probC2) {
		//trainingSet = test
        float productOfTrainingC1 = 1;
        for(int i=0;i<test.length;i++) {
       	 for(int j=0;j<allFC1.length;j++) {
       		 String looking = test[i];
       		 int arrayLooking = 0;
       		 if(looking.contentEquals("n")) {
       			arrayLooking = 0;
       		 }else {
       			arrayLooking = 1;
       		 }
       		 productOfTrainingC1 *= allFC1[i][arrayLooking];
       	 }
        }
        
        float productOfTrainingC2 = 1;
        for(int i=0;i<test.length;i++) {
        	for(int j=0;j<allFC2.length;j++) {
        		String looking = test[i];
          		int arrayLooking = 0;
          		if(looking.contentEquals("n")) {
          			arrayLooking = 0;
          		 }else {
          			arrayLooking = 1;
          		 }
          		productOfTrainingC2 *= allFC2[i][arrayLooking];
          	 }
        }
		
        double chanceC1 = probC1*productOfTrainingC1;
        double chanceC2 = probC2*productOfTrainingC2;
        
        String classifiedAs = "";
        
        if(chanceC1 > chanceC2){
        	classifiedAs = "democrat";
        }else {
        	classifiedAs = "republican";
        }
        
        return classifiedAs;
        
	}
	
	


	
	
	
}
