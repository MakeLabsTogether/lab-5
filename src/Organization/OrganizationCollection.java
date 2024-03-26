package Organization;

import java.util.LinkedList;

public class OrganizationCollection {
    private LinkedList<Organization> list;

    public OrganizationCollection(LinkedList<Organization> list) {
        this.list = list;
    }

    public LinkedList<Organization> getCollection() {
        return list;
    }

    @Override
    public String toString() {
        return "Список организаций: \n" + list;
    }
}
