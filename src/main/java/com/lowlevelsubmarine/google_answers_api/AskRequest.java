package com.lowlevelsubmarine.google_answers_api;

import com.lowlevelsubmarine.google_answers_api.utils.UrlUtils;
import com.sun.media.sound.InvalidDataException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.concurrent.Executor;

public class AskRequest {

    private static final String URL = "https://www.google.de/search";
    private static final String REFERER = "https://www.github.com/LowLevelSubmarine/GoogleAnswersAPI";

    private final String answer;

    public AskRequest(String question) throws IOException, ComprehensionException {
        Connection connection = Jsoup.connect(URL + "?q=" + UrlUtils.urlEncode(question));
        connection.referrer(REFERER);
        Document document = connection.get();
        Element dataElement = document.getElementsByClass("kp-blk").first();
        if (dataElement == null) {
            document.getElementsByClass("kp-wholepage");
        }
        if (dataElement == null) {
            throw new ComprehensionException();
        }
        this.answer = dataElement.getElementsByAttribute("data-attrid").first().text();
    }

    public String getAnswer() {
        return this.answer;
    }

    public static final class ComprehensionException extends Exception {}

}
