package BaiduTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	/**
	 * @param args
	 */
	public static String sendGet(String url) {
		// TODO Auto-generated method stub
		//String url="http://www.baidu.com";
		InputStream input=null;
		String str;
		StringBuffer sb=new StringBuffer();
		try {
			URL realUrl=new URL(url);
			input=realUrl.openStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(input));
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	public static String RegexString(String targetString,String regexRule){
		Pattern p=Pattern.compile(regexRule);
		Matcher m=p.matcher(targetString);
		if(m.find()){
			return m.group(0);
		}
		return null;
	}
	public static void main(String[] args) {
		  // 定义即将访问的链接
		String url = "http://www.baidu.com";
		  // 访问链接并获取页面内容
		String result = sendGet(url);
		  // 使用正则匹配图片的src内容
		String imgSrc = RegexString(result, "src=\"(.+?)\"");
		  // 打印结果
		System.out.println(imgSrc);
	 }
}
