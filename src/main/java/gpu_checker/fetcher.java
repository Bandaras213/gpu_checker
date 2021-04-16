package main.java.gpu_checker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class fetcher {

    public static String sorter(Document document, String sortidf, String productid) {
        String product = "";
        String name;
        String price;
        String stock;

        util.checkdirectory();

        switch (sortidf) {

            case "alternate":
                Elements contentElement = document.select("div.card.p-3.mb-3");

                util.debug("" + contentElement.select("div.product-name").text());
                name = contentElement.select("div.product-name").text();
                util.debug("" + contentElement.select("div.price").text().replace(" ", ""));
                price = contentElement.select("div.price").text().replace(" ", "");
                if (!contentElement.select("div.col-12.p-3.text-center").select("div.font-weight-bold.text-center").text().equals("")) {
                    util.debug("" + contentElement.select("div.col-12.p-3.text-center").select("div.font-weight-bold.text-center").text());
                    stock = "<font color=red>" + contentElement.select("div.col-12.p-3.text-center").select("div.font-weight-bold.text-center").text() + "</font>";
                } else {
                    util.debug("" + contentElement.select("div.col-12.p-3.text-center").select("span.d-flex.justify-content-center.align-items-center").text());
                    if (contentElement.select("div.col-12.p-3.text-center").select("span.d-flex.justify-content-center.align-items-center").text().contains("Lieferbar in")) {
                        stock = "<font color=orange>" + contentElement.select("div.col-12.p-3.text-center").select("span.d-flex.justify-content-center.align-items-center").text() + "</font>";
                    } else {
                        stock = "<font color=green>" + contentElement.select("div.col-12.p-3.text-center").select("span.d-flex.justify-content-center.align-items-center").text() + "</font>";
                    }
                }

                if (name.length() == 0 || price.length() == 0 || stock.length() == 0) {
                    product = "ERROR in Daten";
                } else {
                    product = "<html>" + name + "<br />" + price + "<br />" +  stock + "</html>";
                }

                /*try {
                    BufferedImage image = ImageIO.read(new URL(contentElement.select("div.col-12.col-md-6.text-center").select("meta").attr("content")));
                    ImageIO.write(image , "jpg", new File(System.getProperty("user.home") + "\\AppData\\Local\\gpu_checker\\thumbnails", productid + ".jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                break;
            case "amazon":
                break;
        }
        util.debug("" + sortidf);
        return product;
    }

    static public boolean checkupdate(String version) {
         boolean updateavail = false;
        try {
            Document document = Jsoup.connect("https://github.com/Bandaras213/gpu_checker/releases/tag/master").get();
            String latestrelease = document.select("div.f1.flex-auto.min-width-0.text-normal").text();
            if (!version.equals(latestrelease)) {
                updateavail = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateavail;
    }

}
