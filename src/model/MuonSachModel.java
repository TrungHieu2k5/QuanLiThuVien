package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.JDBCUtil;

public class MuonSachModel {
	public 	int masach;
	public  int madocgia;
	private int maMuonSach;
	private Date ngayMuonSach;
	private Date ngayTra;
	public ArrayList<MuonSachModel> listmuon;
	public ArrayList<MuonSachModel> lisstsach;
	private SachModel sachModel;
	private DocGiaModel docgiamodel;
	public MuonSachModel() {
		this.listmuon = new ArrayList<MuonSachModel>();
		this.sachModel = new SachModel();
		this.docgiamodel = new DocGiaModel();
		this.maMuonSach = 0;
		this.ngayMuonSach = ngayMuonSach;
		this.ngayTra = ngayTra;
	}
	
	public int getMasach() {
		return masach;
	}

	public void setMasach(int masach) {
		this.masach = masach;
	}

	public int getMadocgia() {
		return madocgia;
	}

	public void setMadocgia(int madocgia) {
		this.madocgia = madocgia;
	}

	public Date getNgayTra() {
		return ngayTra;
	}
	public void setNgayTra(Date ngayTra) {
		this.ngayTra = ngayTra;
	}
	public int getMaMuonSach() {
		return maMuonSach;
	}
	public void setMaMuonSach(int maMuonSach) {
		this.maMuonSach = maMuonSach;
	}
	public Date getNgayMuonSach() {
		return ngayMuonSach;
	}
	public void setNgayMuonSach(Date ngayMuonSach) {
		this.ngayMuonSach = ngayMuonSach;
	}
	
	public void docGiaMuonToiDa3Cuon () {
		
	}
	
	public ArrayList<MuonSachModel> getlistmuonsach(){
		listmuon = new ArrayList<MuonSachModel>();
		Connection con = JDBCUtil.getConnection();
		try {
			PreparedStatement pr = con.prepareStatement("SELECT * FROM muonsach");
			ResultSet rk = pr.executeQuery();
			while(rk.next()) {
				MuonSachModel muonSachModel = new MuonSachModel();
				muonSachModel.setMaMuonSach(rk.getInt("Mã mượn sách"));
				muonSachModel.setNgayMuonSach(rk.getDate("Ngày mượn"));
				muonSachModel.setNgayTra(rk.getDate("Ngày trả"));
				muonSachModel.setMadocgia(rk.getInt("Mã độc giả"));
				muonSachModel.setMasach(rk.getInt("Mã sách"));
				listmuon.add(muonSachModel);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return listmuon;
	}
	public void getListMuonSach() {
		listmuon.clear();
		Connection con = JDBCUtil.getConnection();
		try {
			PreparedStatement pr = con.prepareStatement("SELECT * FROM muonsach");
			ResultSet kq = pr.executeQuery();
			while(kq.next()) {
				MuonSachModel muonSachModel = new MuonSachModel();
				muonSachModel.setMaMuonSach(kq.getInt("Mã mượn sách"));
				muonSachModel.setNgayMuonSach(kq.getDate("Ngày mượn"));
				muonSachModel.setNgayTra(kq.getDate("Ngày trả"));
				muonSachModel.setMadocgia(kq.getInt("Mã độc giả"));
				muonSachModel.setMasach(kq.getInt("Mã sách"));
				listmuon.add(muonSachModel);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getMaSach() {
		Connection con = JDBCUtil.getConnection();
		lisstsach = new ArrayList<MuonSachModel>();
		try {
			PreparedStatement prs = con.prepareStatement("SELECT `Mã sách`\r\n"
					+ "FROM sach;");
			ResultSet rs = prs.executeQuery();
			while(rs.next()) {
				MuonSachModel model = new MuonSachModel();
				model.setMasach(rs.getInt("Mã sách"));
				lisstsach.add(model);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
