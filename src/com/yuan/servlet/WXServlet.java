package com.yuan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.yuan.util.CheckUtil;
import com.yuan.util.Message;
import com.yuan.util.PostHttp;
import com.yuan.util.TestMessage;

/**
 * Servlet implementation class WXServlet
 */
@WebServlet("/WXServlet")
public class WXServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WXServlet() {
        super();
       
        // TODO Auto-generated constructor stub
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            Map<String, String> map = Message.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String createTime = map.get("CreateTime");
            String Time = CheckUtil.formatTime(createTime);
            String message = null;
            if ("text".equals(msgType)) {                // ���ı���Ϣ���д���
            	TestMessage text = new TestMessage();
                text.setFromUserName(toUserName);         // ���ͺͻظ��Ƿ����
                text.setToUserName(fromUserName);
                text.setMsgType("text");
                text.setCreateTime(new Date().getTime());//������ǰʱ��Ϊ��Ϣʱ��
                
                try {
                	String stu = content.substring(0,4);
                    if(stu.equals("����:") || stu.equals("���ѣ�")){
                    	PostHttp postHttp=new PostHttp();
                    	String str = postHttp.getpost(content.substring(4,content.length()));
                    	ArrayList<String> arrayList = postHttp.regularExpression(str);
                    	text.setContent(arrayList.get(1)+" ��ʣ��"+arrayList.get(2));
                    	}else{
                    	text.setContent("���Ѹ�ʽ��\n"
                    					+ "���� 9��305 ���� \n\n"
                    					+ "����:935");
                    	}
				} catch (Exception e) {
					text.setContent("���Ѹ�ʽ��\n"
        					+ "���� 9��305 ���� \n\n"
        					+ "����:935");
				}
                
  
                
//                text.setContent("���ã�"+fromUserName+"\n\n���ǣ�"+toUserName
//                +"\n\n�����͵���Ϣ����Ϊ��"+msgType+"\n\n�����͵�ʱ��Ϊ"+Time
//                +"\n\n�һظ���ʱ��Ϊ��"+CheckUtil.getThisTime()+"\n\n�����͵�������"+content);
                
                message = Message.MessageToXml(text);
                System.out.println(message);            
            }
            out.print(message);                            // ����Ӧ���͸�΢�ŷ�����
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //����doGet
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	 // TODO Auto-generated method stub
	 String signature = req.getParameter("signature");
	 String timestamp = req.getParameter("timestamp");
	 String nonce = req.getParameter("nonce");
	 String echostr = req.getParameter("echostr");
	 PrintWriter out  = res.getWriter();
		
	 System.out.println(signature+""+timestamp+""+nonce+""+echostr+""+out+""+"");
	 
	 
	 
	 //У��ɹ�����echostr
	 if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}

}
