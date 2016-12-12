package view;

import controller.AppController;
import controller.MainController;
import model.Task;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewTaskEdit extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    JXDatePicker picker1;
    JXDatePicker picker2;
    JSpinner timeSpinner1;
    JSpinner timeSpinner2;
    JCheckBox activeness;
    JCheckBox repeating;


    /**
     * Create the frame.
     */
    public ViewTaskEdit() {

        AppController engine = new AppController(this);

        // setting the frame
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

        // here you can change the task name
        JLabel lblNewLabel = new JLabel("Task Title:");
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

        // time picker for choosing start date
        JLabel lblNewLabel_2 = new JLabel("Start");
        panel_4.add(lblNewLabel_2);
        picker1 = new JXDatePicker();
        picker1.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        panel_4.add(picker1);

        // timepinner for choosing start time
        timeSpinner1 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor1 = new JSpinner.DateEditor(timeSpinner1, "HH:mm:ss");
        timeSpinner1.setEditor(timeEditor1);
        panel_4.add(timeSpinner1);

        JPanel panel_6 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
        flowLayout.setVgap(30);
        JLabel lblNewLabel_3 = new JLabel("End");
        panel_6.add(lblNewLabel_3);

        // time picker for choosing end date
        picker2 = new JXDatePicker();
        picker2.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        picker2.addActionListener(this);
        panel_6.add(picker2);

        // timepinner for choosing end time
        timeSpinner2 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor2 = new JSpinner.DateEditor(timeSpinner2, "HH:mm:ss");
        timeSpinner2.setEditor(timeEditor2);
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

        // here you can set the task interval
        JLabel labelInt = new JLabel("Interval:");
        panel_8.add(labelInt);
        textField_1 = new JTextField();
        textField_1.setEnabled(false);
        panel_8.add(textField_1);
        textField_1.setColumns(15);

        // here you can set if task is active and/or repeated
        activeness = new JCheckBox("active");
        panel_8.add(activeness);
        repeating = new JCheckBox("repeated");
        repeating.addActionListener(this);
        panel_8.add(repeating);

        JPanel panel_2 = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) panel_2.getLayout();
        flowLayout_2.setVgap(15);
        contentPane.add(panel_2, BorderLayout.SOUTH);

        // buttons for confirming or cancelling changes
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(engine);
        panel_2.add(confirmButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(engine);
        panel_2.add(cancelButton);

        setTitle("Task Edit");
        setMinimumSize(new Dimension(475, 300));
        setLocationRelativeTo(null);
        taskToFrame();
        setVisible(true);
    }

    // saving edited task
    public void editTask() {

        Task tempTask = null;
        String name = textField.getText();
        Date timeSpinner1Date = (Date) timeSpinner1.getValue();
        long startLong = timeSpinner1Date.getTime() + picker1.getDate().getTime();
        boolean activity = activeness.isSelected();

        // if time for end is not set, taks is considered one-time
        if (picker2.getDate() == null) {
            try {
                tempTask = new Task(name, new Date(startLong), activity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // otherwise task is considered repeated so interval and end time are set
        } else {
            try {
                int interval = Integer.parseInt(textField_1.getText());
                //Date timeSpinner2Date = (Date) timeSpinner2.getValue();
                long endLong = timeSpinner1Date.getTime() + picker1.getDate().getTime();
                tempTask = new Task(name, new Date(startLong), new Date(endLong), interval, activity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // adding created task to the list
        Task delTask = MainController.getList().getTask((MainController.selected));
        MainController.getList().remove(delTask);
        MainController.getList().add(tempTask);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JCheckBox click = (JCheckBox) e.getSource();
            if (click.isSelected()) {
                timeSpinner2.setEnabled(true);
                textField_1.setEnabled(true);
            } else {
                timeSpinner2.setEnabled(false);
                textField_1.setEnabled(false);
                picker2.setDate(null);
            }
        } catch (Exception ex) {}

        try {
            JXDatePicker action = (JXDatePicker) e.getSource();
            if (picker1.getDate().getTime() > action.getDate().getTime()) {
                JOptionPane.showMessageDialog(new JFrame(), "End set earlier than start!");
                picker2.setDate(null);
            }
        } catch (Exception ex) {}
    }

    // bringing selected task from viewTaskEditList to viewTaskEdit frame
    public void taskToFrame() {
        Task temp = MainController.getList().getTask(MainController.selected);
        textField.setText(temp.getTitle());
        picker1.setDate(temp.getStartTime());
        timeSpinner1.setValue(temp.getStartTime());
        activeness.setSelected(temp.isActive());
        if (temp.isRepeated()) {
            picker2.setDate(temp.getEndTime());
            repeating.setSelected(true);
            timeSpinner2.setValue(temp.getEndTime());
            textField_1.setText(String.valueOf(temp.getRepeatInterval()));
        }
    }
}