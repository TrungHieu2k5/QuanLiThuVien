package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.awt.im.InputMethodHighlight;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.beans.Statement;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.View;

import connection.JDBCUtil;

import java.sql.*;

import controller.DocGiaListener;
import controller.MuonSachListerner;
import controller.SachListener;
import controller.ThongKeController;
import model.DocGiaModel;
import model.MuonSachModel;
import model.SachModel;
import model.ThongKe;

public class ViewThuVien extends JFrame {
	public ArrayList<SachModel> list; 
	public ArrayList<DocGiaModel> lists;
	public ArrayList<MuonSachModel> listmuon;
	public SachModel sach;
	public DocGiaModel docGiaModel;
	public MuonSachModel muonSachModel;
	public ThongKe thongKe;
	public  JTable jTable1;
	public DefaultTableModel dm1;
	public JTextField jTextField_masach;
	public JTextField jTextField_tensach;
	public JTextField jTextField_tentacgia;
	public JTextField jTextField_theloai;
	public JTextField jTextField_nxb;
	public JTextField jTextField_namxb;
	public JTextField jTextField_sl;
	public JTable docgias;
	public DefaultTableModel dm2;
	public DefaultTableModel dm3;
	public JTable muon_sach;
	public JTextField jTextField_madocgia;
	public JTextField jTextField_tendocgia;
	public JTextField jTextField_ngaysinh;
	public JTextField jTextField_diachi;
	public JTextField jTextField_sdt;
	public ButtonGroup buttonGroup_gioitinh;
	public JRadioButton jRadioButton_nam;
	public JRadioButton RadioButton_nu;
	public JRadioButton jRadioButton_nu;
	public JButton jButton_timkiem;
	public JTextField jTextField_mamuonsach;
	public JTextField jTextField_madocgiamuon;
	public JTextField jTextField_masachmuon;
	public JTextField jTextField_ngaymuon;
	public JTextField jTextField_ngaytra;
	public JComboBox masach;
	public JComboBox madocgia;
	public JPanel jPanel_thongke;
	
	public ViewThuVien() {
		this.sach = new SachModel();
		this.docGiaModel = new DocGiaModel();
		this.muonSachModel = new MuonSachModel();
		docgias = new JTable();
		muon_sach = new JTable(dm3);
		this.masach = new JComboBox();
		this.madocgia = new JComboBox();
		this.thongKe = new ThongKe();
		this.init();
	}
	
	public void init() {
		this.setTitle("QUẢN LÍ THƯ VIỆN");
		this.setSize(1100, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Connection con = JDBCUtil.getConnection();
		jTable1 = new JTable();
		
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                int option = JOptionPane.showConfirmDialog(
                        ViewThuVien.this,
                        "Bạn có chắc chắn muốn thoát?",
                        "Xác nhận thoát",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                    JDBCUtil.closeConnection(con);
                } 
            }
        }); 
		     
		ActionListener acm = new SachListener(this);
		
		Font font = new Font("Arial",Font.BOLD,18);
		JPanel jPanel_west = new JPanel();
		JLabel jLabel_thuvien = new JLabel("Library");
		jLabel_thuvien.setFont(font);
		jLabel_thuvien.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("library (1).png"))));
		jPanel_west.add(jLabel_thuvien);
		
		URL urlIcon_library = ViewThuVien.class.getResource("icon_library.png");
		Image image = Toolkit.getDefaultToolkit().createImage(urlIcon_library);
		this.setIconImage(image);
		
		
		Font fontc = new Font("Constantia", Font.BOLD, 15);
		Font font_sach = new Font("", Font.PLAIN, 20);
		Font font_giam = new Font("",Font.ITALIC,15);
		
		// tạo ra các tab
		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.setFont(fontc);
		
		
// quản lí sách		
		JPanel jPanel_qlsach = new JPanel();
		JPanel jPanel_trensach = new JPanel();
		JPanel jPanel_sach = new JPanel();
		ImageIcon iconsach = new ImageIcon(ViewThuVien.class.getResource("book-stack.png"));
		jTabbedPane.addTab("Sách", iconsach ,jPanel_qlsach);
		jPanel_qlsach.setLayout(new BoxLayout(jPanel_qlsach, BoxLayout.Y_AXIS));
		jPanel_qlsach.add(jPanel_trensach);
		jPanel_trensach.setLayout(new BoxLayout(jPanel_trensach, BoxLayout.X_AXIS));
		jPanel_trensach.add(jPanel_sach);
		
		// viền 
		Border border= BorderFactory.createLineBorder(Color.BLACK);
		TitledBorder borderTitle= BorderFactory.createTitledBorder(border, "Mục lục");
		borderTitle.setTitleFont(fontc);
		jPanel_sach.setBorder(borderTitle);
		
		JLabel jLabel_masach = new JLabel("Mã sách :");
		JLabel jLabel_tensach = new JLabel("Tên sách :");
		JLabel jLabel_tentacgia = new JLabel("Tên tác giả :");
		JLabel jLabel_theloai = new JLabel("Thể loại :");
		JLabel jLabel_nxb = new JLabel("Nhà xuất bản :");
		JLabel jLabel_namxb = new JLabel("Năm xuất bản :");
		JLabel jLabel_sl = new JLabel("Số lượng :");
		jTextField_masach = new JTextField(15);
		jTextField_tensach = new JTextField(15);
		jTextField_tentacgia = new JTextField(15);
		jTextField_theloai = new JTextField(15);
		jTextField_nxb = new JTextField(15);
		jTextField_namxb = new JTextField(15);
		jTextField_sl = new JTextField(15);
		//set font
		jLabel_masach.setFont(font_sach);
		jLabel_tensach.setFont(font_sach);
		jLabel_tentacgia.setFont(font_sach);
		jLabel_theloai.setFont(font_sach);
		jLabel_nxb.setFont(font_sach);
		jLabel_namxb.setFont(font_sach);
		jLabel_sl.setFont(font_sach);
		jTextField_masach.setFont(font_giam);
		jTextField_tensach.setFont(font_giam);
		jTextField_tentacgia.setFont(font_giam);
		jTextField_theloai.setFont(font_giam);
		jTextField_nxb.setFont(font_giam);
		jTextField_namxb.setFont(font_giam);
		jTextField_sl.setFont(font_giam);
		
		
		jPanel_sach.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(10, 10, 10, 10);
		jPanel_sach.setBackground(Color.CYAN);
		g.gridx = 0;
		g.gridy = 0;
		jPanel_sach.add(jLabel_masach,g);
		g.gridx = 1;
		g.gridy = 0;
		jPanel_sach.add(jTextField_masach,g);
		g.gridx = 2;
		g.gridy = 0;
		jPanel_sach.add(jLabel_tensach,g);
		g.gridx = 3;
		g.gridy = 0;
		jPanel_sach.add(jTextField_tensach,g);
		g.gridx = 0;
		g.gridy = 1;
		jPanel_sach.add(jLabel_tentacgia,g);
		g.gridx = 1;
		g.gridy = 1;
		jPanel_sach.add(jTextField_tentacgia,g);
		g.gridx = 2;
		g.gridy = 1;
		jPanel_sach.add(jLabel_theloai,g);
		g.gridx = 3;
		g.gridy = 1;
		jPanel_sach.add(jTextField_theloai,g);
		g.gridx = 0;
		g.gridy = 2;
		jPanel_sach.add(jLabel_nxb,g);
		g.gridx = 1;
		g.gridy = 2;
		jPanel_sach.add(jTextField_nxb,g);
		g.gridx = 2;
		g.gridy = 2;
		jPanel_sach.add(jLabel_namxb,g);
		g.gridx = 3;
		g.gridy = 2;
		jPanel_sach.add(jTextField_namxb,g);
		g.gridx = 0;
		g.gridy = 3;
		jPanel_sach.add(jLabel_sl,g);
		g.gridx = 1;
		g.gridy = 3;
		jPanel_sach.add(jTextField_sl,g);
		
		
		// bảng sách 
		list = new SachModel().getListSach();
		dm1 = (DefaultTableModel) jTable1.getModel();
		String[] tungcot = {"Mã sách","Tên sách","Tên tác giả","Thể loại","Nhà xuất bản","Năm xuất bản","Số lượng"};
		dm1.setColumnIdentifiers(tungcot);
		for (SachModel sach : list) {
			dm1.addRow(new Object[] {
					sach.getMaSach(),sach.getTenSach(),sach.getTenTacGia(),sach.getTheLoai(),sach.getNhaXB(),sach.getNamXB(),sach.getSoLuong()
			});
		}
		JScrollPane sachs =new JScrollPane(jTable1);
		
		// viền danh sách
	    Border border_bangsach= BorderFactory.createLineBorder(Color.black);
		TitledBorder borderTitles = BorderFactory.createTitledBorder(border_bangsach, "Danh sách");
		borderTitles.setTitleFont(fontc);
		sachs.setBorder(borderTitles);
		jPanel_qlsach.add(sachs);
		
		JPanel jPanel_cacnutnhan = new JPanel();
		jPanel_cacnutnhan.setBackground(Color.CYAN);
		JButton jButton_catnhap = new JButton("Cập Nhật");
		jButton_catnhap.setBackground(Color.GREEN);
		jButton_catnhap.addActionListener(acm);
		jButton_catnhap.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("updated.png"))));
		JButton jButton_them = new JButton("Thêm");
		jButton_them.setBackground(Color.GREEN);
		jButton_them.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("plus (1).png"))));
		jButton_them.addActionListener(acm);
		JButton jButton_xoa = new JButton("Xóa");
		jButton_xoa.setBackground(Color.GREEN);
		jButton_xoa.addActionListener(acm);
		jButton_xoa.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("delete.png"))));
		jButton_timkiem = new JButton("Tìm kiếm");
		jButton_timkiem.setBackground(Color.GREEN);
		jButton_timkiem.addActionListener(acm);
		jButton_timkiem.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("search.png"))));
		JButton jButton_lammoi = new JButton("Refresh");
		jButton_lammoi.setBackground(Color.GREEN);
		jButton_lammoi.addActionListener(acm);
		jButton_lammoi.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("refresh-button.png"))));
		
		//viền chức năng
		Border border_cnsach = BorderFactory.createLineBorder(Color.BLACK);
		TitledBorder titledBorder_cn = BorderFactory.createTitledBorder(border_cnsach,"Chức năng");
		titledBorder_cn.setTitleFont(fontc);
		jPanel_cacnutnhan.setBorder(titledBorder_cn);
		jPanel_cacnutnhan.setLayout(new GridLayout(5, 1,5,5));
		jPanel_cacnutnhan.add(jButton_catnhap);
		jPanel_cacnutnhan.add(jButton_them);
		jPanel_cacnutnhan.add(jButton_xoa);
		jPanel_cacnutnhan.add(jButton_timkiem);
		jPanel_cacnutnhan.add(jButton_lammoi);
		jPanel_trensach.add(jPanel_cacnutnhan);
		
		
		//
		DocGiaListener giaListener = new DocGiaListener(this);
		
// doc gia
		JPanel jPanel_quanlidocgia = new JPanel();
		JPanel jPanel_docgiatren = new JPanel();
		JPanel jPanel_docgia = new JPanel();
		JPanel jPanel_buttondocgia = new JPanel();
		jPanel_quanlidocgia.setLayout(new BoxLayout(jPanel_quanlidocgia,BoxLayout.Y_AXIS));
		jPanel_docgiatren.setLayout(new BoxLayout(jPanel_docgiatren, BoxLayout.X_AXIS));
		jPanel_docgia.setBorder(borderTitle);
		jPanel_quanlidocgia.add(jPanel_docgiatren);
		jPanel_docgiatren.add(jPanel_docgia);
		jPanel_docgiatren.add(jPanel_buttondocgia);
		
		JLabel jLabel_madocgia = new JLabel("Mã độc giả");
		JLabel jLabel_tendocgia = new JLabel("Tên độc giả");
		JLabel jLabel_ngaysinh = new JLabel("Ngày sinh");
		JLabel jLabel_gioitinh = new JLabel("Giới tính");
		JLabel jLabel_diachi = new JLabel("Địa chỉ");
		JLabel jLabel_sdt = new JLabel("Số điện thoại");
		jTextField_madocgia = new JTextField(10);
		jTextField_tendocgia = new JTextField(10);
		jTextField_ngaysinh = new JTextField(10);
		jTextField_diachi = new JTextField(10);
		jTextField_sdt = new JTextField(10);
		buttonGroup_gioitinh = new ButtonGroup();
		jRadioButton_nam = new JRadioButton("Nam");
		jRadioButton_nam.setBackground(Color.CYAN);
		jRadioButton_nu = new JRadioButton("Nữ");
		jRadioButton_nu.setBackground(Color.CYAN);
		buttonGroup_gioitinh.add(jRadioButton_nam);
		buttonGroup_gioitinh.add(jRadioButton_nu);
		//set Font
		jLabel_madocgia.setFont(font_sach);
		jLabel_tendocgia.setFont(font_sach);
		jLabel_ngaysinh.setFont(font_sach);
		jLabel_gioitinh.setFont(font_sach);
		jLabel_diachi.setFont(font_sach);
		jLabel_sdt.setFont(font_sach);
		jRadioButton_nam.setFont(font_sach);
		jRadioButton_nu.setFont(font_sach);
		jTextField_madocgia.setFont(font_giam);
		jTextField_tendocgia.setFont(font_giam);
		jTextField_ngaysinh.setFont(font_giam);
		jTextField_diachi.setFont(font_giam);
		jTextField_sdt.setFont(font_giam);
		
		jPanel_docgia.setLayout(new GridBagLayout());
		GridBagConstraints gd = new GridBagConstraints();
		gd.insets = new Insets(10, 10, 5, 20);
		jPanel_docgia.setBackground(Color.CYAN);
		gd.gridx = 0;
		gd.gridy = 0;
		jPanel_docgia.add(jLabel_madocgia,gd);
		gd.gridx = 1;
		gd.gridy = 0;
		jPanel_docgia.add(jTextField_madocgia,gd);
		gd.gridx = 2;
		gd.gridy = 0;
		jPanel_docgia.add(jLabel_tendocgia,gd);
		gd.gridx = 3;
		gd.gridy = 0;
		jPanel_docgia.add(jTextField_tendocgia,gd);
		gd.gridx = 0;
		gd.gridy = 1;
		jPanel_docgia.add(jLabel_ngaysinh,gd);
		gd.gridx = 1;
		gd.gridy = 1;
		jPanel_docgia.add(jTextField_ngaysinh,gd);
		gd.gridx = 2;
		gd.gridy = 1;
		jPanel_docgia.add(jLabel_gioitinh,gd);
		gd.gridx = 3;
		gd.gridy = 1;
		jPanel_docgia.add(jRadioButton_nam,gd);
		gd.gridx = 4;
		gd.gridy = 1;
		jPanel_docgia.add(jRadioButton_nu,gd);
		gd.gridx = 0;
		gd.gridy = 2;
		jPanel_docgia.add(jLabel_diachi,gd);
		gd.gridx = 1;
		gd.gridy = 2;
		jPanel_docgia.add(jTextField_diachi,gd);
		gd.gridx = 2;
		gd.gridy = 2;
		jPanel_docgia.add(jLabel_sdt,gd);
		gd.gridx = 3;
		gd.gridy = 2;
		jPanel_docgia.add(jTextField_sdt,gd);
		
		
		
		//bảng độc giả
		lists = new DocGiaModel().getlistDocGia();
		dm2 = (DefaultTableModel) docgias.getModel();
		dm2.addColumn("Mã độc giả");
		dm2.addColumn("Tên độc giả");
		dm2.addColumn("Ngày sinh");
		dm2.addColumn("Giới tính");
		dm2.addColumn("Địa chỉ");
		dm2.addColumn("Số điện thoại");
		for(DocGiaModel docgia : lists) {
			dm2.addRow(new Object[] {
					docgia.getMaDocGia(),docgia.getTenDocgia(),docgia.getNgaysinh(),docgia.getGioitinh(),docgia.getDiaChi(),docgia.getSdt()
			});;
		}
	    JScrollPane docgia2 =new JScrollPane(docgias);
	    // add viền
	    docgia2.setBorder(borderTitles);
		jPanel_quanlidocgia.add(docgia2);
		jPanel_buttondocgia.setBorder(titledBorder_cn);
		jPanel_cacnutnhan.setBackground(Color.CYAN);
		JButton jButton_catnhap1 = new JButton("Cập Nhật");
		jButton_catnhap1.addActionListener(giaListener);
		jButton_catnhap1.setBackground(Color.GREEN);
		jButton_catnhap1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("updated.png"))));
		JButton jButton_them1 = new JButton("Thêm");
		jButton_them1.setBackground(Color.GREEN);
		jButton_them1.addActionListener(giaListener);
		jButton_them1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("plus (1).png"))));
		JButton jButton_xoa1 = new JButton("Xóa");
		jButton_xoa1.setBackground(Color.GREEN);
		jButton_xoa1.addActionListener(giaListener);
		jButton_xoa1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("delete.png"))));
		JButton jButton_lammoi1 = new JButton("Refresh");
		jButton_lammoi1.addActionListener(giaListener);
		jButton_lammoi1.setBackground(Color.GREEN);
		jButton_lammoi1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("refresh-button.png"))));
		JButton jButton_timkiem1 = new JButton("Tìm kiếm");
		jButton_timkiem1.setBackground(Color.GREEN);
		jButton_timkiem1.addActionListener(giaListener);
		jButton_timkiem1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("search.png"))));
		jPanel_buttondocgia.setLayout(new GridLayout(5,1,5,5));
		jPanel_buttondocgia.setBackground(Color.cyan);
		jPanel_buttondocgia.add(jButton_catnhap1);
		jPanel_buttondocgia.add(jButton_them1);
		jPanel_buttondocgia.add(jButton_xoa1);
		jPanel_buttondocgia.add(jButton_timkiem1);
		jPanel_buttondocgia.add(jButton_lammoi1);
		ImageIcon icondocgia = new ImageIcon(ViewThuVien.class.getResource("student.png"));
		jTabbedPane.addTab("Độc giả",icondocgia ,jPanel_quanlidocgia);
		
		
		ActionListener listerner = new MuonSachListerner(this);
// mượn sách 
		JPanel jPanel_quanlymuonsach = new JPanel();
		JPanel jPanel_muonsach = new JPanel();
		JLabel jLabel_mamuonsach = new JLabel("Mã mượn sách");
		JLabel jLabel_madocgiamuon = new JLabel("Mã độc giả");
		JLabel jLabel_masachmuon = new JLabel("Mã sách");
		JLabel jLabel_ngaymuon = new JLabel("Ngày mượn");
		JLabel jLabel_ngaytra = new JLabel("Ngày trả");
		jTextField_mamuonsach = new JTextField();
		jTextField_ngaymuon = new JTextField();
		jTextField_ngaytra = new JTextField();
		madocgia = new JComboBox();
		this.docGiaModel.getListDocGia();
		for(DocGiaModel docgia : this.docGiaModel.list) {
			madocgia.addItem(docgia.getMaDocGia()+"");
		}
		masach = new JComboBox();
		this.sach.getListSach();
		for(SachModel masachs : this.sach.list) {
			masach.addItem(masachs.getMaSach()+"");
		}
		jPanel_muonsach.setLayout(new GridLayout(1, 5, 5, 5));
		jPanel_muonsach.setBackground(Color.CYAN);
		jPanel_muonsach.add(jLabel_mamuonsach);
		jPanel_muonsach.add(jTextField_mamuonsach);
		jPanel_muonsach.add(jLabel_ngaymuon);
		jPanel_muonsach.add(jTextField_ngaymuon);
		jPanel_muonsach.add(jLabel_ngaytra);
		jPanel_muonsach.add(jTextField_ngaytra);
		jPanel_muonsach.add(jLabel_madocgiamuon);
		jPanel_muonsach.add(madocgia);
		jPanel_muonsach.add(jLabel_masachmuon);
		jPanel_muonsach.add(masach);
		
		//bảng quản lí mượn sách 
		listmuon = new MuonSachModel().getlistmuonsach();
		dm3 = (DefaultTableModel) muon_sach.getModel();
		dm3.addColumn("Mã mượn sách");
		dm3.addColumn("Ngày mượn");
		dm3.addColumn("Ngày trả");
		dm3.addColumn("Mã độc giả");
		dm3.addColumn("Mã sách");
		for(MuonSachModel muonSachModel : listmuon) {
			dm3.addRow(new Object[] {
					muonSachModel.getMaMuonSach(),muonSachModel.getNgayMuonSach(),muonSachModel.getNgayTra(),muonSachModel.getMadocgia(),muonSachModel.getMasach()
			});
		}
	    JScrollPane muon_sach2 =new JScrollPane(muon_sach);
	    
	    // add viền
		jPanel_muonsach.setBorder(borderTitle);
		muon_sach2.setBorder(borderTitles);
		
		JPanel jPanel_nutnhan = new JPanel();
		jPanel_nutnhan.setLayout(new GridLayout(5,2,10,10));
		jPanel_nutnhan.setBackground(Color.CYAN);
		JButton jButton_thems = new JButton("Mượn sách");
		jButton_thems.addActionListener(listerner);
		jButton_thems.setBackground(Color.GREEN);
		jButton_thems.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("add.png"))));
		JButton jButton_xoas = new JButton("Trả sách");
		jButton_xoas.addActionListener(listerner);
		jButton_xoas.setBackground(Color.GREEN);
		jButton_xoas.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("book-stack.png"))));
		JButton jButton_capnhats = new JButton("Cập Nhật");
		jButton_capnhats.addActionListener(listerner);
		jButton_capnhats.setBackground(Color.GREEN);
		jButton_capnhats.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("updated.png"))));
		JButton jButton_timkiem3 = new JButton("Tìm kiếm");
		jButton_timkiem3.addActionListener(listerner);
		jButton_timkiem3.setBackground(Color.GREEN);
		jButton_timkiem3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("search.png"))));
		JButton jButton_lammoi2 = new JButton("Refresh");
		jButton_lammoi2.addActionListener(listerner);
		jButton_lammoi2.setBackground(Color.GREEN);
		jButton_lammoi2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ViewThuVien.class.getResource("refresh-button.png"))));
		jPanel_nutnhan.add(jButton_thems);
		jPanel_nutnhan.add(jButton_xoas);
		jPanel_nutnhan.add(jButton_capnhats);
		jPanel_nutnhan.add(jButton_timkiem3);
		jPanel_nutnhan.add(jButton_lammoi2);
		
		// add viền
		jPanel_nutnhan.setBorder(titledBorder_cn);
		
		//bố cục 
		jPanel_quanlymuonsach.setLayout(new BorderLayout());
		jPanel_quanlymuonsach.add(jPanel_muonsach,BorderLayout.NORTH);
		jPanel_quanlymuonsach.add(muon_sach2,BorderLayout.CENTER);
		jPanel_quanlymuonsach.add(jPanel_nutnhan,BorderLayout.WEST);
		ImageIcon iconqlms = new ImageIcon(ViewThuVien.class.getResource("knowledge-management.png"));
		jTabbedPane.addTab("Quản lí mượn sách",iconqlms,jPanel_quanlymuonsach);
		
		jPanel_thongke = new JPanel();
		ImageIcon icon = new ImageIcon(ViewThuVien.class.getResource("graph.png"));
		jTabbedPane.addTab("Thống kê", icon, jPanel_thongke);
		ThongKeController controller = new ThongKeController(this);
		jTabbedPane.addMouseListener(controller);;
		
		this.setLayout(new BorderLayout());
		this.add(jPanel_west,BorderLayout.WEST);
		this.getContentPane().add(jTabbedPane,BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	
	public void xoaHangSach() {
		jTextField_masach.setText("");
		jTextField_tensach.setText("");
		jTextField_tentacgia.setText("");
		jTextField_theloai.setText("");
		jTextField_nxb.setText("");
		jTextField_namxb.setText("");
		jTextField_sl.setText("");
		int s = jTable1.getSelectedRow();
		dm1.removeRow(s);
	}
	public void capNhapSach() {
	    int select = jTable1.getSelectedRow();
	    if (select >= 0) {
	        dm1.setValueAt(jTextField_tensach.getText(), select, 1);
	        dm1.setValueAt(jTextField_tentacgia.getText(), select, 2);
	        dm1.setValueAt(jTextField_theloai.getText(), select, 3);
	        dm1.setValueAt(jTextField_nxb.getText(), select, 4);
	        dm1.setValueAt(jTextField_namxb.getText(), select, 5);
	        dm1.setValueAt(jTextField_sl.getText(), select, 6);
	        
	    } else {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để cập nhật.");
	    }}
	public void showComboxmadg() {
		
	}
	
}	