package com.sqs.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PuserMapper implements RowMapper<Puser> {
	   public Puser mapRow(ResultSet rs, int rowNum) throws SQLException {
		  Puser puser = new Puser();
		  puser.setUserseq(rs.getInt("userseq"));
		  puser.setIdno(rs.getString("idno"));
		  puser.setIdtype(rs.getString("idtype"));
		  puser.setName(rs.getString("name"));
		  puser.setMobile(rs.getString("mobile"));
		  puser.setState(rs.getString("state"));
	      return puser;
	   }
	}
