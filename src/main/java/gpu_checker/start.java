package main.java.gpu_checker;

import main.java.gpu_checker.ui.mainui;

public class start {

    public static int version = 003;

    public static void main(String[] args) {
        boolean updatable = false;

        util.debug("" +System.getProperty("user.home") + "\\AppData\\Local");
        util.debug(System.getProperty("user.dir"));
        util.checkdirectory();

        if (fetcher.checkupdate(version)) {
            updatable = true;
        }

        mainui.main(args, updatable);
    }
}