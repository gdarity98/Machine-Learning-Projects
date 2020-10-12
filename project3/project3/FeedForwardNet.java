package project3;

import java.util.Arrays;

/*
    Need to figure out how to make a nice architecture of a network
    Node class might work, but I will give it some thought.
 */
public class FeedForwardNet {

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
                weights = new double[input.length][nextLayerSize];

                //randomize weights
                for (int i = 0; i < weights.length; i++) {
                    for (int j = 0; j < weights[0].length; j++) {
                        weights[i][j] = Math.random() * 1;
                    }
                }
            }
            else {
                return input;
            }

            nodes = input;

            return getOutput();
        }

        /*
            Gets an array of output[] that corresponds to the input
            to the next layer if it exists
         */
        public double[] getOutput() {
            //some matrix multiplication
            //[output] = [weights] * [nodes]
            for (int i= 0; i< weights.length; i++) {
                for (int col = 0; col < weights[0].length; col++) {
                    output[col] += weights[col][i] * nodes[col];
                }
            }

            //sigmoid( [outputs] )
            for (int i= 0; i< output.length; i++) {
                output[i] = sigmoid(output[i]);
            }

            return output;
        }

        public double sigmoid(double z) {
            return 1 / (Math.exp(-1 * z));
        }
    }

    public double[] weights;
    public Layer[] network;
    public DataC[] data;

    /*
        Takes an array of layer sizes for the network i.e
        [4, 2, 1] is a 3 layer net with 4 nodes in 1st layer,
        2 in 2nd layer, etc.
     */
    public FeedForwardNet(int[] layers) {

    }


    //make input nodes, hidden nodes, output nodes, randomize weights
    public void init() {

    }

    //push data samples through the net
    public void feedForward() {

    }

    //update weights
    public void backprop() {

    }

}
