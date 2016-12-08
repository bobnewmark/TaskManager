package view;

import controller.AppController;
import model.ArrayTaskList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ViewTaskList extends JFrame {

    private JPanel contentPane;
    private JLabel lblNewLabel;
    DefaultListModel<String> model;
    JList<String> list;


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

        lblNewLabel = new JLabel("My task list");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNewLabel, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        list = new JList<>( model );
        contentPane.add(list, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        JButton calendarButton = new JButton("Calendar");
        calendarButton.addActionListener(engine);
        panel.add(calendarButton);

        JButton editListButton = new JButton("Edit List");
        editListButton.addActionListener(engine);
        panel.add(editListButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(engine);
        panel.add(exitButton);

        setTitle("Task Manager");
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //TODO: METHOD PROBABLY NEEDS A FIX, SEE LATER
    public ArrayList<String> getList() {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < list.getModel().getSize(); i++) {
            temp.add((String) list.getModel().getElementAt(i));
        }
        return temp;
    }


    public void setList(ArrayTaskList arrayTaskList) {
        for (int i = 0; i < arrayTaskList.size(); i++) {
            model.addElement(arrayTaskList.getTask(i).toString());
        }

    }
}