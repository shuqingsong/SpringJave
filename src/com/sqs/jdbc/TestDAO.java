package com.sqs.jdbc;

import java.util.List;
import javax.sql.DataSource;

public interface TestDAO {

	void setDataSource(DataSource ds);
	   
	void create(String userseq, String idno, String idtype, String name, String mobile, String state);

	Puser query(String idno, String idtype);

	List queryList();

	void delete(String idno, String idtype);

	void update(String idno, String idtype, String state);
}
