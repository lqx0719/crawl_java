package test_jsoup;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class crawlPics {
	/**
     * ����ͼƬ��ָ��Ŀ¼
     *
     * @param filePath �ļ�·��
     * @param imgUrl   ͼƬURL
     */
    public static void downImages(String filePath, String imgUrl) {
    	// ��ָ���ļ���û�У����ȴ���
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // ��ȡͼƬ�ļ���
        String fileName = imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.length());

        try {
            // �ļ���������������Ļ��߿ո���������Ҫ���д������ո��ֻᱻURLEncoderת��Ϊ�Ӻ�
            String urlTail = URLEncoder.encode(fileName, "UTF-8");
            // ���Ҫ���Ӻ�ת��ΪUTF-8��ʽ��%20
            imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf('/') + 1) + urlTail.replaceAll("\\+", "\\%20");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // д����·��
        File file = new File(filePath + File.separator + fileName);

        try {
            // ��ȡͼƬURL
            URL url = new URL(imgUrl);
            // �������
            URLConnection connection = url.openConnection();
            // ����10�����Ӧʱ��
            connection.setConnectTimeout(10 * 1000);
            // ���������
            InputStream in = connection.getInputStream();
            // ��������
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            // ����������
            byte[] buf = new byte[1024];
            int size;
            // д�뵽�ļ�
            while (-1 != (size = in.read(buf))) {
                out.write(buf, 0, size);
            }
            out.close();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        // ����Jsoup�������
    	Connection connect = Jsoup.connect("https://www.sina.com.cn/");
        try {
            // �õ�Document����
            Document document = connect.get();
            // ��������img��ǩ
            Elements imgs = document.getElementsByTag("img");
            System.out.println("����⵽����ͼƬURL��");
            System.out.println("��ʼ����");
            // ����img��ǩ�����src������
            for (Element element : imgs) {
                //��ȡÿ��img��ǩURL "abs:"��ʾ����·��
                String imgSrc = element.attr("abs:src");
                // ��ӡURL
                System.out.println(imgSrc);
                //����ͼƬ������
                crawlPics.downImages("d:/img", imgSrc);
            }
            System.out.println("�������");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
