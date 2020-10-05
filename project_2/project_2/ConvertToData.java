package project_2;

public class ConvertToData {
	public ConvertToData() {
		
	}
	
	public DataC[] convertToData(double[][] means) {
		DataC[] meansData = new DataC[means.length];
		for(int i = 0; i < means.length; i++) {
			DataC meanData = new DataC(means[i], i+1);
			meansData[i] = meanData;
		}
		return meansData;
	}
}
