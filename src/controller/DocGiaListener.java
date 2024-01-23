package controller;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import connection.JDBCUtil;
import model.DocGiaModel;
import model.SachModel;
import view.ViewThuVien;

public class DocGiaListener implements ActionListener {
	private ViewThuVien thuVien;
	private JTextField searchField;
	
	public DocGiaListener(ViewThuVien thuVien) {
		this.thuVien = thuVien;
		thuVien.docgias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				
				if(thuVien.docgias.getSelectedRow()>=0) {
					thuVien.jTextField_madocgia.setEnabled(false);
					thuVien.jTextField_madocgia.setText(thuVien.docgias.getValueAt(thuVien.docgias.getSelectedRow(),0)+"");
					thuVien.jTextField_tendocgia.setText(thuVien.docgias.getValueAt(thuVien.docgias.getSelectedRow(),1)+"");
					thuVien.jTextField_ngaysinh.setText(thuVien.docgias.getValueAt(thuVien.docgias.getSelectedRow(),2)+"");
					thuVien.jTextField_diachi.setText(thuVien.docgias.getValueAt(thuVien.docgias.getSelectedRow(),4)+"");
					thuVien.jTextField_sdt.setText(thuVien.docgias.getValueAt(thuVien.docgias.getSelectedRow(),5)+"");
			        String gioiTinh = thuVien.docgias.getValueAt(thuVien.docgias.getSelectedRow(), 3) + "";
			        if ("Nam".equals(gioiTinh)) {
			            thuVien.buttonGroup_gioitinh.setSelected(thuVien.jRadioButton_nam.getModel(), true);
			        } else if ("Nữ".equals(gioiTinh)) {
			            thuVien.buttonGroup_gioitinh.setSelected(thuVien.jRadioButton_nu.getModel(), true);
			        }				} else {
			        	thuVien.jTextField_madocgia.setEnabled(true);
			        }
			}
		});
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String str = e.getActionCommand();
		Connection con = JDBCUtil.getConnection();
		// lấy nam hoac nu
					String gioiTinh = "";
					if (thuVien.jRadioButton_nam.isSelected()) {
					    gioiTinh = thuVien.jRadioButton_nam.getText();
					} else if (thuVien.jRadioButton_nu.isSelected()) {
					    gioiTinh = thuVien.jRadioButton_nu.getText();
					}
		if(str.equals("Thêm")) {
			
			try {
				if(thuVien.jTextField_madocgia.getText().equals("")||
						thuVien.jTextField_tendocgia.getText().equals("")||
						thuVien.jTextField_ngaysinh.getText().equals("")||
						thuVien.jTextField_diachi.getText().equals("")||
						thuVien.jTextField_sdt.getText().equals("")||
						thuVien.buttonGroup_gioitinh.getSelection() == null) {
						JOptionPane.showMessageDialog(thuVien, "Bạn cần nhập dữ liệu");
				} else {
					StringBuffer st = new StringBuffer();
					PreparedStatement pr = con.prepareStatement("SELECT * FROM docgia WHERE `Mã độc giả` = '"+thuVien.jTextField_madocgia.getText()+"'");
					ResultSet rk = pr.executeQuery();
					if(rk.next()) {
						st.append("Đã tồn tại");
					}
					if(st.length()>0) {
						JOptionPane.showMessageDialog(thuVien, st.toString());
					} else {
						PreparedStatement prs = con.prepareStatement("INSERT INTO docgia (`Mã độc giả`, `Tên độc giả`, `Ngày sinh`, `Giới tính`, `Địa chỉ`, `Số điện thoại`)"
					            + "VALUES (?, ?, ?, ?, ?, ?)");
						prs.setString(1, thuVien.jTextField_madocgia.getText());
						prs.setString(2, thuVien.jTextField_tendocgia.getText());
						prs.setString(3, thuVien.jTextField_ngaysinh.getText());
						prs.setString(4, gioiTinh);
						prs.setString(5, thuVien.jTextField_diachi.getText());
						prs.setString(6, thuVien.jTextField_sdt.getText());
						int kq = prs.executeUpdate();
						
						if(kq>0) {
							JOptionPane.showMessageDialog(thuVien, "Thêm mới thành công!");
						}
					}
					thuVien.dm2.setRowCount(0);
					thuVien.docGiaModel.getListDocGia();
					for(DocGiaModel docgia : thuVien.docGiaModel.list) {
						thuVien.dm2.addRow(new Object[] {
								docgia.getMaDocGia(),docgia.getTenDocgia(),docgia.getNgaysinh(),docgia.getGioitinh(),docgia.getDiaChi(),docgia.getSdt()
						});
					}
					
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} else if (str.equals("Xóa")) {
			int row = thuVien.docgias.getSelectedRow();
			if(row>=0) {
			try {
				PreparedStatement pr = con.prepareStatement("DELETE FROM docgia WHERE `Mã độc giả` = '"+thuVien.jTextField_madocgia.getText()+"'");
				int check = JOptionPane.showConfirmDialog(thuVien, "Bạn chắc chắn xóa chứ","Thông báo",JOptionPane.YES_NO_OPTION);
				if (check == JOptionPane.YES_OPTION) {
					pr.executeUpdate();
					JOptionPane.showMessageDialog(thuVien, "Xóa thành công!");
					int s = thuVien.docgias.getSelectedRow();
					thuVien.dm2.removeRow(s);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}} else {
				JOptionPane.showMessageDialog(thuVien, "Vui lòng chọn một hàng để xóa.");
			}
		} else if(str.equals("Refresh")) {
			thuVien.jTextField_madocgia.setEditable(true);
			thuVien.jTextField_madocgia.setText("");
			thuVien.jTextField_tendocgia.setText("");
			thuVien.jTextField_ngaysinh.setText("");
			thuVien.buttonGroup_gioitinh.clearSelection();
			thuVien.jTextField_diachi.setText("");
			thuVien.jTextField_sdt.setText("");
			thuVien.dm2.setRowCount(0);
			thuVien.docGiaModel.getListDocGia();
			for(DocGiaModel docgia : thuVien.docGiaModel.list) {
				thuVien.dm2.addRow(new Object[] {
						docgia.getMaDocGia(),docgia.getTenDocgia(),docgia.getNgaysinh(),docgia.getGioitinh(),docgia.getDiaChi(),docgia.getSdt()
				});
			}
		} else if(str.equals("Cập Nhật")) {
			if(thuVien.docgias.getSelectedRow()>=0) {
				int kq = 0;
			try {
				for(int i=0;i<thuVien.docgias.getRowCount();i++) {
					PreparedStatement prs = con.prepareStatement("UPDATE docgia SET `Tên độc giả`= ?, `Ngày sinh` = ?, `Giới tính` = ? ," +
                            "`Địa chỉ` = ?, `Số điện thoại` = ? " +
                            "WHERE `Mã độc giả` = ?");
					 	prs.setString(1, thuVien.docgias.getValueAt(i, 1).toString()); 
				        prs.setString(2, thuVien.docgias.getValueAt(i, 2).toString()); 
				        prs.setString(3, thuVien.docgias.getValueAt(i, 3).toString()); 
				        prs.setString(4, thuVien.docgias.getValueAt(i, 4).toString());
				        prs.setInt(5, Integer.parseInt(thuVien.docgias.getValueAt(i, 5).toString())); 
				        prs.setInt(6, Integer.parseInt(thuVien.docgias.getValueAt(i, 0).toString())); 
				        kq = prs.executeUpdate();
				}
				if(kq>0) {
			        JOptionPane.showMessageDialog(thuVien, "Cập nhật thành công!");
			        } else {
			        	JOptionPane.showMessageDialog(thuVien, "Cập nhật thất bại");
			        }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		} else if (str.equals("Tìm kiếm")) {
			// Tạo JFrame mới để chứa trang tìm kiếm
		    JFrame searchFrame = new JFrame("Tìm kiếm");
		    URL urlIcon_search = ViewThuVien.class.getResource("search.png");
			Image image = Toolkit.getDefaultToolkit().createImage(urlIcon_search);
			searchFrame.setIconImage(image);
		    searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		    searchFrame.setLocationRelativeTo(null);
		    
		    //Font chữ
		    Font font_c = new Font("", Font.ITALIC, 20);
		    // Panel chứa nút "Tìm kiếm"
		    JPanel searchButtonPanel = new JPanel();
		    JLabel jLabel_nhap = new JLabel("Nhập từ khóa: ");
		    searchField = new JTextField(20);
		    JButton jButton_search = new JButton("Tìm kiếm");
		    jLabel_nhap.setFont(font_c);
		    searchField.setFont(font_c);
		    jButton_search.setFont(font_c);
		    searchButtonPanel.add(jLabel_nhap);
		    searchButtonPanel.add(searchField);
		    searchButtonPanel.add(jButton_search);
		    
		    // Thêm panel chứa nút "Tìm kiếm" vào JFrame
		    searchFrame.add(searchButtonPanel);
		    
		    // Hiển thị JFrame
		    searchFrame.setSize(400, 250);
		    searchFrame.setVisible(true);
		    
		    jButton_search.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				    thuVien.dm2.setRowCount(0);
				    thuVien.docGiaModel.list.clear();
				    int so = 0;
				    try {
				        if (searchField.getText().isEmpty()) {
				            so = 0;
				        } else if (searchField.getText().chars().allMatch(Character::isDigit)) {
				            so = Integer.parseInt(searchField.getText());
				        }
				        
				    	String query = "SELECT * FROM docgia " +
				                "WHERE `Mã độc giả` = ? OR " +
				                "      (`Mã độc giả` <> ? AND (" +
				                "      `Tên độc giả` LIKE ? OR " +
				                "      `Ngày sinh` LIKE ? OR " +
				                "      `Giới tính` LIKE ? OR " +
				                "      `Địa chỉ` LIKE ? OR " +
				                "      `Số điện thoại` = ?))";
				        PreparedStatement prs = con.prepareStatement(query);
				        
				        prs.setInt(1, so);
				        prs.setInt(2, so);
				        prs.setInt(7, so);
				        for (int i = 3; i <7; i++) {
				            prs.setString(i, "%" + searchField.getText() + "%");
				        }
				        ResultSet kq = prs.executeQuery();
				        while (kq.next()) {
				            DocGiaModel docGiaModel = new DocGiaModel();
				            docGiaModel.setMaDocGia(kq.getInt("Mã độc giả"));
				            docGiaModel.setTenDocgia(kq.getString("Tên độc giả"));
				            docGiaModel.setNgaysinh(kq.getString("Ngày sinh"));
				            docGiaModel.setGioitinh(kq.getString("Giới tính"));
				            docGiaModel.setDiaChi(kq.getString("Địa chỉ"));
				            docGiaModel.setSdt(kq.getInt("Số điện thoại"));
				            
				            thuVien.docGiaModel.list.add(docGiaModel);
				        }
				        for(DocGiaModel docGiaModel : thuVien.docGiaModel.list) {
				        	thuVien.dm2.addRow(new Object[] {
				        		docGiaModel.getMaDocGia(),docGiaModel.getTenDocgia(),docGiaModel.getNgaysinh(),docGiaModel.getGioitinh(),docGiaModel.getDiaChi()
				        		,docGiaModel.getSdt()
				        	});
				        }
				    } catch (Exception e2) {
				        e2.printStackTrace();
				        // TODO: handle exception
				    }
				}
			});
		}
		}
	

}
