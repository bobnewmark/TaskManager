package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewTaskList extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public ViewTaskList() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("My task list");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNewLabel, BorderLayout.NORTH);

        JList list = new JList();
        contentPane.add(list, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        JButton btnNewButton = new JButton("Calendar");
        panel.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Edit List");
        panel.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Exit");
        panel.add(btnNewButton_2);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewTaskList frame = new ViewTaskList();
                    frame.setTitle("Task Manager");
                    frame.setMinimumSize(new Dimension(450, 300));
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}