package proj4;

import java.util.*;

/*
    Each particle has:
        position -> weights
        velocity -> weight update
        pBest
        w, c1, c2

    Each swarm has:
        particles[]
        gBest
 */
public class ParticleSwarm {

    //========================================================>Particle

    static class Particle {

        public FeedForwardNet position;
        public List<double[][]> velocity;
        public List<double[][]> pBest;
        public double pBestError;
        public double w, c1, c2;

        public Particle(DataC[] trainingData, int[] layers, boolean isClassification, double w, double c1, double c2) {
            position = new FeedForwardNet(trainingData, layers, isClassification);
            pBestError = Double.POSITIVE_INFINITY;
            pBest = position.getWeights();
            this.w = w;
            this.c1 = c1;
            this.c2 = c2;
        }


        /*
            v(t+1) = wv(t) + c1r1(pBest - position) + c2r2(gBest - position)
         */
        public void updateVelocity(List<double[][]> gBest) {
            double r1, r2;
        }


        /*
            x(t+1) = x(t) + v(t)
         */
        public void updatePosition() {

        }

    }


    //========================================================>ParticleSwarm

    public Particle[] particles;
    public List<double[][]> gBest;
    public double gBestError;

    public ParticleSwarm(int populationSize, DataC[] trainingData, int[] layers, boolean isClassification) {
        particles = new Particle[populationSize];
        gBestError = Double.POSITIVE_INFINITY;
        double w = 0.5;
        double c1 = 0.5;
        double c2 = 0.5;

        for(int i= 0; i< populationSize; i++) {
            particles[i] = new Particle(trainingData, layers, isClassification, w, c1, c2);
        }

        gBest = particles[0].position.getWeights();
    }


    public void updateVelocities() {
        for (Particle particle : particles) {
            particle.updateVelocity(gBest);
        }
    }


    /*
        Update gBest if necessary
     */
    public void updatePositions() {
        for (Particle particle : particles) {
            particle.updatePosition();
        }
    }


    /*
        print gBest
     */
    public void evaluate() {


    }


    /*
        1. Update velocities
        2. Update positions
        3. Check status
        4. Repeat for n generations OR until stopping criterion
     */
    public void PSO(int generations) {

        for(int i= 0; i< generations; i++) {

            updateVelocities();

            updatePositions();

            evaluate();

        }

    }



}
