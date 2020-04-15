package test_jsoup;

import java.io.File;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class jsoupTest {
	public static void main(String[] args) throws IOException{
		/*
		 * 从网站中提取超链接
		 * */
		
//		Connection connection = Jsoup.connect("https://www.qq.com");
//		Document doc = connection.get();
//		Elements urls = doc.getElementsByTag("a");
//		for (Element link : urls) {
//			  String linkHref = link.attr("href");
//			  String linkText = link.text();
//			  System.out.println(linkHref);
//			}
		
		/*
		 * link
		 * */
		
//		String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
//		Document doc = Jsoup.parse(html);
//		Element link = doc.select("a").first();
//		System.out.println(link);
//		String linkHref = link.attr("href");
//		System.out.println(linkHref);
//		String linkText = link.text();
//		System.out.println(linkText);
//		String outHtml = link.outerHtml();
//		System.out.println(outHtml);
//		String linkHtml = link.html();
//		System.out.println(linkHtml);
//		String text = doc.body().text();
//		System.out.println(text);
		
		Document doc = Jsoup.connect("http://www.qq.com").get();

		Element link = doc.select("a").first();
		String relHref = link.attr("href"); // == "/"
		String absHref = link.attr("abs:href"); // "http://www.open-open.com/"
		System.out.println(relHref);
		System.out.println(absHref);
		
	}
}
