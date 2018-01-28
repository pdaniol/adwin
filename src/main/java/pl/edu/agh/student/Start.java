package pl.edu.agh.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.student.adwin.AdwinZero;
import pl.edu.agh.student.data.DataReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

public class Start {

    public static final Logger logger = LoggerFactory.getLogger(Start.class);
    public static final String filePathCsv = "src/main/resources/SEA_testing_data.csv";
    public static final String filePathTxt = "src/main/resources/data.txt";
    public static final String logFilePath = "logs/app.log";
    public static final String listKey = "data0";

    public static void main(String[] args) {

        DataReader dataReader = new DataReader();

        cleanup();
        runTxt(dataReader);

    }


     private static void cleanup(){
         try {
             PrintWriter pw = new PrintWriter(logFilePath);
             pw.close();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

    }

    private static void runTxt(DataReader dataReader){
        AdwinZero adwinTxt = AdwinZero.create(0.9, 5, 30);
        List<Double> exampleFromTxt = dataReader.readDataFromTxt(filePathTxt);

        exampleFromTxt.forEach(adwinTxt::addSample);

        logger.info("TXT: Number of detected changes: "+ adwinTxt.getDriftDetectedCount());
    }


    private static void runCsv(DataReader dataReader){
        List<Double> example = dataReader.readCsvFile(filePathCsv).get(listKey);
        AdwinZero adwin = AdwinZero.create(0.9, 5, 30);

        example.forEach(adwin::addSample);

        logger.info("CSV: Number of detected changes: "+adwin.getDriftDetectedCount());

    }
}
