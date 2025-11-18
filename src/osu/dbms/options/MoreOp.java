package osu.dbms.options;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import osu.dbms.utility.Utility;

public class MoreOp {    
    final static String[] operation_menu = {"Rent equipment", "Return equipment",
        "Delivery of equipment", "Pickup of equipment", "Onsite service appointment", "Go back to top menu"};
    final static String[] operation_menu_option = {"rent", "return", "deliver", "pickup", "appointment", "back"};

    static void displayMoreOpMenu() {
        System.out.println("\nMore operations:");
        for (int i = 0; i < operation_menu.length; i++) {
            System.out.printf("%-15s %s\n", operation_menu_option[i], operation_menu[i]);
        }
        System.out.print("Enter a command here: ");
    }

    public static void doOperation(Scanner sc, Connection conn) throws IOException {
         while (true) {
            displayMoreOpMenu();

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
                    System.out.println("Enter the Serial_no of the rented equipment. If complete, enter a blank line to finish.");
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
                    System.out.println("Enter the Serial_no of the returned equipment. If complete, enter a blank line to finish.");
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
                    System.out.println("Enter the Serial_no of the drone. If complete, enter a blank line to finish.");
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
                    System.out.println("Enter the Serial_no of the drone. If complete, enter a blank line to finish.");
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
    }
}
