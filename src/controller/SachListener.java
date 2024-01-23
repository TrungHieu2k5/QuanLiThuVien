package controller;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

import connection.JDBCUtil;
import model.SachModel;
import view.ViewThuVien;

public class SachListener implements ActionListener,MouseListener{
	private ViewThuVien thuVien;
	private JTextField searchField;
	

	public SachListener(ViewThuVien thuVien) {		
		this.thuVien = thuVien;
		thuVien.jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {	
			@Override
			
			public void valueChanged(ListSelectionEvent e) {
				
				thuVien.jTextField_masach.setEnabled(false);
				if(thuVien.jTable1.getSelectedRow()>=0) {
					
					thuVien.jTextField_masach.setText(thuVien.jTable1.getValueAt(thuVien.jTable1.getSelectedRow(), 0)+"");
					thuVien.jTextField_tensach.setText(thuVien.jTable1.getValueAt(thuVien.jTable1.getSelectedRow(), 1)+"");
					thuVien.jTextField_tentacgia.setText(thuVien.jTable1.getValueAt(thuVien.jTable1.getSelectedRow(), 2)+"");
					thuVien.jTextField_theloai.setText(thuVien.jTable1.getValueAt(thuVien.jTable1.getSelectedRow(), 3)+"");
					thuVien.jTextField_nxb.setText(thuVien.jTable1.getValueAt(thuVien.jTable1.getSelectedRow(), 4)+"");
					thuVien.jTextField_namxb.setText(thuVien.jTable1.getValueAt(thuVien.jTable1.getSelectedRow(), 5)+"");
					thuVien.jTextField_sl.setText(thuVien.jTable1.getValueAt(thuVien.jTable1.getSelectedRow(), 6)+"");
				}else {
					thuVien.jTextField_masach.setEnabled(true);
				}
			} 
		}); 
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		Connection cons = JDBCUtil.getConnection();
		int ketqua = 0;
		if(str.equals("Thêm")) {
			try {
				//bẫy nhập thiếu dữ liệu
				if(thuVien.jTextField_masach.getText().equals("")||
					thuVien.jTextField_tensach.getText().equals("")||
					thuVien.jTextField_tentacgia.getText().equals("")||
					thuVien.jTextField_theloai.getText().equals("")||
					thuVien.jTextField_nxb.getText().equals("")||
					thuVien.jTextField_namxb.getText().equals("")||
					thuVien.jTextField_sl.getText().equals("")) {
					JOptionPane.showMessageDialog(thuVien, "Bạn cần nhập  đủ dữ liệu");
				} else {
					//bẫy trùng khóa chính
					StringBuffer sb = new StringBuffer();
					PreparedStatement prs = cons.prepareStatement("SELECT `Mã sách` FROM sach WHERE `Mã sách` = '"+thuVien.jTextField_masach.getText()+"'");
					ResultSet rqs = prs.executeQuery();
					if(rqs.next()) {
						sb.append("Sách đã tồn tại");
					}
					if(sb.length()>0) {
						JOptionPane.showMessageDialog(thuVien, sb.toString());
					} else {
				
				PreparedStatement pr = cons.prepareStatement("INSERT INTO sach (`Mã sách`,`Tên sách`,`Tên tác giả`,`Thể loại`,`Nhà xuất bản`,`Năm xuất bản`,`Số lượng`) "
						+ "VALUE (?,?,?,?,?,?,?)");
				pr.setString(1, thuVien.jTextField_masach.getText());
			    pr.setString(2, thuVien.jTextField_tensach.getText());
			    pr.setString(3, thuVien.jTextField_tentacgia.getText());
			    pr.setString(4, thuVien.jTextField_theloai.getText());
			    pr.setString(5, thuVien.jTextField_nxb.getText());
			    pr.setString(6, thuVien.jTextField_namxb.getText());
			    pr.setString(7, thuVien.jTextField_sl.getText());
				ketqua = pr.executeUpdate();
				thuVien.sach.getListSachs();
			    thuVien.dm1.setRowCount(0);
			    for (SachModel sach : thuVien.sach.list) {
					thuVien.dm1.addRow(new Object[] {
							sach.getMaSach(),sach.getTenSach(),sach.getTenTacGia(),sach.getTheLoai(),sach.getNhaXB(),sach.getNamXB(),sach.getSoLuong()
					});
				}
				if(ketqua>0) {
					JOptionPane.showMessageDialog(thuVien, "Thêm mới thành công!");
				}
					}
				
			
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} else if (str.equals("Xóa")) {
				int soRow = thuVien.jTable1.getSelectedRow();
				if(soRow>=0) {
				try {
					PreparedStatement pt = cons.prepareStatement("DELETE FROM sach WHERE `Mã sách`= '"+thuVien.jTextField_masach.getText()+"'");
					int chek = JOptionPane.showConfirmDialog(thuVien, "Bạn chắc chắn xóa ","Thông báo!",JOptionPane.YES_NO_OPTION);
					if (chek == JOptionPane.YES_OPTION) {
						pt.executeUpdate();
						JOptionPane.showMessageDialog(thuVien, "Xóa thành công!");
						thuVien.xoaHangSach();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				} else {
					JOptionPane.showMessageDialog(thuVien, "Vui lòng chọn một hàng để xóa.");
				}
					
		} else if(str.equals("Cập Nhật")) {
			if(thuVien.jTable1.getSelectedRow()>=0) {
				int kq = 0;
			try {
				for(int i=0;i<thuVien.jTable1.getRowCount();i++) {
					PreparedStatement prs = cons.prepareStatement("UPDATE sach SET `Tên sách`= ?, `Tên tác giả` = ?, `Thể loại` = ?, " +
                            "`Nhà xuất bản` = ?, `Năm xuất bản` = ?, `Số lượng` = ? " +
                            "WHERE `Mã sách` = ?");
					 	prs.setString(1, thuVien.jTable1.getValueAt(i, 1).toString()); 
				        prs.setString(2, thuVien.jTable1.getValueAt(i, 2).toString()); 
				        prs.setString(3, thuVien.jTable1.getValueAt(i, 3).toString()); 
				        prs.setString(4, thuVien.jTable1.getValueAt(i, 4).toString());
				        prs.setString(5, thuVien.jTable1.getValueAt(i, 5).toString()); 
				        prs.setInt(6, Integer.parseInt(thuVien.jTable1.getValueAt(i, 6).toString())); 
				        prs.setInt(7, Integer.parseInt(thuVien.jTable1.getValueAt(i, 0).toString())); 
				        kq = prs.executeUpdate();
				}
				if(kq>0) {
			        JOptionPane.showMessageDialog(thuVien, "Cập nhật thành công!");
			        
			        } else {
			        	JOptionPane.showMessageDialog(thuVien, "Cập nhật thất bại");
			        }
			} catch (Exception e2) {
				 //TODO: handle exception
			} } else {
				JOptionPane.showMessageDialog(thuVien, "Chọn ít nhất một dòng để cập nhật");
				}
				thuVien.sach.getListSachs();
			    thuVien.dm1.setRowCount(0);
			    for (SachModel sach : thuVien.sach.list) {
					thuVien.dm1.addRow(new Object[] {
							sach.getMaSach(),sach.getTenSach(),sach.getTenTacGia(),sach.getTheLoai(),sach.getNhaXB(),sach.getNamXB(),sach.getSoLuong()
					});
				}
				
		 } else if (str.equals("Refresh")) {
			 	thuVien.jTextField_masach.setEnabled(true);
			 	thuVien.jTextField_masach.setText("");
			    thuVien.jTextField_tensach.setText("");
			    thuVien.jTextField_tentacgia.setText("");
			    thuVien.jTextField_theloai.setText("");
			    thuVien.jTextField_nxb.setText("");
			    thuVien.jTextField_namxb.setText("");
			    thuVien.jTextField_sl.setText("");
			    thuVien.sach.getListSachs();
			    thuVien.dm1.setRowCount(0);
			    for (SachModel sach : thuVien.sach.list) {
					thuVien.dm1.addRow(new Object[] {
							sach.getMaSach(),sach.getTenSach(),sach.getTenTacGia(),sach.getTheLoai(),sach.getNhaXB(),sach.getNamXB(),sach.getSoLuong()
					});
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
						thuVien.dm1.setRowCount(0);
						thuVien.sach.list.clear();
						int so = 0;

						try {
						    boolean foundByMaSach = false;

						    // Kiểm tra nếu searchField là số
						    if (!searchField.getText().isEmpty() && searchField.getText().chars().allMatch(Character::isDigit)) {
						        so = Integer.parseInt(searchField.getText());

						        String queryMaSach = "SELECT * FROM sach WHERE `Mã sách` = ? OR `Số lượng` =  ? OR `Năm xuất bản` = ?";
						        PreparedStatement prsMaSach = cons.prepareStatement(queryMaSach);
						        prsMaSach.setInt(1, so);
						        prsMaSach.setInt(2, so);
						        prsMaSach.setInt(3, so);
						        ResultSet kqMaSach = prsMaSach.executeQuery();
						        
						        while (kqMaSach.next()) {
						            SachModel sach = new SachModel();
						            sach.setMaSach(kqMaSach.getInt("Mã sách"));
						            sach.setTenSach(kqMaSach.getString("Tên sách"));
						            sach.setTenTacGia(kqMaSach.getString("Tên tác giả"));
						            sach.setTheLoai(kqMaSach.getString("Thể loại"));
						            sach.setNhaXB(kqMaSach.getString("Nhà xuất bản"));
						            sach.setNamXB(kqMaSach.getInt("Năm xuất bản"));
						            sach.setSoLuong(kqMaSach.getInt("Số lượng"));
						            thuVien.sach.list.add(sach);
						        }

						        foundByMaSach = true;
						    }
						    
						    if (!foundByMaSach) {
						    	// Thêm số lượng tham số cần thiết
						    	String query = "SELECT * FROM sach WHERE `Mã sách` LIKE ? or `Tên sách` LIKE ? or `Tên tác giả` LIKE ? or `Thể loại` LIKE ? or `Nhà xuất bản` LIKE ? or `Năm xuất bản` = ? or `Số lượng` = ?";
						    	PreparedStatement prs = cons.prepareStatement(query);

						    	// set parameters
						    	prs.setInt(1, so);
						    	prs.setInt(6, so);
						    	prs.setInt(7, so);
						    	for (int i = 2; i <6; i++) {
						    	    prs.setString(i, "%" + searchField.getText() + "%");
						    	}
						        ResultSet kq = prs.executeQuery();

						        while (kq.next()) {
						            SachModel sach = new SachModel();
						            sach.setMaSach(kq.getInt("Mã sách"));
						            sach.setTenSach(kq.getString("Tên sách"));
						            sach.setTenTacGia(kq.getString("Tên tác giả"));
						            sach.setTheLoai(kq.getString("Thể loại"));
						            sach.setNhaXB(kq.getString("Nhà xuất bản"));
						            sach.setNamXB(kq.getInt("Năm xuất bản"));
						            sach.setSoLuong(kq.getInt("Số lượng"));
						            thuVien.sach.list.add(sach);
						        }
						    }

						    for (SachModel sach : thuVien.sach.list) {
						        thuVien.dm1.addRow(new Object[] {
						                sach.getMaSach(), sach.getTenSach(), sach.getTenTacGia(), sach.getTheLoai(), sach.getNhaXB(),
						                sach.getNamXB(), sach.getSoLuong()
						        });
						    }
						} catch (SQLException | NumberFormatException ex) {
						    // Xử lý ngoại lệ nếu cần thiết
						    ex.printStackTrace();
						}
					}
				});
			}
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}		
