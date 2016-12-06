package controller;

import org.apache.log4j.*;
import view.ViewTaskList;
import view.ViewTaskListEdit;

import javax.swing.*;

public class MainController {


    final static Logger logger = Logger.getLogger(MainController.class.getClass());

    public static void main(String[] args) {
        ViewTaskList view = new ViewTaskList();
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ViewTaskListEdit());
        frame.pack();
        frame.setVisible(true);
        new AppController(view);
        logger.debug("Launch successful");
        //System.out.println(System.getProperty("java.class.path"));
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("ViewTaskListEdit");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(new ViewTaskListEdit());
//        frame.pack();
//        frame.setVisible(true);
//    }

//    JFrame frame = new JFrame ("MyPanel");
//        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add (new ViewTaskList());
//        frame.pack();
//        frame.setVisible (true);

}
