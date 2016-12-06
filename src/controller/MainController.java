package controller;

import view.ViewTaskList;
import view.ViewTaskListEdit;

import javax.swing.*;

public class MainController {


    public static void main (String[] args) {
        JFrame frame = new JFrame ("MyPanel");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new ViewTaskList());
        frame.pack();
        frame.setVisible (true);
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("ViewTaskListEdit");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(new ViewTaskListEdit());
//        frame.pack();
//        frame.setVisible(true);
//    }
    
}
