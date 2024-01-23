package controller;

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
import model.MuonSachModel;
import model.SachModel;
import view.ViewThuVien;

public class MuonSachListerner implements ActionListener{
	private ViewThuVien thuVien;
	private JTextField searchField;
	
	public MuonSachListerner(ViewThuVien thuVien) {
		this.thuVien = thuVien;
		thuVien.muon_sach.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int row = thuVien.muon_sach.getSelectedRow();
				thuVien.jTextField_mamuonsach.setEnabled(false);
				// TODO Auto-generated method stub
				if(thuVien.muon_sach.getSelectedRow()>=0) {
					thuVien.jTextField_mamuonsach.setText(thuVien.muon_sach.getValueAt(row, 0)+"");
					thuVien.jTextField_ngaymuon.setText(thuVien.muon_sach.getValueAt(row, 1)+"");
					thuVien.jTextField_ngaytra.setText(thuVien.muon_sach.getValueAt(row, 2)+"");
					thuVien.madocgia.setSelectedItem(thuVien.muon_sach.getValueAt(row, 3)+"");
					thuVien.masach.setSelectedItem(thuVien.muon_sach.getValueAt(row, 4)+"");
				}
				else {
					thuVien.jTextField_mamuonsach.setEnabled(true);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String str = e.getActionCommand();
		Connection con = JDBCUtil.getConnection();
		if(str.equals("Mượn sách")) {
			try {
				if(thuVien.jTextField_mamuonsach.getText().equals("")||
					thuVien.jTextField_ngaymuon.getText().equals("")||
					thuVien.jTextField_ngaytra.getText().equals("")) {
					JOptionPane.showMessageDialog(thuVien, "Bạn cần nhập đủ dữ liệu");
				} else {
					StringBuffer sb = new StringBuffer();
					PreparedStatement prs = con.prepareStatement("SELECT `Mã mượn sách` FROM muonsach WHERE `Mã mượn sách` = '"+thuVien.jTextField_mamuonsach.getText()+"'");
					ResultSet rq = prs.executeQuery();
					if(rq.next()) {
						sb.append("Đã tồn tại người mượn");
					}
					if(sb.length()>0) {
						JOptionPane.showMessageDialog(thuVien,sb.toString());
					} else {
						System.out.println((String)thuVien.masach.getSelectedItem());
						PreparedStatement pr = con.prepareStatement("INSERT INTO muonsach (`Mã mượn sách`,`Ngày mượn`,`Ngày trả`,`Mã độc giả`,`Mã sách`) "
						        + "VALUES (?,?,?,?,?)");

						pr.setString(1,thuVien.jTextField_mamuonsach.getText());
						pr.setString(2, thuVien.jTextField_ngaymuon.getText());
						pr.setString(3, thuVien.jTextField_ngaytra.getText());
						pr.setString(4, (String) thuVien.madocgia.getSelectedItem());
						pr.setString(5, (String) thuVien.masach.getSelectedItem());
				int ketqua = pr.executeUpdate();
				thuVien.dm3.addRow(new Object[] {thuVien.jTextField_mamuonsach.getText(),
					thuVien.jTextField_ngaymuon.getText(),thuVien.jTextField_ngaytra.getText(),
					thuVien.madocgia.getSelectedItem(),thuVien.masach.getSelectedItem()});
				if(ketqua>0) {
					JOptionPane.showMessageDialog(thuVien,"Mượn sách thành công!");
					PreparedStatement pt = con.prepareStatement("UPDATE sach "
							+ "SET `Số lượng` = `Số lượng` - 1 "
							+ "WHERE `Mã sách` = '"+(String)thuVien.masach.getSelectedItem()+"'");
					pt.executeUpdate();
				}
					}
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} else if (str.equals("Trả sách")) {
				int soRow = thuVien.muon_sach.getSelectedRow();
				if(soRow>=0) {
					try {
						PreparedStatement pt = con.prepareStatement("DELETE FROM muonsach WHERE `Mã mượn sách`= '"+thuVien.jTextField_mamuonsach.getText()+"'");
						int chek = JOptionPane.showConfirmDialog(thuVien, "Bạn chắc chắn trả sách ","Thông báo!",JOptionPane.YES_NO_OPTION);
						if (chek == JOptionPane.YES_OPTION) {
							pt.executeUpdate();
							JOptionPane.showMessageDialog(thuVien, "Trả sách thành công");
							thuVien.dm3.removeRow(soRow);
							PreparedStatement pst = con.prepareStatement("UPDATE sach "
									+ "SET `Số lượng` = `Số lượng` + 1 "
									+ "WHERE `Mã sách` = '"+(String)thuVien.masach.getSelectedItem()+"'");
							pst.executeUpdate();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(thuVien, "Vui lòng chọn một hàng để xóa.");
				}
			} else if(str.equals("Refresh")) {
				thuVien.jTextField_mamuonsach.setText("");
				thuVien.jTextField_ngaymuon.setText("");
				thuVien.jTextField_ngaytra.setText("");
				thuVien.masach.removeAllItems();
				thuVien.madocgia.removeAllItems();
				this.thuVien.docGiaModel.getListDocGia();
				for(DocGiaModel docgia : this.thuVien.docGiaModel.list) {
					thuVien.madocgia.addItem(docgia.getMaDocGia()+"");
				}
				this.thuVien.sach.getListSachs();
				for(SachModel masachs : this.thuVien.sach.list) {
					thuVien.masach.addItem(masachs.getMaSach()+"");
				}
				thuVien.muonSachModel.getListMuonSach();
				thuVien.dm3.setRowCount(0);
				for(MuonSachModel muonSachModel : thuVien.muonSachModel.listmuon) {
					thuVien.dm3.addRow(new Object[] {
							muonSachModel.getMaMuonSach(),muonSachModel.getNgayMuonSach(),muonSachModel.getNgayTra(),muonSachModel.getMadocgia(),muonSachModel.getMasach()
					});
				}
			} else if(str.equals("Cập Nhật")){
				if(thuVien.muon_sach.getSelectedRow()>=0) {
					int kq = 0;
				try {
					for(int i=0;i<thuVien.muon_sach.getRowCount();i++) {
						PreparedStatement prs = con.prepareStatement("UPDATE muonsach SET `Ngày mượn`= ?, `Ngày trả` = ?, " +
						        "`Mã độc giả` = ?, `Mã sách` = ? " +
						        "WHERE `Mã mượn sách` = ?");
						System.out.println("Đã cập nhật");
						prs.setString(1, thuVien.muon_sach.getValueAt(i, 1).toString()); 
						prs.setString(2, thuVien.muon_sach.getValueAt(i, 2).toString()); 
						prs.setInt(3, Integer.parseInt(thuVien.muon_sach.getValueAt(i, 3).toString())); 
						prs.setInt(4, Integer.parseInt(thuVien.muon_sach.getValueAt(i, 4).toString())); 
						prs.setInt(5, Integer.parseInt(thuVien.muon_sach.getValueAt(i, 0).toString())); 
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
					thuVien.muonSachModel.getListMuonSach();;
				    thuVien.dm3.setRowCount(0);
				    for (MuonSachModel muonsach : thuVien.muonSachModel.listmuon) {
						thuVien.dm3.addRow(new Object[] {
								muonsach.getMaMuonSach(),muonsach.getNgayMuonSach(),muonsach.getNgayTra(),muonsach.getMadocgia(),muonsach.getMasach()
						});
						
					}
			} else if(str.equals("Tìm kiếm")) {
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
						// TODO Auto-generated method stub
						thuVien.dm3.setRowCount(0);
						thuVien.muonSachModel.listmuon.clear();;
						int so = 0;

						try {
						    boolean foundByMaSach = false;

						    // Kiểm tra nếu searchField là số
						    if (!searchField.getText().isEmpty() && searchField.getText().chars().allMatch(Character::isDigit)) {
						        so = Integer.parseInt(searchField.getText());

						        String queryMh = "SELECT * FROM muonsach WHERE `Mã sách` = ? OR `Mã độc giả` =  ? OR `Mã mượn sách` = ?";
						        PreparedStatement prsMh = con.prepareStatement(queryMh);
						        prsMh.setInt(1, so);
						        prsMh.setInt(2, so);
						        prsMh.setInt(3, so);
						        ResultSet kq = prsMh.executeQuery();
						        
						        while (kq.next()) {
						            MuonSachModel mSachModel = new MuonSachModel();
						            mSachModel.setMaMuonSach(kq.getInt("Mã mượn sách"));
						            mSachModel.setNgayMuonSach(kq.getDate("Ngày mượn"));
						            mSachModel.setNgayTra(kq.getDate("Ngày trả"));
						            mSachModel.setMadocgia(kq.getInt("Mã độc giả"));
						            mSachModel.setMasach(kq.getInt("Mã sách"));
						            thuVien.muonSachModel.listmuon.add(mSachModel);
						        }

						        foundByMaSach = true;
						    }
						    
						    if (!foundByMaSach) {
						    	// Thêm số lượng tham số cần thiết
						    	String querys = "SELECT * FROM muonsach WHERE `Mã mượn sách` = ? or `Ngày mượn` LIKE ? or `Ngày trả` LIKE ? or `Mã độc giả` = ? or `Mã sách` = ?";
						    	PreparedStatement prs = con.prepareStatement(querys);

						    	// set parameters
						    	prs.setInt(1, so);
						    	prs.setInt(4, so);
						    	prs.setInt(5, so);
						    	for (int i = 2; i <4; i++) {
						    	    prs.setString(i, "%" + searchField.getText() + "%");
						    	}
						        ResultSet kq = prs.executeQuery();

						        while (kq.next()) {
						        	MuonSachModel mSachModel = new MuonSachModel();
						            mSachModel.setMaMuonSach(kq.getInt("Mã mượn sách"));
						            mSachModel.setNgayMuonSach(kq.getDate("Ngày mượn"));
						            mSachModel.setNgayTra(kq.getDate("Ngày trả"));
						            mSachModel.setMadocgia(kq.getInt("Mã độc giả"));
						            mSachModel.setMasach(kq.getInt("Mã sách"));
						            thuVien.muonSachModel.listmuon.add(mSachModel);
						        }
						    }

						    for (MuonSachModel muSachModel : thuVien.muonSachModel.listmuon) {
						        thuVien.dm3.addRow(new Object[] {
						        		muSachModel.getMaMuonSach(),muSachModel.getNgayMuonSach(),muSachModel.getNgayTra(),muSachModel.getMadocgia(),muSachModel.getMasach()
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

	
}
