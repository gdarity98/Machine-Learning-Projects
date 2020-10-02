package project_2;

public class Printer {
	public Printer(double[][] means) {
		int i = 0;
		for(double[] d : means) {
			System.out.println("Mean " + i + "-----------");
			for(double j : d) {
				System.out.println(j);
			}
			i++;
		}
	}
}
