package osu.dbms.options;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import osu.dbms.query.SQL;
import osu.dbms.utility.Utility;

import java.io.IOException;

public class Report {
    public static void chooseReport(Scanner sc, Connection conn) throws IOException {
        final String[] report_menu = {
            "Renting checkouts: Get total equipment rented by a member", //this query needs input of member
            "Popular item: Find most popular item", 
            "Popular manufacturer: Find the most frequent equipment manufacturer", 
            "Popular drone: Find the most used drone", 
            "Items checked out: Find the member that has checked out the most items", 
            "Equipment by type: Find the name of equipment by type released before a certain year" //this query needs input of year
        };
        final String[] reportSQL = {
        "SELECT COUNT(*) AS EquipmentCount " +
        "FROM MEMBER as M, RENT_ORDER as R, RENT_ORDER_EQUIPMENT as RE, EQUIPMENT1 as E " +
        "WHERE M.Member_id = R.Placed_by_member AND R.Order_no = RE.Order_no AND RE.Serial_no = E.Serial_no AND M.Member_id = ?;", 
        
        "Placeholder",
        
        "SELECT M.Part_ID, M.Name, COUNT(*) AS TimesRented " +
        "FROM MANUFACTURER AS M, EQUIPMENT1 AS E, RENT_ORDER_EQUIPMENT AS ROE " +
        "WHERE E.Serial_no = ROE.Serial_no AND E.Manufacturer = M.Part_ID " +
        "GROUP BY M.Part_ID " +
        "ORDER BY TimesRented DESC " +
        "LIMIT 1;",
        
        "Placeholder", 
        
        "SELECT m.Member_id, m.Fname, m.Lname, COUNT(roe.Serial_no) AS items_checked_out " +
        "FROM Member m " +
        "JOIN Rent_order RO        ON RO.Placed_by_member = m.Member_id " +
        "JOIN Rent_order_equipment ROE ON ROE.Order_no = RO.Order_no " +
        "GROUP BY m.Member_id, m.Fname, m.Lname " +
        "ORDER BY items_checked_out DESC " +
        "LIMIT 1;", 
        
        "Placeholder"
    
        };
        for (int i = 0; i < report_menu.length;i++) {
            System.out.println(i + ": " + report_menu[i]);
        }
        System.out.print("Choose report: ");
        String reportChoice = Utility.getTokenFromLine(sc);
        if (reportChoice == null) {
            System.out.println("No choice entered.");
            return;
        }
        try {
            String sql;
            PreparedStatement ps;
            switch(reportChoice) {
                case "0":
                    sql = reportSQL[Integer.parseInt(reportChoice)];
                    ps = conn.prepareStatement(sql);
                    System.out.print("Enter the member ID: "); //prompt user for member
                    String memId = Utility.getLineStripped(sc);
                    SQL.setArg(ps, 1, SQL.TYPE.STR, memId);
                    break;
                case "1":
                case "2":
                case "3": //cases 1, 2, 3 will fall through and execute case 4
                case "4":
                    sql = reportSQL[Integer.parseInt(reportChoice)];
                    ps = conn.prepareStatement(sql);
                    break;
                case "5":
                    sql = reportSQL[Integer.parseInt(reportChoice)];
                    ps = conn.prepareStatement(sql);
                    System.out.print("Enter the year: "); //prompt user for year
                    String year = Utility.getLineStripped(sc);
                    SQL.setArg(ps, 1, SQL.TYPE.STR, year);
                    break;
                default:
                    System.out.println("Invalid option.");
                    return;
            
            }
            ResultSet rs = ps.executeQuery();
            SQL.displayResultSet(rs);
            rs.close();
            ps.close();
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
