import java.util.HashMap;
import java.util.Map;

public class Entity {
    public final String attributes_name[];
    final String entity_name;
    boolean weak_flag = false;
    final public int primary_key;
    final Map<String, Record> records = new HashMap();

    public Entity(String attrs[], String name, int primary) {
        attributes_name = attrs;
        entity_name = name;
        primary_key = primary;
    }
    public Entity(String attrs[], String name, int primary, boolean is_weak) {
        attributes_name = attrs;
        entity_name = name;
        primary_key = primary;
        weak_flag = is_weak;
    }

    public int getAttributeCount() {
        return attributes_name.length;
    }
    public boolean isCompatible(Record record) {
        if (record.getEntity() != this || record.getAttributeCount() != attributes_name.length) {
            return false;
        }
        return true;
    }

    public boolean addRecord(Record r) {
        if (!isCompatible(r)) {
            System.out.println("Error: Record is not compatible with Entity: " + entity_name);
            return false;
        }

        String prime_value = r.getPrimaryKey();
        if(prime_value == null) {
            System.out.println("Error: Primary key is null");
            return false;
        }
        if (records.containsKey(prime_value)) {
            System.out.println("Error: Primary key value is duplicate");
            return false;
        }
        records.put(prime_value, r);
        return true;
    }

    public Record getRecord(String prime_value) {
        if (prime_value == null) { return null; }
        return records.get(prime_value);
    }

    public boolean deleteRecord(String prime_value) {
        if (!records.containsKey(prime_value)) {
            return false;
        }
        records.remove(prime_value);
        return true;
    }
}