package main.java.gpu_checker;

import main.java.gpu_checker.ui.mainui;

public class start {

    public static String version = "Version 0.0.2";

    public static void main(String[] args) {
        util.debug("" +System.getProperty("user.home") + "\\AppData\\Local");
        util.debug(System.getProperty("user.dir"));
        util.checkdirectory();
        mainui.main(args);
    }

}
