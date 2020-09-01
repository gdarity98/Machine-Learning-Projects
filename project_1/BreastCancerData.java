package project_1;

public class BreastCancerData {
	private String allData = " ";
	public String id = " ";
	//private int section = 0;
	//private String seats = " ";
	//private String title = " ";
	//private String when = " ";
	//private String lecture = " ";
	
	public BreastCancerData(String fullData) {
		//This sets up all the variables by splitting the string and assigning the correct value to variables.
		allData = fullData;
		String[] tokens = fullData.split(","); //splitting string at commas
		
		//assigning proper strings to the variables
		
		//WHILE data is being separated we can filter out unknown items and change them to what we need them to be.
		
		id = tokens[0];
		//when = (tokens[15] + "-" + tokens[16]);
		//lecture = tokens[14];
		//seats = tokens[4];
		//title = tokens[2];
		
		
		//check if it is a lecture or a lab
		//walk through the string and separate it and store necessary data
	}
	
	//returns full info variable
	public String getFullInfo() {
		return allData;
	}
	
	//returns the id variable
	public String getID() {
		return id;
	}
//	
//	//returns the section variable
//	public int getSection() {
//		return section;
//	}
//	
//	//increments the section variable by 1
//	public void sectionIncrement() {
//		section++;
//	}
//	public void sectionIncrement(MSUClass val) {
//		section += val.getSection();
//	}
//	
//	
//	public String getSeats() {
//		return seats;
//	}
//	
//	public void setSeats(String newVal) {
//		Integer seatsInt = Integer.valueOf(seats);
//		Integer newValInt = Integer.valueOf(newVal);
//		seatsInt += newValInt;
//		seats = Integer.toString(seatsInt);
//	}
//	
//	public String getTitle() {
//		return title;
//	}
//	
//	public String getWhen() {
//		return when;
//	}
//	
//	public String getLecture() {
//		return lecture;
//	}
	
}
