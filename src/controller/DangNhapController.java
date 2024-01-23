package controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.cj.x.protobuf.MysqlxSession.AuthenticateContinue;

import connection.JDBCUtil;
import model.TaiKhoanModel;
import view.TaiKhoan;
import view.ViewThuVien;

public class DangNhapController implements ActionListener {
	private TaiKhoan taiKhoan;
	private ViewThuVien thuVien;
	
	public DangNhapController(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String str = e.getActionCommand();
		String tentk = this.taiKhoan.jTextField_tk.getText();
		String matkhau = this.taiKhoan.jPasswordField_mk.getText();
		Connection connections = JDBCUtil.getConnection();
		if(str.equals("Đăng nhập")) {
		try {
			Statement st = connections.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM quanli");
			boolean giatri = false;
			while (rs.next()) {
				String tk = rs.getString("tentk");
				String mk = rs.getString("matkhau");
				if (tentk.equals(tk)&&matkhau.equals(mk)) {
					
					JOptionPane.showMessageDialog(taiKhoan,
						    "Đăng nhập thành công"
						    );
					this.thuVien = new ViewThuVien();
					this.taiKhoan.setVisible(false);
					giatri = true;
					break;
				}
			}
			if (!giatri) {
				JOptionPane.showMessageDialog(taiKhoan,
					    "Chưa có tài khoản",
					    "Thông báo",
					    JOptionPane.WARNING_MESSAGE);
			}
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
	   }  else if (str.equals("Đăng kí ")) {
		   if(tentk.equals("")&&matkhau.equals("")) {
				JOptionPane.showMessageDialog(taiKhoan, "Bạn cần nhập dữ liệu để đăng kí");
		} else {
		   try {
			Statement sta = connections.createStatement();
			
			String res =("INSERT INTO quanli (tentk, matkhau) "
					+ "VALUES ('"+tentk+"','"+matkhau+"')");
			 sta.executeUpdate(res);
			JOptionPane.showMessageDialog(taiKhoan, "Đăng kí thành công");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	   }} else if(str.equals("Hiển thị mật khẩu")) {
		   Font font = new Font("", Font.BOLD, 20);
		   taiKhoan.jPasswordField_mk.setEchoChar(taiKhoan.jCheckBox.isSelected() ?'\0':'*');
	   }
	}
}
