package pl.edu.agh.semantics;

final class WeightPair<TARGET> implements Comparable<WeightPair<TARGET>> {

    private final TARGET target;
    private final double weight;

    public TARGET getTarget() {
        return target;
    }

    public double getWeight() {
        return weight;
    }

    WeightPair(TARGET target, double weight) {
        this.target = target;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "[\"" + target + "\"," + weight + ']';
    }

    @Override
    public int compareTo(WeightPair<TARGET> o) {
        return Double.compare(weight, o.weight);
    }
}
