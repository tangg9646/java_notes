package com.itranswarp.learnjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * App entry for Maven project.
 * 
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) throws Exception {
		List<Student> students = queryStudents();
		//由于Students类覆写了toString方法，可以直接调用println
		students.forEach(System.out::println);
	}

	static List<Student> queryStudents() throws SQLException {
		List<Student> students = new ArrayList<>();
		//建立连接
		try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
			//为preparestatement
			try (PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM students WHERE grade = ? AND score >= ?")) {
				ps.setInt(1, 3); // 第一个参数grade=?
				ps.setInt(2, 90); // 第二个参数score=?
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						students.add(extractRow(rs));
					}
				}
			}
		}
		return students;
	}

	static Student extractRow(ResultSet rs) throws SQLException {
		//此方法从结果集中读取结果后，设置student实例的对应字段，返回Student实例
		var std = new Student();
		std.setId(rs.getLong("id"));
		std.setName(rs.getString("name"));
		std.setGender(rs.getBoolean("gender"));
		std.setGrade(rs.getInt("grade"));
		std.setScore(rs.getInt("score"));
		return std;
	}

	static final String jdbcUrl = "jdbc:mysql://localhost/learnjdbc?useSSL=false&characterEncoding=utf8";
	static final String jdbcUsername = "learn";
	static final String jdbcPassword = "learnpassword";
}
