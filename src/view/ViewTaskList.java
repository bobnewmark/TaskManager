package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import org.apache.*;
import org.apache.logging.log4j.Logger;

public class ViewTaskList extends JPanel {
    private JList jlist;
    private JButton calendarButton;
    private JButton editListButton;
    private JButton exitButton;
    private JLabel label;

    //static Logger logger = Logger(ViewTaskList.getClass());

    public ViewTaskList() {
        //construct preComponents
        String[] jlistItems = {"Item 1", "Item 2", "Item 3"};

        //construct components
        jlist = new JList (jlistItems);
        calendarButton = new JButton ("Calendar");
        editListButton = new JButton ("Edit List");
        exitButton = new JButton ("Exit");
        label = new JLabel ("Task List");

        //adjust size and set layout
        setPreferredSize (new Dimension (624, 391));
        setLayout (null);

        //add components
        add (jlist);
        add (calendarButton);
        add (editListButton);
        add (exitButton);
        add (label);

        //set component bounds (only needed by Absolute Positioning)
        jlist.setBounds (55, 45, 515, 235);
        calendarButton.setBounds (55, 305, 130, 40);
        editListButton.setBounds (245, 305, 130, 40);
        exitButton.setBounds (440, 305, 130, 40);
        label.setBounds (275, 10, 65, 25);
    }

}
