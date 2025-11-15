package osu.dbms.query;

import java.util.Scanner;

import osu.dbms.utility.Utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Selection {
    public enum OPERATIONS {
        ADD, EDIT, DELETE, SEARCH, NONE
    };

    public static final String[] queryable_entites_name = {"member", "drone", "equipment"};
    // for each queryable entity, define its Queryable object here and initialize it in initializeQueryableEntities()
    public static QueryDrone drone_queryable = null;

    public static void initializeQueryableEntities(Connection conn) throws SQLException {
        // for each queryable entity, initialize its Queryable object here
        drone_queryable = new QueryDrone(conn);
    }

    static void displaySelectEntity() {
        System.out.println();
        System.out.println("Select an entity to do the operation:");
        for (int i = 0; i < queryable_entites_name.length; i++) {
            System.out.print(queryable_entites_name[i] + "  ");
        }
        System.out.println();
    }

    static int readEntity(Scanner sc) throws IOException {
        String entity;

        entity = Utility.getTokenFromLine(sc);
        if (entity == null) {  //empty line
            return -1;
        } 
        for (int i = 0; i < entity.length(); i++) {
            if (entity.equals(queryable_entites_name[i]))
                return i;
        }
        System.out.println("Unmatched entity name.");
        return -1;
    }

    public static void doOperation(Scanner sc, Connection conn, OPERATIONS oper) throws IOException {

        displaySelectEntity();
        int entity_id = readEntity(sc);
        if (entity_id == -1) {
            return;
        }
        Queryable queryable_entity = null;
        switch (entity_id) {
            case 0:
                // member entity is not queryable yet
                System.out.println("The selected entity is not queryable yet.");
                return;
            case 1:
                queryable_entity = drone_queryable;
                break;
            case 2:
                // equipment entity is not queryable yet
                System.out.println("The selected entity is not queryable yet.");
                return;
            default:
                // shouldn't reach here
                System.out.println("Unmatched entity id. Abort.");
                return;
        }

        if (oper == OPERATIONS.ADD) {
            // System.out.println("add a new record of " + entites_name[entity_id]);
            try {
                queryable_entity.addOp(sc, conn);
            } catch (Exception e) {
                System.out.println("Error occurred during adding a new record: " + e.getMessage());
            } /*
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

        } /*
        else if (oper == OPERATIONS.EDIT) {
            // System.out.println("edit a record of " + entites_name[entity_id]);
            System.out.println("Which record do you want to edit?");
            System.out.println("Enter the value of primary key: " + entity.attributes_name[entity.primary_key]);
            String prime_value = Utility.getLineStripped(sc);
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

                    String attr_name = Utility.getTokenFromLine(sc);
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
                    if (record.editRecord(attr_idx, Utility.getLineStripped(sc))) {
                        System.out.println("Record edited Successfully.\n");
                    }
                }
            }

        } else if (oper == OPERATIONS.DELETE) {
            // System.out.println("delete a record of " + entites_name[entity_id]);
            System.out.println("Which record do you want to delete?");
            System.out.println("Enter the value of primary key: " + entity.attributes_name[entity.primary_key]);
            String prime_value = Utility.getLineStripped(sc);

            if (!entity.deleteRecord(prime_value)) {
                System.out.println("Record not found.");
            } else {
                System.out.println("Record deleted Successfully.");
            }
        } else if (oper == OPERATIONS.SEARCH) {
            System.out.println("Search by the primary key: " + entity.attributes_name[entity.primary_key]);
            System.out.println("Enter the value:");
            String prime_value = Utility.getLineStripped(sc);

            Record record = entity.getRecord(prime_value);
            if (record == null) {
                System.out.println("Record not found.");
            } else {
                record.display();
            }
        } */
        else {  //shouldn't reach here
            System.out.println("Unmatched operation. Abort");
        }
    }
}
