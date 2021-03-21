package main.java.gpu_checker.ui;

import main.java.gpu_checker.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class mainui {

    private static final DefaultListModel<String> model = new DefaultListModel<>();
    private static final ArrayList<String> URLs = (ArrayList<String>) util.readfile();
    private static final JLabel label = new JLabel();

    public static void main(String[] args) {

        JPopupMenu popupmenu = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem clear = new JMenuItem("Clear Selected");
        popupmenu.add(delete);
        popupmenu.add(clear);

        JList<String> list = new JList<>(model);
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)) {
                    popupmenu.show(list , me.getX(), me.getY());
                }
            }
        });

        delete.addActionListener(e -> {
            util.removeline(list.getSelectedIndex());
            model.removeElementAt(list.getSelectedIndex());
        });

        clear.addActionListener(e -> list.clearSelection());

        JScrollPane scrollpane = new JScrollPane(list);
        scrollpane.setBounds(5, 5, 675, 200);

        JTextField text = new JTextField("");
        text.setBounds(5,235, 400, 50);
        text.setSize(350, 20);

        MyFrame myFrame = new MyFrame();
        myFrame.add(popupmenu);
        myFrame.add(scrollpane);
        myFrame.add(text);

        JButton button = new JButton();
        button.setText("Get Items");
        button.setBounds(580,235, 100, 20);
        button.addActionListener(actionEvent -> {
            String gettext = text.getText();
            if (!util.checkifexist(gettext)) {
                util.addline(gettext);
                model.addElement(util.urlstringhandling(gettext));
            } else {
                JOptionPane.showMessageDialog(myFrame,
                        gettext + " is already in the List.",
                        "Duplicate Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
            text.setText("");
        });


        label.setBounds(480,220, 100, 50);
        label.setForeground(Color.WHITE);

        myFrame.add(label);
        myFrame.add(button);

        starttimer();
    }

    private static void starttimer() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = mainui::run;
        executor.scheduleWithFixedDelay(task, 0, 10, TimeUnit.MINUTES);
    }

    private static void run() {
        model.removeAllElements();
        assert URLs != null;
        if (URLs.size() != 0) {
            ArrayList<String> data = util.urlhandling(URLs);
            for (String datum : data) {
                model.addElement(datum);
            }
        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable refresh = new Runnable() {
            int i = 600;

            public void run() {
                String time = String.format("%02d:%02d", i / 60, i % 60);
                label.setText("Refresh: " + time);
                --i;
                if (i == 0) {
                    scheduler.shutdown();
                }
            }
        };
        scheduler.scheduleAtFixedRate(refresh, 0, 1, TimeUnit.SECONDS);
    }
}
