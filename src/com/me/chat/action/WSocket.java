package com.me.chat.action;

import java.util.Collections;
import java.util.Set;


import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/wsocket")
public class WSocket{
	
	private static final Set<Session> sessions 
		= Collections.synchronizedSet(new java.util.HashSet<Session>());
	

	//	onopen �̺�Ʈ�� ȣ�� �Ǹ� ����Ǵ� �Լ�
	@OnOpen
	public void handleOpen(Session session)
	{
		System.out.println("Ŭ���̾�Ʈ�� �����߽��ϴ�.");
		System.out.println("session id : "+session.getId());
		sessions.add(session);
		
	}
	
	//	onclose �̺�Ʈ�� ȣ�� �Ǹ� ����Ǵ� �Լ�
	@OnClose
	public void handleClose(Session session)
	{
		System.out.println(session.getId()+" Ŭ���̾�Ʈ�� ������ �����߽��ϴ�.");
		
		// ������ �ݴ´�.
		sessions.remove(session);
	}
	
	//	onerror �̺�Ʈ�� ȣ�� �Ǹ� ����Ǵ� �Լ�
	@OnError
	public void handleError(Throwable t)
	{
		t.printStackTrace();
	}
	
	//	onmessage �̺�Ʈ�� ȣ�� �Ǹ� ����Ǵ� �Լ�
	@OnMessage
	public void handleMessage(String message, Session session)
	{
		//	������ �޴´�.
		System.out.print("Ŭ���̾�Ʈ�� ������ �޽��� : ");
		System.out.println(message);
		
		this.sendAll(session, message);
		
		try {
			final Basic basic = session.getBasicRemote();
			basic.sendText(message);
		}catch(Exception e) {}
		
	}
	
	public void sendAll(Session session, String message)
	{
		System.out.println(session.getId() + ":" +message);
		try {
			int i = 0;
			//	�� ���Ͽ� ����Ǿ� �ִ� ��� ���̵� ã�´�.	
			for (Session s : WSocket.sessions) 
			{
				System.out.println(++i);
				if (!s.getId().equals(session.getId()))
				{
					s.getBasicRemote().sendText(message);
				}
			}
		}catch(Exception e) {e.printStackTrace();}
	}
	
}