package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.tools.Tool;

import controller.DangNhapController;
import model.TaiKhoanModel;
import test.Test;


public class TaiKhoan extends JFrame{
	private TaiKhoanModel taiKhoanModel;
	public JTextField jTextField_tk;
	public JPasswordField jPasswordField_mk;
	public JButton jButton_dangnhap;
	public JButton jButton_dangki;
	public JCheckBox jCheckBox;

	public TaiKhoan(){
		this.taiKhoanModel = new TaiKhoanModel();
		this.init();
	}
	
	private void init() {
		this.setTitle("Quản lí thư viện");
		this.setSize(920,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		
		URL urlIcon_library = getClass().getResource("icon_library.png");
		Image imagec = Toolkit.getDefaultToolkit().createImage(urlIcon_library);
		this.setIconImage(imagec);
		
		
		
		ActionListener s = new DangNhapController(this);
		Font font = new Font("Copperplate Gothic", Font.ITALIC, 34);
		Font font_c = new Font("Corbel", Font.PLAIN, 18);
		Font font_cn = new Font("", Font.TRUETYPE_FONT, 19);
		
		JPanel jPanel_tong = new JPanel();
		JPanel jPanel_button = new JPanel();
		JLabel jLabel_vesion = new JLabel("Vesion 1.50");
		JLabel jLabel_ten = new JLabel("User Login",SwingConstants.CENTER);
		jLabel_ten.setForeground(new Color(255, 105, 180));
		jLabel_ten.setFont(font);
		JPanel jPanel_dangnhap = new JPanel();
		JLabel jLabel_tk = new JLabel("Tên tài khoản");
		jLabel_tk.setFont(font_c);
		jTextField_tk = new JTextField(10);
		jTextField_tk.setFont(font_cn);
		JLabel jLabel_mk = new JLabel("Mật khẩu");
		jLabel_mk.setFont(font_c);
		jPasswordField_mk = new JPasswordField(10);
		jPasswordField_mk.setEchoChar('*');
		jPasswordField_mk.setFont(font_cn);
		
		jPanel_tong.setLayout(new BorderLayout());
		jPanel_dangnhap.setLayout(new GridBagLayout()); 
		jPanel_tong.add(jPanel_dangnhap, BorderLayout.CENTER);
		jPanel_button.setLayout(new GridLayout(1,2,30,30));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); 

		gbc.gridx = 0;
		gbc.gridy = 0;
		
		jPanel_dangnhap.add(jLabel_tk, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		
		jPanel_dangnhap.add(jTextField_tk, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		
		jPanel_dangnhap.add(jLabel_mk, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		jPanel_dangnhap.add(jPasswordField_mk, gbc);

		
		jPanel_tong.add(jLabel_ten, BorderLayout.NORTH);
		jPanel_tong.add(jLabel_vesion,BorderLayout.SOUTH);
		JLabel image = new JLabel();
		image.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(TaiKhoan.class.getResource("Screenshot 2023-12-28 202149 (2).png"))));
		
		jButton_dangnhap = new JButton("Đăng nhập");
		jButton_dangnhap.setForeground(new Color(255, 105, 180));
		jButton_dangnhap.setFont(font_c);
		jButton_dangnhap.addActionListener(s);
		jButton_dangki= new JButton("Đăng kí ");
		jButton_dangki.setForeground(new Color(255, 105, 180));
		jButton_dangki.setFont(font_c);
		jButton_dangki.addActionListener(s);
		jCheckBox = new JCheckBox("Hiển thị mật khẩu");
		jCheckBox.addActionListener(s);
		gbc.gridx = 1;
		gbc.gridy = 2;
		jPanel_dangnhap.add(jCheckBox,gbc);
		jPanel_button.add(jButton_dangki);
		jPanel_button.add(jButton_dangnhap);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		jPanel_dangnhap.add(jPanel_button,gbc);
		
		jButton_dangnhap.setBorder(new LineBorder(Color.PINK));
		jButton_dangki.setBorder(new LineBorder(Color.PINK));
		jPanel_tong.setBorder(new LineBorder(Color.PINK));
		image.setBorder(new LineBorder(Color.PINK));
		this.setLayout(new BorderLayout());
		this.add(image,BorderLayout.EAST);
		this.add(jPanel_tong,BorderLayout.CENTER);
		
		this.setVisible(true);
		
	}
}
