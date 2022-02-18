package com.mb.board.db;

import java.sql.Timestamp;

public class BoardDTO {

	private String id; // member id �� ����
	private int bno; // �۹�ȣ  
	private int count; // ��ȸ��   
	private Timestamp write_time; // �ۼ��ð�   
	private String when_name; // �ð�		
	private String where_name; // ����/�ο���
	private String food_category; // ���� ī�װ�
	private String upload_image; // ���ε� �̹���
	private int x; //���� x  
	private int y; //�浵 y 
	private String content; // ������
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getbno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Timestamp getWrite_time() {
		return write_time;
	}
	public void setWrite_time(Timestamp write_time) {
		this.write_time = write_time;
	}
	public String getWhen_name() {
		return when_name;
	}
	public void setWhen_name(String when_name) {
		this.when_name = when_name;
	}
	public String getWhere_name() {
		return where_name;
	}
	public void setWhere_name(String where_name) {
		this.where_name = where_name;
	}
	public String getfood_category() {
		return food_category;
	}
	public void setfood_category(String food_category) {
		this.food_category = food_category;
	}
	public String getUpload_image() {
		return upload_image;
	}
	public void setUpload_image(String upload_image) {
		this.upload_image = upload_image;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "BoardDTO [id=" + id + ", bno=" + bno + ", count=" + count + ", write_time=" + write_time
				+ ", when_name=" + when_name + ", where_name=" + where_name + ", food_category=" + food_category
				+ ", upload_image=" + upload_image + ", x=" + x + ", y=" + y + ", content=" + content + "]";
	}
	


	


} // DTO end