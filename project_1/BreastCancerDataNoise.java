package project_1;
import java.util.Random;

public class BreastCancerDataNoise {
	private String tempData = " ";
	private String allData = " ";
	public String id = " ";
	private String clumpThickness = " ";
	private String uniformCSize = " ";
	private String uniformCShape = " ";
	private String margAdhesion = " ";
	private String singEpithCSize = " ";
	private String bareNuclei = " ";
	private String blandChrom = " ";
	private String normNucleoli = " ";
	private String mitoses = " ";
	private String benMal = " ";
	private String[] features = new String[9]; //only thing that really matters. Needs to be up to date when noise happens.
	
	public BreastCancerDataNoise(String fullData) {
		//This sets up all the variables by splitting the string and assigning the correct value to variables.
		tempData = fullData;
		String[] tokens = fullData.split(","); //splitting string at commas
		
		//assigning proper strings to the variables
		id = tokens[0];
		clumpThickness = tokens[1];
		uniformCSize = tokens[2];
	    uniformCShape = tokens[3];
	    margAdhesion = tokens[4];
		singEpithCSize = tokens[5];
		bareNuclei = tokens[6];  // the only attribute with missing information
		blandChrom = tokens[7];
		normNucleoli = tokens[8];
		mitoses = tokens[9];
		benMal = tokens[10];
		
		//WHILE data is being separated we can filter out unknown items and change them to what we need them to be.
		
		allData = String.join(",",id,clumpThickness,uniformCSize,uniformCShape,margAdhesion,singEpithCSize,bareNuclei,blandChrom,normNucleoli,mitoses,benMal);
		//features = String.join(",",id,clumpThickness,uniformCSize,uniformCShape,margAdhesion,singEpithCSize,bareNuclei,blandChrom,normNucleoli,mitoses,benMal);
		
        for (int i = 0; i < 9; i++) {
            features[i] = tokens[i+1];
        }
        
        if(features[5].equals("?")){
			Random random = new Random();
			int randomNum = 0;
			while(true) {
				randomNum = random.nextInt(11);
				if (randomNum != 0) break;
			}
			features[5] = Integer.toString(randomNum);
			
		}
        
        //Making noise 10%.
		int[] randomArray = new int[9];
		for(int i = 0; i < randomArray.length; i++) {
			Random random = new Random();
			int randomNumNoise;
			while(true) {
				randomNumNoise = random.nextInt(11);
				if (randomNumNoise != 0) break;
			}
			randomArray[i] = randomNumNoise;
		}
		
		//Adding in noise.
		int i = 0;
		for(String feat : features) {
			features[i] = setNoise(i,randomArray,feat);
			
			i++;
		}
	
	}
	
	public String setNoise(int check, int[] arrayToCheck, String change) {
		if(arrayToCheck[check] == 7) {
			Random random = new Random();
			int randomNumNoise;
			while(true) {
				randomNumNoise = random.nextInt(11);
				if (randomNumNoise != 0) break;
			}
			change = Integer.toString(randomNumNoise);
			return change;
		}else {
			return change;
		}
		
	}
	
	//returns full info variable
	public String getFullInfo() {
		return allData;
	}
	
	//returns the id variable
	public String getID() {
		return id;
	}
	
	public String getCl(){
		return benMal;
	}
	
	public String[] getFeat() {
		return features;
	}
}
