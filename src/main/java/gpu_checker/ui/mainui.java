package main.java.gpu_checker.ui;

import main.java.gpu_checker.ui.constructor.buttoncon;
import main.java.gpu_checker.ui.constructor.mainframe;
import main.java.gpu_checker.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class mainui {

    private static final DefaultListModel<String> model = new DefaultListModel<>();
    private static ArrayList<String> URLs = util.readfile();
    private static final JLabel label = new JLabel();
    private static final Integer delay = 10;

    public static void main(String[] args, boolean updateavail) {

        JPopupMenu popupmenu = new JPopupMenu();
        JMenuItem openurl = new JMenuItem("Open in Browser");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem clear = new JMenuItem("Clear Selected");
        popupmenu.add(openurl);
        popupmenu.addSeparator();
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

        openurl.addActionListener(e -> {
            try {
                URI u = new URI(URLs.get(list.getSelectedIndex()));

                Desktop d = Desktop.getDesktop();
                d.browse(u);
            } catch (Exception evt) {
                evt.printStackTrace();
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

        mainframe main = new mainframe("GPU Checker", JFrame.EXIT_ON_CLOSE, 700, 300,
                false, new Color(44, 49, 60), null);
        main.add(popupmenu);
        main.add(scrollpane);
        main.add(text);

        buttoncon button = new buttoncon("Get Item", new Rectangle(580, 235, 100, 20));
        button.addActionListener(actionEvent -> {
            String gettext = text.getText();

            String product = util.urlstringhandling(gettext);
            if (product.contains("ERROR")) {
                JOptionPane.showMessageDialog(main,
                        "Das Produkt wurde nicht vollständig gefunden ist die URL " + gettext + " richtig?",
                        "Iteminfo Fehlerhaft",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                if (!util.checkifexist(gettext)) {
                    Runnable addnewitem = () -> {
                        util.addline(gettext);
                        model.addElement(product);
                        URLs = util.readfile();
                    };
                    Thread t = new Thread(addnewitem);
                    t.start();
                } else {
                    JOptionPane.showMessageDialog(main,
                            gettext + " is already in the List.",
                            "Duplicate Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            text.setText("");
        });

        label.setBounds(480,220, 100, 50);
        label.setForeground(Color.WHITE);

        main.add(label);
        main.add(button);

        if (updateavail) {
            Object[] options1 = { "Go to Update Page", "Ignore" };

            int result = JOptionPane.showOptionDialog(main, "Es ist ein Update für die Version vefügbar.", "Update vefügbar.",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);
            if (result == JOptionPane.YES_OPTION){
                try {
                    URI u = new URI("https://github.com/Bandaras213/gpu_checker/releases/latest");

                    Desktop d = Desktop.getDesktop();
                    d.browse(u);
                } catch (Exception evt) {
                    evt.printStackTrace();
                }
            }
        }
        starttimer();
    }

    private static void starttimer() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = mainui::run;
        executor.scheduleWithFixedDelay(task, 0, delay, TimeUnit.MINUTES);
    }

    private static void run() {
        model.removeAllElements();
        if (URLs.size() != 0) {
            ArrayList<String> data = util.urlhandling(URLs);
            for (String datum : data) {
                model.addElement(datum);
            }
        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable refresh = new Runnable() {
            int i = delay * 60;

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
