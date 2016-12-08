package view;

import controller.AppController;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewTaskEdit extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;


    /**
     * Create the frame.
     */
    public ViewTaskEdit() {

        AppController engine = new AppController(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Enter new title for the task here:");
        panel.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        JPanel panel_3 = new JPanel();
        panel_1.add(panel_3, BorderLayout.NORTH);

        textField = new JTextField();
        panel_3.add(textField);
        textField.setColumns(30);

        JPanel panel_4 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
        flowLayout.setVgap(60);
        flowLayout.setHgap(10);
        panel_1.add(panel_4, BorderLayout.CENTER);

        JXDatePicker picker = new JXDatePicker();
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        panel_4.add(picker);

        JXDatePicker picker2 = new JXDatePicker();
        picker2.setDate(Calendar.getInstance().getTime());
        picker2.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        panel_4.add(picker2);

        JLabel labelInt = new JLabel("Interval:");
        panel_4.add(labelInt);

        JCheckBox chckbxNewCheckBox = new JCheckBox("active");
        panel_4.add(chckbxNewCheckBox);

        textField_1 = new JTextField();
        textField_1.setColumns(5);
        panel_4.add(textField_1);


        JLabel lblNewLabel_1 = new JLabel("For repeated task set start/end and interval, leave interval blank otherwise");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        panel_1.add(Box.createRigidArea(new Dimension(50,50)), BorderLayout.SOUTH);
        panel_1.add(lblNewLabel_1, BorderLayout.SOUTH);

        JPanel panel_2 = new JPanel();
        contentPane.add(panel_2, BorderLayout.SOUTH);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(engine);
        panel_2.add(confirmButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(engine);
        panel_2.add(cancelButton);

        setTitle("Task Edit");
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);
        setVisible(true);

    }

}