package tbc.client.components;

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

    // syncronize put function across threads
    public void put(String key, Object value) {
        synchronized (Object.class) {
            components.put(key, value);
        }
    }

    public void update(String key, Object newValue) {
        synchronized (Object.class) {
            components.remove(key);
            components.put(key, newValue);
        }
    }

    // returns the Object stored at the key value
    public Object get(String key) {
        return components.get(key);
    }
}
