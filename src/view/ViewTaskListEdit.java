package view;

import controller.AppController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewTaskListEdit extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public ViewTaskListEdit() {

        AppController engine = new AppController(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("Task List Editor");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNewLabel, BorderLayout.NORTH);

        JList list = new JList();
        contentPane.add(list, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        JButton addNewButton = new JButton("Add New");
        addNewButton.addActionListener(engine);
        panel.add(addNewButton);

        JButton editButton = new JButton("Edit Task");
        editButton.addActionListener(engine);
        panel.add(editButton);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(engine);
        panel.add(removeButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(engine);
        panel.add(backButton);

        setTitle("Task List Edit");
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewTaskListEdit frame = new ViewTaskListEdit();
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

