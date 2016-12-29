package view;

import controller.AppController;
import controller.MainController;
import model.Task;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewTaskAdd extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JXDatePicker picker1;
    private JXDatePicker picker2;
    private JSpinner timeSpinner1;
    private JSpinner timeSpinner2;
    private JCheckBox activeness;

    private final static Logger logger = Logger.getLogger(ViewTaskAdd.class.getClass());



    /**
     * Create the frame.
     */
    public ViewTaskAdd() {

        AppController engine = new AppController(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 195);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        JPanel panel_3 = new JPanel();
        FlowLayout flowLayout_3 = (FlowLayout) panel_3.getLayout();
        flowLayout_3.setVgap(15);
        panel_1.add(panel_3, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("New Task:");
        panel_3.add(lblNewLabel);
        textField = new JTextField();
        panel_3.add(textField);
        textField.setColumns(30);

        JPanel panel_5 = new JPanel();
        panel_1.add(panel_5, BorderLayout.CENTER);
        panel_5.setLayout(new BorderLayout(0, 0));
        JPanel panel_4 = new JPanel();
        panel_5.add(panel_4, BorderLayout.WEST);
        panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 30));

        JLabel lblNewLabel_2 = new JLabel("Start");
        panel_4.add(lblNewLabel_2);
        picker1 = new JXDatePicker();
        picker1.setDate(Calendar.getInstance().getTime());
        picker1.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        panel_4.add(picker1);

        timeSpinner1 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor1 = new JSpinner.DateEditor(timeSpinner1, "HH:mm");
        timeSpinner1.setEditor(timeEditor1);
        timeSpinner1.setValue(zeroTime());
        panel_4.add(timeSpinner1);

        JPanel panel_6 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
        flowLayout.setVgap(30);
        JLabel lblNewLabel_3 = new JLabel("End");
        panel_6.add(lblNewLabel_3);

        picker2 = new JXDatePicker();
        picker2.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        picker2.addActionListener(this);
        panel_6.add(picker2);

        timeSpinner2 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor2 = new JSpinner.DateEditor(timeSpinner2, "HH:mm");
        timeSpinner2.setEditor(timeEditor2);
        timeSpinner2.setValue(zeroTime());
        timeSpinner2.setEnabled(false);
        panel_6.add(timeSpinner2);
        panel_5.add(panel_6, BorderLayout.EAST);

        JPanel panel_7 = new JPanel();
        panel_5.add(panel_7, BorderLayout.SOUTH);
        panel_7.setLayout(new BorderLayout(0, 5));
        JPanel panel_8 = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel_8.getLayout();
        flowLayout_1.setVgap(15);
        panel_7.add(panel_8, BorderLayout.NORTH);

        JLabel labelInt = new JLabel("Interval (min):");
        panel_8.add(labelInt);
        textField_1 = new JTextField();
        textField_1.setEnabled(false);
        panel_8.add(textField_1);
        textField_1.setColumns(20);
        activeness = new JCheckBox("active");
        panel_8.add(activeness);

        JLabel lblNewLabel_1 = new JLabel("If task is repeated set its end time and interval");
        lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        panel_7.add(lblNewLabel_1, BorderLayout.SOUTH);

        JPanel panel_2 = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) panel_2.getLayout();
        flowLayout_2.setVgap(15);
        contentPane.add(panel_2, BorderLayout.SOUTH);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(engine);
        panel_2.add(confirmButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(engine);
        panel_2.add(cancelButton);

        setTitle("Task Add");
        setMinimumSize(new Dimension(475, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }


    // adding new task
    public void newTaskAdd() {

        Task tempTask = null;
        int interval = -1;
        String name = textField.getText();
        Date timeSpinner1Date = (Date) timeSpinner1.getValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(0));
        cal.add(Calendar.HOUR_OF_DAY, timeSpinner1Date.getHours());
        cal.add(Calendar.MINUTE, timeSpinner1Date.getMinutes());
        cal.add(Calendar.SECOND, timeSpinner1Date.getSeconds());
        long startLong = cal.getTimeInMillis() + picker1.getDate().getTime();
        boolean activity = activeness.isSelected();

        // if time for end is not set, task is considered one-time
        if (picker2.getDate() == null) {
            try {
                tempTask = new Task(name, new Date(startLong), activity);

            } catch (Exception e) {
                logger.error("Cannot create non repeating task");

            }
            // otherwise task is considered repeated so interval and end time are set
        } else {
            try {
                interval = Integer.parseInt(textField_1.getText()) * 60;
                Date timeSpinner2Date = (Date) timeSpinner2.getValue();
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(new Date(0));
                cal1.add(Calendar.HOUR_OF_DAY, timeSpinner2Date.getHours());
                cal1.add(Calendar.MINUTE, timeSpinner2Date.getMinutes());
                cal1.add(Calendar.SECOND, timeSpinner2Date.getSeconds());
                long endLong = cal1.getTimeInMillis() + picker2.getDate().getTime();
                tempTask = new Task(name, new Date(startLong), new Date(endLong), interval, activity);
            } catch (Exception e) {
                logger.error("Cannot create repeating task");

            }
        }
        // adding created task to the list
        MainController.getList().add(tempTask);
    }

    //checks if interval is all numeric
    public boolean checkInterval() {
        String str = textField_1.getText();
        if (str == null) return false;
        int length = str.length();
        if (length == 0) return false;
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return Integer.parseInt(str) > 0;
    }

    public boolean checkTitle() {
        String s = textField.getText();
        return s.length() > 0;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JXDatePicker action = (JXDatePicker) e.getSource();
        // when user sets date for end fields for interval and end time become editable
        if (action.getDate() != null) {
            timeSpinner2.setEnabled(true);
            textField_1.setEnabled(true);
        }
        // warning message if end is set earlier than start
        if (picker1.getDate().getTime() > action.getDate().getTime()) {
            JOptionPane.showMessageDialog(new JFrame(), "End set earlier than start!");
            picker2.setDate(null);
        }
    }

    // method to discard hours, mins, secs and millisecs from date (to add time from jspinner)
    public static Date zeroTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public boolean isRepeated() {
        return picker2.getDate() != null;
    }


}