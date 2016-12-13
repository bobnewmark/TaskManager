package controller;

import model.Task;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotifyController extends Thread {

    public NotifyController() {
        run();
    }

    public void run() {

        // arraylist for titles of task to pop-up in notifier
        ArrayList<String> arrList = new ArrayList<>();

        while (true) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.get(Calendar.HOUR_OF_DAY);

            // looping through tasklist for tasks to notify user about
            for (Task task : MainController.getList()) {
                long t = calendar.getTimeInMillis();
                Calendar testDate = Calendar.getInstance();
                if (task.nextTimeAfter(new Date(t - 60000)) != null) {
                    System.out.println("task.nexTimeAfter(new Date(t - 60000)) = " + task.nextTimeAfter(new Date(t - 60000)));
                    testDate.setTime(task.nextTimeAfter(new Date(t - 60000)));
                    System.out.println("CALENDAR NEW DATE IS: " + calendar.getTime());
                    System.out.println("TESTDATE NEW DATE IS: " + testDate.getTime());
                    if (testDate != null) {
                        if (testDate.get(Calendar.YEAR) != calendar.get(Calendar.YEAR)) continue;
                        if (testDate.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) continue;
                        if (testDate.get(Calendar.DAY_OF_MONTH) != calendar.get(Calendar.DAY_OF_MONTH)) continue;
                        if (testDate.get(Calendar.HOUR_OF_DAY) != calendar.get(Calendar.HOUR_OF_DAY)) continue;
                        if (testDate.get(Calendar.MINUTE) != calendar.get(Calendar.MINUTE)) continue;
                        arrList.add(task.getTitle());
                    }
                }
            }

            // if there's at least one task to notify the message dialog pane informs user about task/s to do
            if (arrList.size() > 0) {
                String a = "IT IS TIME FOR THE FOLLOWING:" + "\n" + "\n";
                for (String anArrList : arrList) {
                    a += "\"" + anArrList + "\"" + "\n";
                }
                JOptionPane.showMessageDialog(new JFrame(), a);
                arrList.clear();
            }
            // cooldown for 1 minute
            try {
                sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

