package com.hua.library.singleton;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;

public class SingletonDataSource {
    private static SingletonDataSource singletonDataSource =
            new SingletonDataSource();
    private DataSource dataSource;
    
    public SingletonDataSource() {
            // create MySQL data source object  
            MysqlDataSource mysqlds = new MysqlDataSource();
            mysqlds.setURL("jdbc:mysql://localhost/library?characterEncoding=UTF-8");
            mysqlds.setUser("it21208");
            mysqlds.setPassword("changeit");
            dataSource = mysqlds;    
    };
            
    public DataSource getDataSource() {
        return dataSource;
    }
}
