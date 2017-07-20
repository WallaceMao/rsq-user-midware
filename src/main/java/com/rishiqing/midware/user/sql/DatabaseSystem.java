package com.rishiqing.midware.user.sql;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by  on 2017/7/20.Wallace
 */
public class DatabaseSystem {
    public static Logger logger = Logger.getLogger(DatabaseSystem.class);
    private static String config = "mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getSqlSessionFactory(){
        if(null == sqlSessionFactory){
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream(config);
            }catch (IOException e){
                logger.error("config file not found", e);
            }
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        return sqlSessionFactory;
    }
}
