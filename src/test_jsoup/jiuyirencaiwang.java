package test_jsoup;

import java.awt.SystemTray;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class jiuyirencaiwang {
	/*
	 * 手动翻页加载数据
	 * */
	
	public static void forEachdata(Elements elements) {
		for(Element element:elements){
        	String job = element.select(".zwtitle_1").text();
        	String company = element.select(".zw_2").text();
        	String place = element.select(".zw_3").text();
        	String salary = element.select(".zw_4").text();

        	System.out.println(job+ "\t" + company + "\t" + place + "\t" + salary);
        }
	}
	public static void main(String[] args) throws IOException {
        // write your code here
		for(int i = 1;i<11;i++) {
			Document doc = Jsoup.connect("https://www.gz91.com/JOBsearch/?keyword=%E4%BC%9A%E8%AE%A1&page=" + i).get();
	        Elements elements = doc.select("#tgleft > li");
	        System.out.println("当前是第 "+ i +" 页，一共有 "+elements.size()+" 条数据！！！");
	        forEachdata(elements);
	        System.out.println();
		}
        
        
    }
}
