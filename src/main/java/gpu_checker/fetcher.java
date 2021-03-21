package main.java.gpu_checker;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class fetcher {

    public static String sorter(Document document, String sortidf, String productid) {
        String name = "";
        String price = "";
        String avail = "";

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
                    avail = "<font color=red>" + contentElement.select("div.col-12.p-3.text-center").select("div.font-weight-bold.text-center").text() + "</font>";
                } else {
                    util.debug("" + contentElement.select("div.col-12.p-3.text-center").select("span.d-flex.justify-content-center.align-items-center").text());
                    avail = "<font color=green>" + contentElement.select("div.col-12.p-3.text-center").select("span.d-flex.justify-content-center.align-items-center").text() + "</font>";
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
        return "<html>" + name + "<br />" + price + "<br />" +  avail + "</html>";
    }

}
