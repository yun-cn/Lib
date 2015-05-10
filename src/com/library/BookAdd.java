package com.library;

import javax.swing.*;

import com.model.Book;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BookAdd extends Dialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4646426758340479964L;
	
	JButton btnAdd, btnCancel;
	JTextField txtBookName, txtAuthor, txtISBN, txtWordsCount, txtUnitPrice;
	JLabel lblBookName, lblAuthor, lblISBN, lblWordsCount, lblUnitPrice;
	JLabel lblContentDescription, lblAurhorDescription;
	JScrollPane jspContentDescription, jspAurhorDescription;
	JTextArea jtaContentDescription, jtaAurhorDescription;
	JPanel jp1, jp2, jp3, jp4, jp5;
	public BookAdd(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		// swing
		lblBookName = new JLabel("タイトル：");
		txtBookName = new JTextField(10);
		lblAuthor = new JLabel("作者：");
		txtAuthor = new JTextField(10);
		jp1 = new JPanel(new FlowLayout(0, 11, 0));
		jp1.add(lblBookName);
		jp1.add(txtBookName);
		jp1.add(lblAuthor);
		jp1.add(txtAuthor);

		lblISBN = new JLabel("ISBN：");
		txtISBN = new JTextField(10);
		lblWordsCount = new JLabel("字数：");
		txtWordsCount = new JTextField(10);
		lblUnitPrice = new JLabel("値　段：");
		txtUnitPrice = new JTextField(10);
		jp2 = new JPanel(new FlowLayout(0, 11, 0));
		jp2.add(lblISBN);
		jp2.add(txtISBN);
		jp2.add(lblWordsCount);
		jp2.add(txtWordsCount);
		jp2.add(lblUnitPrice);
		jp2.add(txtUnitPrice);

		lblContentDescription = new JLabel("书の　内　容：");
		jtaContentDescription = new JTextArea(10, 55);
		jspContentDescription = new JScrollPane(jtaContentDescription);
		jp3 = new JPanel();
		jp3.add(lblContentDescription);
		jp3.add(jspContentDescription);
		lblAurhorDescription = new JLabel("作者について：");
		jtaAurhorDescription = new JTextArea(10, 55);
		jspAurhorDescription = new JScrollPane(jtaAurhorDescription);
		jp4 = new JPanel();
		jp4.add(lblAurhorDescription);
		jp4.add(jspAurhorDescription);

		btnAdd = new JButton("追加");// ボダン
		btnAdd.addActionListener(this);
		btnCancel = new JButton("キャンセル");
		btnCancel.addActionListener(this);
		jp5 = new JPanel(new FlowLayout(0, 150, 0));
		jp5.add(btnAdd);
		jp5.add(btnCancel);

		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.add(jp5);
		this.setLayout(new FlowLayout(0, 22, 1));
		
		this.setIconImage(new ImageIcon("image/ct.jpg").getImage());
		this.setSize(800, 500);
		double sw = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double sh = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int fw = this.getWidth();
		int fh = this.getHeight();
		this.setLocation((int) (sw - fw) / 2, (int) (sh - fh) / 2);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {


		if (e.getSource() == btnAdd) {
			int option = JOptionPane.showConfirmDialog(this, "追加ですか？", "プロンプト",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				Connection ct = null;
				PreparedStatement ps = null;
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					ct = DriverManager
							.getConnection(
									"jdbc:sqlserver://localhost:1433;databaseName=MyBookShop",
									"123", "123456");
					
					String sql = "insert into Books(Title,Author,PublishDate,ISBN,WordsCount,"
							+ "UnitPrice,ContentDescription,AurhorDescription,PublisherId,"
							+ "CategoryId) values(?,?,GETDATE(),?,?,?,?,?,2,2)";
					ps = ct.prepareStatement(sql);
		
					ps.setString(1, txtBookName.getText());
					ps.setString(2, txtAuthor.getText());
					ps.setString(3, txtISBN.getText());
					ps.setString(4, txtWordsCount.getText());
					ps.setString(5, txtUnitPrice.getText());
					ps.setString(6, jtaContentDescription.getText());
					ps.setString(7, jtaAurhorDescription.getText());
					
					ps.executeUpdate();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				this.dispose();
				new Book();
				
			}
		}
		if (e.getSource() == btnCancel) {
			int option = JOptionPane.showConfirmDialog(this,
					"quit？", "プロンプト", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				this.dispose();
				new Book();
				
			}

		}

	}
}