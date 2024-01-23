package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.JDBCUtil;

public class ThongKe {
	public String ngaymuonsach;
	public int soluongmuon;
	public ArrayList<ThongKe> list;
	public ThongKe() {
		this.list = new ArrayList<ThongKe>();
		this.ngaymuonsach = "";
		this.soluongmuon = 0;
	}
	public String getNgaymuonsach() {
		return ngaymuonsach;
	}
	public void setNgaymuonsach(String ngaymuonsach) {
		this.ngaymuonsach = ngaymuonsach;
	}
	public int getSoluongmuon() {
		return soluongmuon;
	}
	public void setSoluongmuon(int soluongmuon) {
		this.soluongmuon = soluongmuon;
	}
	
	public List<ThongKe> getListByBrrow(){
		Connection cons = JDBCUtil.getConnection();
		list = new ArrayList<ThongKe>();
		try {
			PreparedStatement prs =  cons.prepareStatement("SELECT CONCAT(YEAR(`Ngày mượn`), '-', MONTH(`Ngày mượn`)) AS \"Tháng mượn sách\","
					+ "       COUNT(`Ngày mượn`) AS \"Số lượng đăng kí\""
					+ "FROM muonsach  "
					+ "GROUP BY CONCAT(YEAR(`Ngày mượn`), '-', MONTH(`Ngày mượn`));");
			ResultSet kq = prs.executeQuery();
			while(kq.next()) {
				ThongKe ke = new ThongKe();
				ke.setNgaymuonsach(kq.getString("Tháng mượn sách"));
				ke.setSoluongmuon(kq.getInt("Số lượng đăng kí"));
				list.add(ke);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
