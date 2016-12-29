package view;

import controller.AppController;
import controller.MainController;
import model.Task;
import model.Tasks;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

public class ViewCalendar extends JFrame implements ActionListener {

    private JPanel contentPane;
    private DefaultListModel<String> model;
    private JList<String> list;
    private JXDatePicker picker1;
    private JXDatePicker picker2;
    private JButton buildButton;
    private JButton backButton;

    private final static Logger logger = Logger.getLogger(ViewCalendar.class.getClass());


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
        contentPane.add(lblNewLabel, BorderLayout.NORTH);
        model = new DefaultListModel<>();
        list = new JList<>(model);
        list.setSelectionModel(new DisabledItemSelectionModel());
        contentPane.add(list, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);


        JPanel panel_for_pickers = new JPanel(new GridLayout(10, 1));
        picker1 = new JXDatePicker();
        picker1.setDate(Calendar.getInstance().getTime());
        picker1.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

        picker2 = new JXDatePicker();
        picker2.setDate(Calendar.getInstance().getTime());
        picker2.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

        JLabel begin = new JLabel("From");
        panel_for_pickers.add(begin);
        panel_for_pickers.add(picker1);

        JLabel end = new JLabel("To");
        panel_for_pickers.add(end);
        panel_for_pickers.add(picker2);
        panel_for_pickers.add(Box.createRigidArea(new Dimension(0, 0)));

        buildButton = new JButton("Show");
        buildButton.addActionListener(this);
        buildButton.setPreferredSize(new Dimension(60, 40));
        panel_for_pickers.add(buildButton);

        panel_for_pickers.add(Box.createRigidArea(new Dimension(0, 0)));
        panel_for_pickers.add(Box.createRigidArea(new Dimension(0, 0)));
        panel_for_pickers.add(Box.createRigidArea(new Dimension(0, 0)));

        backButton = new JButton("Back");
        backButton.addActionListener(engine);
        backButton.setPreferredSize(new Dimension(60, 40));
        panel_for_pickers.add(backButton);
        contentPane.add(panel_for_pickers, BorderLayout.EAST);

        setTitle("Calendar");
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // builds calendar for given period and brings it to the frame
    public void setCalendar() {
        model.removeAllElements();
        Date start = picker1.getDate();
        Date end = picker2.getDate();
        if (picker1.getDate().getTime() > picker2.getDate().getTime()) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid period set");
        }
        try {
            TreeMap<Date, Set<Task>> treeMap1 = (TreeMap<Date, Set<Task>>) Tasks.calendar(MainController.getList(), start, end);
            for (Map.Entry<Date, Set<Task>> entry: treeMap1.entrySet()) {
                model.addElement(entry.getKey().toString());
                for (Task task: entry.getValue()) {
                    model.addElement(task.getTitle());
                }
            }
        } catch (CloneNotSupportedException e) {
            logger.error("Error setting up ViewCalendar");

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked.getActionCommand().equals("Show")) {
            setCalendar();
        }
    }

    // creating a class for jlist that makes its elements unselectable
    class DisabledItemSelectionModel extends DefaultListSelectionModel {
        @Override
        public void setSelectionInterval(int index0, int index1) {
            super.setSelectionInterval(-1, -1);
            super.setSelectionMode(SINGLE_SELECTION);
        }
    }

}