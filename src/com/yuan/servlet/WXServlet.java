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
            if ("text".equals(msgType)) {                // 对文本消息进行处理
            	TestMessage text = new TestMessage();
                text.setFromUserName(toUserName);         // 发送和回复是反向的
                text.setToUserName(fromUserName);
                text.setMsgType("text");
                text.setCreateTime(new Date().getTime());//创建当前时间为消息时间
                
                try {
                	String stu = content.substring(0,4);
                    if(stu.equals("查电费:") || stu.equals("查电费：")){
                    	PostHttp postHttp=new PostHttp();
                    	String str = postHttp.getpost(content.substring(4,content.length()));
                    	ArrayList<String> arrayList = postHttp.regularExpression(str);
                    	text.setContent(arrayList.get(1)+" 还剩："+arrayList.get(2));
                    	}else{
                    	text.setContent("查电费格式：\n"
                    					+ "例如 9栋305 查电费 \n\n"
                    					+ "查电费:935");
                    	}
				} catch (Exception e) {
					text.setContent("查电费格式：\n"
        					+ "例如 9栋305 查电费 \n\n"
        					+ "查电费:935");
				}
                
  
                
//                text.setContent("您好，"+fromUserName+"\n\n我是："+toUserName
//                +"\n\n您发送的消息类型为："+msgType+"\n\n您发送的时间为"+Time
//                +"\n\n我回复的时间为："+CheckUtil.getThisTime()+"\n\n您发送的内容是"+content);
                
                message = Message.MessageToXml(text);
                System.out.println(message);            
            }
            out.print(message);                            // 将回应发送给微信服务器
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //接入doGet
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	 // TODO Auto-generated method stub
	 String signature = req.getParameter("signature");
	 String timestamp = req.getParameter("timestamp");
	 String nonce = req.getParameter("nonce");
	 String echostr = req.getParameter("echostr");
	 PrintWriter out  = res.getWriter();
		
	 System.out.println(signature+""+timestamp+""+nonce+""+echostr+""+out+""+"");
	 
	 
	 
	 //校验成功返回echostr
	 if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}

}
