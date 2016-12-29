package controller;

import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;
import view.ViewNotification;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class NotifyController extends Thread implements ActionListener {

    private final static Logger logger = Logger.getLogger(NotifyController.class.getClass());

    private ViewNotification viewNotification;
    static ArrayTaskList arrayTaskList;
    private static TreeMap<Task, Date> snoozeMap = new TreeMap<>((o1, o2) -> o1.hashCode() - o2.hashCode());
    static File snoozeTasks = new File("snoozeTasks.txt");
    static File snoozeTime = new File("snoozeTime.txt");


    public NotifyController() {

        //if needed files aren't found they are created
        if (!snoozeTasks.exists()) try {
            snoozeTasks.createNewFile();
        } catch (IOException e) {
            logger.error("Cannot create \"snoozeTasks.txt\"");
        }

        if (!snoozeTime.exists()) try {
            snoozeTime.createNewFile();
        } catch (IOException e) {
            logger.error("Cannot create \"snoozeTime.txt\"");
        }

        setDaemon(true);
        start();
    }

    public void run() {
        readSnooze();

        while (true) {
            buildArrayTaskList();

            // if there's at least one task to notify, notifier informs user about task/s to do
            if (arrayTaskList.size() > 0) {
                if (viewNotification != null) viewNotification.dispose();
                viewNotification = new ViewNotification(this);
                viewNotification.setModel(arrayTaskList);
            }

            // cooldown for 1 minute
            try {
                sleep(60000);
            } catch (InterruptedException e) {
                logger.info("NotifyController is shutting down");
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
                testDate.setTime(task.nextTimeAfter(new Date(t - 60000)));
                if (testDate != null && task.isActive()) {
                    if (testDate.get(Calendar.YEAR) != calendar.get(Calendar.YEAR)) continue;
                    if (testDate.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) continue;
                    if (testDate.get(Calendar.DAY_OF_MONTH) != calendar.get(Calendar.DAY_OF_MONTH)) continue;
                    if (testDate.get(Calendar.HOUR_OF_DAY) != calendar.get(Calendar.HOUR_OF_DAY)) continue;
                    if (testDate.get(Calendar.MINUTE) != calendar.get(Calendar.MINUTE)) continue;

                    // if there are some snoozed tasks in snoozeMap we check them
                    if (snoozeMap.size() > 0) {
                        for (Map.Entry<Task, Date> entry : snoozeMap.entrySet()) {
                            if (task.equals(entry.getKey()) && task.nextTimeAfter(new Date(t - 60000)).before(entry.getValue())) {
                                continue; // we don't put task in notification list if its snooze time is set later than current time
                            } else if (task.equals(entry.getKey())) {
                                snoozeMap.remove(entry.getKey()); //we remove task from snoozeMap if its time has passed
                                arrayTaskList.add(task);
                            } else {
                                arrayTaskList.add(task);
                            }
                        }
                    } else {
                        arrayTaskList.add(task);
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton) e.getSource();

        // if user dismisses a task its activeness sets to false
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
        // all tasks' activeness in notification list set to false
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

        // setting snooze for tasks puts them to the snoozeMap and prevents from appearing in notifier for set time
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
                                arrayTaskList.remove(arrayTaskList.getTask(index));
                                buildArrayTaskList();
                                viewNotification.setModel(arrayTaskList);
                            }
                        }
                        break;

                    case "Remind 1 hour later":
                        for (int j = 0; j < MainController.getList().size(); j++) {
                            if (MainController.getList().getTask(j).equals(snoozeTask)) {
                                snoozeMap.put(snoozeTask, new Date(new Date().getTime() + 3600000));
                                arrayTaskList.remove(arrayTaskList.getTask(index));
                                buildArrayTaskList();
                                viewNotification.setModel(arrayTaskList);
                            }
                        }
                        break;

                    case "Remind 1 day later":
                        for (int j = 0; j < MainController.getList().size(); j++) {
                            if (MainController.getList().getTask(j).equals(snoozeTask)) {
                                snoozeMap.put(snoozeTask, new Date(new Date().getTime() + 86400000));
                                arrayTaskList.remove(arrayTaskList.getTask(index));
                                buildArrayTaskList();
                                viewNotification.setModel(arrayTaskList);
                            }
                        }
                        break;

                    case "":
                        JOptionPane.showMessageDialog(new JFrame(), "You didn't set snooze time!");
                        break;
                }
                buildArrayTaskList();
                viewNotification.setModel(arrayTaskList);
            }
        }
    }

    // method saves snoozed tasks and their time to files
    public static void saveSnooze() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(snoozeTasks))) {
            ArrayTaskList temp = new ArrayTaskList();
            ArrayList<Date> tempDate = new ArrayList<>();
            for (Map.Entry<Task, Date> entry : snoozeMap.entrySet()) {
                temp.add(entry.getKey());
                tempDate.add(entry.getValue());
            }
            TaskIO.writeText(temp, snoozeTasks);
            try (BufferedWriter br2 = new BufferedWriter(new FileWriter(snoozeTime))) {
                for (int i = 0; i < tempDate.size(); i++) {
                    br2.write(String.valueOf(tempDate.get(i).getTime()));
                    br2.newLine();
                }
            }
        } catch (IOException e) {
            logger.error("Cannot save snoozed tasks into files \"snoozeTime.txt\" and \"snoozeTasks.txt\"");
        }
    }

    // method reads snoozed tasks and their time from files
    public static void readSnooze() {
        int linesTasks = 0;
        int linesTime = 0;
        ArrayTaskList temp = new ArrayTaskList();

        ArrayList<Date> tempDate = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(snoozeTasks));
             BufferedReader br2 = new BufferedReader(new FileReader(snoozeTime))) {
            String line;
            String line2;

            while ((line = br.readLine()) != null) {
                linesTasks++;
            }
            while ((line2 = br2.readLine()) != null) {
                tempDate.add(new Date(Long.parseLong(line2)));
                linesTime++;
            }
            if ((linesTasks == linesTime) && linesTasks != 0) {
                TaskIO.readText(temp, snoozeTasks);
                for (int i = 0; i < linesTasks; i++) {
                    snoozeMap.put(temp.getTask(i), tempDate.get(i));
                }
            }
            // if number of tasks and number of time for snooze if different we clear both files
            else if (linesTasks != linesTime) {
                JOptionPane.showMessageDialog(new JFrame(), "Snoozed tasks cannot be restored! Snooze list was reset");
                try {
                    Writer writer = new BufferedWriter(new FileWriter(snoozeTasks));
                    writer.write("");
                    writer = new BufferedWriter(new FileWriter(snoozeTime));
                    writer.write("");
                } catch (IOException e) {
                    logger.error("Cannot clear files \"snoozeTime.txt\" and \"snoozeTasks.txt\"");
                }
            }
        } catch (Exception e) {
            logger.error("Cannot read snoozed tasks from files \"snoozeTime.txt\" and \"snoozeTasks.txt\"");
        }
    }
}