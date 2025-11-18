package osu.dbms;
import java.io.IOException;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

import osu.dbms.options.*;
import osu.dbms.query.Selection;
import osu.dbms.utility.Utility;

public class user_menu {
    final String[] top_menu = {"Add new records", "Edit records", "Delete records", "Search",
        "More operations", "Generate useful reports", "Exit program"};
    final public String[] top_menu_options = {"add", "edit", "delete", "search", "more", "report", "exit"};

    final String db_url = "jdbc:sqlite:WarehouseDatabase.db";
    Connection conn = null;

    private Scanner sc = new Scanner(System.in);


    private void connectDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(db_url);
        conn.setAutoCommit(false);
        Selection.initializeQueryableEntities(conn);
    }

    private void displayTopMenu() {
        int n = top_menu.length;
        System.out.println();
        System.out.println("Top menu:");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-15s %s\n", top_menu_options[i], top_menu[i]);
        }
        System.out.print("Enter a command here: ");
    }


    boolean chooseOperation(String command) throws IOException {   //if return false, means to exit the program!
        Selection.OPERATIONS oper = Selection.OPERATIONS.NONE;

        if (command.equals(top_menu_options[0])) {  //add
            System.out.println("Which entity would you like to add?");
            oper = Selection.OPERATIONS.ADD;

        } else if (command.equals(top_menu_options[1])) {  //edit
            System.out.println("Which entity would you like to edit?");
            oper = Selection.OPERATIONS.EDIT;

        } else if (command.equals(top_menu_options[2])) {  //delete
            System.out.println("Which entity would you like to delete?");
            oper = Selection.OPERATIONS.DELETE;

        } else if (command.equals(top_menu_options[3])) {  //search
            System.out.println("Which entity would you like to search?");
            oper = Selection.OPERATIONS.SEARCH;

        } else if (command.equals(top_menu_options[4])) {  //more

            MoreOp.doOperation(sc, conn);
            return true;
            
        } else if (command.equals(top_menu_options[5])) {  //report

            System.out.println("Generate useful report: ");
            Report.chooseReport(sc, conn);
            return true;

        } else if (command.equals(top_menu_options[6])) {  //exit
            System.out.println("Exit program");
            return false;
        } else {
            System.out.println("Unrecognized command.");
            System.out.println("Press <Enter> to continue...");
            sc.nextLine();
            return true;
        }

        try {
            Selection.doOperation(sc, conn, oper);
        } catch (SQLException e) {
            System.out.println("Error occurred during rollback: " + e.getMessage());
        }
        return true;
    }

    void menu() {
        while(true) {
            displayTopMenu();
            String command;

            try {
                command = Utility.getTokenFromLine(sc);
            } catch (Exception e) {
                // System.out.println("Something went wrong with the scanner.");
                break;
            }
            if (command == null) {
                //skip if it is an empty line
                continue;
            }

            try {
                if(!chooseOperation(command)) {  //if returns false, means to exit the program
                    break;
                }
            } catch (Exception e) {
                System.out.println("Abort");
                break;
            }
        }
    }

    public static void main(String[] args) {
        user_menu um = new user_menu();
        try {
            um.connectDatabase();
            System.out.println("Connected to the database successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to connect to the database.");
            return;
        }
        um.menu();
    }
}
