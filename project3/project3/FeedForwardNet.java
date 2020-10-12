package project3;

import java.util.Arrays;

/*
    A feed forward neural network

    @Author: Dave Miller
 */
public class FeedForwardNet {

    //===============================================>LAYER
    /*
        Layer describes a single layer in the network

        NEED TO ADD
            update nodes
            update weights w/ backprop
     */
    private class Layer {
        private double[][] weights;
        private double[] nodes;
        private double[] output;

        public Layer(int size) {
            nodes = new double[size];
        }

        /*
            First pass through, returns getOutput() so you can create the other
            layers. If nextLayerSize == 0, this is the last layer and input is
            returned.
         */
        public double[] init(double[] input, int nextLayerSize) {
            //if last layer return input
            if (nextLayerSize > 0) {
                weights = new double[nextLayerSize][input.length];
                output = new double[nextLayerSize];

                //randomize weights
                for (int row = 0; row < weights.length; row++) {
                    for (int col = 0; col < weights[0].length; col++) {
                        weights[row][col] = Math.random()*2 -1;
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
                    output[i] += weights[i][k] * nodes[k];
                }
            }

            //sigmoid( [outputs] )
            for (int i= 0; i< output.length; i++) {
                output[i] = sigmoid(output[i]);
            }

            return output;
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
        network = new Layer[layers.length];

        //for each layer, make a Layer object of size layers[i]
        for (int i= 0; i< layers.length; i++) {
            network[i] = new Layer(layers[i]);
        }

        init();
    }


    //call Layer.init(), first pass through the network
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
        for (DataC d: data) {
            double[] output = feedForward(d.getNormalizedFeatures());
            int target = Integer.parseInt(d.getClassLabel())-1;
            double[] t = new double[output.length];
            Arrays.fill(t, 0.0);
            t[target] = 1.0;

            System.out.println(cost(output, t));
        }
    }

    /*
        Gives squared error
     */
    public double cost(double[] output, double[] target) {
        double sum= 0;

        for (int i= 0; i< output.length; i++) {
            sum += Math.pow(output[i] - target[i], 2);
        }

        return sum / 2;
    }


    //==================================>MAIN
    public static void main(String[] args) {
        DataSetUp glass = new DataSetUp("data-sets/glass.data", "end", "classification");
        glass.zScoreNormalize();

        int inputLen = glass.getAllData()[0].getNormalizedFeatures().length;
        int[] layers = {inputLen, 5, 7};
        FeedForwardNet network = new FeedForwardNet(glass.getAllData(), layers);
        network.backprop();
    }

}
