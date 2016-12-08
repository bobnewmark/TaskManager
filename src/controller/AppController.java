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




    public void setViewCalendar(ViewCalendar viewCalendar) {
        this.viewCalendar = viewCalendar;
    }

    public void setViewTaskListEdit(ViewTaskListEdit viewTaskListEdit) {
        this.viewTaskListEdit = viewTaskListEdit;
    }

    public void setViewTaskAdd(ViewTaskAdd viewTaskAdd) {
        this.viewTaskAdd = viewTaskAdd;
    }

    public void setViewTaskEdit(ViewTaskEdit viewTaskEdit) {
        this.viewTaskEdit = viewTaskEdit;
    }

    public void setViewNotification(ViewNotification viewNotification) {
        this.viewNotification = viewNotification;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton) e.getSource();
        System.out.println(clicked.getActionCommand());

        if (clicked.getActionCommand().equals("Calendar")) {
            viewTaskList.dispose();
            new ViewCalendar();

        }

        if (clicked.getActionCommand().equals("Exit")) {
            JOptionPane.showMessageDialog(new JFrame(), "This is going to be a warning pop-up later!");
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
        }

        if (clicked.getActionCommand().equals("Edit Task")) {
            viewTaskListEdit.dispose();
            new ViewTaskEdit();
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
                viewTaskAdd.dispose();
                new ViewTaskListEdit();

            } catch (Exception ex) {
            }

            try {
                viewTaskEdit.dispose();
                new ViewTaskListEdit();

            } catch (Exception ex) {
            }
        }


    }
}
