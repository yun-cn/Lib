package com.library;

/**
 * inquiry:查询
 * revise:修改
 */
import javax.swing.*;

import com.model.Book;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9000664790040990091L;
	private final static int WIDTH = 988;
	private final static int HEIGHT = 410;
	
	JButton btnManage, btnSecede;
	JPanel jp0, jp1, jp2;
	JLabel tabel1,tabel2,tabel3;
	ImageIcon bg, bg1, bg2;

	public MainForm() {

		bg = new ImageIcon("image/ct.jpg");
		bg1 = new ImageIcon("image/mm.gif");
		bg2 = new ImageIcon("image/m1.gif");
		btnManage = new JButton("図書館システム");
		btnManage.addActionListener(this);
		btnSecede = new JButton("quit");
		btnSecede.addActionListener(this);
		tabel1 = new JLabel(bg);
		tabel2 = new JLabel(bg1);
		tabel3 = new JLabel(bg2);
		jp0 = new JPanel();
		jp1 = new JPanel();
		jp2 = new JPanel();
		
		jp1.add(tabel3);
		jp1.add(btnManage);
		jp1.add(btnSecede);
		jp1.add(tabel2);
		jp2.add(tabel1);
		this.add(jp1);
		this.add(jp2);
		
		bg.setImage(bg.getImage().getScaledInstance(MainForm.WIDTH,
				MainForm.HEIGHT, Image.SCALE_DEFAULT));
		
		// jb2.setContentAreaFilled(false);
		jp2.setOpaque(false);
		tabel1.setBounds(0, 0, 0, 0);
		btnManage.setMargin(new Insets(0, 0, 0, 0));
		btnSecede.setMargin(new Insets(0, 0, 0, 0));
		jp1.setBackground(Color.BLACK);
		jp2.setBackground(Color.white);
		btnManage.setFont(new Font("タイプ１", 1, 50));
		btnManage.setBackground(Color.ORANGE);
		btnSecede.setFont(new Font("タイプ２", 1, 50));
		btnSecede.setBackground(Color.ORANGE);
		btnManage.setPreferredSize(new Dimension(230, 100));
		btnSecede.setPreferredSize(new Dimension(230, 100));
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.setIconImage(new ImageIcon("image/ct.jpg").getImage());
		this.setTitle("図書館システム");
		double sw = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double sh = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setSize(1000, 700);
		int fw = this.getWidth();
		int fh = this.getHeight();
		this.setResizable(false);
		this.setLocation((int) (sw - fw) / 2, (int) (sh - fh) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnManage) {
			new Book();
			setVisible(false);
		}
		if (e.getSource() == btnSecede) {
			int option = JOptionPane.showConfirmDialog(this, "do you quit？", "プロンプト",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				this.dispose();
				new Login();
			}
		}
	}
}
