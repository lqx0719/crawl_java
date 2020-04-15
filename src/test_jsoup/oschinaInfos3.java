package test_jsoup;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class oschinaInfos3 {
	/*
	 * 下拉刷新实现翻页
	 * 
	 * */
	
	public static void downImages(String path,String imgUrl) {
		File dirFile = new File(path);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1,imgUrl.length());
		//图片名字可能包含空格，URLEncoder会转为+号，url中空格应为20%
		try {
			String urlTailString = URLEncoder.encode(fileName,"UTF-8");
			imgUrl = imgUrl.substring(0,imgUrl.lastIndexOf("/") + 1) + urlTailString.replaceAll("\\+", "\\20%");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file = new File(path+ File.separator + fileName);
		try {
			URL url = new URL(imgUrl);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setConnectTimeout(10 * 1000);
			urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			InputStream in = urlConnection.getInputStream(); 
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buffered = new byte[1024];
			int size;
			while((size = in.read(buffered))!= -1) {
				out.write(buffered,0,size);
			}
			out.close();
			in.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void processEach(Elements elements) {
		for(Element e:elements) {
			if(!e.attr("data-traceid").isEmpty()) continue;
			Elements e_infos = e.select("div");
			String title = e_infos.select("h3").text();
			String desc = e_infos.select(".description").text();
			String author = e_infos.select(".item").get(0).text();
			String date = e_infos.select(".item").get(1).text();
			String picUrl = e.select("a > img").attr("abs:src");
			
			if(picUrl!="") {
				downImages("D:/img/oschina", picUrl);
			}
			System.out.println("标题: " + title);
			System.out.println("内容: " + desc);
			System.out.println("作者: " + author);
			System.out.println("发布日期: " + date);
			System.out.println();	    	  	  							  
			 
		}
	}
	public static void main(String[] args) {
		try {
			//自动下拉换页，10页
			for(int i = 0;i<10;i++) {
				Document document = Jsoup.connect("https://www.oschina.net/news/widgets/_news_index_all_list?p="
										+ i +"&type=ajax")
									.userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0")
									.get();
				Elements elements = document.select(".news-item");
				processEach(elements);
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
