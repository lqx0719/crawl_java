package test_jsoup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
public class oschinaInfos {
	public static void main(String[] args) throws IOException {
        // write your code here
        Document doc = Jsoup.connect("https://www.oschina.net/news/list").get();
        Elements elements = doc.select(".news-item > div");
        for(Element element:elements){
        	String header = element.select("h3").text();
        	String desc = element.select(".description").text();
        	Elements extras = element.select(".item");
        	String author = extras.get(0).text();
        	String date = extras.get(1).text();

        	System.out.println("title: "+header);
        	System.out.println("desc: "+desc);
        	System.out.println("author: "+author);
        	System.out.println("date: "+date); 	
        	System.out.println();
        }
    }
}
