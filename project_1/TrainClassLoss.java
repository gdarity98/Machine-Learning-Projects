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
    
	public GlassData[] bWFP = new GlassData[70];
	public GlassData[] bWNFP = new GlassData[76];
	public GlassData[] vWFP = new GlassData[17];
	//public GlassData[] vWNFP = new GlassData[0];
	public GlassData[] cont = new GlassData[13];
	public GlassData[] tableW = new GlassData[9];
	public GlassData[] headL = new GlassData[29];
	public GlassData[][] data = {bWFP,bWNFP,vWFP,cont,tableW,headL};
	public int countBWFP = 0;
	public int countBWNFP = 0;
	public int countVWFP = 0;
	//public int countVWNFP = 0;
	public int countCont = 0;
	public int countTableW = 0;
	public int countHeadL= 0;
	public float xBWFP = 0;
	public float xBWNFP = 0;
	public float xVWFP = 0;
	//public float xVWNFP = 0;
	public float xCont = 0;
	public float xTableW = 0;
	public float xHeadL = 0;
	public double[][] trainDataBWFP = new double[9][10];
	public double[][] trainDataBWNFP = new double[9][10];
	public double[][] trainDataVWFP = new double[9][10];
	//public double[][] trainDataVWNFP = new double[9][7];
	public double[][] trainDataCont = new double[9][10];
	public double[][] trainDataTableW = new double[9][10];
	public double[][] trainDataHeadL = new double[9][10];
	
	public GlassDataNoise[] bWFPN = new GlassDataNoise[70];
	public GlassDataNoise[] bWNFPN = new GlassDataNoise[76];
	public GlassDataNoise[] vWFPN = new GlassDataNoise[17];
	//public GlassData[] vWNFP = new GlassData[0];
	public GlassDataNoise[] contN = new GlassDataNoise[13];
	public GlassDataNoise[] tableWN = new GlassDataNoise[9];
	public GlassDataNoise[] headLN = new GlassDataNoise[29];
	public GlassDataNoise[][] dataN = {bWFPN,bWNFPN,vWFPN,contN,tableWN,headLN};
	public int countBWFPN = 0;
	public int countBWNFPN = 0;
	public int countVWFPN = 0;
	//public int countVWNFP = 0;
	public int countContN = 0;
	public int countTableWN = 0;
	public int countHeadLN= 0;
	public float xBWFPN = 0;
	public float xBWNFPN = 0;
	public float xVWFPN = 0;
	//public float xVWNFP = 0;
	public float xContN = 0;
	public float xTableWN = 0;
	public float xHeadLN = 0;
	public double[][] trainDataBWFPN = new double[9][10];
	public double[][] trainDataBWNFPN = new double[9][10];
	public double[][] trainDataVWFPN = new double[9][10];
	//public double[][] trainDataVWNFP = new double[9][7];
	public double[][] trainDataContN = new double[9][10];
	public double[][] trainDataTableWN = new double[9][10];
	public double[][] trainDataHeadLN = new double[9][10];

    
	public TrainClassLoss() {
		
	}
	
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
	public double[] loss(BreastCancerData[] testData, float probC1, float probC2, double[][] allFC1, double[][] allFC2) {
	    int count= 0;
	    int[][] confusionMatrix = new int[2][2];
	    
	    //this loops through all irisData's in the test data and fills the confusion matrix
	    for (BreastCancerData bcD: testData) {
	    	if(bcD != null) {
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
	
	    }

	    //Calculating Precision Pmacro and Pmicro for 3 classes
	    //System.out.println("\nBreast Cancer Data:");
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
	    Pmacro /= 2.0;
	    Pmicro = (double)TPsum / FPsum;
	   // System.out.println("Pmacro: "+ Pmacro);
	   // System.out.println("Pmicro: "+ Pmicro);

	   // Calculating Risk Rmacro and Rmicro for 2 classes
	    double Rmacro = 0;
	    double Rmicro =0;
	    TPsum=0;
	    int FN, FNsum =0;
	    for (int j= 0; j < 2; j++) {
	    	TP = confusionMatrix[j][j];
	    	FN = confusionMatrix[(j+1)%2][j]; //+ confusionMatrix[(j+2)%2][j]
	        Rmacro += (double)TP/(TP+FN);
	        TPsum += TP;
	        FNsum += FN + TP;
	        
	    }
	    Rmacro /= 2.0;
	    Rmicro =(double)TPsum / FNsum;
	    
	  //  System.out.println("Rmacro: "+ Rmacro);

	    double accuracy = count / 699.0;
	  //  System.out.println("Accuracy: "+ accuracy);
	    double error = 1 - accuracy;
	  //  System.out.println("Error: "+ error);
	    
	    double[] lossData = new double[6];
        lossData[0] = accuracy;
        lossData[1] = error;
        lossData[2] = Pmacro;
        lossData[3] = Pmicro;
        lossData[4] = Rmacro;
        lossData[5] = Rmicro;

        return lossData;

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
	
	public double[] loss(BreastCancerDataNoise[] testData, float probC1, float probC2, double[][] allFC1, double[][] allFC2) {
	    int count= 0;
	    int[][] confusionMatrix = new int[2][2];
	
	    //this loops through all irisData's in the test data and fills the confusion matrix
	    for (BreastCancerDataNoise bcD: testData) {
	    	if(bcD != null) {
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
	    }

	    //Calculating Precision Pmacro and Pmicro for 3 classes
	    //System.out.println("\nBreast Cancer Noise Data:");
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
	    Pmacro /= 2.0;
	    Pmicro = (double)TPsum / FPsum;
	    //System.out.println("Pmacro: "+ Pmacro);
	    //System.out.println("Pmicro: "+ Pmicro);


		   // Calculating Risk Rmacro and Rmicro for 2 classes
	    double Rmacro = 0;
	    double Rmicro =0;
	    TPsum=0;
	    int FN, FNsum =0;
	    for (int j= 0; j < 2; j++) {
	    	TP = confusionMatrix[j][j];
	    	FN = confusionMatrix[(j+1)%2][j]; //+ confusionMatrix[(j+2)%2][j]
	        Rmacro += (double)TP/(TP+FN);
	        TPsum += TP;
	        FNsum += FN + TP;
	        
	    }
	    Rmacro /= 2.0;
	    Rmicro =(double)TPsum / FNsum;
	    
	  //  System.out.println("Rmacro: "+ Rmacro);
	    double accuracy = count / 699.0;
	  //  System.out.println("Accuracy: "+ accuracy);
	    double error = 1 - accuracy;
	  //  System.out.println("Error: "+ error);
	    
	    double[] lossData = new double[6];
        lossData[0] = accuracy;
        lossData[1] = error;
        lossData[2] = Pmacro;
        lossData[3] = Pmicro;
        lossData[4] = Rmacro;
        lossData[5] = Rmicro;

        return lossData;

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
	
	public double[] loss(HouseVotesData[] testData, float probC1, float probC2, double[][] allFC1, double[][] allFC2) {
	    int count= 0;
	    int[][] confusionMatrix = new int[2][2];
	
	    //this loops through all irisData's in the test data and fills the confusion matrix
	    for (HouseVotesData hvD: testData) {
	    	if(hvD != null) {
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
	    }

	    //Calculating Precision Pmacro and Pmicro for 3 classes
	    //System.out.println("\nHouse Votes Data:");
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
	    Pmacro /= 2.0;
	    Pmicro = (double)TPsum / FPsum;
	   // System.out.println("Pmacro: "+ Pmacro);
	    //System.out.println("Pmicro: "+ Pmicro);

		   // Calculating Risk Rmacro and Rmicro for 2 classes
	    double Rmacro = 0;
	    double Rmicro =0;
	    TPsum=0;
	    int FN, FNsum =0;
	    for (int j= 0; j < 2; j++) {
	    	TP = confusionMatrix[j][j];
	    	FN = confusionMatrix[(j+1)%2][j]; //+ confusionMatrix[(j+2)%2][j]
	        Rmacro += (double)TP/(TP+FN);
	        TPsum += TP;
	        FNsum += FN + TP;
	        
	    }
	    Rmacro /= 2.0;
	    Rmicro =(double)TPsum / FNsum;
	    
	  //  System.out.println("Rmacro: "+ Rmacro);
	    double accuracy = count / 435.0;
	  //  System.out.println("Accuracy: "+ accuracy);
	    double error = 1 - accuracy;
	  //  System.out.println("Error: "+ error);
	    
	    double[] lossData = new double[6];
     lossData[0] = accuracy;
     lossData[1] = error;
     lossData[2] = Pmacro;
     lossData[3] = Pmicro;
     lossData[4] = Rmacro;
     lossData[5] = Rmicro;

     return lossData;

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

	public double[] loss(HouseVotesDataNoise[] testData, float probC1, float probC2, double[][] allFC1, double[][] allFC2) {
	    int count= 0;
	    int[][] confusionMatrix = new int[2][2];
	
	    //this loops through all irisData's in the test data and fills the confusion matrix
	    for (HouseVotesDataNoise hvD: testData) {
	    	if(hvD != null) {
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
	    }

	    //Calculating Precision Pmacro and Pmicro for 3 classes
	    //System.out.println("\nHouse Votes Noise Data:");
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
	   // System.out.println("Pmacro: "+ Pmacro);
	   // System.out.println("Pmicro: "+ Pmicro);

		   // Calculating Risk Rmacro and Rmicro for 2 classes
	    double Rmacro = 0;
	    double Rmicro =0;
	    TPsum=0;
	    int FN, FNsum =0;
	    for (int j= 0; j < 2; j++) {
	    	TP = confusionMatrix[j][j];
	    	FN = confusionMatrix[(j+1)%2][j]; //+ confusionMatrix[(j+2)%2][j]
	        Rmacro += (double)TP/(TP+FN);
	        TPsum += TP;
	        FNsum += FN + TP;
	        
	    }
	    Rmacro /= 2.0;
	    Rmicro =(double)TPsum / FNsum;
	    
	  //  System.out.println("Rmacro: "+ Rmacro);
	    double accuracy = count / 435.0;
	  //  System.out.println("Accuracy: "+ accuracy);
	    double error = 1 - accuracy;
	  //  System.out.println("Error: "+ error);
	    
	    double[] lossData = new double[6];
	    lossData[0] = accuracy;
  		lossData[1] = error;
  		lossData[2] = Pmacro;
  		lossData[3] = Pmicro;
  		lossData[4] = Rmacro;
  		lossData[5] = Rmicro;

  		return lossData;

	}
	
	public TrainClassLoss(GlassData[] sA) {
	       //"4" does not exist
    	for(int i = 0; i < sA.length; i++) {
    		if(sA[i].getCl().equals("1")){
    			bWFP[countBWFP] = sA[i];
    			countBWFP++;
    		}else if(sA[i].getCl().equals("2")){
    			bWNFP[countBWNFP] = sA[i];
    			countBWNFP++;
    		}else if(sA[i].getCl().equals("3")){
    			vWFP[countVWFP] = sA[i];
    			countVWFP++;
    		}else if(sA[i].getCl().equals("5")){
    			cont[countCont] = sA[i];
    			countCont++;
    		}else if(sA[i].getCl().equals("6")){
    			tableW[countTableW] = sA[i];
    			countTableW++;
    		}else {
    			headL[countHeadL] = sA[i];
    			countHeadL++;
    		}
    	}

        
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
        		trainDataBWFP[attribute][attributeVal] = train(bWFP, attributeVal, attribute);
        	}
        }
        
        for(int attribute = 0; attribute < bWNFP[0].getFeat().length; attribute++) {
        	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
        		trainDataBWNFP[attribute][attributeVal] = train(bWNFP, attributeVal, attribute);
        	}
        }
        
        for(int attribute = 0; attribute < vWFP[0].getFeat().length; attribute++) {
        	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
        		trainDataVWFP[attribute][attributeVal] = train(vWFP, attributeVal, attribute);
        	}
        }
        
//        for(int attribute = 0; attribute < vWNFP[0].getFeat().length; attribute++) {
//        	for(int attributeVal = 1; attributeVal<=7; attributeVal++) {
//        		trainDataVWNFP[attribute][attributeVal-1] = train(vWNFP, attributeVal, attribute);
//        	}
//        }
        
        for(int attribute = 0; attribute < cont[0].getFeat().length; attribute++) {
        	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
        		trainDataCont[attribute][attributeVal] = train(cont, attributeVal, attribute);
        	}
        }
        
        for(int attribute = 0; attribute < tableW[0].getFeat().length; attribute++) {
        	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
        		trainDataTableW[attribute][attributeVal] = train(tableW, attributeVal, attribute);
        	}
        }
        
        for(int attribute = 0; attribute < headL[0].getFeat().length; attribute++) {
        	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
        		trainDataHeadL[attribute][attributeVal] = train(headL, attributeVal, attribute);
        	}
        }
        
        //Classifying one set of data for one set of data
        	//double c1 = xBen*(trainDataBen[0][4] * trainDataBen[1][0] * trainDataBen[2][0] * trainDataBen[3][0] * trainDataBen[4][1] * trainDataBen[5][0] * trainDataBen[6][1] * trainDataBen[7][0] * trainDataBen[8][0]);
        	//double c2 = xMal*(trainDataMal[0][4] * trainDataMal[1][0] * trainDataMal[2][0] * trainDataMal[3][0] * trainDataMal[4][1] * trainDataMal[5][0] * trainDataMal[6][1] * trainDataMal[7][0] * trainDataMal[8][0]);
        loss(sA,xBWFP,xBWNFP,xVWFP,xCont,xTableW,xHeadL,trainDataBWFP,
        		trainDataBWNFP,
        		trainDataVWFP,
        		trainDataCont,
        		trainDataTableW,
        		trainDataHeadL);
        
	}
		
	public double train(GlassData[] classSpecificData, int targetInstance, int feature) {

        int numFeatures= 9;
        int num= 0;
        for (GlassData data : classSpecificData) {
            int[] attributeData = data.getBinnedFeat();
            int attr = attributeData[feature];

            if (attr == targetInstance) {
                num++;
            }
        }

        return (double)(num+1) / (classSpecificData.length + numFeatures); //<-- making denominator smaller = worse accuracy on c3 ?!
	}
	
	public double[] loss(GlassData[] testData, float probC1, float probC2,float probC3,float probC5,float probC6,float probC7, 
			double[][] allFC1, double[][] allFC2,double[][] allFC3,
			double[][] allFC5,double[][] allFC6,double[][] allFC7) {
	    int count= 0;
	    int[][] confusionMatrix = new int[6][6];
	
	    //this loops through all irisData's in the test data and fills the confusion matrix
	    for (GlassData gD: testData) {
	    	if(gD != null) {
		        String trueClass = gD.getCl();
		        int[] test = gD.getBinnedFeat();
		        String guess = classifyG(test,allFC1,allFC2,allFC3,
		    			allFC5,allFC6,allFC7,probC1, 
		    			probC2,probC3,probC5,probC6,probC7); //this is what I did outside this function
		
		        if (trueClass.contentEquals("1")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[0][0]++;
		            }
		            if (guess.contentEquals("2")) {
		                confusionMatrix[1][0]++;
		            }
		            if (guess.contentEquals("3")) {
		                confusionMatrix[2][0]++;
		            }
		            if (guess.contentEquals("5")) {
		                confusionMatrix[3][0]++;
		            }
		            if (guess.contentEquals("6")) {
		                confusionMatrix[4][0]++;
		            }
		            if (guess.contentEquals("7")) {
		                confusionMatrix[5][0]++;
		            }
		        }
		        
		        if (trueClass.contentEquals("2")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[1][1]++;
		            }
		            if (guess.contentEquals("1")) {
		                confusionMatrix[0][1]++;
		            }
		            if (guess.contentEquals("3")) {
		                confusionMatrix[2][1]++;
		            }
		            if (guess.contentEquals("5")) {
		                confusionMatrix[3][1]++;
		            }
		            if (guess.contentEquals("6")) {
		                confusionMatrix[4][1]++;
		            }
		            if (guess.contentEquals("7")) {
		                confusionMatrix[5][1]++;
		            }
		        }
		        
		        if (trueClass.contentEquals("3")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[2][2]++;
		            }
		            if (guess.contentEquals("1")) {
		                confusionMatrix[0][2]++;
		            }
		            if (guess.contentEquals("2")) {
		                confusionMatrix[1][2]++;
		            }
		            if (guess.contentEquals("5")) {
		                confusionMatrix[3][2]++;
		            }
		            if (guess.contentEquals("6")) {
		                confusionMatrix[4][2]++;
		            }
		            if (guess.contentEquals("7")) {
		                confusionMatrix[5][2]++;
		            }
		        }
		        
		        if (trueClass.contentEquals("5")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[3][3]++;
		            }
		            if (guess.contentEquals("1")) {
		                confusionMatrix[0][3]++;
		            }
		            if (guess.contentEquals("2")) {
		                confusionMatrix[1][3]++;
		            }
		            if (guess.contentEquals("3")) {
		                confusionMatrix[2][3]++;
		            }
		            if (guess.contentEquals("6")) {
		                confusionMatrix[4][3]++;
		            }
		            if (guess.contentEquals("7")) {
		                confusionMatrix[5][3]++;
		            }
		        }
		        
		        if (trueClass.contentEquals("6")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[4][4]++;
		            }
		            if (guess.contentEquals("1")) {
		                confusionMatrix[0][4]++;
		            }
		            if (guess.contentEquals("2")) {
		                confusionMatrix[1][4]++;
		            }
		            if (guess.contentEquals("3")) {
		                confusionMatrix[2][4]++;
		            }
		            if (guess.contentEquals("5")) {
		                confusionMatrix[3][4]++;
		            }
		            if (guess.contentEquals("7")) {
		                confusionMatrix[5][4]++;
		            }
		        }
		        
		        if (trueClass.contentEquals("7")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[5][5]++;
		            }
		            if (guess.contentEquals("1")) {
		                confusionMatrix[0][5]++;
		            }
		            if (guess.contentEquals("2")) {
		                confusionMatrix[1][5]++;
		            }
		            if (guess.contentEquals("3")) {
		                confusionMatrix[2][5]++;
		            }
		            if (guess.contentEquals("5")) {
		                confusionMatrix[3][5]++;
		            }
		            if (guess.contentEquals("6")) {
		                confusionMatrix[4][5]++;
		            }
		        }
	    	}
	    }

	    //Calculating Precision Pmacro and Pmicro for 6 classes
	    //System.out.println("\nGlass Data:");
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
	    Pmacro /= 6.0;
	    Pmicro = (double)TPsum / FPsum;
	    //System.out.println("Pmacro: "+ Pmacro);
	    //System.out.println("Pmicro: "+ Pmicro);

		   // Calculating Risk Rmacro and Rmicro for 2 classes
	    double Rmacro = 0;
	    double Rmicro =0;
	    TPsum=0;
	    int FN, FNsum =0;
	    for (int j= 0; j < 2; j++) {
	    	TP = confusionMatrix[j][j];
	    	FN = confusionMatrix[(j+1)%2][j] + confusionMatrix[(j+2)%2][j] + confusionMatrix[(j+3)%2][j] + confusionMatrix[(j+4)%2][j] + confusionMatrix[(j+5)%2][j];
	        Rmacro += (double)TP/(TP+FN);
	        TPsum += TP;
	        FNsum += FN + TP;
	        
	    }
	    Rmacro /= 6.0;
	    Rmicro =(double)TPsum / FNsum;
	    
	  //  System.out.println("Rmacro: "+ Rmacro);
	    double accuracy = count / 214.0;
	  //  System.out.println("Accuracy: "+ accuracy);
	    double error = 1 - accuracy;
	  //  System.out.println("Error: "+ error);
	    
	    double[] lossData = new double[6];
	    lossData[0] = accuracy;
		lossData[1] = error;
		lossData[2] = Pmacro;
		lossData[3] = Pmicro;
		lossData[4] = Rmacro;
		lossData[5] = Rmicro;

		return lossData;
	}

	public TrainClassLoss(GlassDataNoise[] sA) {
	       //"4" does not exist
 	for(int i = 0; i < sA.length; i++) {
 		if(sA[i].getCl().equals("1")){
 			bWFPN[countBWFPN] = sA[i];
 			countBWFPN++;
 		}else if(sA[i].getCl().equals("2")){
 			bWNFPN[countBWNFPN] = sA[i];
 			countBWNFPN++;
 		}else if(sA[i].getCl().equals("3")){
 			vWFPN[countVWFPN] = sA[i];
 			countVWFPN++;
 		}else if(sA[i].getCl().equals("5")){
 			contN[countContN] = sA[i];
 			countContN++;
 		}else if(sA[i].getCl().equals("6")){
 			tableWN[countTableWN] = sA[i];
 			countTableWN++;
 		}else {
 			headLN[countHeadLN] = sA[i];
 			countHeadLN++;
 		}
 	}

     
     //Q(of a class)
 	float xBWFPN = ((float)bWFPN.length/((float)sA.length));
 	float xBWNFPN = ((float)bWNFPN.length/((float)sA.length));
 	float xVWFPN = ((float)vWFPN.length/((float)sA.length));
 	//float xVWNFP = ((float)vWNFP.length/((float)sA.length));
 	float xContN = ((float)contN.length/((float)sA.length));
 	float xTableWN = ((float)tableWN.length/((float)sA.length));
 	float xHeadLN = ((float)headLN.length/((float)sA.length));
 	
     	
     //All POSSIBLE F(A_j = a_k, C = c_i)
     for(int attribute = 0; attribute < bWFPN[0].getFeat().length; attribute++) {
     	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
     		trainDataBWFPN[attribute][attributeVal] = train(bWFPN, attributeVal, attribute);
     	}
     }
     
     for(int attribute = 0; attribute < bWNFPN[0].getFeat().length; attribute++) {
     	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
     		trainDataBWNFPN[attribute][attributeVal] = train(bWNFPN, attributeVal, attribute);
     	}
     }
     
     for(int attribute = 0; attribute < vWFPN[0].getFeat().length; attribute++) {
     	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
     		trainDataVWFPN[attribute][attributeVal] = train(vWFPN, attributeVal, attribute);
     	}
     }
     
//     for(int attribute = 0; attribute < vWNFP[0].getFeat().length; attribute++) {
//     	for(int attributeVal = 1; attributeVal<=7; attributeVal++) {
//     		trainDataVWNFP[attribute][attributeVal-1] = train(vWNFP, attributeVal, attribute);
//     	}
//     }
     
     for(int attribute = 0; attribute < contN[0].getFeat().length; attribute++) {
     	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
     		trainDataContN[attribute][attributeVal] = train(contN, attributeVal, attribute);
     	}
     }
     
     for(int attribute = 0; attribute < tableWN[0].getFeat().length; attribute++) {
     	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
     		trainDataTableWN[attribute][attributeVal] = train(tableWN, attributeVal, attribute);
     	}
     }
     
     for(int attribute = 0; attribute < headLN[0].getFeat().length; attribute++) {
     	for(int attributeVal = 0; attributeVal<9; attributeVal++) {
     		trainDataHeadLN[attribute][attributeVal] = train(headLN, attributeVal, attribute);
     	}
     }
     
     //Classifying one set of data for one set of data
     	//double c1 = xBen*(trainDataBen[0][4] * trainDataBen[1][0] * trainDataBen[2][0] * trainDataBen[3][0] * trainDataBen[4][1] * trainDataBen[5][0] * trainDataBen[6][1] * trainDataBen[7][0] * trainDataBen[8][0]);
     	//double c2 = xMal*(trainDataMal[0][4] * trainDataMal[1][0] * trainDataMal[2][0] * trainDataMal[3][0] * trainDataMal[4][1] * trainDataMal[5][0] * trainDataMal[6][1] * trainDataMal[7][0] * trainDataMal[8][0]);
     loss(sA,xBWFPN,xBWNFPN,xVWFPN,xContN,xTableWN,xHeadLN,trainDataBWFPN,
     		trainDataBWNFPN,
     		trainDataVWFPN,
     		trainDataContN,
     		trainDataTableWN,
     		trainDataHeadLN);
     
	}
		
	public double train(GlassDataNoise[] classSpecificData, int targetInstance, int feature) {

     int numFeatures= 9;
     int num= 0;
     for (GlassDataNoise data : classSpecificData) {
         int[] attributeData = data.getBinnedFeat();
         int attr = attributeData[feature];

         if (attr == targetInstance) {
             num++;
         }
     }

     return (double)(num+1) / (classSpecificData.length + numFeatures); //<-- making denominator smaller = worse accuracy on c3 ?!
	}
	
	public double[] loss(GlassDataNoise[] testData, float probC1, float probC2,float probC3,float probC5,float probC6,float probC7, 
			double[][] allFC1, double[][] allFC2,double[][] allFC3,
			double[][] allFC5,double[][] allFC6,double[][] allFC7) {
	    int count= 0;
	    int[][] confusionMatrix = new int[6][6];
	
	    //this loops through all irisData's in the test data and fills the confusion matrix
	    for (GlassDataNoise gD: testData) {
	    	if(gD != null) {
		        String trueClass = gD.getCl();
		        int[] test = gD.getBinnedFeat();
		        String guess = classifyG(test,allFC1,allFC2,allFC3,
		    			allFC5,allFC6,allFC7,probC1, 
		    			probC2,probC3,probC5,probC6,probC7); //this is what I did outside this function
		
		        if (trueClass.contentEquals("1")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[0][0]++;
		            }
		            if (guess.contentEquals("2")) {
		                confusionMatrix[1][0]++;
		            }
		            if (guess.contentEquals("3")) {
		                confusionMatrix[2][0]++;
		            }
		            if (guess.contentEquals("5")) {
		                confusionMatrix[3][0]++;
		            }
		            if (guess.contentEquals("6")) {
		                confusionMatrix[4][0]++;
		            }
		            if (guess.contentEquals("7")) {
		                confusionMatrix[5][0]++;
		            }
		        }
		        
		        if (trueClass.contentEquals("2")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[1][1]++;
		            }
		            if (guess.contentEquals("1")) {
		                confusionMatrix[0][1]++;
		            }
		            if (guess.contentEquals("3")) {
		                confusionMatrix[2][1]++;
		            }
		            if (guess.contentEquals("5")) {
		                confusionMatrix[3][1]++;
		            }
		            if (guess.contentEquals("6")) {
		                confusionMatrix[4][1]++;
		            }
		            if (guess.contentEquals("7")) {
		                confusionMatrix[5][1]++;
		            }
		        }
		        
		        if (trueClass.contentEquals("3")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[2][2]++;
		            }
		            if (guess.contentEquals("1")) {
		                confusionMatrix[0][2]++;
		            }
		            if (guess.contentEquals("2")) {
		                confusionMatrix[1][2]++;
		            }
		            if (guess.contentEquals("5")) {
		                confusionMatrix[3][2]++;
		            }
		            if (guess.contentEquals("6")) {
		                confusionMatrix[4][2]++;
		            }
		            if (guess.contentEquals("7")) {
		                confusionMatrix[5][2]++;
		            }
		        }
		        
		        if (trueClass.contentEquals("5")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[3][3]++;
		            }
		            if (guess.contentEquals("1")) {
		                confusionMatrix[0][3]++;
		            }
		            if (guess.contentEquals("2")) {
		                confusionMatrix[1][3]++;
		            }
		            if (guess.contentEquals("3")) {
		                confusionMatrix[2][3]++;
		            }
		            if (guess.contentEquals("6")) {
		                confusionMatrix[4][3]++;
		            }
		            if (guess.contentEquals("7")) {
		                confusionMatrix[5][3]++;
		            }
		        }
		        
		        if (trueClass.contentEquals("6")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[4][4]++;
		            }
		            if (guess.contentEquals("1")) {
		                confusionMatrix[0][4]++;
		            }
		            if (guess.contentEquals("2")) {
		                confusionMatrix[1][4]++;
		            }
		            if (guess.contentEquals("3")) {
		                confusionMatrix[2][4]++;
		            }
		            if (guess.contentEquals("5")) {
		                confusionMatrix[3][4]++;
		            }
		            if (guess.contentEquals("7")) {
		                confusionMatrix[5][4]++;
		            }
		        }
		        
		        if (trueClass.contentEquals("7")) {
		            if (guess.contentEquals(trueClass)) {
		                count++;
		                confusionMatrix[5][5]++;
		            }
		            if (guess.contentEquals("1")) {
		                confusionMatrix[0][5]++;
		            }
		            if (guess.contentEquals("2")) {
		                confusionMatrix[1][5]++;
		            }
		            if (guess.contentEquals("3")) {
		                confusionMatrix[2][5]++;
		            }
		            if (guess.contentEquals("5")) {
		                confusionMatrix[3][5]++;
		            }
		            if (guess.contentEquals("6")) {
		                confusionMatrix[4][5]++;
		            }
		        }
	    	}
	    }

	    //Calculating Precision Pmacro and Pmicro for 6 classes
	   // System.out.println("\nGlass Data Noise:");
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
	    Pmacro /= 6.0;
	    Pmicro = (double)TPsum / FPsum;
	    //System.out.println("Pmacro: "+ Pmacro);
	    //System.out.println("Pmicro: "+ Pmicro);

		   // Calculating Risk Rmacro and Rmicro for 2 classes
	    double Rmacro = 0;
	    double Rmicro =0;
	    TPsum=0;
	    int FN, FNsum =0;
	    for (int j= 0; j < 2; j++) {
	    	TP = confusionMatrix[j][j];
	    	FN = confusionMatrix[(j+1)%2][j] + confusionMatrix[(j+2)%2][j] + confusionMatrix[(j+3)%2][j] + confusionMatrix[(j+4)%2][j] + confusionMatrix[(j+5)%2][j];
	        Rmacro += (double)TP/(TP+FN);
	        TPsum += TP;
	        FNsum += FN + TP;
	        
	    }
	    Rmacro /= 6.0;
	    Rmicro =(double)TPsum / FNsum;
	    
	  //  System.out.println("Rmacro: "+ Rmacro);
	    double accuracy = count / 214.0;
	  //  System.out.println("Accuracy: "+ accuracy);
	    double error = 1 - accuracy;
	  //  System.out.println("Error: "+ error);
	    
	    double[] lossData = new double[6];
	    lossData[0] = accuracy;
		lossData[1] = error;
		lossData[2] = Pmacro;
		lossData[3] = Pmicro;
		lossData[4] = Rmacro;
		lossData[5] = Rmicro;

		return lossData;

	}
			
	public String classify(String[] test, double[][] allFC1, double[][] allFC2,float probC1,float probC2) {
		//trainingSet = test
        float productOfTrainingC1 = 1;
        for(int i=0;i<test.length;i++) {
        	String looking = test[i];
       		int arrayLooking = Integer.valueOf(looking);
       		productOfTrainingC1 *= allFC1[i][arrayLooking-1];
        }
        
        float productOfTrainingC2 = 1;
        for(int i=0;i<test.length;i++) {
        	String looking = test[i];
        	int arrayLooking = Integer.valueOf(looking);
        	productOfTrainingC2 *= allFC2[i][arrayLooking-1];
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
       		String looking = test[i];
       		int arrayLooking = 0;
       		if(looking.contentEquals("n")) {
       			arrayLooking = 0;
       		}else {
       			arrayLooking = 1;
       		}
       		productOfTrainingC1 *= allFC1[i][arrayLooking];
        }
        
        float productOfTrainingC2 = 1;
        for(int i=0;i<test.length;i++) {
        	String looking = test[i];
          	int arrayLooking = 0;
          	if(looking.contentEquals("n")) {
          		arrayLooking = 0;
          	}else {
          		arrayLooking = 1;
          	}
          	productOfTrainingC2 *= allFC2[i][arrayLooking];
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

	public String classifyG(int[] test, double[][] allFC1, 
			double[][] allFC2, double[][] allFC3,
			double[][] allFC5, double[][] allFC6,double[][] allFC7, 
			float probC1,float probC2, float probC3,
			float probC5,float probC6,float probC7) {
		//trainingSet = test
        float productOfTrainingC1 = 1;
        for(int i=0;i<test.length;i++) {
       		 int looking = test[i];
       		 int arrayLooking = looking;
       		 productOfTrainingC1 *= allFC1[i][arrayLooking];
        }
        
        float productOfTrainingC2 = 1;
        for(int i=0;i<7;i++) {
        	int looking = test[i];
        	int arrayLooking = looking;
        	productOfTrainingC2 *= allFC2[i][arrayLooking];
        	
        }
        
        float productOfTrainingC3 = 1;
        for(int i=0;i<7;i++) {
        	int looking = test[i];
        	int arrayLooking = looking;
        	productOfTrainingC3 *= allFC3[i][arrayLooking];
        }
        
        float productOfTrainingC5 = 1;
        for(int i=0;i<7;i++) {
        	int looking = test[i];
        	int arrayLooking = looking;
        	productOfTrainingC5 *= allFC5[i][arrayLooking];
        }
        
        float productOfTrainingC6 = 1;
        for(int i=0;i<7;i++) {
        	int looking = test[i];
        	int arrayLooking = looking;
        	productOfTrainingC6 *= allFC6[i][arrayLooking];

        }
        
        float productOfTrainingC7 = 1;
        for(int i=0;i<7;i++) {
        	int looking = test[i];
        	int arrayLooking = looking;
        	productOfTrainingC7 *= allFC7[i][arrayLooking];
        }
		
        double chanceC1 = probC1*productOfTrainingC1;
        double chanceC2 = probC2*productOfTrainingC2;
        double chanceC3 = probC3*productOfTrainingC3;
        double chanceC5 = probC5*productOfTrainingC5;
        double chanceC6 = probC6*productOfTrainingC6;
        double chanceC7 = probC7*productOfTrainingC7;
        
        String classifiedAs = "";
        
        if(chanceC1 > chanceC2 && chanceC1 > chanceC3 && chanceC1 > chanceC5 && chanceC1 > chanceC6 && chanceC1 > chanceC7){ //&& chanceC1 > chanceC3 ... 
        	classifiedAs = "1";
        }else if(chanceC2 > chanceC1 && chanceC2 > chanceC3 && chanceC2 > chanceC5 && chanceC2 > chanceC6 && chanceC2 > chanceC7 ) {  //&& chanceC2 > chanceC3 ... 
        	classifiedAs = "2";
        }else if(chanceC3 > chanceC1 && chanceC3 > chanceC2 && chanceC3 > chanceC5 && chanceC3 > chanceC6 && chanceC3 > chanceC7 ) {  //&& chanceC2 > chanceC3 ... 
        	classifiedAs = "3";
        }else if(chanceC5 > chanceC1 && chanceC5 > chanceC3 && chanceC5 > chanceC2 && chanceC5 > chanceC6 && chanceC5 > chanceC7 ) {  //&& chanceC2 > chanceC3 ... 
        	classifiedAs = "5";
        }else if(chanceC6 > chanceC1 && chanceC6 > chanceC3 && chanceC6 > chanceC5 && chanceC6 > chanceC2 && chanceC6 > chanceC7 ) {  //&& chanceC2 > chanceC3 ... 
        	classifiedAs = "6";
        }else if(chanceC7 > chanceC1 && chanceC7 > chanceC3 && chanceC7 > chanceC5 && chanceC7 > chanceC6 && chanceC7 > chanceC2 ) {  //&& chanceC2 > chanceC3 ... 
        	classifiedAs = "7";
        }
        
        return classifiedAs;
        
	}

    public void printLossData(double[] lossData) {
        if (lossData.length < 7) {
            System.out.println("Accuracy: "+ lossData[0]);
            System.out.println("Error:    "+ lossData[1]);
            System.out.println("Pmacro:   "+ lossData[2]);
            System.out.println("Pmicro:   "+ lossData[3]);
            System.out.println("Rmacro:   "+ lossData[4]);
            System.out.println("Rmicro:   "+ lossData[5]);
        }
    }


	
	
	
}
