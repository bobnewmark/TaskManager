package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class ViewTaskListEdit extends JPanel {
    private JList jList;
    private JButton addTaskButton;
    private JButton removeTaskButton;
    private JButton editTaskButton;
    private JButton exitButton;
    private JLabel label;


    public ViewTaskListEdit() {
        //construct preComponents
        String[] jListItems = {"Item 1", "Item 2", "Item 3"};

        //construct components
        jList = new JList(jListItems);
        addTaskButton = new JButton("Add");
        removeTaskButton = new JButton("Remove");
        exitButton = new JButton("Exit");
        label = new JLabel("Task List");
        editTaskButton = new JButton("Edit");

        //adjust size and set layout
        setPreferredSize(new Dimension(620, 381));
        setLayout(null);

        //add components
        add(jList);
        add(addTaskButton);
        add(removeTaskButton);
        add(exitButton);
        add(label);
        add(editTaskButton);

        //set component bounds (only needed by Absolute Positioning)
        jList.setBounds(55, 45, 515, 235);
        addTaskButton.setBounds(55, 305, 115, 40);
        removeTaskButton.setBounds(188, 305, 115, 40);
        exitButton.setBounds(455, 305, 115, 40);
        label.setBounds(275, 10, 65, 25);
        editTaskButton.setBounds(322, 305, 115, 40);
    }

}

