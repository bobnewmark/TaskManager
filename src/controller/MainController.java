package controller;

import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;
import view.ViewTaskList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class MainController {

    final static Logger logger = Logger.getLogger(MainController.class.getClass());
    static ArrayTaskList taskList = new ArrayTaskList();
    static File listFile;
    public static int selected;

    //saves current taskList to the text file listFile.txt. USED ONCE BEFORE CLOSING PROGRAM
    public static void saveList() {
        try {
            TaskIO.writeText(taskList, listFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // reads listFile.txt and fills the taskList. USED ONCE PROGRAM LAUNCHES
    public static void readList() {
        try {
            TaskIO.readText(taskList, listFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // clears the content of listFile.txt (NEVER YET USED, JUST IN CASE)
    public static void clearFile() {
        try {
            new PrintWriter("listFile.txt").close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // public access to the taskList
    public static ArrayTaskList getList() {
        return taskList;
    }

    // removes an item from taskList
    public static void removeFromList(int i) {
        taskList.remove(taskList.getTask(i));
    }


    public static void main(String[] args) {

        listFile = new File("listFile.txt");
        readList();
        new ViewTaskList();

        logger.info("engine started");
    }

}