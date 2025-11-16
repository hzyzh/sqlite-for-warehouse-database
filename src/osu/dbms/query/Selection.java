package osu.dbms.query;

import java.util.Scanner;

import osu.dbms.exception.MyException;
import osu.dbms.utility.Utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Selection {
    public enum OPERATIONS {
        ADD, EDIT, DELETE, SEARCH, NONE
    };

    public static final String[] queryable_entites_name = {"member", "drone", "equipment"};
    // for each queryable entity, add a class for it (e.g. QueryDrone), define its Queryable object here and initialize it in initializeQueryableEntities()
    public static QueryDrone drone_queryable = null;

    public static void initializeQueryableEntities(Connection conn) throws SQLException {
        // for each queryable entity, initialize its Queryable object here
        drone_queryable = new QueryDrone(conn);
    }

    static void displaySelectEntity() {
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

    public static void doOperation(Scanner sc, Connection conn, OPERATIONS oper) throws IOException, SQLException {

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
            try {
                queryable_entity.addOp(sc, conn);
                System.out.println("Record added Successfully.");
                conn.commit();
            } catch (Exception e) {
                System.out.println("Error occurred during adding a new record: " + e.getMessage());
                conn.rollback();
            }

        }
        else if (oper == OPERATIONS.EDIT) {
            try {
                queryable_entity.editOp(sc, conn);
                System.out.println("Record updated Successfully.");
                conn.commit();
            } catch (Exception e) {
                System.out.println("Error occurred during editing a record: " + e.getMessage());
                conn.rollback();
            }

        } else if (oper == OPERATIONS.DELETE) {
            try {
                queryable_entity.deleteOp(sc, conn);
                System.out.println("Record deleted Successfully.");
                conn.commit();
            } catch (Exception e) {
                System.out.println("Error occurred during deleting a record: " + e.getMessage());
                conn.rollback();
            }
        } else if (oper == OPERATIONS.SEARCH) {
            try {
                queryable_entity.searchOp(sc, conn);
            } catch (MyException me) { 
            } catch (Exception e) {
                System.out.println("Error occurred during searching records: " + e.getMessage());
            }
        }
        else {  //shouldn't reach here
            System.out.println("Unmatched operation. Abort");
        }
    }
}
