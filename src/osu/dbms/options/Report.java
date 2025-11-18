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
        
        "SELECT E.Serial_no, SUM(JULIANDAY(RO.Actual_return_date) - JULIANDAY(RO.Actual_arrival_date)) AS TotalDaysRented, COUNT(RO.Actual_arrival_date) AS TimesRented " +
        "FROM EQUIPMENT1 AS E LEFT OUTER JOIN RENT_ORDER_EQUIPMENT AS ROE ON E.Serial_no = ROE.Serial_no LEFT OUTER JOIN RENT_ORDER AS RO ON ROE.Order_no = RO.Order_no " +
        "GROUP BY E.Serial_no " +
        "ORDER BY TotalDaysRented DESC " +
        "LIMIT 1;",
        
        "SELECT M.Part_ID, M.Name, COUNT(*) AS TimesRented " +
        "FROM MANUFACTURER AS M, EQUIPMENT1 AS E, RENT_ORDER_EQUIPMENT AS ROE " +
        "WHERE E.Serial_no = ROE.Serial_no AND E.Manufacturer = M.Part_ID " +
        "GROUP BY M.Part_ID " +
        "ORDER BY TimesRented DESC " +
        "LIMIT 1;",
        
        "SELECT T1.Serial_no, Total_distance, Delivery_time " +
        "FROM " +
            "(SELECT Serial_no, SUM(Warehouse_distance) AS Total_distance " +
            "FROM ( " +
                "SELECT Drone_serial_no AS Serial_no, Warehouse_distance " +
                "FROM DRONE_DELIVERIES AS DD, RENT_ORDER AS RO, MEMBER AS M " +
                "WHERE DD.Order_no = RO.Order_no AND RO.Placed_by_member = M.Member_id " +
                    "UNION ALL " +
                "SELECT Drone_serial_no AS Serial_no, Warehouse_distance " +
                "FROM DRONE_PICKUPS AS DP, RENT_ORDER AS RO, MEMBER AS M " +
                "WHERE DP.Order_no = RO.Order_no AND RO.Placed_by_member = M.Member_id) " +
                "GROUP BY Serial_no " +
                "ORDER BY Total_distance DESC) " +
                    "AS T1 LEFT OUTER JOIN " +
            "(SELECT Drone_serial_no AS Serial_no, COUNT(*) AS Delivery_time " +
            "FROM DRONE_DELIVERIES " +
            "GROUP BY Serial_no) AS T2 ON T1.Serial_no = T2.Serial_no " +
        "LIMIT 1;", 
        
        "SELECT m.Member_id, m.Fname, m.Lname, COUNT(roe.Serial_no) AS items_checked_out " +
        "FROM Member m " +
        "JOIN Rent_order RO        ON RO.Placed_by_member = m.Member_id " +
        "JOIN Rent_order_equipment ROE ON ROE.Order_no = RO.Order_no " +
        "GROUP BY m.Member_id, m.Fname, m.Lname " +
        "ORDER BY items_checked_out DESC " +
        "LIMIT 1;", 
        
        "SELECT Serial_no, Description, Year " +
        "FROM EQUIPMENT1 " +
        "WHERE Year < ?;"
    
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
                    SQL.setArg(ps, 1, SQL.TYPE.INT, year);
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
