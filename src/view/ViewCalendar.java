package view;

import controller.AppController;
import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewCalendar extends JFrame {

    private JPanel contentPane;


    /**
     * Create the frame.
     */
    public ViewCalendar() {

        AppController engine = new AppController(this);

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




        JPanel panel_for_pickers = new JPanel(new GridLayout(10, 1));
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

        JButton buildButton = new JButton("View");
        buildButton.addActionListener(engine);
        buildButton.setPreferredSize(new Dimension(60, 40));
        panel_for_pickers.add(buildButton);

        panel_for_pickers.add(Box.createRigidArea(new Dimension(0,0)));
        panel_for_pickers.add(Box.createRigidArea(new Dimension(0,0)));
        panel_for_pickers.add(Box.createRigidArea(new Dimension(0,0)));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(engine);
        backButton.setPreferredSize(new Dimension(60, 40));
        panel_for_pickers.add(backButton);
        contentPane.add(panel_for_pickers, BorderLayout.EAST);

        setTitle("Calendar");
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);
        setVisible(true);




    }

}