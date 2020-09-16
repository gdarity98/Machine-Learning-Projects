package project_2;
import java.util.Random;

public class HouseVotesData {
	private String tempData = " ";
	private String allData = " ";
	public String idClassName = " ";
	private String handicappedInfants = " ";
	private String waterProjCost = " ";
	private String adoptBudgetReso = " ";
	private String physicianFeeFreeze = " ";
	private String elSalvAid = " ";
	private String religiousGrSchool = " ";
	private String antiSatTestBan = " ";
	private String aidNicarContras = " ";
	private String mxMissile = " ";
	private String immigration = " ";
	private String synCorpCut = " ";
	private String eduSpend = " ";
	private String superfundSue = " ";
	private String crime = " ";
	private String dutyExport = " ";
	private String exportAdminASA = " ";
	private String[] features = new String[16]; //only thing that really matters. Needs to be up to date when noise happens.
	
	public HouseVotesData(String fullData) {
		//This sets up all the variables by splitting the string and assigning the correct value to variables.
		tempData = fullData;
		String[] tokens = fullData.split(","); //splitting string at commas
		
		//assigning proper strings to the variables
		
		//WHILE data is being separated we can filter out unknown items and change them to what we need them to be.
		
		idClassName = tokens[0];
		handicappedInfants = tokens[1];
		waterProjCost = tokens[2];
	    adoptBudgetReso = tokens[3];
	    physicianFeeFreeze = tokens[4];
		elSalvAid = tokens[5];
		religiousGrSchool = tokens[6];  // the only attribute with missing information
		antiSatTestBan = tokens[7];
		aidNicarContras = tokens[8];
		mxMissile = tokens[9];
		immigration = tokens[10];
		synCorpCut = tokens[11];
		eduSpend = tokens[12];
		superfundSue = tokens[13];
		crime = tokens[14];
		dutyExport = tokens[15];
		exportAdminASA = tokens[16];
		
		allData = String.join(",",idClassName,
				handicappedInfants,
				waterProjCost,
				adoptBudgetReso,
				physicianFeeFreeze,
				elSalvAid,
				religiousGrSchool,
				antiSatTestBan,
				aidNicarContras,
				mxMissile,
				immigration,
				synCorpCut,
				eduSpend,
				superfundSue,
				crime,
				dutyExport,
				exportAdminASA);
		
        for (int i = 0; i < 16; i++) {
            features[i] = tokens[i+1];
        }
        
        int i = 0;
		for(String feat : features){
			if(feat.equals("?")) {
				Random random = new Random();
				int randomNum = 0;
				while(true) {
					randomNum = random.nextInt(3);
					if (randomNum != 0) break;
				}
				if(randomNum == 1) {
					features[i] = "y";
					i++;
				}else {
					features[i] = "n";
					i++;
				}
			}else {
				i++;
			}
		}
	}
	
	//returns full info variable
	public String getFullInfo() {
		return allData;
	}

	public String getCl(){
		return idClassName;
	}
	
	public String[] getFeat() {
		return features;
	}
}
