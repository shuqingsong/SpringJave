package com.sqs.jdbc;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestJDBCTemplate implements TestDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public void create(Long userseq,String idno,String idtype,String name,String mobile,String state) {
		String SQL = "insert into user(userseq,idno,idtype,name,mobile,state) values(?,?,?,?,?,?)";
		jdbcTemplateObject.update( SQL, userseq, idno, idtype, name, mobile, state);
		System.out.println("插入数据");
		return;
	}
	
	public User query(String idno, String idtype) {
		String SQL = "select * from user where idno=? and idtype=?";
		User puser = jdbcTemplateObject.queryForObject(SQL, new Object[]{idno,idtype}, new UserMapper());
		System.out.println("查询满足条件数据");
		return puser;
	}
	
	public List queryList() {
		String SQL = "select * from user";
		List pusers = jdbcTemplateObject.query(SQL, new UserMapper());
		System.out.println("查询全部数据");
		return pusers;
	}
	
	public void delete(String idno,String idtype){
		String SQL = "delete from user where idno=? and idtype=?";
		jdbcTemplateObject.update(SQL, idno, idtype);
		System.out.println("删除数据");
		return;
	}
		  
	public void update(String idno,String idtype,String state){
		 String SQL = "update user set state=? where idno=? and idtype=?";
		 jdbcTemplateObject.update(SQL, state, idno, idtype);
		 System.out.println("更新数据");
		 return;
	}

}
