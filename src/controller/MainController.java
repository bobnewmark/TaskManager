package controller;

import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;
import view.ViewTaskList;
import java.io.File;
import java.io.IOException;

public class MainController {

    final static Logger logger = Logger.getLogger(MainController.class.getClass());
    static ArrayTaskList taskList = new ArrayTaskList();

    public int getTaskListSize() {
        return taskList.size();
    }

    public static void main(String[] args) {

        File listFile = new File("listFile.txt");
        ArrayTaskList taskList = new ArrayTaskList();
        try {
            TaskIO.readText(taskList, listFile);
            logger.info("reading from file successful");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            listFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (Task t: taskList) {
            System.out.println(t.toString());
        }


        ViewTaskList startFrame = new ViewTaskList();
        startFrame.setList(taskList);

        logger.info("engine started");

    }

}
