package project_1;

public class BreastCancerData {
	private String allData;
	public String id;
	public String classNo;	//this is a 2 or a 4 (benign / malignant)
	public int[] features;			//feature array (1 - 9)
	
	public BreastCancerData(String fullData) {
		//This sets up all the variables by splitting the string and assigning the correct value to variables.
		allData = fullData;
		String[] tokens = fullData.split(","); //splitting string at commas
		
		//assigning proper strings to the variables
		
		//WHILE data is being separated we can filter out unknown items and change them to what we need them to be.
		
		id = tokens[0];
		classNo = tokens[10];

		features = new int[9];
		for (int i= 0; i < 9; i++) {
			features[i]= Integer.parseInt(tokens[i+1]);
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

	//returns class number (2 or 4)
	public String getClassNo() {
		return classNo;
	}

	//returns feature array
	public int[] getFeatures() {
		return features;
	}
}
