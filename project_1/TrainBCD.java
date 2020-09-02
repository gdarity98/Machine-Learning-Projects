package project_1;

public class TrainBCD {
	
	public void train() {
		
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
	
}
