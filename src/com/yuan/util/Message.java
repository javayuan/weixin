package com.yuan.util;



import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;




public class Message{
	/**

	 * �½������������յ���XML��ʽ��ת��ΪMap����

	 * @param request ��request����ͨ����������

	 * @return ����ת�����Map����

	 */

	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		
		for (Element e : list) {
			map.put(e.getName(), e.getText());	
			System.out.println("key=>"+e.getName()+"  value=> "+e.getText());	
		}
		
		ins.close();
		return map;
	}
	 

	/**

	 * ���ı���Ϣ����ת����XML��ʽ

	 * @param message �ı���Ϣ����

	 * @return ����ת�����XML��ʽ

	 */

	public static String MessageToXml (TestMessage textMessage){
		System.err.println(textMessage.getToUserName());
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
}