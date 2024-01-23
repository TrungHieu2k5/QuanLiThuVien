package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.UIManager;
import javax.swing.text.View;
import connection.JDBCUtil;
import model.TaiKhoanModel;
import view.TaiKhoan;
import view.ViewThuVien;

public class Test {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new TaiKhoan();
			} catch (Exception ex) {
			ex.printStackTrace();
		}	
	}
}
