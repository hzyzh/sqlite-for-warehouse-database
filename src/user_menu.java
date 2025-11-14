import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class user_menu {
    final String[] top_menu = {"Add new records", "Edit records", "Delete records", "Search",
        "More operations", "Generate useful reports", "Exit program"};
    final String[] top_menu_options = {"add", "edit", "delete", "search", "more", "report", "exit"};

    final String[] operation_menu = {"Rent equipment", "Return equipment",
        "Delivery of equipment", "Pickup of equipment", "Onsite service appointment", "Go back to top menu"};
    final String[] operation_menu_option = {"rent", "return", "deliver", "pickup", "appointment", "back"};
    final String[] entites_name = {"member", "drone", "equipment"};

    final String[] member_attributes = {"member_ID", "fname", "lname", "gender", "address", "start_date"};
    final String[] drone_attributes = {"serial_number", "name", "model", "location", "year", "status", "warranty_expiration", "max_speed", "distance_autonomy", "weight_capacity"};
    final String[] equipment_attributes = {"serial_number", "type", "model", "year", "status", "description", "location", "warranty_expiration", "weight", "dimensions", "renting_fee", "per-day_renting_cost"};
    final Entity[] entities = {
            new Entity(member_attributes, entites_name[0], 0),
            new Entity(drone_attributes, entites_name[1], 0),
            new Entity(equipment_attributes, entites_name[2], 0),
    };

    private Scanner sc = new Scanner(System.in);
    enum OPERATIONS {
        ADD, EDIT, DELETE, SEARCH, NONE
    };


    private void displayTopMenu() {
        int n = top_menu.length;
        System.out.println();
        System.out.println("Top menu:");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-15s %s\n", top_menu_options[i], top_menu[i]);
        }
        System.out.print("Enter a command here: ");
    }
    void displaySelectEntity() {
        System.out.println();
        System.out.println("Select an entity to do the operation:");
        for (int i = 0; i < entites_name.length; i++) {
            System.out.print(entites_name[i] + "  ");
        }
        System.out.println();
    }

    private String getTokenFromLine() throws IOException {
        String inputLine;
        try {
            // read a line of user's input
            inputLine = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Something went wrong with the scanner.");
            throw new IOException(e.getMessage());
        }

        // System.out.println("You entered: " + inputLine);
        String commands[] = inputLine.split(" ", 0);

        // skip if it is an empty line
        if (commands.length <= 0) {
            return null;
        }

        int idx = 0;
        while (idx < commands.length && commands[idx].isEmpty()) {
            idx = idx + 1;
        }
        if (idx >= commands.length) {
            return null;
        }
        return commands[idx];
    }
    private String getLineStripped() throws IOException {
        String line;
        try {
            line = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Something went wrong with the scanner.");
            throw new IOException(e.getMessage());
        }

        if (line == null) {
            //System.out.println("length: 0");
            return null;
        }
        line = line.strip();
        //System.out.println("length: " + line.length());
        if (line.isBlank()) {
            return null;
        }
        return line;
    }

    private int readEntity() throws IOException {
        String entity;

        entity = getTokenFromLine();
        if (entity == null) {  //empty line
            return -1;
        } else if (entity.equals(entites_name[0])) {  //member
            return 0;
        } else if (entity.equals(entites_name[1])) {  //drone
            return 1;
        } else if (entity.equals(entites_name[2])) {  //equipment
            return 2;
        } else {  //not matched
            System.out.println("Unmatched entity name.");
            return -1;
        }
    }

    void moreOperation() throws IOException {
        int n = operation_menu.length;

        while (true) {
            System.out.println("\nMore operations:");
            for (int i = 0; i < n; i++) {
                System.out.printf("%-15s %s\n", operation_menu_option[i], operation_menu[i]);
            }
            System.out.print("Enter a command here: ");

            String command = getTokenFromLine();
            if (command == null) { continue; }
            if (command.equals(operation_menu_option[0])) {  //rent equipments
                System.out.println("Which member is renting?");
                String member = getLineStripped();
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
                    String equipment = getLineStripped();

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
                String member = getLineStripped();
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
                    String equipment = getLineStripped();

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
                String member = getLineStripped();
                if (member == null) {
                    System.out.println("No member selected. Operation failed.");
                    break;
                }

                System.out.println("Schedule the time for delivery. Enter the scheduled time here.");
                String schedule = getLineStripped();
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
                    String drone = getLineStripped();

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
                String member = getLineStripped();
                if (member == null) {
                    System.out.println("No member selected. Operation failed.");
                    break;
                }

                System.out.println("Schedule the time for pickup. Enter the scheduled time here.");
                String schedule = getLineStripped();
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
                    String drone = getLineStripped();

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

    boolean doOperation(String command) throws IOException {   //if return false, means to exit the program!
        OPERATIONS oper = OPERATIONS.NONE;

        if (command.equals(top_menu_options[0])) {  //add
            System.out.println("Which entity would you like to add?");
            oper = OPERATIONS.ADD;

        } else if (command.equals(top_menu_options[1])) {  //edit
            System.out.println("Which entity would you like to edit?");
            oper = OPERATIONS.EDIT;

        } else if (command.equals(top_menu_options[2])) {  //delete
            System.out.println("Which entity would you like to delete?");
            oper = OPERATIONS.DELETE;

        } else if (command.equals(top_menu_options[3])) {  //search
            System.out.println("Which entity would you like to search for?");
            oper = OPERATIONS.SEARCH;

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


        displaySelectEntity();
        int entity_id = readEntity();
        if (entity_id == -1) {
            return true;
        }
        Entity entity = entities[entity_id];
        //System.out.println(entity);

        if (oper == OPERATIONS.ADD) {
            // System.out.println("add a new record of " + entites_name[entity_id]);
            String attributes[] = new String[entity.getAttributeCount()];
            for (int i = 0; i < attributes.length; i++) {
                System.out.println("Enter the value for " + entity.attributes_name[i] + ":");
                attributes[i] = getLineStripped();
            }

            Record record = new Record(entity, attributes);
            if(entity.addRecord(record)) {
                System.out.println("Record added Successfully.");
            } else {
                System.out.println("Record could not be added.");
            }

        } else if (oper == OPERATIONS.EDIT) {
            // System.out.println("edit a record of " + entites_name[entity_id]);
            System.out.println("Which record do you want to edit?");
            System.out.println("Enter the value of primary key: " + entity.attributes_name[entity.primary_key]);
            String prime_value = getLineStripped();
            Record record = entity.getRecord(prime_value);

            if (record == null) {
                System.out.println("Record not found.");
            } else {
                while (true) {
                    System.out.println("Which attribute do you want to edit?");
                    for (int i = 0; i < entity.getAttributeCount(); i++) {
                        System.out.print(entity.attributes_name[i] + "  ");
                    }
                    System.out.println();
                    System.out.println("or enter a blank line to finish editing.");

                    String attr_name = getTokenFromLine();
                    int attr_idx = -1;
                    if (attr_name == null) { break; }
                    for (int i = 0; i < entity.getAttributeCount(); i++) {
                        if (entity.attributes_name[i].equals(attr_name)) {
                            attr_idx = i;
                            break;
                        }
                    }
                    if (attr_idx == -1) {
                        System.out.println("Unmatched attribute name.");
                        continue;
                    } else if (attr_idx == entity.primary_key) {
                        System.out.println("Primary key can't be edited!");
                        continue;
                    }

                    System.out.println("Enter the new value for " + entity.attributes_name[attr_idx]);
                    if (record.editRecord(attr_idx, getLineStripped())) {
                        System.out.println("Record edited Successfully.\n");
                    }
                }
            }

        } else if (oper == OPERATIONS.DELETE) {
            // System.out.println("delete a record of " + entites_name[entity_id]);
            System.out.println("Which record do you want to delete?");
            System.out.println("Enter the value of primary key: " + entity.attributes_name[entity.primary_key]);
            String prime_value = getLineStripped();

            if (!entity.deleteRecord(prime_value)) {
                System.out.println("Record not found.");
            } else {
                System.out.println("Record deleted Successfully.");
            }
        } else if (oper == OPERATIONS.SEARCH) {
            System.out.println("Search by the primary key: " + entity.attributes_name[entity.primary_key]);
            System.out.println("Enter the value:");
            String prime_value = getLineStripped();

            Record record = entity.getRecord(prime_value);
            if (record == null) {
                System.out.println("Record not found.");
            } else {
                record.display();
            }
        } else {  //shouldn't reach here
            System.out.println("Unmatched operation. Abort");
        }
        return true;
    }

    void menu() {
        while(true) {
            displayTopMenu();
            String command;

            try {
                command = getTokenFromLine();
            } catch (Exception e) {
                // System.out.println("Something went wrong with the scanner.");
                break;
            }
            if (command == null) {
                //skip if it is an empty line
                continue;
            }

            try {
                if(!doOperation(command)) {  //if returns false, means to exit the program
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
        um.menu();
    }
}
