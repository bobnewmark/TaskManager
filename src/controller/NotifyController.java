package controller;

import model.ArrayTaskList;
import model.Task;
import view.ViewNotification;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NotifyController extends Thread implements ActionListener {

    private ViewNotification viewNotification;
    static ArrayTaskList arrayTaskList;
    private HashMap<Task, Date> snoozeMap = new HashMap<>();

    public NotifyController() {
        setDaemon(true);
        run();
    }

    public void run() {

        System.out.println("::::::::::::::NOTIFY CONTROLLER RUNNING::::::::::::::");

        while (true) {
            for (Map.Entry<Task, Date> entry : snoozeMap.entrySet()) {
                System.out.println(entry.getKey().getTitle() + " " + entry.getValue());
            }
            buildArrayTaskList();

            // if there's at least one task to notify the message dialog pane informs user about task/s to do
            if (arrayTaskList.size() > 0) {

                if (viewNotification != null) viewNotification.dispose();
                viewNotification = new ViewNotification(this);
                viewNotification.setModel(arrayTaskList);
            }

            // cooldown for 1 minute
            try {
                sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Task getInfo(int index) {
        return arrayTaskList.getTask(index);
    }

    public void buildArrayTaskList() {
        arrayTaskList = new ArrayTaskList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.get(Calendar.HOUR_OF_DAY);

        // looping through tasklist for tasks to notify user about
        for (Task task : MainController.getList()) {

            long t = calendar.getTimeInMillis();
            Calendar testDate = Calendar.getInstance();
            if (task.nextTimeAfter(new Date(t - 60000)) != null) {
                //System.out.println("task.nexTimeAfter(new Date(t - 60000)) = " + task.nextTimeAfter(new Date(t - 60000)));
                testDate.setTime(task.nextTimeAfter(new Date(t - 60000)));
                //System.out.println("CALENDAR NEW DATE IS: " + calendar.getTime());
                //System.out.println("TESTDATE NEW DATE IS: " + testDate.getTime());
                if (testDate != null && task.isActive()) {
                    if (testDate.get(Calendar.YEAR) != calendar.get(Calendar.YEAR)) continue;
                    if (testDate.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) continue;
                    if (testDate.get(Calendar.DAY_OF_MONTH) != calendar.get(Calendar.DAY_OF_MONTH)) continue;
                    if (testDate.get(Calendar.HOUR_OF_DAY) != calendar.get(Calendar.HOUR_OF_DAY)) continue;
                    if (testDate.get(Calendar.MINUTE) != calendar.get(Calendar.MINUTE)) continue;
                    //System.out.println("test0");
                    if (snoozeMap.size() > 0) {
                        for (Map.Entry<Task, Date> entry : snoozeMap.entrySet()) {
                            if (task.equals(entry.getKey()) && task.nextTimeAfter(new Date(t - 60000)).before(entry.getValue())) {
                                //System.out.println("test1");
                                continue;
                            } else if (task.equals(entry.getKey())) {
                                snoozeMap.remove(entry.getKey());
                                arrayTaskList.add(task);
                            } else {
                                arrayTaskList.add(task);
                            }
                        }

                    } else {
                        arrayTaskList.add(task);
                    }

                    //System.out.println("::::::::::::::TASK ADDED TO NOTIFY::::::::::::::");


                }
            }
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton) e.getSource();

        if (clicked.getActionCommand().equals("Dismiss")) {
            int i = viewNotification.getSelectedNotify();
            if (i < 0) {
                JOptionPane.showMessageDialog(new JFrame(), "Select task to dismiss!");
                viewNotification.dispose();
                viewNotification = new ViewNotification(this);
                viewNotification.setModel(arrayTaskList);
            } else {
                Task disTask = arrayTaskList.getTask(i);
                for (int j = 0; j < MainController.getList().size(); j++) {
                    if (MainController.getList().getTask(j).equals(disTask)) {
                        MainController.getList().getTask(j).setActive(false);
                    }
                }
                buildArrayTaskList();
                viewNotification.setModel(arrayTaskList);
            }
        }
        if (clicked.getActionCommand().equals("Dismiss All")) {

            for (int j = 0; j < MainController.getList().size(); j++) {
                for (int i = 0; i < arrayTaskList.size(); i++) {
                    if (MainController.getList().getTask(j).equals(arrayTaskList.getTask(i))) {
                        MainController.getList().getTask(j).setActive(false);
                    }
                }
            }
            buildArrayTaskList();
            viewNotification.setModel(arrayTaskList);
        }
        if (clicked.getActionCommand().equals("Set Snooze")) {
            int index = viewNotification.getSelectedNotify();
            String comBoxValue = viewNotification.getComboBoxValue();

            if (index < 0) {
                JOptionPane.showMessageDialog(new JFrame(), "Select task to set snooze to!");
            } else {
                Task snoozeTask = arrayTaskList.getTask(index);
                switch (comBoxValue) {
                    case "Remind 5 minutes later":
                        for (int j = 0; j < MainController.getList().size(); j++) {
                            if (MainController.getList().getTask(j).equals(snoozeTask)) {
                                snoozeMap.put(snoozeTask, new Date(new Date().getTime() + 300000));
                                System.out.println("snoozeMap");
                                for (Map.Entry<Task, Date> entry : snoozeMap.entrySet()) {
                                    System.out.println(entry.getKey().getTitle() + " " + entry.getValue());
                                }
                                arrayTaskList.remove(arrayTaskList.getTask(index));
                                buildArrayTaskList();
                                viewNotification.setModel(arrayTaskList);
                            }
                        }
                        break;
                    case "Remind 30 minutes later":
                        for (int j = 0; j < MainController.getList().size(); j++) {
                            if (MainController.getList().getTask(j).equals(snoozeTask)) {
                                snoozeMap.put(snoozeTask, new Date(new Date().getTime() + 1800000));
                            }
                        }
                        break;
                    case "Remind 1 hour later":
                        for (int j = 0; j < MainController.getList().size(); j++) {
                            if (MainController.getList().getTask(j).equals(snoozeTask)) {
                                snoozeMap.put(snoozeTask, new Date(new Date().getTime() + 3600000));
                            }
                        }
                        break;
                    case "Remind 1 day later":
                        for (int j = 0; j < MainController.getList().size(); j++) {
                            if (MainController.getList().getTask(j).equals(snoozeTask)) {
                                snoozeMap.put(snoozeTask, new Date(new Date().getTime() + 86400000));
                            }
                        }
                        break;
                    case "":
                        break;
                }
                buildArrayTaskList();
                viewNotification.setModel(arrayTaskList);
            }


        }


    }
}

