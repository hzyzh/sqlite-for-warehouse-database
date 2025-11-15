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

    final String[] operation_menu = {"Rent equipment", "Return equipment",
        "Delivery of equipment", "Pickup of equipment", "Onsite service appointment", "Go back to top menu"};
    final String[] operation_menu_option = {"rent", "return", "deliver", "pickup", "appointment", "back"};

    final String[] member_attributes = {"member_ID", "fname", "lname", "gender", "address", "start_date"};
    final String[] drone_attributes = {"serial_number", "name", "model", "location", "year", "status", "warranty_expiration", "max_speed", "distance_autonomy", "weight_capacity"};
    final String[] equipment_attributes = {"serial_number", "type", "model", "year", "status", "description", "location", "warranty_expiration", "weight", "dimensions", "renting_fee", "per-day_renting_cost"};


    final String db_url = "jdbc:sqlite:WarehouseDatabase.db";
    Connection conn = null;

    private Scanner sc = new Scanner(System.in);


    private void connectDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(db_url);
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



    void moreOperation() throws IOException {
        Utility.placeholder();
        return;
    /*
        int n = operation_menu.length;

        while (true) {
            System.out.println("\nMore operations:");
            for (int i = 0; i < n; i++) {
                System.out.printf("%-15s %s\n", operation_menu_option[i], operation_menu[i]);
            }
            System.out.print("Enter a command here: ");

            String command = Utility.getTokenFromLine(sc);
            if (command == null) { continue; }
            if (command.equals(operation_menu_option[0])) {  //rent equipments
                System.out.println("Which member is renting?");
                String member = Utility.getLineStripped(sc);
                if (member == null) {
                    System.out.println("No member selected. Operation failed.");
                    break;
                }

                int n_rented = 0;
                while (true) {
                    if (n_rented == 0) {
                        System.out.println("What equipment to rent?");
                    } else {
                        System.out.println("More equipment to rent?");
                    }
                    System.out.println("Enter the " + equipment_attributes[entities[2].primary_key] +
                            " of the rented equipment. If complete, enter a blank line to finish.");
                    String equipment = Utility.getLineStripped(sc);

                    if (equipment == null) {  //blank line
                        break;
                    } else { //need to change in the future
                        System.out.println("Equipment added.");
                        n_rented++;
                    }
                }
                if (n_rented == 0) {
                    System.out.println("No equipment rented. Operation finished.");
                } else {
                    System.out.println(n_rented + " equipment is rented by \"" + member + "\". Operation finished.");
                }
                break;
            }
            else if (command.equals(operation_menu_option[1])) { //return equipments
                System.out.println("Which member is returning?");
                String member = Utility.getLineStripped(sc);
                if (member == null) {
                    System.out.println("No member selected. Operation failed.");
                    break;
                }

                int n_returned = 0;
                while (true) {
                    if (n_returned == 0) {
                        System.out.println("Which equipment to return?");
                    } else {
                        System.out.println("More equipment to return?");
                    }
                    System.out.println("Enter the " + equipment_attributes[entities[2].primary_key] +
                            " of the returned equipment. If complete, enter a blank line to finish.");
                    String equipment = Utility.getLineStripped(sc);

                    if (equipment == null) {  //blank line
                        break;
                    } else { //need to change in the future
                        System.out.println("Equipment returned.");
                        n_returned++;
                    }
                }
                if (n_returned == 0) {
                    System.out.println("No equipment returned. Operation finished.");
                } else {
                    System.out.println(n_returned + " equipment is returned by \"" + member + "\". Operation finished.");
                }
                break;
            }
            else if (command.equals(operation_menu_option[2])) { //deliver equipments
                System.out.println("Deliver equipment for which member?");
                String member = Utility.getLineStripped(sc);
                if (member == null) {
                    System.out.println("No member selected. Operation failed.");
                    break;
                }

                System.out.println("Schedule the time for delivery. Enter the scheduled time here.");
                String schedule = Utility.getLineStripped(sc);
                if (schedule == null) { // or schedule is invalid
                    System.out.println("Please provide a valid time. Operation failed.");
                    break;
                }

                int n_drones = 0;
                while (true) {
                    if (n_drones == 0) {
                        System.out.println("Assign a drone for delivery.");
                    } else {
                        System.out.println("Assign more drones?");
                    }
                    System.out.println("Enter the " + equipment_attributes[entities[1].primary_key] +
                            " of the drone. If complete, enter a blank line to finish.");
                    String drone = Utility.getLineStripped(sc);

                    if (drone == null) {  //blank line
                        break;
                    } else { //need to change in the future
                        System.out.println("Drone added.");
                        n_drones++;
                    }
                }
                if (n_drones == 0) {
                    System.out.println("No drone is assigned for delivery. Operation finished.");
                } else {
                    if (n_drones == 1) {
                        System.out.print(n_drones + " drone is ");
                    } else {
                        System.out.print(n_drones + " drones are ");
                    }
                    System.out.println("assigned to deliver equipment for \"" + member
                                    + "\" on " + schedule + ". Operation finished.");
                }
                break;
            }
            else if (command.equals(operation_menu_option[3])) {  //pick up equipments
                System.out.println("Pick up equipment for which member?");
                String member = Utility.getLineStripped(sc);
                if (member == null) {
                    System.out.println("No member selected. Operation failed.");
                    break;
                }

                System.out.println("Schedule the time for pickup. Enter the scheduled time here.");
                String schedule = Utility.getLineStripped(sc);
                if (schedule == null) { // or schedule is invalid
                    System.out.println("Please provide a valid time. Operation failed.");
                    break;
                }

                int n_drones = 0;
                while (true) {
                    if (n_drones == 0) {
                        System.out.println("Assign a drone for pickup.");
                    } else {
                        System.out.println("Assign more drones?");
                    }
                    System.out.println("Enter the " + equipment_attributes[entities[1].primary_key] +
                            " of the drone. If complete, enter a blank line to finish.");
                    String drone = Utility.getLineStripped(sc);

                    if (drone == null) {  //blank line
                        break;
                    } else { //need to change in the future
                        System.out.println("Drone added.");
                        n_drones++;
                    }
                }
                if (n_drones == 0) {
                    System.out.println("No drone is assigned for pickup. Operation finished.");
                } else {
                    if (n_drones == 1) {
                        System.out.print(n_drones + " drone is ");
                    } else {
                        System.out.print(n_drones + " drones are ");
                    }
                    System.out.println("assigned to pick up equipment for \"" + member
                            + "\" on " + schedule + ". Operation finished.");
                }
                break;
            } else if (command.equals(operation_menu_option[4])) {  //appointment for an onsite service
                System.out.println("Make an appointment for an onsite service.");
                break;
            } else if (command.equals(operation_menu_option[5])) {  //go back to top menu
                break;
            } else {
                System.out.println("Unrecognized command.");
                continue;
            }
        }
            */
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
            System.out.println("Which entity would you like to search for?");
            oper = Selection.OPERATIONS.SEARCH;

        } else if (command.equals(top_menu_options[4])) {  //more
            moreOperation();
            return true;
        } else if (command.equals(top_menu_options[5])) {  //report
            System.out.println("Generate useful report: ");
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

        Selection.doOperation(sc, conn, oper);
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
