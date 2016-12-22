package controller;

import org.apache.log4j.Logger;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController implements ActionListener {

    private final static Logger logger = Logger.getLogger(AppController.class.getClass());

    private ViewTaskList viewTaskList;
    private ViewCalendar viewCalendar;
    private ViewTaskListEdit viewTaskListEdit;
    private ViewTaskAdd viewTaskAdd;
    private ViewTaskEdit viewTaskEdit;

    // constructors for different View frames

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


    // action listeners for buttons of all View frames
    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton) e.getSource();

        // opens calendar frame
        if (clicked.getActionCommand().equals("Calendar")) {
            viewTaskList.dispose();
            new ViewCalendar();
        }

        // saves list of current tasks to file and closes the program
        if (clicked.getActionCommand().equals("Exit")) {
            logger.info("Saving list of tasks to file 'listFule.txt'...");
            MainController.saveList();
            logger.info("TASKMANAGER IS SHUTTING DOWN");
            System.exit(0);
        }

        // if user clicks 'back' on calendar frame it disposes and user gets to the ViewTaskList frame
        if (clicked.getActionCommand().equals("Back")) {
            try {
                viewCalendar.dispose();
                new ViewTaskList();
            } catch (Exception ignored) {
            }
        // if user clicks 'back' on editin task frame he goes to ViewTaskList as well
            try {
                viewTaskListEdit.dispose();
                new ViewTaskList();
            } catch (Exception ignored) {
            }
        }

        // opens ViewTaskListEdit
        if (clicked.getActionCommand().equals("Edit List")) {
            new ViewTaskListEdit();
            viewTaskList.dispose();
        }

        // to edit task user has to select it and then he may go to ViewTaskEdit
        if (clicked.getActionCommand().equals("Edit Task")) {
            if (viewTaskListEdit.getSelectedFromList() < 0) {
                JOptionPane.showMessageDialog(new JFrame(), "Select task to edit!");
                //viewTaskListEdit.dispose();
                //new ViewTaskListEdit();
            } else {
                MainController.selected = viewTaskListEdit.getSelectedFromList();
                viewTaskListEdit.dispose();
                new ViewTaskEdit();
            }
        }

        // cancels any current editing of the selected task
        if (clicked.getActionCommand().equals("Cancel")) {
            try {
                viewTaskEdit.dispose();
                new ViewTaskListEdit();
            } catch (Exception ignored) {
            }
        // cancels task adding
            try {
                viewTaskAdd.dispose();
                new ViewTaskListEdit();
            } catch (Exception ignored) {
            }
        }

        // opens ViewTaskAdd
        if (clicked.getActionCommand().equals("Add New")) {
            viewTaskListEdit.dispose();
            new ViewTaskAdd();
        }

        // checks interval for positive integer value and adds new task to the list
        if (clicked.getActionCommand().equals("Confirm")) {
            try {
                if (viewTaskAdd.checkInterval() && viewTaskAdd.checkTitle()) {
                    viewTaskAdd.newTaskAdd();
                    viewTaskAdd.dispose();
                    new ViewTaskListEdit();
                } else if (!viewTaskAdd.checkInterval() && viewTaskAdd.isRepeated()) {
                    JOptionPane.showMessageDialog(new JFrame(), "Interval needs to be a positive number!");
                    //viewTaskAdd.dispose();
                    //new ViewTaskAdd();
                } else if (!viewTaskAdd.checkTitle()) {
                    JOptionPane.showMessageDialog(new JFrame(), "Enter the title of new task!");
                } else {
                    viewTaskAdd.newTaskAdd();
                    viewTaskAdd.dispose();
                    new ViewTaskListEdit();
                }
                logger.info("New task was added to the list");
            } catch (Exception ignored) {
            }
        // returns user from ViewTaskEdit ti ViewTaskListEdit (changes saved)
            try {
                viewTaskEdit.editTask();
                viewTaskEdit.dispose();
                new ViewTaskListEdit();
                logger.info("A task was edited");
            } catch (Exception ignored) {
            }
        }
        // removes selected task from the list
        if (clicked.getActionCommand().equals("Remove")) {
            viewTaskListEdit.removeFromList();
            logger.info("A task was removed the list");
        }
    }
}