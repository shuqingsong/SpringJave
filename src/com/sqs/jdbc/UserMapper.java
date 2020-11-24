package com.sqs.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {
	   public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		  User user = new User();
		  user.setUserseq(rs.getLong("userseq"));
		  user.setIdno(rs.getString("idno"));
		  user.setIdtype(rs.getString("idtype"));
		  user.setName(rs.getString("name"));
		  user.setMobile(rs.getString("mobile"));
		  user.setState(rs.getString("state"));
	      return user;
	   }
	}
