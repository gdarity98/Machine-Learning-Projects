package project3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
    A feed forward neural network

    @Author: Dave Miller
 */
public class FeedForwardNet {

    //===============================================>LAYER
    /*
        Layer describes a single layer in the network
        It holds the activations sigmoid(W*X) for the layer,
        and the weights associated with connections to the
        next layer.
     */
    private class Layer {
        private double[][] weights;
        private double[] biases;
        private double[] nodes;
        private double[] output;

        public Layer(int size) {
            nodes = new double[size];
        }

        /*
            First pass through, returns feedForward() so you can create the other
            layers. If nextLayerSize == 0, this is the last layer and input is
            returned.
         */
        public double[] init(double[] input, int nextLayerSize) {
            //if last layer return input
            if (nextLayerSize > 0) {
                weights = new double[nextLayerSize][input.length];
                output = new double[nextLayerSize];
                biases = new double[nextLayerSize];

                //randomize weights and biases
                for (int row = 0; row < weights.length; row++) {
                    biases[row] = Math.random();
                    for (int col = 0; col < weights[0].length; col++) {
                        weights[row][col] = (Math.random()* 2) -1;
                    }
                }
                return feedForward(input);
            }
            else {
                return input;
            }
        }

        /*
            Gets an array of output[] that corresponds to the input
            to the next layer if it exists

            This should not be called on the output layer
         */
        public double[] feedForward(double[] input) {
            this.nodes = input;
            //some matrix multiplication
            //[output] = [weights] * [nodes]
            for (int i= 0; i< weights.length; i++) {
                for (int k= 0; k< weights[0].length; k++) {
                    output[i] += (weights[i][k] * nodes[k]);
                }
            }

            //sigmoid( [output] )
            for (int i= 0; i< output.length; i++) {
                output[i] = sigmoid(output[i]);
            }

            return output;
        }

        /*
            Update weights of this layer given a weightUpdate array of
            length nextLayerSize
         */
        public void updateWeights(double[] weightUpdate, double eta) {
            for (int i= 0; i< weights.length; i++) {
                for (int j= 0; j< weights[0].length; j++) {
                    weights[i][j] += (-1 * eta * weightUpdate[i] * nodes[j]);
                }
            }
        }

        public double[] getWeightUpdates(double[] weightUpdate) {
            double[] nextUpdate = new double[nodes.length];
            for (int i= 0; i< weights.length; i++) {
                for (int j= 0; j< weights[0].length; j++) {
                    nextUpdate[j] += weightUpdate[i] * weights[i][j];
                }
            }

            for (int i= 0; i< nextUpdate.length; i++) {
                nextUpdate[i] *= nodes[i] * (1 - nodes[i]);
            }

            return nextUpdate;
        }

        public double sigmoid(double z) {
            return 1 / (1 + Math.exp(-1 * z));
        }
    }


    //========================================>FEED FORWARD NET

    public Layer[] network;
    public DataC[] data;
    public double eta;

    /*
        Takes an array of layer sizes for the network i.e
        [4, 2, 1] is a 3 layer net with 4 nodes in 1st layer,
        2 in 2nd layer, etc.

        For classification, layers should be [numFeatures, ..., numClasses]
     */
    public FeedForwardNet(DataC[] trainingData, int[] layers) {
        this.data = trainingData;
        this.eta = 0.5;
        network = new Layer[layers.length];

        //for each layer, make a Layer object of size layers[i]
        for (int i= 0; i< layers.length; i++) {
            network[i] = new Layer(layers[i]);
        }

        init();
    }


    /*
        Calls Layer.init() for each layer in the network
     */
    public void init() {
        //init() first layer with DataC[] input
        double[] output;
        output = network[0].init(data[0].getNormalizedFeatures(), network[1].nodes.length);

        //hidden layer(s)
        for (int i= 1; i< network.length-1; i++) {
            output = network[i].init(output, network[i+1].nodes.length);
        }

        //output layer
        network[network.length-1].init(output, 0);
    }

    /*
        Push 1 data sample through the network,
        returns output layer
     */
    public double[] feedForward(double[] in) {
        //first layer
        double[] output;
        output = network[0].feedForward(in);

        //hidden layer(s)
        for (int i= 1; i< network.length-1; i++) {
            output = network[i].feedForward(output);
        }

        //output layer
        return output;
    }

    //update weights
    public void backprop() {

        double totalError = 0;
        int miniBatchSize = data.length / 10;

        for (int epochs= 0; epochs< 1000; epochs++) {
            double[] weightUpdate = new double[network[network.length-1].nodes.length];

            List<DataC> temp = Arrays.asList(data);
            Collections.shuffle(temp);

            List<DataC> miniBatch = new ArrayList<>(temp.subList(0, miniBatchSize));


            for (DataC d : miniBatch) {
                double[] output = feedForward(d.getNormalizedFeatures());
                int target = Integer.parseInt(d.getClassLabel()) - 1;
                double[] t = new double[output.length];
                Arrays.fill(t, 0);
                t[target] = 1;

                totalError += error(output, t);
                double[] deltas = delta(output, t);

                for (int i= 0; i< weightUpdate.length; i++) {
                    weightUpdate[i] += deltas[i];
                }
            }

            totalError /= miniBatchSize;

            for (int j= 0; j< weightUpdate.length; j++) {
                weightUpdate[j] /= miniBatchSize;
            }

            updateMiniBatch(weightUpdate);

            System.out.println("Error: " + totalError);
        }
    }

    /*
        Updates weights based on performance of mini batch
        Calls Layer.getUpdates() for each layer, then Layer.updateWeights
     */
    public void updateMiniBatch(double[] weightUpdate) {
        //output layer
        double[] newUpdates = network[network.length-2].getWeightUpdates(weightUpdate);
        network[network.length-2].updateWeights(weightUpdate, eta);
        network[network.length-3].updateWeights(newUpdates, eta);

        //propagate back to input layer
//        for (int i= network.length-3; i >= 0; i--) {
//            newUpdates = network[i].updateWeights(newUpdates, eta);
//        }
    }

    public void evaluate() {
        int count = 0;
        for (DataC d: data) {
            double[] output = feedForward(d.getNormalizedFeatures());
            int trueClass = Integer.parseInt(d.getClassLabel());

            double max = Double.NEGATIVE_INFINITY;
            int guess = 0;

            for (int i= 0; i< output.length; i++) {
                if (output[i] > max) {
                    max = output[i];
                    guess = i+1;
                }
            }
            if (guess == trueClass) {
                count++;
            }

            System.out.println("G: "+guess + "\tT: "+trueClass);
        }
        double accuracy = (double)count / data.length;
        System.out.println(count + " / " + data.length + " = " + accuracy);
    }

    /*
        Gives squared error
     */
    public double error(double[] output, double[] target) {
        double sum= 0;

        for (int i= 0; i< output.length; i++) {
            sum += Math.pow(target[i] - output[i], 2);
        }

        return sum / 2;
    }

    /*
        Gives squared error derivative DELTA_j
     */
    public double[] delta(double[] output, double[] target) {
        double[] delta = new double[output.length];

        for (int i= 0; i< output.length; i++) {
            delta[i] = -1 * (target[i] - output[i]);
        }

        return delta;
    }


    //==================================>MAIN
    public static void main(String[] args) {
        DataSetUp glass = new DataSetUp("data-sets/glass.data", "end", "classification");
        glass.zScoreNormalize();

        int inputLen = glass.getAllData()[0].getNormalizedFeatures().length;
        int[] layers = {inputLen, 16, 7};
//        FeedForwardNet network = new FeedForwardNet(glass.getAllData(), layers);
//        network.backprop();
//        network.evaluate();

        DataSetUp soybean = new DataSetUp("data-sets/soybean-small.data", "endS", "classification");
        soybean.zScoreNormalize();

        inputLen = soybean.getAllData()[0].getNormalizedFeatures().length;
        int[] layers3 = {inputLen, 20, 4};
//        FeedForwardNet net2 = new FeedForwardNet(soybean.getAllData(), layers3);
//        net2.backprop();
//        net2.evaluate();

        DataSetUp breastCancer = new DataSetUp("data-sets/breast-cancer-wisconsin.data", "endB", "classification");
        breastCancer.zScoreNormalize();

        inputLen = breastCancer.getAllData()[0].getNormalizedFeatures().length;
        int[] layersB = {inputLen, 16, 2};
        FeedForwardNet netB = new FeedForwardNet(breastCancer.getAllData(), layersB);
        netB.backprop();
        netB.evaluate();

//        DataSetUp forestFires = new DataSetUp("data-sets/forestfires.data", "endF", "regression");
//        forestFires.zScoreNormalize();
//
//        inputLen = forestFires.getAllData()[0].getNormalizedFeatures().length;
//        int[] layers2 = {inputLen, 16, 1};
//        FeedForwardNet net = new FeedForwardNet(forestFires.getAllData(), layers2);
//        net.backprop();
//        net.evaluate();
    }

}
