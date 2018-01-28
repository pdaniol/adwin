package pl.edu.agh.student.adwin;

import java.util.List;

class AdwinMathUtils {

     private static double sum(List<Double> dataSamples) {
        double sum = 0;
        for (double sample : dataSamples) {
            sum += sample;
        }
        return sum;

    }

     static Double mean(List<Double> list) {
        return sum(list) / list.size();
    }

     static Double harmonicMean(int firstSize, int secondSize) {
        return 2.0 / (1.0 / firstSize + 1.0 / secondSize);
    }

     static Double testValue(Double harmonicMean, Double confidence, int windowSize) {
        return Math.sqrt((1.0 / (2 * harmonicMean)) * Math.log(4 * windowSize / confidence));
    }
}
