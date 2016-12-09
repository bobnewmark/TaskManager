package view;

import controller.AppController;
import controller.MainController;
import model.Task;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.calendar.DateUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewTaskAdd extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    JXDatePicker picker1;
    JXDatePicker picker2;
    JSpinner timeSpinner1;
    JSpinner timeSpinner2;
    JCheckBox activeness;


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
        JSpinner.DateEditor timeEditor1 = new JSpinner.DateEditor(timeSpinner1, "HH:mm:ss");
        timeSpinner1.setEditor(timeEditor1);
        timeSpinner1.setValue(new Date());
        panel_4.add(timeSpinner1);


        JPanel panel_6 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
        flowLayout.setVgap(30);
        JLabel lblNewLabel_3 = new JLabel("End");
        panel_6.add(lblNewLabel_3);

        picker2 = new JXDatePicker();
        //picker2.setDate(Calendar.getInstance().getTime());
        picker2.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        panel_6.add(picker2);

        timeSpinner2 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor2 = new JSpinner.DateEditor(timeSpinner2, "HH:mm:ss");
        timeSpinner2.setEditor(timeEditor2);
        timeSpinner2.setValue(new Date());
        panel_6.add(timeSpinner2);
        panel_5.add(panel_6, BorderLayout.EAST);

        JPanel panel_7 = new JPanel();
        panel_5.add(panel_7, BorderLayout.SOUTH);
        panel_7.setLayout(new BorderLayout(0, 5));

        JPanel panel_8 = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel_8.getLayout();
        flowLayout_1.setVgap(15);
        panel_7.add(panel_8, BorderLayout.NORTH);

        JLabel labelInt = new JLabel("Interval:");
        panel_8.add(labelInt);

        textField_1 = new JTextField();
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


    public void newTaskAdd() {

        Task tempTask = null;
        String name = textField.getText();
//        SimpleDateFormat format = new SimpleDateFormat("y-MM-dd");
//        String startDate = format.format(picker1.getDate());
//        String endDate = format.format(picker1.getDate());
        //Date one = (Date) timeSpinner1.getValue();
        //Date two = (Date) timeSpinner2.getValue();

//        Calendar calOne = Calendar.getInstance();
//        calOne.setTime(picker1.getDate());

        System.out.println("::::::::::::::::::::BEFORE STUPID TIMESPINNER1:::::::::::::::::::::::::::::::::");

        Date timeSpinner1Date = (Date) timeSpinner1.getValue();
        System.out.println("cast to date");
        long startLong = timeSpinner1Date.getTime() + picker1.getDate().getTime();
        int interval = Integer.parseInt(textField_1.getText());
        boolean activity = activeness.isSelected();

        if (picker2.getDate() == null) {
            try {
                tempTask = new Task(name, new Date(startLong), activity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Date timeSpinner2Date = (Date) timeSpinner2.getValue();
                System.out.println("cast to date222");
                long endLong = timeSpinner1Date.getTime() + picker1.getDate().getTime();
                tempTask = new Task(name, new Date(startLong), new Date(endLong), interval, activity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        MainController.getList().add(tempTask);




    }

}