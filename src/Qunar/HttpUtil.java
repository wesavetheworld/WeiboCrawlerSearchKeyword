package Qunar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HttpUtil {
	static DefaultHttpClient client;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(client==null){
			client=new DefaultHttpClient();
		}
		try {
			HttpGet get=new HttpGet("http://trains.ctrip.com/TrainBooking/RoundTrip.aspx?from=wuhan&to=yangzhou&dayreturn=5&day=3&number=&fromCn=Œ‰∫∫&toCn=—Ô÷›");
			HttpResponse resp=client.execute(get);
			if(resp.getStatusLine().getStatusCode()==200){
				HttpEntity entity=resp.getEntity();
				String txt=EntityUtils.toString(entity, "utf-8");
				System.out.println(txt);
				parseHtml(txt);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void parseHtml(String txt){
		Document doc=Jsoup.parse(txt);
		Element target=doc.getElementById("result_table01");
		System.out.println(target);
	}
}
