package tbc.client.components;

import org.junit.Test;
import org.junit.runners.JUnit4;

import javax.swing.*;

import static org.junit.Assert.*;

public class ComponentStoreTest {

    @Test
    public void test_componentStoreInstance() {
        ComponentStore store1 = ComponentStore.getInstance();
        ComponentStore store2 = ComponentStore.getInstance();
        assertSame(store1, store2);
    }

    @Test
    public void test_componentStorePutGet() {
        ComponentStore store1 = ComponentStore.getInstance();
        ComponentStore store2 = ComponentStore.getInstance();
        JTextArea component = new JTextArea();
        store1.put("test", component);
        assertSame(component, store2.get("test"));
    }

    @Test
    public void test_componentStoreUpdate() {
        ComponentStore store1 = ComponentStore.getInstance();
        ComponentStore store2 = ComponentStore.getInstance();
        JTextArea component = new JTextArea();
        store1.put("test", component);
        assertSame(component, store2.get("test"));

        JTextArea component2 = new JTextArea();
        store1.put("test", component2);
        assertSame(component2, store2.get("test"));
        assertNotSame(component, store2.get("test"));
    }
}