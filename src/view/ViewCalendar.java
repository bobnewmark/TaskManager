package view;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewCalendar extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewCalendar frame = new ViewCalendar();
                    frame.setTitle("Calendar");
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
    public ViewCalendar() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("Task calendar for selected period");
        //lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNewLabel, BorderLayout.NORTH);



        JList list = new JList();
        contentPane.add(list, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);




        JPanel panel_for_pickers = new JPanel(new GridLayout(8, 1));
//panel_for_pickers.setLayout(new BoxLayout(panel_for_pickers, BoxLayout.Y_AXIS));
        JXDatePicker picker = new JXDatePicker();
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

        JXDatePicker picker2 = new JXDatePicker();
        picker2.setDate(Calendar.getInstance().getTime());
        picker2.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

        JLabel begin = new JLabel("From");
        panel_for_pickers.add(begin);
        panel_for_pickers.add(picker);

        JLabel end = new JLabel("To");
        panel_for_pickers.add(end);
        panel_for_pickers.add(picker2);
        panel_for_pickers.add(Box.createRigidArea(new Dimension(0,0)));
        panel_for_pickers.add(Box.createRigidArea(new Dimension(0,0)));
        panel_for_pickers.add(Box.createRigidArea(new Dimension(0,0)));

        JButton btnNewButton = new JButton("Back");
        btnNewButton.setPreferredSize(new Dimension(60, 40));
        panel_for_pickers.add(btnNewButton);
        contentPane.add(panel_for_pickers, BorderLayout.EAST);





    }

}