package proj4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class DE {
	// skip selection, considering all examples
	// mutation -> crossover -> replacement
	// Mutation is pretty different from GA!
	// Beta used in mutation, p_r is crossover probability for binomial crossover
	// Create and initialize population just like GA
	// While loop
	//    for each individual
	//		  the individual is the target vector
	//        target used to create trial vector, by applying mutation
	//        create an offspring, by applying crossover (only 1 offspring)
	//        if offspring is better than the target
	//            add to pop
    //		  else
	//            add target to pop
	
	
    public FeedForwardNet[] population;
    //Below are hard coded in constructor.
    public double mutationRate;
    public double beta;
    public double crossoverProb;
    boolean idk = true;
    
	/*
	 * Init pop like GA, and start while loop and for loop to go through each individual in pop and do mut->cross->rep until done
	 */
	public DE(int populationSize, DataC[] trainingData, int[] layers, boolean isClassification) {
        population = new FeedForwardNet[populationSize];
        
        //all of this is hard coded rn
        mutationRate = 0.1; 
        beta = 1.5;
        crossoverProb = 0.6;

        for (int i= 0; i< populationSize; i++) {
            population[i] = new FeedForwardNet(trainingData, layers, isClassification);
        }
        
        
	}
	
	
	public DataC mutation(FeedForwardNet[] population, DataC targetVector) {
		//DONE?
		//target vector x_j
		//select three other distinct vectors from out population X1,X2,X3
		//calculate trail vector u_j(u_i?) = X1 + Beta(X2-X3)            Beta in [0,2]
		      //X2 - X3 reverses the direction of the X2 vector? (should it be X3-X2?)
		//return the trial vector
		
		Random rand = new Random();
		
		DataC[] distinctVectors = new DataC[3];
		
		//picking what feedforwardnet to go into
		int[] prevIntSub = new int[3];
		int[] prevIntData = new int[3];
		
		//checking if the prev data is the same as the ones before
		//if they are the same the choose again.
		//set the distinct vectors into the array.
		for(int i = 0; i < 3; i++) {
			int randomIntSub = rand.nextInt(population.length);
			prevIntSub[i] = randomIntSub;
			DataC[] subpop = population[randomIntSub].data;
			int randomIntData = rand.nextInt(subpop.length);
			prevIntData[i] = randomIntData;
			if(i != 0) {
				if(randomIntSub == prevIntSub[i-1] && randomIntData == prevIntData[i-1]) {
					i--;
					continue;
				}
			}
			distinctVectors[i] = subpop[randomIntData];
		}
		
		
		double[] X1 = distinctVectors[1].getNormalizedFeatures();
		double[] X2 = distinctVectors[2].getNormalizedFeatures();
		double[] X3 = distinctVectors[3].getNormalizedFeatures();
		double[] trialFeatures = new double[X1.length];
		
		//creating trial vector features
		for(int i = 0; i < X1.length; i++) {
			trialFeatures[i] = X1[i] + (beta*(X2[i]+X3[i]));
		}
		
		//creating trialVector and setting the features
		DataC trialVector = null;
		trialVector.setNormFeatures(trialFeatures);
		return trialVector;
	}
	
	public DataC crossover(DataC targetVector, DataC trialVector) {
		//Done?
		//Binomial Crossover
		//x_j'_i (where i are the individual components of the vector)
		//
		//		   {x_j_i if rand(0,1) <= p_r (if p_r is .5 then its the same as GA
		//x_j'_i = {
		//		   {U_j_i otherwise
		//
		//basically for loop over the components of the vector and pick as described above if the 
		//   component should be taken from x_j or from u_j
		// then return offspring x_j'
		
		//getting all the features
		double[] offspringFeatures = new double[targetVector.getNormalizedFeatures().length];
		double[] targetFeatures = targetVector.getNormalizedFeatures();
		double[] trialFeatures = trialVector.getNormalizedFeatures();
		
		//choosing what features to take from target and trial and putting them in offspring features
		for(int i = 0; i < offspringFeatures.length; i++) {
			Random rand = new Random();
			double double_random = rand.nextDouble();
			if(double_random <= crossoverProb) {
				offspringFeatures[i] = targetFeatures[i];
			}else {
				offspringFeatures[i] = trialFeatures[i];
			}
		}
		
		//creating the offspring and adding the features
		DataC offspring = null;
		offspring.setNormFeatures(offspringFeatures);
		return offspring;
	}
	
	/*
	 * IDK how to compare the fitnesses??
	 */
	public Boolean compare(DataC offspring, DataC targetVector) {
		
		return true;
	}
	
    /*
    Check progress of DE, update fitnesses,
    maybe sort the population by fitness?
 */
	public void evaluate() {
	    //update fitness
	    for (FeedForwardNet feedForwardNet : population) {
	        feedForwardNet.updateFitness();
	    }
	
	    //sort by fitness
	    Arrays.sort(population, new Comparator<FeedForwardNet>() {
	        @Override
	        public int compare(FeedForwardNet o1, FeedForwardNet o2) {
	            Double fitness1 = o1.fitness;
	            Double fitness2 = o2.fitness;
	            return fitness1.compareTo(fitness2);
	        }
	    });
	
	    System.out.print("[");
	    for (FeedForwardNet net: population) {
	        System.out.printf("%.3f, ", net.fitness);
	    }
	    System.out.print("]\n");
	}
	
	public void DiffEvolution(int generations) {
		
        
    	// While loop (idk what condition needs to be ^-^)
    	//    for each individual
    	//		  the individual is the target vector
    	//        target used to create trial vector, by applying mutation
    	//        create an offspring, by applying crossover (only 1 offspring)
    	//        if offspring is better than the target
    	//            add to pop
        //		  else
    	//            add target to pop
		
		//go until we go the number of generations or until specific condition is met
        for(int z = 0; z < generations; z++) {
        	//going over all of the population (no need for selection)
        	for(int i=0; i<population.length;i++) {
        		DataC[] subpop = population[i].data;
        		//for each individual
        		for(int j = 0; j < subpop.length; j++) {
        			DataC targetVector = subpop[j];
        			//Need to evaluate fitness of the target? Which would be its rank ?
        			//mutation
        			DataC trialVector = mutation(population, targetVector);
        			//offspring
        			DataC offspring = crossover(targetVector, trialVector);
        			//Need to evaluate fitness of the offspring and compare to target
        			//replace whichever has the higher fitness
        			//compare function just returns true rn since idk how to do thisS
        			if(compare(offspring,targetVector)) {
        				subpop[j] = offspring;
        			}else {
        				subpop[j] = targetVector;
        			}
        			
        		}
        		
        	}
        	
        	evaluate();
        }
        
        
	}
	
    public static void main(String[] args) {
        DataSetUp soybean = new DataSetUp("data-sets/soybean-small.data", "endS", "classification");
        soybean.zScoreNormalize();

        int inputLen = soybean.getAllData()[0].getNormalizedFeatures().length;
        int[] layers = {inputLen, 12, 4};

        DE D = new DE(20, soybean.getAllData(), layers, true);
        
        D.DiffEvolution(800);
        
        FeedForwardNet best = D.population[0];
        best.evaluate();
    }
}
