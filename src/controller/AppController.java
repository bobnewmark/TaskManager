package controller;

import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController implements ActionListener {
    ViewTaskList viewTaskList;
    ViewTaskListEdit viewTaskListEdit;
    ViewTaskAdd viewTaskAdd;
    ViewTaskEdit viewTaskEdit;
    ViewNotification viewNotification;

    public AppController(ViewTaskList viewTaskList) {
        this.viewTaskList = viewTaskList;
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

    }
}
