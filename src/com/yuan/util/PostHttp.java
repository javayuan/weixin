package com.yuan.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/*
 *      getpost����:ץȡ��ҳ
 *      regularExpression����:����ƥ����ҳ����
 * */

public class PostHttp {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> arrayList = new ArrayList<String>();
		PostHttp postHttp = new PostHttp();
		// System.out.println(postHttp.getpost("9304"));
		 String str = postHttp.getpost("9305");
		arrayList = postHttp.regularExpression(str);
		System.out.println(arrayList.toString());
	}

	/*
	 * ������ʽд���� ������ȡ����ҳ����������ʽ����ƥ�䣬java���н�ȡ�� ��ȡ���õ���Ϣ���ŵ�ArrayList�ﷵ��
	 */
	public ArrayList<String> regularExpression(String str) {
		Pattern pattern = Pattern.compile("<td><font color=\"#000066\">.+?</font></td>");
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> arrayList = new ArrayList<String>();
		while (matcher.find()) {
			String a = matcher.group();
			a = a.substring(26, a.length() - 12);
			// System.out.println(a);
			arrayList.add(a);
		}

		return arrayList;
	}

	/*
	 * ��������ţ���ȡ��ҳ ����һ���ַ��� ��ҳipд��
	 * 
	 */
	public String getpost(String dormitory) {

		HttpClient httpClient = new HttpClient();
		String url = "http://192.168.62.108:8089/";
		PostMethod postMethod = new PostMethod(url);
		// ������������ֵ
		NameValuePair[] data = {
				new NameValuePair("__VIEWSTATE",
						"/wEPDwULLTE1MTk0OTI1NDYPZBYCAgMPZBYCAgcPPCsAEQIADxYEHgtfIURhdGFCb3VuZGceC18hSXRlbUNvdW50AgFkDBQrAAYWCB4ETmFtZQUJ55S16KGo5Y+3HgpJc1JlYWRPbmx5aB4EVHlwZRkrAh4JRGF0YUZpZWxkBQnnlLXooajlj7cWCB8CBQbmiL/lj7cfA2gfBBkrAh8FBQbmiL/lj7cWCB8CBQzliankvZnnlLXph48fA2gfBBkpWlN5c3RlbS5Eb3VibGUsIG1zY29ybGliLCBWZXJzaW9uPTQuMC4wLjAsIEN1bHR1cmU9bmV1dHJhbCwgUHVibGljS2V5VG9rZW49Yjc3YTVjNTYxOTM0ZTA4OR8FBQzliankvZnnlLXph48WCB8CBQ/otoXotJ/ojbfmrKHmlbAfA2gfBBkrAR8FBQ/otoXotJ/ojbfmrKHmlbAWCB8CBRLmgbbmhI/otJ/ovb3mrKHmlbAfA2gfBBkrAR8FBRLmgbbmhI/otJ/ovb3mrKHmlbAWCB8CBQzmioTooajml6XmnJ8fA2gfBBkpXFN5c3RlbS5EYXRlVGltZSwgbXNjb3JsaWIsIFZlcnNpb249NC4wLjAuMCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1iNzdhNWM1NjE5MzRlMDg5HwUFDOaKhOihqOaXpeacnxYCZg9kFgQCAQ9kFgxmDw8WAh4EVGV4dAUMMDgwMTQwMDAwNDk5ZGQCAQ8PFgIfBgUEOTMwNWRkAgIPDxYCHwYFBTIxLjE1ZGQCAw8PFgIfBgUBMGRkAgQPDxYCHwYFATBkZAIFDw8WAh8GBRIyMDE5LzEwLzIyIDA6MDA6MDBkZAICDw8WAh4HVmlzaWJsZWhkZBgBBQdndnRhYmxlDzwrAAwBCAIBZOAak2NaUtqqJi98Xic0tXhJV9rABCNOGhufbTzSt6NE"),
				new NameValuePair("__EVENTVALIDATION",
						"/wEdAAgVJoORZHqwOoqeCY7+i7TF3Lxh3wSembP1YU+THxINzBYtd1nNs+dCUa7C1MaaipmsmctyreT/37SH9r5g2tYqabDp/F/r+npiTQbhE3lTP2oh/h1CCovYTk3SM/vf1kyZJojLEgLn7p8rBvOOqxpNONCg4DcJDh47moI1STXzTzohn1aO7/73Iivnow+qF/nUzED/xKr1TUrgj9Vy5Npv"),
				new NameValuePair("ddlquerytable", "2"), new NameValuePair("txthouseNo", dormitory),
				new NameValuePair("btnquery", "��ѯ") };
		// ������ֵ����postMethod��
		postMethod.setRequestBody(data);
		// ִ��postMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// HttpClient����Ҫ����ܺ�̷����������POST��PUT�Ȳ����Զ�����ת��
		// 301����302
		String str = "";
		Header locationHeader;
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// ��ͷ��ȡ��ת��ĵ�ַ
			locationHeader = postMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
				System.out.println("The page was redirected to:" + location);
			} else {
				System.err.println("Location field value is null.");
			}
			return null;
		} else {
			System.out.println(postMethod.getStatusLine());
			try {
				str = postMethod.getResponseBodyAsString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(str);

		}
		postMethod.releaseConnection();
		return str;
	}

}