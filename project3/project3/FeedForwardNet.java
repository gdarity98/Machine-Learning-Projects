package project3;

/*
    Need to figure out how to make a nice architecture of a network
    Node class might work, but I will give it some thought.
 */
public class FeedForwardNet {

    private class Node {
        private double[] in;
        private double out;

        public double feedForward() {
            double sum = 0;
            for (double d : in) {
                sum += d;
            }
            //do sigmoid

            return 0;
        }
    }

    public int numInputs;
    public int numHiddenLayers;
    public int numHiddenNodes;
    public int numOutputs;
    public DataC[] data;

    public FeedForwardNet(int numInputs, int numHiddenLayers, int numHiddenNodes, int numOutputs) {
        this.numInputs = numInputs;
        this.numHiddenLayers = numHiddenLayers;
        this.numHiddenNodes = numHiddenNodes;
        this.numOutputs = numOutputs;
    }

    public FeedForwardNet(DataC[] data) {
        this.data = data;
        new FeedForwardNet(data[0].getFeatures().length, 1, 10, 1);
    }

    //make input nodes, hidden nodes, output nodes, randomize weights
    public void init() {

    }

    //push add data samples through the net
    public void feedForward() {

    }

    //update weights
    public void backprop() {

    }

}
