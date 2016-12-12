package controller;

import org.apache.log4j.Logger;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController implements ActionListener {

    final static Logger logger = Logger.getLogger(AppController.class.getClass());

    ViewTaskList viewTaskList;
    ViewCalendar viewCalendar;
    ViewTaskListEdit viewTaskListEdit;
    ViewTaskAdd viewTaskAdd;
    ViewTaskEdit viewTaskEdit;
    ViewNotification viewNotification;


    public AppController(ViewTaskList parent) {

        viewTaskList = parent;
        logger.info("AppController initialized by ViewTaskList");
    }

    public AppController(ViewCalendar parent) {

        viewCalendar = parent;
        logger.info("AppController initialized by ViewCalendar");
    }

    public AppController(ViewTaskListEdit parent) {

        viewTaskListEdit = parent;
        logger.info("AppController initialized by ViewTaskListEdit");
    }

    public AppController(ViewTaskEdit parent) {

        viewTaskEdit = parent;
        logger.info("AppController initialized by ViewTaskEdit");
    }

    public AppController(ViewTaskAdd parent) {

        viewTaskAdd = parent;
        logger.info("AppController initialized by ViewTaskAdd");
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        JButton clicked = (JButton) e.getSource();

        System.out.println("BUTTON CLICKED: " + clicked.getActionCommand());

        if (clicked.getActionCommand().equals("Calendar")) {
            viewTaskList.dispose();
            new ViewCalendar();

        }

        if (clicked.getActionCommand().equals("Exit")) {
            JOptionPane.showMessageDialog(new JFrame(), "This is going to be a warning pop-up later!");
            MainController.saveList();
            System.exit(0);
        }

        if (clicked.getActionCommand().equals("Back")) {
            try {
                viewCalendar.dispose();
                new ViewTaskList();
            } catch (Exception ex) {
            }

            try {
                viewTaskListEdit.dispose();
                new ViewTaskList();
            } catch (Exception ex) {
            }
        }

        if (clicked.getActionCommand().equals("Edit List")) {
            new ViewTaskListEdit();
            viewTaskList.dispose();
            System.out.println(MainController.taskList.size());
        }

        if (clicked.getActionCommand().equals("Edit Task")) {
            if (viewTaskListEdit.getSelectedFromList() < 0) {
                JOptionPane.showMessageDialog(new JFrame(), "Select task to edit!");
                viewTaskListEdit.dispose();
                new ViewTaskListEdit();
            } else {
                MainController.selected = viewTaskListEdit.getSelectedFromList();

                viewTaskListEdit.dispose();
                new ViewTaskEdit();

            }

        }

        if (clicked.getActionCommand().equals("Cancel")) {
            try {
                viewTaskEdit.dispose();
                new ViewTaskListEdit();
            } catch (Exception ex) {
            }

            try {
                viewTaskAdd.dispose();
                new ViewTaskListEdit();
            } catch (Exception ex) {
            }
        }

        if (clicked.getActionCommand().equals("Add New")) {
            viewTaskListEdit.dispose();
            new ViewTaskAdd();
        }

        if (clicked.getActionCommand().equals("Confirm")) {
            try {
                if (viewTaskAdd.checkInterval()) {
                    viewTaskAdd.newTaskAdd();
                    viewTaskAdd.dispose();
                    new ViewTaskListEdit();
                } else if (!viewTaskAdd.checkInterval() && viewTaskAdd.isRepeated()) {
                    JOptionPane.showMessageDialog(new JFrame(), "Interval needs to be a positive number!");
                    viewTaskAdd.dispose();
                    new ViewTaskAdd();
                } else {
                    viewTaskAdd.newTaskAdd();
                    viewTaskAdd.dispose();
                    new ViewTaskListEdit();
                }


            } catch (Exception ex) {
            }

            try {
                viewTaskEdit.editTask();
                viewTaskEdit.dispose();
                new ViewTaskListEdit();

            } catch (Exception ex) {
            }
        }

        if (clicked.getActionCommand().equals("Remove")) {
            viewTaskListEdit.removeFromList();
        }


    }


}