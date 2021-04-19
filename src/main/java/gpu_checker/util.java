package main.java.gpu_checker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class util {

    static String tempdirectory = System.getProperty("user.home") + "\\AppData\\Local\\gpu_checker\\";
    static String filename;

    public static void setFilename(String filename) {
        util.filename = filename;
    }

    public static void checkdirectory() {
        File directory = new File(tempdirectory + "thumbnails");
        if (!directory.exists()) {
            boolean mkdirs = directory.mkdirs();
            try {
                File file = new File(tempdirectory + "URLs.txt");
                file.createNewFile();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        setFilename(tempdirectory + "URLs.txt");
    }

    public static void debug(String m) {
        System.out.println(m);
    }

    public static ArrayList<String> readfile() {
        ArrayList<String> list = new ArrayList<>();

        try {
        Scanner s = new Scanner(new File(filename));
        while (s.hasNext()){
            list.add(s.next());
        }
        s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<String> urlhandling(ArrayList<?> URLs) {
        ArrayList<String> productinfo = new ArrayList<>();
        int counter = 0;
        String sortidf = null;
        String productid = null;

        if (URLs.size() != 0) {
            do {
                try {
                    String urlName = (String) URLs.get(counter);

                    util.debug(urlName);

                    Document document = Jsoup.connect(urlName).get();

                    if (urlName.toLowerCase().contains("alternate")) {
                        sortidf = "alternate";
                        productid = urlName.substring(urlName.lastIndexOf("/") + 1);
                        util.debug("" + productid);
                    }

                    if (urlName.toLowerCase().contains("amazon")) {
                        sortidf = "amazon";
                    }

                    productinfo.add(fetcher.sorter(document, sortidf, productid));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                counter++;
            } while (URLs.size() != counter);
        }
        return productinfo;
    }

    public static String urlstringhandling(String gettext) {
        String productinfo = null;
        String sortidf = null;
        String productid = null;

        try {
            if (!gettext.contains("https:") || !gettext.contains("http:") || !gettext.contains("www.")) {
                productinfo = "ERROR";
                return productinfo;
            }

            util.debug(gettext);

            Document document = Jsoup.connect(gettext).get();

            if (gettext.toLowerCase().contains("alternate")) {
                sortidf = "alternate";
                productid = gettext.substring(gettext.lastIndexOf("/") + 1);
                util.debug("" + productid);
            }

            if (gettext.toLowerCase().contains("amazon")) {
                sortidf = "amazon";
            }

            productinfo = fetcher.sorter(document, sortidf, productid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productinfo;
    }

    public static boolean checkifexist(String gettext) {
        try {
            Scanner s = new Scanner(new File(filename));
            while (s.hasNext()){
                if (s.next().contains(gettext)) {
                    return true;
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addline(String gettext) {
        try {
        FileWriter fw = new FileWriter(filename, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(gettext);
        bw.newLine();
        bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeline(int selectedIndex) {
        ArrayList<String> newtext = new ArrayList<>();
        BufferedReader reader;
        int currentLine = 0;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                if (currentLine != selectedIndex) {
                    newtext.add(line);
                }
                line = reader.readLine();
                currentLine++;
            }
            reader.close();

            PrintWriter pw = new PrintWriter(filename);
            pw.close();

            try {
                FileWriter fw = new FileWriter(filename, true);
                BufferedWriter bw = new BufferedWriter(fw);
                for (String s : newtext) {
                    bw.write(s);
                    bw.newLine();
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
