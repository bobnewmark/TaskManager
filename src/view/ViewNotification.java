package view;

import controller.NotifyController;
import model.ArrayTaskList;
import model.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ViewNotification extends JFrame implements ActionListener{

    private JPanel contentPane;
    private JTextField textField;
    private DefaultListModel<String> model;
    private JList<String> list;
    NotifyController engine;
    JButton snoozeButton;
    JButton dismissButton;
    JButton disAllButton;
    JButton okButton;
    JComboBox comboBox;


    public ViewNotification(NotifyController nc) {
        engine = nc;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Hold mouse cursor on a task for details");
        panel.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        JPanel panel_2 = new JPanel();
        panel_1.add(panel_2, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        list = new JList<>( model );

        // shows task's info when mouse is over it
        list.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                JList l = (JList)e.getSource();
                ListModel m = l.getModel();
                int index = l.locationToIndex(e.getPoint());
                if( index>-1 ) {
                    Task put = NotifyController.getInfo(index);
                    l.setToolTipText(put.toString());
                }
            }
        });

        panel_1.add(list, BorderLayout.CENTER);

        JPanel panel_3 = new JPanel();
        panel_1.add(panel_3, BorderLayout.SOUTH);
        panel_3.setLayout(new BorderLayout(0, 0));

        JPanel panel_4 = new JPanel();
        panel_3.add(panel_4, BorderLayout.NORTH);
        panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));

        comboBox = new JComboBox();
        comboBox.addItem("");
        comboBox.addItem("Remind 5 minutes later");
        comboBox.addItem("Remind 30 minutes later");
        comboBox.addItem("Remind 1 hour later");
        comboBox.addItem("Remind 1 day later");
        panel_4.add(comboBox);
        snoozeButton = new JButton("Set Snooze");
        snoozeButton.addActionListener(engine);
        panel_4.add(snoozeButton);

        JPanel panel_5 = new JPanel();
        panel_3.add(panel_5, BorderLayout.SOUTH);


        dismissButton = new JButton("Dismiss");
        dismissButton.addActionListener(engine);
        panel_5.add(dismissButton);

        disAllButton = new JButton("Dismiss All");
        disAllButton.addActionListener(engine);
        panel_5.add(disAllButton);

        okButton = new JButton("OK");
        okButton.addActionListener(this);
        panel_5.add(okButton);

        setTitle("NOTIFICATION! It is time for following tasks!");
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setModel(ArrayTaskList arrayTaskList) {
        model.removeAllElements();
        for (int i = 0; i < arrayTaskList.size(); i++) {
            model.addElement(arrayTaskList.getTask(i).getTitle());
        }
    }

    // returns index of selected task in the list
    public int getSelectedNotify() {
        return list.getSelectedIndex();
    }

    //returns selected value from the combobox
    public String getComboBoxValue() {
        return (String) comboBox.getSelectedItem();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked.getActionCommand().equals("OK")) {
            this.dispose();
        }
    }
}


