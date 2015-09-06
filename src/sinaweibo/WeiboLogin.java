package sinaweibo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WeiboLogin implements UncaughtExceptionHandler{

	/**
	 * @param args
	 */
	private static WeiboLogin login;
	public final static String KEYWORD="李银河";
	public static CloseableHttpClient client;
	public static Cookie mCookie;
	public static File f;
	public static PrintStream pstream;
	public static int type=6;
	public static int datestart=109;
	private static int pagelast=38;
	private static long pagetime=1;
	static WeiboLogin log;
	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
	public static String 
		account0="13667194526",password0="13035104409",
		account1="15172441733",password1="199106",//超妹
		account2="18202772929",password2="liupenghang",//鹏爷
		account3="670240102@qq.com",password3="02721163",
		account4="13396040309",password4="291017",
		account5="15527126279",password5="080650",//小强
		account6="13852589578",password6="52589578",
		account7="15995122782",password7="95122782",
	    account8="zxctyzx@sina.com",password8="02721163";
	
	public static String[] yeardays={"20140710","20140711","20140712","20140713","20140714","20140715","20140716","20140717","20140718","20140719","20140720","20140721","20140722","20140723","20140724","20140725","20140726","20140727","20140728","20140729","20140730","20140731",
			"20140801","20140802","20140803","20140804","20140805","20140806","20140807","20140808","20140809","20140810","20140811","20140812","20140813","20140814","20140815","20140816","20140817","20140818","20140819","20140820","20140821","20140822","20140823","20140824","20140825","20140826","20140827","20140828","20140829","20140830","20140831",
			"20140901","20140902","20140903","20140904","20140905","20140906","20140907","20140908","20140909","20140910","20140911","20140912","20140913","20140914","20140915","20140916","20140917","20140918","20140919","20140920","20140921","20140922","20140923","20140924","20140925","20140926","20140927","20140928","20140929","20140930",
			"20141001","20141002","20141003","20141004","20141005","20141006","20141007","20141008","20141009","20141010","20141011","20141012","20141013","20141014","20141015","20141016","20141017","20141018","20141019","20141020","20141021","20141022","20141023","20141024","20141025","20141026","20141027","20141028","20141029","20141030","20141031",
			"20141101","20141102","20141103","20141104","20141105","20141106","20141107","20141108","20141109","20141110","20141111","20141112","20141113","20141114","20141115","20141116","20141117","20141118","20141119","20141120","20141121","20141122","20141123","20141124","20141125","20141126","20141127","20141128","20141129","20141130",
			"20141201","20141202","20141203","20141204","20141205","20141206","20141207","20141208","20141209","20141210","20141211","20141212","20141213","20141214","20141215","20141216","20141217","20141218","20141219","20141220","20141221","20141222","20141223","20141224","20141225","20141226","20141227","20141228","20141229","20141230","20141231",
			"20150101","20150102","20150103","20150104","20150105","20150106","20150107","20150108","20150109","20150110","20150111","20150112","20150113","20150114","20150115","20150116","20150117","20150118","20150119","20150120","20150121","20150122","20150123","20150124","20150125","20150126","20150127","20150128","20150129","20150130","20150131",
			"20150201","20150202","20150203","20150204","20150205","20150206","20150207","20150208","20150209","20150210","20150211","20150212","20150213","20150214","20150215","20150216","20150217","20150218","20150219","20150220","20150221","20150222","20150223","20150224","20150225","20150226","20150227","20150228",
			"20150301","20150302","20150303","20150304","20150305","20150306","20150307","20150308","20150309","20150310","20150311","20150312","20150313","20150314","20150315","20150316","20150317","20150318","20150319","20150320","20150321","20150322","20150323","20150324","20150325","20150326","20150327","20150328","20150329","20150330","20150331",
			"20150401","20150402","20150403","20150404","20150405","20150406","20150407","20150408","20150409","20150410","20150411","20150412","20150413","20150414","20150415","20150416","20150417","20150418","20150419","20150420","20150421","20150422","20150423","20150424","20150425","20150426","20150427","20150428","20150429","20150430",
			"20150501","20150502","20150503","20150504","20150505","20150506","20150507","20150508","20150509","20150510","20150511","20150512","20150513","20150514","20150515","20150516","20150517","20150518","20150519","20150520","20150521","20150522","20150523","20150524","20150525","20150526","20150527","20150528","20150529","20150530","20150531",
			"20150601","20150602","20150603","20150604","20150605","20150606","20150607","20150608","20150609","20150610","20150611","20150612","20150613","20150614","20150615","20150616","20150617","20150618","20150619","20150620","20150621","20150622","20150623","20150624","20150625","20150626","20150627","20150628","20150629","20150630",
			"20150701","20150702","20150703","20150704","20150705","20150706","20150707","20150708","20150709"};
	
	private WeiboLogin(String filepath){
		/*
		if(client==null){
			client=new DefaultHttpClient();
			client.setRedirectHandler(new MyRedirectHandler());
		}
		*/
		f=new File(filepath);
		try {
			pstream = new PrintStream
        	        (new FileOutputStream(f,true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public WeiboLogin getInstance(){
		if(login==null){
			login=new WeiboLogin("");
		}
		return login;
	}
	
	public static void setProxy(String proxyIP,int proxyPort){
		    // TODO Auto-generated method stub
			HttpHost proxy=new HttpHost(proxyIP,proxyPort);
			client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		log=new WeiboLogin("E:\\新浪微博GIS挖掘项目\\topicbigtext\\"+KEYWORD+".txt");
		//setProxy("222.59.246.38", 8118);
		prelogin1();
		
		if(pstream!=null) pstream.close();
	}
	
	
	public static void prelogin1(){
		//HttpGet get=new HttpGet("https://login.weibo.cn/?");
		client=null;
		client=new DefaultHttpClient();
		((AbstractHttpClient) client).setRedirectHandler(new MyRedirectHandler());
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
		client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		
		HttpGet get=new HttpGet("https://weibo.cn/pub/");
		CloseableHttpResponse response=null;
		String responsetxt;
		try {
			response=client.execute(get);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entityback=response.getEntity();
				responsetxt=EntityUtils.toString(entityback, "utf-8");
				response.close();
				get.releaseConnection();
				Document doc=Jsoup.parse(responsetxt);
				Element urlbtn=doc.getElementsByClass("ut").get(0);
				Element loginbtn=urlbtn.getAllElements().get(1);
				String loginurl=loginbtn.attributes().get("href");
				
				switch (type%8) {
				case 0:
					prelogin2(loginurl,account0,password0);
					break;
				case 1:
					prelogin2(loginurl,account1,password1);
					break;
				case 2:
					prelogin2(loginurl,account2,password2);
					break;
				case 3:
					prelogin2(loginurl,account3,password3);
					break;
				case 4:
					prelogin2(loginurl,account4,password4);
					break;
				case 5:
					prelogin2(loginurl,account5,password5);
					break;
				case 6:
					prelogin2(loginurl,account6,password6);
					break;
				case 7:
					prelogin2(loginurl,account7,password7);
					break;
				default:
					System.out.println("让哥静一静");
					break;
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				Thread.sleep(500000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(datestart+","+type);
			type++;
			prelogin1();
			
		}
	}
	
	public static void prelogin2(String url,String account,String password) throws Exception{
		HttpGet get=new HttpGet(url);
		CloseableHttpResponse response=null;
		String responsetxt;
		response=client.execute(get);
		if(response.getStatusLine().getStatusCode()==200){
			HttpEntity entityback=response.getEntity();
			responsetxt=EntityUtils.toString(entityback, "utf-8");
			get.releaseConnection();
			response.close();
			Document doc=Jsoup.parse(responsetxt);
			Element form=doc.getElementsByTag("form").get(0);
			Element pwd=form.getElementsByTag("input").get(1);
			Element submit=form.getElementsByTag("input").get(7);
			Element vk=form.getElementsByTag("input").get(6);
			String pwdname=pwd.attributes().get("name");
			String vkstr=vk.attributes().get("value");
			
			String submiturl=form.attributes().get("action");
			login(account,password,pwdname,submiturl,vkstr);
		}
	}
	
	public static void login(String name,String password,String pwdname,String posturl,String vkstr)throws Exception{
		HttpPost post=new HttpPost("http://login.weibo.cn/login/"+posturl);
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("backTitle", "微博"));
		params.add(new BasicNameValuePair("backURL", "http%3A%2F%2Fweibo.cn%2F%3Ffrom%3Dhome"));
		params.add(new BasicNameValuePair("mobile", name));
		params.add(new BasicNameValuePair(pwdname, password));
		params.add(new BasicNameValuePair("submit", "登录"));
		params.add(new BasicNameValuePair("tryCount", ""));
		params.add(new BasicNameValuePair("vk", vkstr));
		CloseableHttpResponse response;
		String responsetxt="";
		UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"utf-8");
		post.setEntity(entity);
		post.setConfig(requestConfig);
		response=client.execute(post);
		if(response.getStatusLine().getStatusCode()==200){
			HttpEntity entityback=response.getEntity();
			responsetxt=EntityUtils.toString(entityback, "utf-8");
			/*
			List<Cookie> cookies=client.getCookieStore().getCookies();
			String mSESSIONID="";
	        //这里是读取指定Cookie 的值  
	        for (int i = 0; i < cookies.size(); i++) {   
	            if ("SESSIONID".equals(cookies.get(i).getName())) {   
	               mSESSIONID = cookies.get(i).getValue();  
	               break;
	            }
	        }   
			System.out.println(mSESSIONID);
			*/
			System.out.println(name+" login successfully");
			post.releaseConnection();
			response.close();
			search2fisrtpage(KEYWORD);
		}
	}
	
	public static void search2fisrtpage(String keyword) throws Exception{
		
		for(int date=datestart;date<365;date++,datestart++){
			int page;
			String k=URLEncoder.encode(keyword, "utf-8");			
			String uri="http://weibo.cn/search/mblog?hideSearchFrame=&keyword="+k+"&starttime="+yeardays[date]+"&endtime="+yeardays[date]+"&sort=time&page=1&vt=4";
			//Thread.sleep(4000);
			HttpGet get=new HttpGet(uri);
			get.setHeader("Connection","close");
			get.setConfig(requestConfig);
			CloseableHttpResponse response=client.execute(get);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entityback=response.getEntity();
				String responsetxt=EntityUtils.toString(entityback, "utf-8");
				get.releaseConnection();
				response.close();
				Document doc=Jsoup.parse(responsetxt);
				//can't find any search result
				Element title=doc.getElementsByTag("title").get(0);
				if(title!=null&&title.text().equals("微博广场")){
					throw new Exception("该账号挂了");
				}
				
			    Element result=doc.getElementsByClass("c").get(2);
			    if(result!=null){
			    	if(result.text().contains("抱歉，未找到")) continue;
			    }
			    //search
				Element pglist=doc.getElementById("pagelist");
				
				if(pglist!=null){
					String pagestring=pglist.getElementsByTag("form").get(0).getElementsByTag("div").get(0).text();
					int no=pagestring.lastIndexOf("/");
					page=Integer.parseInt(pagestring.substring(no+1, pagestring.length()-1));
					System.out.println(date+",pagenum:"+page);
					for(int i=pagelast;i<=page;i++){
						pagetime=System.currentTimeMillis();
						search2secondpage(keyword,i,date);
						pagelast++;
					}
					pagelast=1;
				}
				//can't find page number
				else{
					System.out.println(date+",1");
					search2secondpage(k, 1, date);
				}
			}
		}
		
		
	}
	
	
	
	public static void search2secondpage(String keyword,int i,int date) throws Exception{
		String k=URLEncoder.encode(keyword, "utf-8");
		String uri="http://weibo.cn/search/mblog?hideSearchFrame=&keyword="+k+"&advancedfilter=1&starttime="+yeardays[date]+"&endtime="+yeardays[date]+"&sort=time&page="+i+"&vt=4";
		HttpGet get=new HttpGet(uri);
		get.setHeader("Connection","close");
		get.setConfig(requestConfig);
		CloseableHttpResponse response=client.execute(get);
		if(response.getStatusLine().getStatusCode()==200){
			HttpEntity entityback=response.getEntity();
			String responsetxt=EntityUtils.toString(entityback, "utf-8");
			get.releaseConnection();
			response.close();
			Document doc=Jsoup.parse(responsetxt);
			Element title=doc.getElementsByTag("title").get(0);
			if(title!=null&&title.text().equals("微博广场")){
				throw new Exception("该账号挂了");
			}
			
			Elements as=doc.getElementsByClass("nk");
			System.out.println(date+","+i+",prefinished");
			for(Element a:as){
				String followerpage=a.attr("href");
				getfollowerpage(followerpage);
			}
		}
		System.out.println(date+","+i+",finished");
	}
	
	public static void getfollowerpage(String followerurl) throws Exception{
		//Thread.sleep(4000);
		HttpGet get=new HttpGet(followerurl);
		get.setHeader("Connection", "close");
		get.setConfig(requestConfig);
		CloseableHttpResponse response=client.execute(get);
		if(response.getStatusLine().getStatusCode()==200){
			HttpEntity entityback=response.getEntity();
			String responsetxt=EntityUtils.toString(entityback);
			get.releaseConnection();
			response.close();
			Document doc=Jsoup.parse(responsetxt);
			Element title=doc.getElementsByTag("title").get(0);
			if(title!=null&&title.text().equals("微博广场")){
				throw new Exception("该账号挂了");
			}
			
			Elements a=doc.getElementsByTag("a");
			for(Element as:a){
				String nexturl="http://weibo.cn"+as.attr("href");
				if(nexturl.matches("http://weibo.cn/.*/info?.*")){
					getfollowerinfo(nexturl);
					//new Thread(new PageThread(client, nexturl,pstream,log)).start();
					break;
				}
			}	
		}
	}
	
	
	
	public static void getfollowerinfo(String infourl) throws Exception{
		//Thread.sleep(4000);
		HttpGet get=new HttpGet(infourl);
		System.out.print(",c,");
		get.setHeader("Connection","close");
		RequestConfig rcfg=RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(5000).build();
		get.setConfig(rcfg);
		CloseableHttpResponse response=client.execute(get);
		System.out.print(",c1,");
		if(response.getStatusLine().getStatusCode()==200){
			HttpEntity entityback=response.getEntity();
			System.out.print(",d,");
			/*
			StringBuilder sbuilder=new StringBuilder();
			if(entityback!=null){
				InputStream input=entityback.getContent();
				BufferedReader reader=new BufferedReader(new InputStreamReader(input));
				String sline="";
				while((sline=reader.readLine())!=null){
					sbuilder.append(sline);
				}
				input.close();
				reader.close();
			}
			*/
			String responsetxt=
					EntityUtils.toString(entityback);
					//sbuilder.toString();
			System.out.print(",d1,");
			get.releaseConnection();
			response.close();
			new Thread(new PageThread(responsetxt,log,pstream)).start();
			/*
			Document doc=Jsoup.parse(responsetxt);
			
			Element title=doc.getElementsByTag("title").get(0);
			if(title!=null&&title.text().equals("微博广场")){
				throw new Exception("该账号挂了");
			}
			Element as=doc.getElementsByClass("c").get(2);
			//System.out.println(as.text());
			pstream.println(as.text());
			*/
		}
		
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO Auto-generated method stub
		t.interrupt();
		e.printStackTrace();
		try {
			Thread.sleep(500000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(datestart+","+type);
		type++;
		prelogin1();
	}
	

}
class PageThread implements Runnable{
	private String responsetxt;
	private WeiboLogin w;
	private PrintStream pstream;
	public PageThread(String txt,WeiboLogin w,PrintStream pstream) {
		// TODO Auto-generated constructor stub
		responsetxt=txt;this.w=w;this.pstream=pstream;
	}
	@Override
	public void run(){
		// TODO Auto-generated method stub
		try {
				Document doc=Jsoup.parse(responsetxt);
				
				Element title=doc.getElementsByTag("title").get(0);
				if(title!=null&&title.text().equals("微博广场")){
					throw new Exception("该账号挂了");
				}
				
				Element as=doc.getElementsByClass("c").get(2);
				pstream.println(as.text());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Thread.setDefaultUncaughtExceptionHandler(w);
		}
		
	}
}

/*
class PageThread implements Runnable{
	private HttpClient client;
	private String infourl;
	private PrintStream pstream;
	private WeiboLogin w;
	public PageThread(HttpClient client,String infourl,PrintStream pstream,WeiboLogin w) {
		// TODO Auto-generated constructor stub
		this.client=client;this.infourl=infourl;
	}
	@Override
	public void run(){
		// TODO Auto-generated method stub
		try {
			HttpGet get=new HttpGet(infourl);
			RequestConfig rcfg=RequestConfig.custom().setConnectTimeout(1000).setSocketTimeout(1000).build();
			get.setConfig(rcfg);
			HttpResponse response=client.execute(get);

			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entityback=response.getEntity();
				String responsetxt=EntityUtils.toString(entityback, "utf-8");
				Document doc=Jsoup.parse(responsetxt);
				
				Element title=doc.getElementsByTag("title").get(0);
				if(title!=null&&title.text().equals("微博广场")){
					throw new Exception("该账号挂了");
				}
				
				Element as=doc.getElementsByClass("c").get(2);
				pstream.println(as.text());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Thread.setDefaultUncaughtExceptionHandler(w);
		}
		
	}
}
*/
