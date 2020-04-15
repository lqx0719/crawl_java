package test_jsoup;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class jiuyirencaiLogin {
	public static String getCookie(String url) {
		String cookie = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");
		httpPost.setHeader("Host","per.gz91.com");
		httpPost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader("Referer","https://per.gz91.com/login/index.html");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("person_user","18879738210"));
		params.add(new BasicNameValuePair("person_psw","lqx19960719"));
		
		httpPost.setEntity(new UrlEncodedFormEntity(params,Consts.UTF_8));
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode != 200) {
				System.out.print(statusCode);
			}
			
			Header[] headers = httpResponse.getAllHeaders();
			
			for(Header header:headers) {
				if(header.getName().contains("Set-Cookie")) {
					cookie += header.getValue() + ";";
				}
			}
			System.out.println("Cookie: " + cookie);
			return cookie;
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cookie;
	}

	public static String getData(String url,String cookie) {
		String result = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Cookie", cookie);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode != 200) {
				System.out.println("StatusCode: " + statusCode);
			}
			
			HttpEntity httpEntity = httpResponse.getEntity();
			if(httpEntity != null) {
				result = EntityUtils.toString(httpEntity,"utf-8");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
		
	}
	public static void main(String[] args) {
		String url = "https://per.gz91.com/login/index.html";
		String cookie = getCookie(url);
		String urlTarget = "https://per.gz91.com/";
		String dataResult = getData(urlTarget, cookie);
		Document doc = Jsoup.parse(dataResult);
		System.out.println("登录成功！欢迎你~~ " + doc.select(".header_right").text());


	}
}
