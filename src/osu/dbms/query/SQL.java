package osu.dbms.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSetMetaData;

import osu.dbms.exception.MyException;
import osu.dbms.utility.Utility;

public class SQL {
    public enum TYPE{
        STR, INT, REAL, DATE
    };

    public static void setArg(PreparedStatement pstmt, int index, TYPE type, String value) throws Exception {
        switch(type){
            case STR:
                if (value == null)
                    pstmt.setNull(index, java.sql.Types.VARCHAR);
                else
                    pstmt.setString(index, value);
                break;
            case INT:
                if (value == null)
                    pstmt.setNull(index, java.sql.Types.INTEGER);
                else
                    pstmt.setInt(index, Integer.parseInt(value));
                break;
            case REAL:
                if (value == null)
                    pstmt.setNull(index, java.sql.Types.DOUBLE);
                else
                    pstmt.setDouble(index, Double.parseDouble(value));
                break;
            case DATE:
                if (value == null)
                    pstmt.setNull(index, java.sql.Types.DATE);
                else
                    pstmt.setString(index, value); // assuming the date is in correct format
                break;
            default:
                throw new Exception("Unsupported SQL TYPE");
        }
    }

    public static ResultSet search(Connection conn, String value, String from_sql, String attribute_name, TYPE type, int search_method) throws Exception {

        if (value == null || value.isEmpty()) {
            throw new MyException("No value entered for search.");
        }
        String sql = "SELECT * " + from_sql;
        switch (search_method) {
            case 0:
                sql += " WHERE " + attribute_name + " = ?;";
                break;
            case 1:
                if (type == SQL.TYPE.STR) {
                    throw new MyException("Cannot perform >= search on string type.");
                } else if (type == SQL.TYPE.DATE) {
                    sql += " WHERE julianday(" + attribute_name + ") >= julianday(?);";
                }
                sql += " WHERE " + attribute_name + " >= ?;";
                break;
            case 2:
                if (type == SQL.TYPE.STR) {
                    throw new MyException("Cannot perform <= search on string type.");
                } else if (type == SQL.TYPE.DATE) {
                    sql += " WHERE julianday(" + attribute_name + ") <= julianday(?);";
                }
                sql += " WHERE " + attribute_name + " <= ?;";
                break;
            default:
                throw new Exception("Invalid search method.");
        }
        PreparedStatement pstmt = conn.prepareStatement(sql);
        setArg(pstmt, 1, type, value);
        return pstmt.executeQuery();
    }

   /**
    * Search drone records based on a criterion. 
    * "SELECT * " + from_sql + " WHERE attribute_name ( = | >= | <= ) value;"
    * @param conn the database connection
    * @param sc Scanner object for user input
    * @param from_sql the FROM part for SQL query, e.g. "FROM DRONE1 NATURAL JOIN DRONE2 NATURAL JOIN DRONE3"
    * @param attribute_name the search attribute name
    * @param type the SQL type of the search attribute
    * @param search_method 0 for exact match, 1 for >= , 2 for <=
    */
    public static ResultSet search(Connection conn, Scanner sc, String from_sql, String attribute_name, SQL.TYPE type, int search_method) throws Exception {
        System.out.print("Searching records by " + attribute_name);
        switch (search_method) {
            case 0:
                System.out.print(" == ");
                break;
            case 1:
                System.out.print(" >= ");
                break;
            case 2:
                System.out.print(" <= ");
                break;
        }
        String value = Utility.getLineStripped(sc);
        return search(conn, value, from_sql,  attribute_name, type, search_method);
    }
   /**
    * Exact match search records based on a criterion.
    * "SELECT * " + from_sql + " WHERE attribute_name = value;"
    * @param conn the database connection
    * @param sc Scanner object for user input
    * @param from_sql the FROM part for SQL query, e.g. "FROM DRONE1 NATURAL JOIN DRONE2 NATURAL JOIN DRONE3"
    * @param attribute_name the search attribute name
    * @param type the SQL type of the search attribute
    */
    public static ResultSet search(Connection conn, Scanner sc, String from_sql, String attribute_name, SQL.TYPE type) throws Exception {
        return search(conn, sc, from_sql, attribute_name, type, 0);
    }

    public static void displayResultSet(ResultSet rs) throws Exception {
        ResultSetMetaData rsmd = rs.getMetaData();
        int n = rsmd.getColumnCount();

        for (int i = 1; i <= n; i++) {
            System.out.print(rsmd.getColumnName(i) + "\t");
        }
        System.out.println();

        while (rs.next()) {
            for (int i = 1; i <= n; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
    }
}
