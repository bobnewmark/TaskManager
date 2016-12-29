package controller;

import model.ArrayTaskList;
import model.TaskIO;
import org.apache.log4j.Logger;
import view.ViewTaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class MainController {

    static File listFile = new File("listFile.txt");
    final static Logger logger1 = Logger.getLogger(MainController.class.getClass());
    static ArrayTaskList taskList = new ArrayTaskList();
    public static int selected;

    //saves current taskList to the text file listFile.txt. USED ONCE BEFORE CLOSING PROGRAM
    public static void saveList() {
        try {
            TaskIO.writeText(taskList, listFile);
        } catch (IOException e) {
            logger1.error("Cannot save changes to file \"listFile.txt\"");
        }
    }

    // reads listFile.txt and fills the taskList. USED ONCE PROGRAM LAUNCHES
    public static void readList() {
        try {
            TaskIO.readText(taskList, listFile);
        } catch (Exception e) {
            logger1.error("Cannot read file \"listFile.txt\"");
        }
    }

    // clears the content of listFile.txt (NEVER YET USED, JUST IN CASE)
    public static void clearFile() {
        try {
            new PrintWriter("listFile.txt").close();
        } catch (FileNotFoundException e) {
            logger1.error("Cannot clear file \"listFile.txt\"");
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
        logger1.info("TASKMANAGER LAUNCHED SUCCESSFULLY");
        if (!listFile.exists()) try {
            listFile.createNewFile();
        } catch (IOException e) {
            logger1.error("Cannot create file \"listFile.txt\"");
        }
        logger1.info("reading file 'listFile.txt' for any tasks...");
        readList();
        new ViewTaskList();
        new NotifyController();
    }
}