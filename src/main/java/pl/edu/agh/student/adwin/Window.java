package pl.edu.agh.student.adwin;

import java.util.Iterator;
import java.util.LinkedList;

public class Window {
    private LinkedList<Double> dataSamples = new LinkedList<>();

    public Window(Window window) {
        this.dataSamples = new LinkedList<>(window.getDataSamples());
    }

    public Window() {
    }

    public Window(LinkedList<Double> dataSamples) {
        this.dataSamples = dataSamples;
    }

    public void addSample(Double sample) {
        dataSamples.addFirst(sample);
    }

    public void removeSample(Double sample) {
        dataSamples.remove(sample);
    }

    public void removeLastSample() {
        dataSamples.removeLast();
    }

    public int findIndex(Double elem) {
        return dataSamples.indexOf(elem);
    }

    public void splitToIndex(int index) {
        dataSamples = new LinkedList<>(dataSamples.subList(0, index));
    }

    public Iterator<Double> getIterator() {
        return dataSamples.descendingIterator();
    }

    public LinkedList<Double> getDataSamples() {
        return dataSamples;
    }

    public void setDataSamples(LinkedList<Double> dataSamples) {
        this.dataSamples = dataSamples;
    }
}
