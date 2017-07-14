package com.rishiqing.midware.user.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by  on 2017/6/21.Wallace
 */
public class BaseDaoTest {
    public static SqlSession session = null;

    @BeforeClass
    public static void prepareSqlSession(){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            session = sqlSessionFactory.openSession(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void cleanSqlSession(){
        if(null != session){
            session.close();
        }
    }
}
