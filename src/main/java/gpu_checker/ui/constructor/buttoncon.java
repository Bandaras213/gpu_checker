package main.java.gpu_checker.ui.constructor;

import javax.swing.*;
import java.awt.*;

public class buttoncon extends JButton {

    public buttoncon(String name, Rectangle rectangle) {
        this.setText(name);
        this.setBounds(rectangle);

    }
}
