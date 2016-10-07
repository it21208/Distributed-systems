package com.hua.library3.singleton;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;

public class SingletonDataSource {
    private static SingletonDataSource singletonDataSource =
            new SingletonDataSource();
    private static DataSource dataSource;
    
    private SingletonDataSource() {
            // create MySQL data source object  
            MysqlDataSource mysqlds = new MysqlDataSource();
            mysqlds.setURL("jdbc:mysql://localhost/library2?characterEncoding=UTF-8");
            mysqlds.setUser("root");
            mysqlds.setPassword("Gam0t0mou");
            dataSource = mysqlds;    
    };
            
    public static DataSource getDataSource() {
        return dataSource;
    }
}
