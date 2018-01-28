package pl.edu.agh.student.adwin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class AdwinZero {
    public static final Logger logger = LoggerFactory.getLogger(AdwinZero.class);
    private Window window = new Window();
    private double confidence;
    private int minWindowSize;
    private int maxWindowSize;
    private int driftDetectedCount = 0;


    private static final double DEFAULT_CONFIDENCE = 1.0;
    private static final int DEFAULT_MIN_WINDOW_SIZE = 5;
    private static final int DEFAULT_MAX_WINDOW_SIZE = 50;

    public AdwinZero(double confidence, int minWindowSize, int maxWindowSize) {
        this.confidence = confidence;
        this.minWindowSize = minWindowSize;
        this.maxWindowSize = maxWindowSize;
    }

    public void addSample(Double sample) {

        if(window.getDataSamples().size()>=maxWindowSize){
            logger.error(String.format("Max window size exceeded - removing last element..."));
            window.removeLastSample();
        }

        window.addSample(sample);
        logWindowElements();
        int size = window.getDataSamples().size();
        while (window.getDataSamples().size() >= minWindowSize && !checkIfHolds()) {
            window.removeLastSample();
        }

        int newsize = window.getDataSamples().size();
        if (newsize < size) {
            logger.info(String.format("Change detected - window split!"));
            driftDetectedCount++;
            logWindowElements();

        }
    }


    private void logWindowElements() {
        StringBuilder tmp = new StringBuilder();
        for (Double elem : window.getDataSamples()) {
            tmp.append(elem.toString());
            tmp.append(", ");
        }
        logger.info("Window: " + tmp.toString());


    }

    private boolean checkIfHolds() {
        Window olderWindow = new Window();
        Window youngerWindow = new Window(window);
        Iterator<Double> iterator = window.getIterator();

        while (iterator.hasNext()) {
            Double windowItem = iterator.next();

            olderWindow.addSample(windowItem);
            youngerWindow.removeSample(windowItem);

            if (olderWindow.getDataSamples().size() > 0 && youngerWindow.getDataSamples().size() > 0) {
                double harmonicMean = getHarmonicMean(olderWindow, youngerWindow);
                double eCut = getECut(harmonicMean);

                double difference = getDifference(olderWindow, youngerWindow);

                if (difference >= eCut) {
                    return false;
                }
            }
        }
        return true;

    }

    private double getDifference(Window olderWindow, Window youngerWindow) {
        double olderMean = AdwinMathUtils.mean(olderWindow.getDataSamples());
        double youngerMean = AdwinMathUtils.mean(youngerWindow.getDataSamples());
        double result = Math.abs(olderMean - youngerMean);
        return result;
    }

    private double getECut(double harmonicMean) {
        double result = AdwinMathUtils.testValue(harmonicMean, confidence,
                window.getDataSamples().size());
        return result;
    }

    private double getHarmonicMean(Window olderWindow, Window youngerWindow) {
        double result = AdwinMathUtils.harmonicMean(olderWindow.getDataSamples().size(),
                youngerWindow.getDataSamples().size());
        return result;
    }

    public static AdwinZero create(double confidence, int minWindowSize, int maxWindowSize) {
        return new AdwinZero(confidence, minWindowSize, maxWindowSize);

    }

    public static AdwinZero create() {
        return new AdwinZero(DEFAULT_CONFIDENCE, DEFAULT_MIN_WINDOW_SIZE, DEFAULT_MAX_WINDOW_SIZE);
    }


    public double getConfidence() {
        return confidence;
    }

    public int getMinWindowSize() {
        return minWindowSize;
    }

    public int getMaxWindowSize() {
        return maxWindowSize;
    }

    public int getDriftDetectedCount() {
        return driftDetectedCount;
    }
}
