package proj4;

import java.util.*;

public class Genetic {

    public FeedForwardNet[] population;



    /*
        Init population
     */
    public Genetic(int populationSize, DataC[] trainingData, int[] layers, boolean isClassification) {
        population = new FeedForwardNet[populationSize];

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

        for (int i= 0; i< finalSelection.length; i++) {
            FeedForwardNet[] tourney = new FeedForwardNet[k];

            //select k individuals at random
            for (int j= 0; j< k; j++) {
                rand = (int)(Math.random() * population.length);
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
     */
    public FeedForwardNet[] crossover(FeedForwardNet[] selected) {

        return null;
    }


    /*
        Mutation
     */
    public FeedForwardNet[] mutate(FeedForwardNet[] selected) {

        return null;
    }


    /*
        Replacement via steady state - kill weakest & replace
     */
    public void replace(FeedForwardNet[] newIndividuals) {

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

            selected = mutate(selected);

            replace(selected);
        }

    }

}
