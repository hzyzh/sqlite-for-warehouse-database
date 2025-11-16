package osu.dbms.query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;

import osu.dbms.utility.Utility;
import osu.dbms.exception.MyException;

public class QueryDrone implements Queryable {
    String columns_name[];
    SQL.TYPE columns_type[];



    public QueryDrone(Connection conn) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM DRONE1 NATURAL JOIN DRONE2 NATURAL JOIN DRONE3 WHERE 1=0;");
        ResultSetMetaData rsmd = rs.getMetaData();
        int n = rsmd.getColumnCount();

        columns_name = new String[n];
        columns_type = new SQL.TYPE[n];
        for (int i = 0 ;i < n; i++){
            columns_name[i] = rsmd.getColumnName(i+1);
            String typeName = rsmd.getColumnTypeName(i+1);

            // System.out.println("Column: " + columns_name[i] + ", Type: " + typeName);

            switch(typeName){
                case "INT":
                case "INTEGER":
                case "TINYINT":
                case "SMALLINT":
                case "MEDIUMINT":
                case "BIGINT":
                    columns_type[i] = SQL.TYPE.INT;
                    break;
                case "REAL":
                case "FLOAT":
                case "DOUBLE":
                    columns_type[i] = SQL.TYPE.REAL;
                    break;
                case "DATE":
                case "DATETIME":
                    columns_type[i] = SQL.TYPE.DATE;
                    break;
                default:
                    columns_type[i] = SQL.TYPE.STR;
            }
        }
    }

    public void addOp(Scanner sc, Connection conn) throws Exception {
        System.out.println("Adding a new drone record...");
        int attr_cnt = columns_name.length;
        String new_values[] = new String[attr_cnt];
        for (int i = 0; i < attr_cnt; i++) {
            System.out.print("Enter value for " + columns_name[i]);
            switch (columns_type[i]) {
                case INT:
                    System.out.print(" (Integer): ");
                    break;
                case REAL:
                    System.out.print(" (Real number): ");
                    break;
                case DATE:
                    System.out.print(" (YYYY-MM-DD): ");
                    break;
                default:
                    System.out.print(" (String): ");
            }
            new_values[i] = Utility.getLineStripped(sc);
        }

        String insert_sql = "INSERT INTO DRONE1 VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pstmt = conn.prepareStatement(insert_sql);
        for (int i = 0; i < 7; i++) {
            SQL.setArg(pstmt, i + 1, columns_type[i], new_values[i]);
        }
        if (pstmt.executeUpdate() > 0) {
            System.out.println("Record added to DRONE1 successfully.");
        } else {
            System.out.println("Failed to add record to DRONE1.");
        }
        pstmt.close();

        String insert_sql2 = "INSERT INTO DRONE2 VALUES (?, ?, ?, ?, ?);";
        pstmt = conn.prepareStatement(insert_sql2);
        SQL.setArg(pstmt, 1, columns_type[3], new_values[3]);   //Manufacturer
        SQL.setArg(pstmt, 2, columns_type[4], new_values[4]);   //Year
        for (int i = 7; i < 10; i++) {
            SQL.setArg(pstmt, i - 4, columns_type[i], new_values[i]);   //Model, Name, Warranty_expiration
        }
        if (pstmt.executeUpdate() > 0) {
            System.out.println("Record added to DRONE2 successfully.");
        } else {
            System.out.println("Failed to add record to DRONE2.");
        }
        pstmt.close();

        String insert_sql3 = "INSERT INTO DRONE3 VALUES (?, ?, ?, ?);";
        pstmt = conn.prepareStatement(insert_sql3);
        SQL.setArg(pstmt, 1, columns_type[7], new_values[7]);   //Model
        for (int i = 10; i < attr_cnt; i++) {
            SQL.setArg(pstmt, i - 8, columns_type[i], new_values[i]);   //Max_speed, Distance_autonomy, Weight_capacity
        }
        if (pstmt.executeUpdate() > 0) {
            System.out.println("Record added to DRONE3 successfully.");
        } else {
            System.out.println("Failed to add record to DRONE3.");
        }
        pstmt.close();
    }

    public void editOp(Scanner sc, Connection conn) throws Exception {
        System.out.println("Editing a drone record...");
        Utility.placeholder();
    }
    public void deleteOp(Scanner sc, Connection conn) throws Exception {
        System.out.println("Deleting a drone record...");
        Utility.placeholder();
    }
    public void searchOp(Scanner sc, Connection conn) throws Exception {
        System.out.println("Searching drone records...");
        System.out.println("Choose a searching criterion (enter number):");
        System.out.println("1. Serial No\n2. Status\n3. Warehouse City\n4. Weight Capacity");
        ResultSet rs = null;
        String from_sql = "FROM DRONE1 NATURAL JOIN DRONE2 NATURAL JOIN DRONE3";

        String choice = Utility.getTokenFromLine(sc);
        if (choice == null) {
            System.out.println("No choice entered. Aborting search.");
            throw new MyException("No choice entered for searching criterion.");
        }
        switch (choice) {
            case "1":
                rs = SQL.search(conn, sc, from_sql, "Serial_no", SQL.TYPE.STR);
                break;
            case "2":
                rs = SQL.search(conn, sc, from_sql, "Status", SQL.TYPE.STR);
                break;
            case "3":
                rs = SQL.search(conn, sc, from_sql, "Warehouse_city", SQL.TYPE.STR);
                break;
            case "4":
                rs = SQL.search(conn, sc, from_sql, "Weight_capacity", SQL.TYPE.INT, 1);
                break;
            default:
                System.out.println("Invalid choice. Aborting search.");
                throw new MyException("Invalid choice for searching criterion.");
        }    
        SQL.displayResultSet(rs);
        rs.close();
    }
}
