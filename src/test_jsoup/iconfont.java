package test_jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.MailcapCommandMap;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class iconfont {
	/*
	 * 通过ajax返回json文件实现翻页，无刷新请求
	 * */
	public static String post(String url,int pagenum) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		//设置request headers
		httpPost.setHeader("content-type","application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader(":authority","www.iconfont.cn");
		httpPost.setHeader("cookie","trace=AQAAAG0fVg48nAAA5yuldSRF4JwECPBk; ctoken=Kus0W-IuBCoWVDKwp-k1k8hK; cna=cvT6Fp81ZAACAXWyDAPGkval; isg=BAUFexZd9xFmUdNNZEl2mqdSFEE_wrlU_ffRwAdoODzdniwQxxHUJsg4qMJo2dEM");
		httpPost.setHeader("referer","https://www.iconfont.cn/search/index?searchType=icon&q=java&fromCollection=-1&page=2");
		httpPost.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");
		
		//设置request data
		ArrayList<NameValuePair> data  = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("q", "java"));
		data.add(new BasicNameValuePair("page", String.valueOf(pagenum)));
		data.add(new BasicNameValuePair("sortType", "updated_at"));
		data.add(new BasicNameValuePair("pageSize", "54"));
		data.add(new BasicNameValuePair("ctoken", "Kus0W-IuBCoWVDKwp-k1k8hK"));
		data.add(new BasicNameValuePair("t", String.valueOf(new Date().getTime())));
		
		//把data作为request entity
		httpPost.setEntity(new UrlEncodedFormEntity(data,Consts.UTF_8));
		
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode != 200) {
				System.out.println("请求状态: " + statusCode);
				return null;
			}
			HttpEntity httpEntity = httpResponse.getEntity();
			String resultString = null;
			if(httpEntity != null) {
				
				resultString = EntityUtils.toString(httpEntity, "utf-8");
			}
			EntityUtils.consume(httpEntity);
			return resultString;
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static void main(String[] args) {
		String urlString = "https://www.iconfont.cn/api/icon/search.json";
		int pagenum = 1;
		while(true) {
			String resultString = post(urlString, pagenum);
			Map<String,Object> responseData = JSON.parseObject(resultString, Map.class);
			if(responseData.isEmpty() || Integer.parseInt(responseData.get("code").toString()) != 200) {
				System.out.println("获取第  " + pagenum + "页失败！！！");
				break;
			}
			Map<String,Object> data = (Map<String, Object>) responseData.get("data");
			List<Map<String,Object>> icons = (List<Map<String, Object>>) data.get("icons");
			if(icons.isEmpty()) {
				System.out.println("检索结束，一共 " + data.get("count") + " 条数据！！！");
				break;
			}else {
				System.out.println("获取第  " + pagenum + "页数据: 一共  "+ icons.size()+" 条!!!");
				pagenum++;
			}
			
			
		}
		
		
	}
}
