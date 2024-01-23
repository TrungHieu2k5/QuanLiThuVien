package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;

import connection.JDBCUtil;
import view.ViewThuVien;

public class SachModel {
	private int maSach;
	private String tenSach;
	private String tenTacGia;
	private String theLoai;
	private String nhaXB;
	private int namXB;
	private int soLuong;
	public ArrayList<SachModel> list;
	public SachModel() {
		this.list = new ArrayList<SachModel>();
		this.maSach = 0;
		this.tenSach = "";
		this.tenTacGia = "";
		this.theLoai = "";
		this.nhaXB = "";
		this.namXB = 0;
		this.soLuong = 0;
	}
	public int getMaSach() {
		return maSach;
	}
	public void setMaSach(int maSach) {
		this.maSach = maSach;
	}
	public String getTenSach() {
		return tenSach;
	}
	public void setTenSach(String tenSach) {
		this.tenSach = tenSach;
	}
	public String getTenTacGia() {
		return tenTacGia;
	}
	public void setTenTacGia(String tenTacGia) {
		this.tenTacGia = tenTacGia;
	}
	public String getTheLoai() {
		return theLoai;
	}
	public void setTheLoai(String theLoai) {
		this.theLoai = theLoai;
	}
	public String getNhaXB() {
		return nhaXB;
	}
	public void setNhaXB(String nhaXB) {
		this.nhaXB = nhaXB;
	}
	public int getNamXB() {
		return namXB;
	}
	public void setNamXB(int i) {
		this.namXB = i;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	
	public ArrayList<SachModel> getListSach() {
	    list = new ArrayList<SachModel>();
	    String sql = "SELECT * FROM sach";

	    try {
	        Connection con = JDBCUtil.getConnection();
	        PreparedStatement ps =  con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            SachModel sach = new SachModel();
	            sach.setMaSach(rs.getInt("Mã sách"));
	            sach.setTenSach(rs.getString("Tên sách"));
	            sach.setTenTacGia(rs.getString("Tên tác giả"));
	            sach.setTheLoai(rs.getString("Thể loại"));
	            sach.setNhaXB(rs.getString("Nhà xuất bản"));
	            sach.setNamXB(rs.getInt("Năm xuất bản"));
	            sach.setSoLuong(rs.getInt("Số lượng"));
	            list.add(sach);
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    }
		return list;
	}
	public void getListSachs() {
	    list.clear();
	    try {
	        Connection connection = JDBCUtil.getConnection();
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT * FROM sach");
	        while (resultSet.next()) {
	            SachModel sach = new SachModel();
	            sach.setMaSach(resultSet.getInt("Mã sách"));
	            sach.setTenSach(resultSet.getString("Tên sách"));
	            sach.setTenTacGia(resultSet.getString("Tên tác giả"));
	            sach.setTheLoai(resultSet.getString("Thể loại"));
	            sach.setNhaXB(resultSet.getString("Nhà xuất bản"));
	            sach.setNamXB(resultSet.getInt("Năm xuất bản"));
	            sach.setSoLuong(resultSet.getInt("Số lượng"));
	            list.add(sach);
	        }
	    } catch (SQLException e1) {
	        e1.printStackTrace();
	    }
	}
	
}
