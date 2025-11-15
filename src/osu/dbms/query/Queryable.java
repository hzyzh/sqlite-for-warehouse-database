package osu.dbms.query;
import java.sql.Connection;
import java.util.Scanner;

public interface Queryable {
    public void addOp(Scanner sc, Connection conn) throws Exception;
    public void editOp(Scanner sc, Connection conn) throws Exception;
    public void deleteOp(Scanner sc, Connection conn)  throws Exception;
    public void searchOp(Scanner sc, Connection conn)  throws Exception;
}