package pl.edu.agh.student.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DataReader {
    public static final Logger logger = LoggerFactory.getLogger(DataReader.class);


    public Map<String, LinkedList<Double>> readCsvFile(String filePath){
        Map<String, LinkedList<Double>> dataMap = new HashMap<>();
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {

                String[] dataLine = line.split(cvsSplitBy);
                for(int i= 0; i< dataLine.length; i++){
                    addToMap(dataMap, "data"+i, dataLine[i]);
                }
            }

        } catch (IOException e) {
            logger.error("Error while reading CSV file.");
        }

        return dataMap;
    }

    public LinkedList<Double> readDataFromTxt(String path){
        String line = "";
        String textSplitBy = ",";
        LinkedList<Double> list = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((line = br.readLine()) != null) {

                String[] dataLine = line.split(textSplitBy);
                for(int i= 0; i< dataLine.length; i++){
                    list.add(Double.parseDouble(dataLine[i]));
                }
            }

        } catch (IOException e) {
            logger.error("Error while reading text file.");
        }

        return list;
    }

    void addToMap(Map<String,LinkedList<Double>> map, String key, String sample){

        if(!map.containsKey(key)){
            LinkedList<Double> valueList = new LinkedList<>();
            valueList.add(Double.parseDouble(sample));
            map.put(key, valueList);
       } else {
            map.get(key).add(Double.parseDouble(sample));
        }
    }



}