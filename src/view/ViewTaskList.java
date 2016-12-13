package view;

import controller.AppController;
import controller.MainController;
import model.ArrayTaskList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ViewTaskList extends JFrame {

    private JPanel contentPane;
    private JLabel label;
    private JButton calendarButton;
    private JButton editListButton;
    private JButton exitButton;
    private DefaultListModel<String> model;
    private JList<String> list;


    /**
     * Create the frame.
     */
    public ViewTaskList() {

        AppController engine = new AppController(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        label = new JLabel("My task list");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        list = new JList<>( model );
        list.setSelectionModel(new DisabledItemSelectionModel());
        model.removeAllElements();
        setList(MainController.getList());
        contentPane.add(list, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        calendarButton = new JButton("Calendar");
        calendarButton.addActionListener(engine);
        panel.add(calendarButton);

        editListButton = new JButton("Edit List");
        editListButton.addActionListener(engine);
        panel.add(editListButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(engine);
        panel.add(exitButton);

        setTitle("Task Manager");
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // brings tasklist to the frame
    private void setList(ArrayTaskList arrayTaskList) {
        model.removeAllElements();
        for (int i = 0; i < arrayTaskList.size(); i++) {
            model.addElement(arrayTaskList.getTask(i).toString());
        }
    }

    // creating a class for jlist that makes its elements unselectable
    private class DisabledItemSelectionModel extends DefaultListSelectionModel {
        @Override
        public void setSelectionInterval(int index0, int index1) {
            super.setSelectionInterval(-1, -1);
            super.setSelectionMode(SINGLE_SELECTION);
        }
    }
}