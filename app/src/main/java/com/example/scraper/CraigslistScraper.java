package com.example.scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class CraigslistScraper {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";

    public static ArrayList<SaleItem> query_craigslist(String query){
        String html = getCraigslistPage(query);
        html = html.substring(html.indexOf("<div class=\"content\""), html.indexOf("<div class=\"search-legend bottom\""));
        html = html.substring(html.indexOf("<ul class=\"rows\""), html.indexOf("</ul") + 5);
        return getListItems(html);
    }

    private static ArrayList<SaleItem> getListItems(String ul) {
        ArrayList<SaleItem> items = new ArrayList<>();
        while (ul.contains("<li class=\"result-row\"")) {
            int start = ul.indexOf("<li class=\"result-row\"");
            int end = ul.indexOf("</li>") + 5;
            String substr = ul.substring(start, end);
            ul = ul.substring(end);
            items.add(getSaleItem(substr));
        }
        return items;
    }

    private static SaleItem getSaleItem(String li) {
        String price = null, description = null, imgUrl = null;
        int stpr = 0, enpr = 0, stds = 0, ends = 0, stim = 0, enim = 0;
        try {
            stpr = li.indexOf("<span class=\"result-price\">") + "<span class=\"result-price\">".length();
            enpr = li.indexOf("</span>", stpr);
            price = li.substring(stpr, enpr);
            stds = li.indexOf("class=\"result-title hdrlnk\">") + "class=\"result-title hdrlnk\">".length();
            ends = li.indexOf("</a>", li.indexOf("class=\"result-title hdrlnk\">"));
            description = li.substring(stds, ends);
            if (li.contains("data-ids=\"1:")) {
                stim = li.indexOf("data-ids=\"1:") + "data-ids=\"1:".length();
                enim = li.indexOf(",", li.indexOf("data-ids=\"1:"));
                String substr = li.substring(stim, stim + 17);
                if(substr.charAt(substr.length() - 1) == ','){
                    substr = substr.substring(0, substr.length()-2);
                }
                imgUrl = "https://images.craigslist.org/" + substr + "_600x450.jpg";
            }
        } catch (Exception e) {
            e.toString();
        }
        return new SaleItem(description, price, imgUrl);
    }

    private static String getCraigslistPage(String amazonQuery) {
        String response = "", url = null;
        try {
            url = "https://annarbor.craigslist.org/search/sss?query=" + URLEncoder.encode(amazonQuery, "UTF-8") + "&sort=rel";
        }catch (UnsupportedEncodingException e)
        {
            System.out.println(e.toString());
        }
        try {
            URL urlobj = new URL(url);
            try {
                HttpsURLConnection.setFollowRedirects(true);
                HttpsURLConnection connection = (HttpsURLConnection) urlobj.openConnection();
                connection.setRequestProperty("User-Agent", USER_AGENT);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=windows-1251");
                connection.setInstanceFollowRedirects(true);
                String redirect = connection.getHeaderField("Location");
                if(redirect != null){
                    try {
                        connection = (HttpsURLConnection) new URL(redirect).openConnection();
                    }
                    catch (Exception e)
                    {
                        System.out.println("failed redirect");
                    }
                }


                int responseCode = connection.getResponseCode();

                BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                String input;
                char c;
                StringBuffer buffer = new StringBuffer();
                while ((input = inputStream.readLine()) != null) {
                    buffer.append(input);
                }
                inputStream.close();
                response = buffer.toString();
                int x = 0;

            } catch (IOException e) {
                System.out.println("IOException upon connection attempt: " + e.toString());
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL query: " + e.toString());
        }
        return response;
    }

    public static class SaleItem {
        private String name;
        private String cost;
        private String imgUrl;

        public SaleItem(String name, String cost, String imgUrl) {
            this.name = name;
            this.cost = cost;
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

}
