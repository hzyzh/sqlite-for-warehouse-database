package osu.dbms.query;

import java.sql.PreparedStatement;

public class SQL {
    public enum TYPE{
        STR, INT, REAL, DATE
    };

    public static void setArg(PreparedStatement pstmt, int index, TYPE type, String value) throws Exception {
        switch(type){
            case STR:
                if (value == null)
                    pstmt.setNull(index, java.sql.Types.VARCHAR);
                else
                    pstmt.setString(index, value);
                break;
            case INT:
                if (value == null)
                    pstmt.setNull(index, java.sql.Types.INTEGER);
                else
                    pstmt.setInt(index, Integer.parseInt(value));
                break;
            case REAL:
                if (value == null)
                    pstmt.setNull(index, java.sql.Types.DOUBLE);
                else
                    pstmt.setDouble(index, Double.parseDouble(value));
                break;
            case DATE:
                if (value == null)
                    pstmt.setNull(index, java.sql.Types.DATE);
                else
                    pstmt.setString(index, value); // assuming the date is in correct format
                break;
            default:
                throw new Exception("Unsupported SQL TYPE");
        }
    }

    /*
    public void display() {
        if (attributes == null || entity == null) {
            throw new NullPointerException("attributes or entity is null");
        }
        for (int i = 0; i < attributes.length; i++) {
            System.out.print(entity.attributes_name[i] + ": ");
            if (attributes[i] != null) {
                System.out.print('"' + attributes[i] + '"');
            } else { System.out.print("null"); }
            System.out.print("  ");
        }
        System.out.println();
    } */
}
