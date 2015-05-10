package com.model;

/**
 * 功能：図書館システム
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.library.BookAdd;
import com.library.BookUp;
import com.library.Login;

public class Book extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5431014376219067795L;
	Font font1 = new Font("华文彩云", Font.BOLD, 20);
	JLabel lblBookOrAuthor, lblBookDu;
	JButton btnSearch, btnAdd, btnEdit, btnDelete,btnExit;
	JTextField txtSearch;
	JTable tabel;
	JPanel jp1;
	JScrollPane jspTabel;
	DefaultTableModel model;
	StuModel sm ;
	public Book() {
		lblBookOrAuthor = new JLabel("タイトルや著者：");
		lblBookDu = new JLabel("ブック操作:");
		lblBookOrAuthor.setFont(font1);
		lblBookDu.setFont(font1);
		lblBookOrAuthor.setForeground(Color.darkGray);
		lblBookDu.setForeground(Color.darkGray);
		btnSearch = new JButton("検索");
		btnSearch.addActionListener(this);
		btnAdd = new JButton("add");
		btnAdd.addActionListener(this);
		btnEdit = new JButton("エディタ");
		btnEdit.addActionListener(this);
		btnDelete = new JButton("delete");
		btnDelete.addActionListener(this);
		 btnExit=new JButton("quit");
		 btnExit.addActionListener(this);
		txtSearch = new JTextField(10);
		jp1 = new JPanel();

		jp1.add(lblBookOrAuthor);
		jp1.add(txtSearch);
		jp1.add(btnSearch);
		jp1.add(lblBookDu);
		jp1.add(btnAdd);
		jp1.add(btnEdit);
		jp1.add(btnDelete);
		jp1.add(btnExit);
		sm= new StuModel();
		model = new DefaultTableModel(sm.rowdata, sm.columnName);
		tabel = new JTable(model);
		jspTabel = new JScrollPane(tabel);
		this.add(jspTabel);
		this.add(jp1, BorderLayout.SOUTH);
		tabel.setBackground(Color.cyan);
		jp1.setBackground(Color.orange);
		this.setIconImage(new ImageIcon("image/ct.jpg").getImage());
		this.setTitle("図書館システム界面");
		this.setSize(1000, 700);
		double sw = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double sh = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int fw = this.getWidth();
		int fh = this.getHeight();
		this.setLocation((int) (sw - fw) / 2, (int) (sh - fh) / 2);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		// 検索
		if (e.getSource() == btnSearch) {
			
			String name = this.txtSearch.getText().trim();
			String s = this.txtSearch.getText().trim();
			String sql = "select Id,Title,Author,PublishDate,ISBN,WordsCount,UnitPrice,ContentDescription,AurhorDescription from Books where Title like '%"
					+ name + "%'" + "or Author like'%" + s + "%'";
		
			StuModel sm = new StuModel(sql);
			tabel.setModel(sm);
		}
		// add
		if (e.getSource() == btnAdd) {
			new BookAdd(this, "add", true);
			StuModel sm = new StuModel();
			tabel.setModel(sm);
			setVisible(false);
		}
		// エディタ
		if (e.getSource() == btnEdit) {
			int row = this.tabel.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Please select one of the lines!");
				return;
			}
			
			
			new BookUp(this, "修正", true, sm, row);
			StuModel sm = new StuModel();
			tabel.setModel(sm);
			this.dispose();
			
		}
		// 删除
		if (e.getSource() == btnDelete) {
			Connection ct = null;
			PreparedStatement ps = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				ct = DriverManager
						.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","123456");

				int rows = tabel.getSelectedRows().length;
				if (rows == 0) {
					JOptionPane.showMessageDialog(this, "Please select delete a row!");
				} else {
					int option = JOptionPane.showConfirmDialog(this, "Do you delete",
							"プロンプト", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {

						for (int i = 0; i < rows; i++) {

							String sql1 = "alter table dbo.OrderBook nocheck constraint all ";
							String sql = sql1
									+ "delete from Books where Id='"
									+ tabel.getValueAt(tabel.getSelectedRow(), 0)
									+ "'"
									+ "alter table dbo.OrderBook check constraint all ";
							ps = ct.prepareStatement(sql);
							ps.executeUpdate();
							model.removeRow(tabel.getSelectedRow());
						}
						JOptionPane.showMessageDialog(this, "删除成功!");
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				try {

					if (ps != null)
						ps.close();
					if (ct != null)
						ct.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}

		}
		if(e.getSource()==btnExit){
			int option = JOptionPane.showConfirmDialog(this, "Do you quit ？", "プロンプト",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
			this.dispose();
			new Login();
			}
		}
		
	}

}
