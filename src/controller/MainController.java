package controller;

import org.apache.log4j.Logger;
import view.ViewTaskList;
import java.io.File;
import java.io.IOException;

public class MainController {

    final static Logger logger = Logger.getLogger(MainController.class.getClass());

    public static void main(String[] args) {

        File listFile = new File("listFile.txt");
        try {
            listFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ViewTaskList startFrame = new ViewTaskList();

        logger.info("engine started");

    }

}
