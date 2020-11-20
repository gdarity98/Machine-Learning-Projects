package proj4;

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
    
	/*
	 * Init pop like GA, and start while loop and for loop to go through each individual in pop and do mut->cross->rep until done
	 */
	public DE(int populationSize, DataC[] trainingData, int[] layers, boolean isClassification) {
        population = new FeedForwardNet[populationSize];
        mutationRate = 0.1;
        beta = 0;
        crossoverProb = 0;

        for (FeedForwardNet net: population) {
            new FeedForwardNet(trainingData, layers, isClassification);
        }
        
    	// While loop (idk what condition needs to be ^-^)
    	//    for each individual
    	//		  the individual is the target vector
    	//        target used to create trial vector, by applying mutation
    	//        create an offspring, by applying crossover (only 1 offspring)
    	//        if offspring is better than the target
    	//            add to pop
        //		  else
    	//            add target to pop
        
	}
	
	
	public void mutation(FeedForwardNet[] population) {
		//target vector x_j
		//select three other distinct vectors from out population X1,X2,X3
		//calculate trail vector u_j(u_i?) = X1 + Beta(X2-X3)            Beta in [0,2]
		      //X2 - X3 reverses the direction of the X2 vector? (should it be X3-X2?)
		//return the trial vector
	}
	
	public void crossover() {
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
	}
}
