package main.java.gpu_checker.ui.constructor;

import javax.swing.*;
import java.awt.*;

public class mainframe extends JFrame {

    public mainframe(String name, int closeoperator, int width, int height, boolean resizeable, Color color,
                      LayoutManager layout) {
        this.setTitle(name);
        this.setDefaultCloseOperation(closeoperator);
        this.setSize(width, height);
        this.setResizable(resizeable);
        this.getContentPane().setBackground(color);
        this.setLayout(layout);

        ImageIcon image = new ImageIcon("src/main/resources/logo.png");

        this.setIconImage(image.getImage());

        this.setVisible(true);
    }

}
