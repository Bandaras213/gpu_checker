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
        Elements contentElement;

        util.checkdirectory();

        switch (sortidf) {

            case "alternate":
                contentElement = document.select("div.card.p-3.mb-3");

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
                    product = "<html><font color=red> ERROR </font> " + "in Daten oder Seite, Manuell Prüfen oder Löschen. </html>";
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
            case "caseking":
                contentElement = document.select("#listing-4col").select("div.artbox.grid_5.last");
                stock = "";

                util.debug("" + contentElement.first().select("a.producttitles").attr("title"));
                name = contentElement.first().select("a.producttitles").attr("title");
                util.debug("" + contentElement.first().select("p.price.both").select("span.price").text());
                if (!contentElement.first().select("p.price.both").select("span.price").text().equals("")) {
                    price = contentElement.first().select("p.price.both").select("span.price").text();
                } else {
                    price = contentElement.first().select("p.pseudoprice").select("span.price").text();
                }

                util.debug("" + contentElement.first().select("span.delivery_container").select("p").attr("class"));

                switch (contentElement.first().select("span.delivery_container").select("p").attr("class")) {
                    case "deliverable1":
                        stock = "<font color=green> lagernd </font>";
                        break;
                    case "deliverable2":
                        if (!contentElement.first().select("span.delivery_container").select("p").attr("title").equals("")) {
                            stock = "<font color=orange> " + contentElement.first().select("span.delivery_container").select("p").attr("title") + "</font>";
                        } else {
                            stock = "<font color=orange> im Zulauf </font>";
                        }
                        break;
                    case "deliverable3":
                        stock = "<font color=blue> unbekannt </font>";
                        break;
                    case "deliverable4":
                        stock = "<font color=gray> unbekannt </font>";
                        break;
                    case "deliverable5":
                        stock = "<font color=cyan> individuell </font>";
                        break;
                }

                price = price.substring(0, price.indexOf("€") - 1);

                if (name.length() == 0 || price.length() == 0 || stock.length() == 0) {
                    product = "<html><font color=red> ERROR </font> " + "in Daten oder Seite, Manuell Prüfen oder Löschen. </html>";
                } else {
                    product = "<html>" + name + "<br />" + "€ " + price + "<br />" +  stock + "</html>";
                }
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
            Document document = Jsoup.connect("https://github.com/Bandaras213/gpu_checker/releases/latest").get();
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
