package project_2;
import java.util.Random;

public class GlassData {
	private String tempData = " ";
	private String allData = " ";
	public String id = " ";
	private String RI = " ";
	private String Na = " ";
	private String Mg = " ";
	private String Al = " ";
	private String Si = " ";
	private String K = " ";
	private String Ca = " ";
	private String Ba = " ";
	private String Fe = " ";
	private String Type = " ";
	private double[] features = new double[9]; //only thing that really matters. Needs to be up to date when noise happens.
	
	public GlassData(String fullData) {
		//This sets up all the variables by splitting the string and assigning the correct value to variables.
		tempData = fullData;
		String[] tokens = fullData.split(","); //splitting string at commas
		
		//assigning proper strings to the variables
		
		//WHILE data is being separated we can filter out unknown items and change them to what we need them to be.
		
		id = tokens[0];
		RI = tokens[1];
		Na = tokens[2];
	    Mg = tokens[3];
	    Al = tokens[4];
		Si = tokens[5];
		K = tokens[6];  // the only attribute with missing information
		Ca = tokens[7];
		Ba = tokens[8];
		Fe = tokens[9];
		Type = tokens[10];		
		
		allData = String.join(",",id,RI,Na,Al,Si,K,Ca,Ba,Fe,Type);

        for (int i = 0; i < 9; i++) {
            features[i] = Double.parseDouble(tokens[i+1]);
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
		return Type;
	}
	
	//Not binned
	public double[] getFeat() {
		return features;
	}
	
	public int[] getBinnedFeat() {
		int noBins = 9; //hardwired
		int[] bins = new int[noBins];
		
		double RImin = 1.5112;
		double Namin = 10.73;
		double Mgmin = 0;
		double Almin = 0.29;
		double Simin = 69.81;
		double Kmin = 0;
		double Camin = 5.43;
		double Bamin = 0;
		double Femin = 0;
		
		double RIBinSize = 0.0227 / noBins;
		double NaBinSize = 6.65 / noBins;
		double MgBinSize = 4.49 / noBins;
		double AlBinSize = 3.21 / noBins;
		double SiBinSize = 5.60 / noBins;
		double KBinSize = 6.21 / noBins;
		double CaBinSize = 10.76 / noBins;
		double BaBinSize = 3.15 / noBins;
		double FeBinSize = 0.51 / noBins;

		for(int i = 1; i<=noBins; i++) {
			if(features[0] >= RImin && features[0] <(RImin + RIBinSize)) {
				bins[0] = i;
			}
			RImin = RImin + RIBinSize;
			if(features[1] >= Namin && features[1] <(Namin + NaBinSize)) {
				bins[1] = i;
			}
			Namin = Namin + NaBinSize;
			if(features[2] >= Mgmin && features[2] <(Mgmin + MgBinSize)) {
				bins[2] = i;
			}
			Mgmin = Mgmin + MgBinSize;
			if(features[3] >= Almin && features[3] <(Almin + AlBinSize)) {
				bins[3] = i;
			}
			Almin = Almin + AlBinSize;
			if(features[4] >= Simin && features[4] <(Simin + SiBinSize)) {
				bins[4] = i;
			}
			Simin = Simin + SiBinSize;
			if(features[5] >= Kmin && features[5] <(Kmin + KBinSize)) {
				bins[5] = i;
			}
			Kmin = Kmin + KBinSize;
			if(features[6] >= Camin && features[6] <(Camin + CaBinSize)) {
				bins[6] = i;
			}
			Camin = Camin + CaBinSize;
			if(features[7] >= Bamin && features[7] <(Bamin + BaBinSize)) {
				bins[7] = i;
			}
			Bamin = Bamin + BaBinSize;
			if(features[8] >= Femin && features[8] <(Femin + FeBinSize)) {
				bins[8] = i;
			}
			Bamin = Bamin + BaBinSize;	
		}
		return bins;
	}
}
