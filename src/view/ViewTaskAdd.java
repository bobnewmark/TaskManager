package view;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ViewTaskAdd extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewTaskAdd frame = new ViewTaskAdd();
                    frame.setTitle("Add New Task");
                    frame.setMinimumSize(new Dimension(450, 300));
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ViewTaskAdd() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Enter the new task title here:");
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

        textField_1 = new JTextField();
        textField_1.setColumns(5);
        panel_4.add(textField_1);


        JLabel lblNewLabel_1 = new JLabel("If task is repeated set its end time and interval");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        panel_1.add(Box.createRigidArea(new Dimension(50,50)), BorderLayout.SOUTH);
        panel_1.add(lblNewLabel_1, BorderLayout.SOUTH);

        JPanel panel_2 = new JPanel();
        contentPane.add(panel_2, BorderLayout.SOUTH);

        JButton btnNewButton = new JButton("Confirm");
        panel_2.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Cancel");
        panel_2.add(btnNewButton_1);


    }

}