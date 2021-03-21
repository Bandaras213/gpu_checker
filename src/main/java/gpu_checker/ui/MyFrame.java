package main.java.gpu_checker.ui;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    MyFrame() {
        this.setTitle("GPU Checker");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,300);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(44, 49, 60));
        this.setLayout(null);

        ImageIcon image = new ImageIcon("src/main/resources/logo.png");

        this.setIconImage(image.getImage());

        this.setVisible(true);
    }

}
