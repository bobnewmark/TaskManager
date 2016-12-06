package controller;

import view.ViewTaskList;
import view.ViewTaskListEdit;

import javax.swing.*;

public class MainController {


    public static void main(String[] args) {
        ViewTaskList view = new ViewTaskList();
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ViewTaskListEdit());
        frame.pack();
        frame.setVisible(true);
        new AppController(view);
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
