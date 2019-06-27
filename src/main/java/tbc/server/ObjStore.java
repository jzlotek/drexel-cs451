package tbc.server;

import java.util.ArrayList;
import java.util.List;

public class ObjStore {

    private static List<String> store;

    private ObjStore(){}

    public static List<String> getInstance() {

        if (store == null) {
            store = new ArrayList<>();
        }

        return store;
    }
}
