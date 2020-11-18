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
	
	public void create(String userseq,String idno,String idtype,String name,String mobile,String state) {
		String SQL = "insert into puser(userseq,idno,idtype,name,mobile,state) values(?,?,?,?,?,?)";
		jdbcTemplateObject.update( SQL, idno, idtype, name, mobile, state);
		System.out.println("插入数据");
		return;
	}
	
	public Puser query(String idno,String idtype) {
		String SQL = "select * from puser where idno=? and idtype=?";
		Puser puser = jdbcTemplateObject.queryForObject(SQL, new Object[]{idno,idtype}, new PuserMapper());
		System.out.println("查询满足条件数据");
		return puser;
	}
	
	public List queryList() {
		String SQL = "select * from puser";
		List pusers = jdbcTemplateObject.query(SQL, new PuserMapper());
		System.out.println("查询全部数据");
		return pusers;
	}
	
	public void delete(String idno,String idtype){
		String SQL = "delete from puser where idno=? and idtype=?";
		jdbcTemplateObject.update(SQL, idno, idtype);
		System.out.println("删除数据");
		return;
	}
		  
	public void update(String idno,String idtype,String state){
		 String SQL = "update puser set state=? where idno=? and idtype=?";
		 jdbcTemplateObject.update(SQL, state, idno, idtype);
		 System.out.println("更新数据");
		 return;
	}

}
