package com.example.enclosedmusicshareapp;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HtmlController {
    public String getTitleFromUrl(String url){
        Connection.Response response = null;
        try {
            response = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = response.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = document.select("meta[name=title]").first().attr("content");

        if(title != title){
            return "null";
        }

        return title;
    }
}
