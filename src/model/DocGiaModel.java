package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.JDBCUtil;

public class DocGiaModel {
	private int maDocGia;
	private String tenDocgia;
	private String ngaysinh;
	private String gioitinh;
	private String diaChi;
	private int sdt;
	public ArrayList<DocGiaModel> list;
	public DocGiaModel() {
		this.list = new ArrayList<DocGiaModel>();
		this.maDocGia = 0;
		this.tenDocgia = "";
		this.ngaysinh = "";
		this.gioitinh = "";
		this.diaChi = "";
		this.sdt = 0;
	}
	public String getGioitinh() {
		return gioitinh;
	}
	public void setGioitinh(String gioitinh) {
		this.gioitinh = gioitinh;
	}
	public int getMaDocGia() {
		return maDocGia;
	}
	public void setMaDocGia(int maDocGia) {
		this.maDocGia = maDocGia;
	}
	public String getTenDocgia() {
		return tenDocgia;
	}
	public void setTenDocgia(String tenDocgia) {
		this.tenDocgia = tenDocgia;
	}
	public String getNgaysinh() {
		return ngaysinh;
	}
	public void setNgaysinh(String ngaysinh) {
		this.ngaysinh = ngaysinh;
	}
	
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public int getSdt() {
		return sdt;
	}
	public void setSdt(int sdt) {
		this.sdt = sdt;
	}
	
	public ArrayList<DocGiaModel> getlistDocGia(){
		list = new ArrayList<DocGiaModel>();
		Connection con = JDBCUtil.getConnection();
		try {
			PreparedStatement pr = con.prepareStatement("SELECT * FROM docgia");
			ResultSet kq = pr.executeQuery();
			while(kq.next()) {
				DocGiaModel docgia = new DocGiaModel();
				docgia.setMaDocGia(kq.getInt("Mã độc giả"));
				docgia.setTenDocgia(kq.getString("Tên độc giả"));
				docgia.setNgaysinh(kq.getString("Ngày sinh"));
				docgia.setGioitinh(kq.getString("Giới tính"));
				docgia.setDiaChi(kq.getString("Địa chỉ"));
				docgia.setSdt(kq.getInt("Số điện thoại"));
				list.add(docgia);
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		return list;
	}
	public void getListDocGia() {
		list.clear();
		Connection con = JDBCUtil.getConnection();
		try {
			PreparedStatement pr = con.prepareStatement("SELECT * FROM docgia");
			ResultSet kq = pr.executeQuery();
			while(kq.next()) {
				DocGiaModel docgia = new DocGiaModel();
				docgia.setMaDocGia(kq.getInt("Mã độc giả"));
				docgia.setTenDocgia(kq.getString("Tên độc giả"));
				docgia.setNgaysinh(kq.getString("Ngày sinh"));
				docgia.setGioitinh(kq.getString("Giới tính"));
				docgia.setDiaChi(kq.getString("Địa chỉ"));
				docgia.setSdt(kq.getInt("Số điện thoại"));
				list.add(docgia);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
