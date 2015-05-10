package com.library;

/**
 * 機能：界面

 * 张允岭
 * 2015.3.10
 * @author Administrator
 *
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7112451517749297948L;
	private final static int WIDTH = 1000;
	private final static int HEIGHT = 650;
	private final static int bg1WIDTH = 33;
	private final static int bg1HEIGHT = 33;
	
	JButton btnCancel, btnLogin;
	JLabel lblUserName, lblPassWord, jl3, jl4;
	JTextField txtUserName;
	JPasswordField txtPassWord;
	JPanel jp1, jp5;
	ImageIcon bg, bg1;
	JTable tabel;

	public Login() {

		
		bg = new ImageIcon("image/登录.jpg");
		bg1 = new ImageIcon("image/图标.jpg");
		btnCancel = new JButton("キャンセル");
		btnCancel.addActionListener(this);
		btnLogin = new JButton("ログイン");
		btnLogin.addActionListener(this);
		
		lblUserName = new JLabel("管理员番号：");
		lblPassWord = new JLabel("暗証番号：");
		jl3 = new JLabel(bg1);
		jl4 = new JLabel(bg);
		txtUserName = new JTextField(10);
		txtPassWord = new JPasswordField(10);
		jp1 = new JPanel();
		jp5 = new JPanel();
		tabel = new JTable();
		
		jp1.add(jl3);
		jp1.add(lblUserName);
		jp1.add(txtUserName);
		jp1.add(lblPassWord);
		jp1.add(txtPassWord);
		jp1.add(btnLogin);
		jp1.add(btnCancel);
		jp5.add(jl4);
		this.add(jp1, BorderLayout.PAGE_START);
		this.add(jp5, BorderLayout.EAST);
		
		jp1.setBackground(Color.ORANGE);
		jp5.setBackground(Color.LIGHT_GRAY);
		bg.setImage(bg.getImage().getScaledInstance(Login.WIDTH, Login.HEIGHT,
				Image.SCALE_DEFAULT));
		bg1.setImage(bg1.getImage().getScaledInstance(Login.bg1WIDTH,
				Login.bg1HEIGHT, Image.SCALE_SMOOTH));
		
		this.setIconImage(new ImageIcon("image/ct.jpg").getImage());
		this.setVisible(true);
		this.setTitle("ログイン");
		this.setSize(1000, 700);
		this.setResizable(false);
		double sw = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double sh = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int fw = this.getWidth();
		int fh = this.getHeight();
		this.setLocation((int) (sw - fw) / 2, (int) (sh - fh) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnCancel) {
			setVisible(false);
			System.exit(0);// System quit
		}
		if (e.getSource() == btnLogin) {
			Connection ct = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (Exception e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(this, "データベースのロードのエラー");
			}
			try {
				ct = DriverManager
						.getConnection(
								"jdbc:sqlserver://localhost:1433;databaseName=MyBookShop",
								"123", "123456");
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "データベースのエラー");
			}
			try {
				String sSQL = "select * from Users where LoginId='"
						+ txtUserName.getText() + "' and LoginPwd='" + txtPassWord.getText()
						+ "'";
				ps = ct.prepareStatement(sSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					new MainForm();
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(this, "暗証番号エラー!");
				}
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}
		
	}
}
