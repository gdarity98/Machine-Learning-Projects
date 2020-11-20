package proj4;

import java.util.*;

public class Genetic {

    public FeedForwardNet[] population;
    public double mutationRate;


    /*
        Init population
     */
    public Genetic(int populationSize, DataC[] trainingData, int[] layers, boolean isClassification) {
        population = new FeedForwardNet[populationSize];
        mutationRate = 0.1;

        for (FeedForwardNet net: population) {
            new FeedForwardNet(trainingData, layers, isClassification);
        }
    }


    /*
        Tournament selection, k=2 for now
        Choosing population.length / 2 individuals
     */
    public FeedForwardNet[] selection() {
        int k = 2;
        int rand;
        FeedForwardNet[] finalSelection = new FeedForwardNet[population.length / 2];

        //make sure of even # of parents
        if (finalSelection.length % 2 == 1) {
            finalSelection = new FeedForwardNet[population.length/2 + 1];
        }

        for (int i= 0; i< finalSelection.length; i++) {
            FeedForwardNet[] tourney = new FeedForwardNet[k];

            //select k individuals at random
            for (int j= 0; j< k; j++) {
                rand = (int)(Math.random() * (population.length-1));
                tourney[j] = population[rand];
            }

            //add best individual to finalSelection[]
            double best= 0;
            FeedForwardNet bestInd = null;
            for (int j= 0; j< k; j++) {
                if (tourney[j].fitness > best) {
                    bestInd = tourney[j];
                    best = tourney[j].fitness;
                }
            }

            finalSelection[i] = bestInd;
        }

        return finalSelection;
    }


    /*
        Uniform crossover
        2 parents -> 2 children

        Decided to do mutation in here since already accessing
        the double arrays,
        currently hardcoded mutation rate of 0.1,
        adds to value a number in range [-0.1, +0.1]
     */
    public FeedForwardNet[] crossover(FeedForwardNet[] selected) {
        double rand;

        //goes in groups of 2
        for(int i= 0; i< selected.length; i++) {
            List<double[][]> parent1 = selected[i].getWeights();
            List<double[][]> parent2 = selected[i+1].getWeights();

            int c = 0;
            for(double[][] layer: parent1) {

                for(int j= 0; j< layer.length; j++) {
                    for(int k= 0; k< layer[0].length; k++) {

                        //CROSSOVER
                        rand = Math.random();

                        if (rand >= 0.5) {
                            double temp = layer[j][k];
                            parent1.get(c)[j][k] = parent2.get(c)[j][k];
                            parent2.get(c)[j][k] = temp;
                        }

                        //MUTATION
                        rand = Math.random();

                        if (rand >= (1 - mutationRate)) {
                            parent1.get(c)[j][k] += (Math.random() * .2)-0.1;
                        }

                        rand = Math.random();

                        if (rand >= (1 - mutationRate)) {
                            parent2.get(c)[j][k] += (Math.random() * .2)-0.1;
                        }
                    }
                }

                c++;
            }

            selected[i].setWeights(parent1);
            selected[i+1].setWeights(parent2);

            i++;
        }

        return selected;
    }


    /*
        Replacement via steady state - kill weakest & replace

        Assumes fitness sorted population lowest to highest
     */
    public void replace(FeedForwardNet[] newIndividuals) {
        System.arraycopy(newIndividuals, 0, population, 0, newIndividuals.length);
    }


    /*
        Check progress of GA, update fitnesses,
        maybe sort the population by fitness?
     */
    public void evaluate() {
        //update fitness
        for(int i= 0; i< population.length; i++) {
            population[i].updateFitness();
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
    }


    /*
        1. Make a randomized population & gather fitness scores
        2. Selection
        3. Crossover & mutation
        4. Replacement
        5. Repeat for n generations OR until stopping criterion
     */
    public void GA(int generations) {

        for (int i= 0; i< generations; i++) {
            FeedForwardNet[] selected = selection();

            selected = crossover(selected);

            replace(selected);

            evaluate();
        }

    }

}
