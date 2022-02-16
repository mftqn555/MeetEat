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
public class WSocket {
	
	private static final Set<Session> sessions 
		= Collections.synchronizedSet(new java.util.HashSet<Session>());
	
	//	onopen 이벤트가 호출 되면 실행되는 함수
	@OnOpen
	public void handleOpen(Session session)
	{
		System.out.println("클라이언트가 접속했습니다.");
		System.out.println("session id : "+session.getId());
		sessions.add(session);
	}
	
	//	onclose 이벤트가 호출 되면 실행되는 함수
	@OnClose
	public void handleClose(Session session)
	{
		System.out.println(session.getId()+" 클라이언트가 연결을 해제했습니다.");
		
		// 세션을 닫는다.
		sessions.remove(session);
	}
	
	//	onerror 이벤트가 호출 되면 실행되는 함수
	@OnError
	public void handleError(Throwable t)
	{
		t.printStackTrace();
	}
	
	//	onmessage 이벤트가 호출 되면 실행되는 함수
	@OnMessage
	public void handleMessage(String message, Session session)
	{
		//	서버가 받는다.
		System.out.print("클라이언트가 보내온 메시지 : ");
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
			//	웹 소켓에 연결되어 있는 모든 아이디를 찾는다.	
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
