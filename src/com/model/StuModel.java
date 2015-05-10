package com.model;

import java.sql.*;
import java.util.Vector;

import javax.swing.table.*;

public class StuModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -44952199646762484L;
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	Vector rowdata, columnName;

	public void init(String sql) {
		if (sql.equals("")) {
			sql = " select Id,Title,Author,PublishDate,ISBN,WordsCount,UnitPrice,ContentDescription,AurhorDescription from Books";
		}
		columnName = new Vector();
		columnName.add("ID");
		columnName.add("タイトル");// 添加列名
		columnName.add("著者");
		columnName.add("Publication Date");
		columnName.add("ISBN");
		columnName.add("数字");
		columnName.add("値段");
		columnName.add("书について");
		columnName.add("作者について");
		rowdata = new Vector( );
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ct = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","123456");
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Vector v = new Vector();
				v.add(rs.getInt(1));

				v.add(rs.getString(2));

				v.add(rs.getString(3));

				v.add(rs.getString(4));

				v.add(rs.getString(5));

				v.add(rs.getDouble(6));
				v.add(rs.getString(7));
				v.add(rs.getString(8));
				v.add(rs.getString(9));

				rowdata.add(v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// shut down
			try {
				if (rs != null)
					rs.close();
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


	public StuModel() {
		this.init("");
	}

	
	public StuModel(String sql) {
		this.init(sql);
	}

	@Override
	public int getRowCount() {
		
		return this.rowdata.size();
	}

	// Get the total number of columns
	@Override
	public int getColumnCount() {

		return this.columnName.size();
	}

	// Get the data in a column in a row
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		return ((Vector) this.rowdata.get(rowIndex)).get(columnIndex);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return (String) this.columnName.get(column);
	}
}
