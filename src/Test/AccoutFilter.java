package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class AccoutFilter {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FileInputStream input=new FileInputStream(new File("E:\\新浪微博GIS挖掘项目\\topicbigtext\\同性恋 包邮.txt"));
		BufferedReader reader=new BufferedReader(new InputStreamReader(input));
		String line,account;
		Set<String> set=new HashSet<String>();
		while((line=reader.readLine())!=null){
			account=line.split(" ")[0];
			if(set.contains(account)) continue;
			set.add(account);
		}
		System.out.println(set.size());
	}

}
