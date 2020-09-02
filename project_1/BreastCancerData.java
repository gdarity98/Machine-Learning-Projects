package project_1;
import java.util.Random;

public class BreastCancerData {
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
	private String[] features = new String[9];
	
	public BreastCancerData(String fullData) {
		//This sets up all the variables by splitting the string and assigning the correct value to variables.
		tempData = fullData;
		String[] tokens = fullData.split(","); //splitting string at commas
		
		//assigning proper strings to the variables
		
		//WHILE data is being separated we can filter out unknown items and change them to what we need them to be.
		
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
		
		if(bareNuclei.equals("?")){
			Random random = new Random();
			int randomNum = 0;
			while(true) {
				randomNum = random.nextInt(11);
				if (randomNum != 0) break;
			}
			bareNuclei = Integer.toString(randomNum);
			
		}
		
		allData = String.join(",",id,clumpThickness,uniformCSize,uniformCShape,margAdhesion,singEpithCSize,bareNuclei,blandChrom,normNucleoli,mitoses,benMal);
		//features = String.join(",",id,clumpThickness,uniformCSize,uniformCShape,margAdhesion,singEpithCSize,bareNuclei,blandChrom,normNucleoli,mitoses,benMal);
		
        for (int i = 0; i < 9; i++) {
            features[i] = tokens[i+1];
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
