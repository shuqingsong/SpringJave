package com.sqs.jdbc;

import java.util.List;
import javax.sql.DataSource;

public interface TestDAO {

	public void setDataSource(DataSource ds);
	   
	public void create(String idno,String idtype,String name,String mobile,String state);

	public Puser query(String idno,String idtype);

	public List queryList();

	public void delete(String idno,String idtype);

	public void update(String idno,String idtype,String state);
}
