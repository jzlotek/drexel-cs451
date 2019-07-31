package tbc.client.components;

import javax.swing.*;
import java.util.HashMap;

public class ComponentStore {

    private static HashMap<String, Object> components;
    private static ComponentStore instance = null;

    private ComponentStore() {
        components = new HashMap<>();
    }

    // Thread safe component store
    public static ComponentStore getInstance() {
        if (instance == null) {
            synchronized (ComponentStore.class) {
                if (instance == null) {
                    instance = new ComponentStore();
                }
            }
        }
        return instance;
    }

    public void put(String key, Object value) {
        synchronized (Object.class) {
            components.put(key, value);
        }
    }

    public Object get(String key) {
        return components.get(key);
    }
}
