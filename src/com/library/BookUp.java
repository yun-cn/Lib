package com.library;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.model.Book;
import com.model.StuModel;

/**
 * 機能；修正
 * 
 * @author Huang
 * 
 */
public class BookUp extends Dialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6723070459343504613L;
	
	JButton btnModification, btnCancel;
	JTextField txtBookName, txtAuthor, txtPublishDate, txtISBN, txtWordsCount, txtUnitPrice,jtf7;
	JLabel lblBookName, lblAuthor, lblPublishDate, lblISBN, lblWordsCount, lblUnitPrice,lblID;
	JLabel lblContentDescription, lblAurhorDescription;
	JScrollPane jsplblContentDescription, jspEditorComment;
	JTextArea jtaContentDescription, jtaEditorComment;
	JPanel jp1, jp2, jp3, jp4, jp5;
	
	public BookUp(JFrame jf, String s, boolean b, StuModel sm, int rowNums) {
		super(jf, s, b);
		
		lblID=new JLabel("Id");
		jtf7=new JTextField(5);
		jtf7.setText(sm.getValueAt(rowNums, 0).toString());
		jtf7.setEditable(false);
		lblBookName = new JLabel("タイトル：");
		txtBookName = new JTextField(10);
		txtBookName.setText((String) sm.getValueAt(rowNums, 1));
		lblAuthor = new JLabel("作者：");
		txtAuthor = new JTextField(10);
		txtAuthor.setText((String) sm.getValueAt(rowNums, 2));
		lblPublishDate = new JLabel("日期：");
		txtPublishDate = new JTextField(10);
		txtPublishDate.setText((String) sm.getValueAt(rowNums, 3));
		jp1 = new JPanel(new FlowLayout(0, 11, 0));
		jp1.add(lblID);
		jp1.add(jtf7);
		jp1.add(lblBookName);
		jp1.add(txtBookName);
		jp1.add(lblAuthor);
		jp1.add(txtAuthor);
		jp1.add(lblPublishDate);
		jp1.add(txtPublishDate);
		lblISBN = new JLabel("ISBN：");
		txtISBN = new JTextField(10);
		txtISBN.setText((String) sm.getValueAt(rowNums, 4));
		lblWordsCount = new JLabel("字数：");
		txtWordsCount = new JTextField(10);
		txtWordsCount.setText(sm.getValueAt(rowNums, 5).toString());
		lblUnitPrice = new JLabel("値　段：");
		txtUnitPrice = new JTextField(10);
		txtUnitPrice.setText((String) sm.getValueAt(rowNums, 6));
		jp2 = new JPanel(new FlowLayout(0, 11, 0));
		jp2.add(lblISBN);
		jp2.add(txtISBN);
		jp2.add(lblWordsCount);
		jp2.add(txtWordsCount);
		jp2.add(lblUnitPrice);
		jp2.add(txtUnitPrice);

		lblContentDescription = new JLabel("书について：");
		jtaContentDescription = new JTextArea(10, 55);
		jtaContentDescription.setText((String) sm.getValueAt(rowNums, 7));
		jsplblContentDescription = new JScrollPane(jtaContentDescription);
		jp3 = new JPanel();
		jp3.add(lblContentDescription);
		jp3.add(jsplblContentDescription);
		lblAurhorDescription = new JLabel("作者について：");
		jtaEditorComment = new JTextArea(10, 55);
		jtaEditorComment.setText((String) sm.getValueAt(rowNums, 8));
		jspEditorComment = new JScrollPane(jtaEditorComment);
		jp4 = new JPanel();
		jp4.add(lblAurhorDescription);
		jp4.add(jspEditorComment);

		btnModification = new JButton("修正");
		btnModification.addActionListener(this);
		btnCancel = new JButton("キャンセル");
		btnCancel.addActionListener(this);
		jp5 = new JPanel(new FlowLayout(0, 150, 0));
		jp5.add(btnModification);
		jp5.add(btnCancel);

		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.add(jp5);
		// 界面制定
		this.setLayout(new FlowLayout(0, 22, 1));
		// プロパティ制定
		this.setIconImage(new ImageIcon("image/ct.jpg").getImage());
		this.setSize(800, 516);
		double sw = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double sh = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int fw = this.getWidth();
		int fh = this.getHeight();
		this.setResizable(false);
		this.setLocation((int) (sw - fw) / 2, (int) (sh - fh) / 2);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnModification) {
			
			int option = JOptionPane.showConfirmDialog(this, "修正しますか", "プロンプト",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				Connection ct = null;
				PreparedStatement ps = null;
				try {
					Class.forName("com.mysql.jdbc.Driver");
					ct = DriverManager
							.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","123456");
					String sql = "update Books set Title=?,Author=?,PublishDate=?,"
							+ "ISBN=?,WordsCount=?,UnitPrice=?,ContentDescription=?,AurhorDescription=? where Id=?";
					ps = ct.prepareStatement(sql);
					// 给问好赋值
					ps.setString(1, txtBookName.getText());
					ps.setString(2, txtAuthor.getText());
					ps.setString(3, txtPublishDate.getText());
					ps.setString(4, txtISBN.getText());
					ps.setDouble(5, Double.parseDouble(txtWordsCount.getText()));
					ps.setString(6, txtUnitPrice.getText());
					ps.setString(7, jtaContentDescription.getText());
					ps.setString(8, jtaEditorComment.getText());
					ps.setString(9, jtf7.getText());
					ps.executeUpdate();
					JOptionPane.showMessageDialog(this, "修改成功!");
					this.dispose();
					new Book();
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
	
					try {
						if (ps != null)
							ps.close();
						if (ct != null)
							ct.close();
					} catch (Exception e2) {
						
						e2.printStackTrace();
					}
				}
			}
		}
		if (e.getSource() == btnCancel) {
			int option = JOptionPane.showConfirmDialog(this,
					"まだ保存してない", "プロンプト", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				new Book();
				this.dispose();
			}
		}
	}
}
