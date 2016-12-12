package view;

import controller.AppController;
import controller.MainController;
import model.ArrayTaskList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewTaskListEdit extends JFrame {

    private JPanel contentPane;
    private JButton addNewButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton backButton;
    DefaultListModel<String> model;
    JList<String> list;

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

        JLabel label = new JLabel("Task List Editor");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        list = new JList<>( model );
        model.removeAllElements();
        setList(MainController.getList());
        contentPane.add(list, BorderLayout.CENTER);


        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        addNewButton = new JButton("Add New");
        addNewButton.addActionListener(engine);
        panel.add(addNewButton);

        editButton = new JButton("Edit Task");
        editButton.addActionListener(engine);
        panel.add(editButton);

        removeButton = new JButton("Remove");
        removeButton.addActionListener(engine);
        panel.add(removeButton);

        backButton = new JButton("Back");
        backButton.addActionListener(engine);
        panel.add(backButton);

        setTitle("Task List Edit");
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public int getSelectedFromList() {

        return list.getSelectedIndex();
    }

    // removes selected task from the list
    public void removeFromList() {
        int selectedIndex = list.getSelectedIndex();
        MainController.removeFromList(selectedIndex);
        model.removeAllElements();
        setList(MainController.getList());
    }

    // builds list from MainController.taskList
    public void setList(ArrayTaskList arrayTaskList) {
        model.removeAllElements();
        for (int i = 0; i < arrayTaskList.size(); i++) {
            model.addElement(arrayTaskList.getTask(i).toString());
        }
    }



}

