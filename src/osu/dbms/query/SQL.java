package osu.dbms.query;

public class SQL {
    public enum TYPE{
        STR, INT, REAL, DATE
    };

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
