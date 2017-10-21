package ir.pkokabi.pdialogs.DatePicker;

import java.util.LinkedHashMap;
import java.util.Set;

public class DialogLinkedMap<K, V> extends LinkedHashMap<K, V> {

    public V getValueByIndex(int i) {
        Entry<K, V> entry = this.getEntryByIndex(i);
        if (entry == null) return null;
        return entry.getValue();
    }

    public K getKeyByIndex(int i) {
        Entry<K, V> entry = this.getEntryByIndex(i);
        if (entry == null) return null;
        return entry.getKey();
    }

    public Entry<K, V> getEntryByIndex(int i) {
        Set<Entry<K, V>> entries = entrySet();
        int j = 0;
        for (Entry<K, V> entry : entries)
            if (j++ == i) return entry;
        return null;
    }

    public String getKeyByValue(Object value) {
        for (Object o : keySet()) {
            if (get(o).equals(value)) {
                return o.toString();
            }
        }
        return null;
    }

}

