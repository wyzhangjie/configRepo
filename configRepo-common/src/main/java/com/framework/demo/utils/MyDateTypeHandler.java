package com.framework.demo.utils;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hassop
 * @Description
 * @date 2016/9/1 0001
 * To change this template use File | Settings | File Templates.
 */
public class MyDateTypeHandler implements TypeHandler<String> {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setParameter(java.sql.PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.valueOf(parameter));
    }

    public String getResult(ResultSet rs, String columnName) throws SQLException {
         String sqlTimestamp = rs.getString(columnName);
        if (sqlTimestamp != null) {
            try {
                return sqlTimestamp.substring(0,sqlTimestamp.lastIndexOf("."));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        Date sqlTimestamp = rs.getDate(columnIndex);

        if (sqlTimestamp != null) {
            try {
                return dateFormat.format(sqlTimestamp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        String sqlTimestamp = cs.getString(columnIndex);
        if (sqlTimestamp != null) {
            try {
                return dateFormat.format(sqlTimestamp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
