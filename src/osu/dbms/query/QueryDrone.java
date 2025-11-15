package osu.dbms.query;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;

import osu.dbms.utility.Utility;

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

            System.out.println("Column: " + columns_name[i] + ", Type: " + typeName);

            switch(typeName){
                case "INT":
                case "INTEGER":
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

/*
            String attributes[] = new String[entity.getAttributeCount()];
            for (int i = 0; i < attributes.length; i++) {
                System.out.println("Enter the value for " + entity.attributes_name[i] + ":");
                attributes[i] = Utility.getLineStripped(sc);
            }

            Record record = new Record(entity, attributes);
            if(entity.addRecord(record)) {
                System.out.println("Record added Successfully.");
            } else {
                System.out.println("Record could not be added.");
            } */
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
        Utility.placeholder();
    }
}
