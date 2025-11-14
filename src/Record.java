public class Record {
    Entity entity;
    String attributes[];

    public Entity getEntity() {
        return entity;
    }
    public String getPrimaryKey() {
        if (entity == null) {
            throw new NullPointerException("entity is null");
        }
        return attributes[entity.primary_key];
    }
    public int getAttributeCount() {
        if (entity == null) {
            return 0;
        }
        return attributes.length;
    }

    public Record(Entity entity) {
        this.entity = entity;
        attributes = null;
    }
    public Record(Entity entity, String attrs[]) {
        this.entity = entity;
        attributes = attrs;
    }

    public boolean editRecord(int idx, String new_value) {
        int prime = entity.primary_key;
        if (attributes == null) {
            throw new NullPointerException("attributes is null");
        }
        if (idx == prime) {
            System.out.println("Primary key can not be modified!");
            return false;
        }
        attributes[idx] = new_value;
        return true;
    }

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
    }
}
