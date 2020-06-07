package de.sprax2013.advanced_dev_utils.mojang;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Map.Entry;
import java.util.TreeMap;

public class MojangHistory {
    private final String initialName;

    private final TreeMap<Long, String> history;

    public MojangHistory(String initialName, TreeMap<Long, String> history) {
        this.initialName = initialName;
        this.history = history == null ? new TreeMap<>() : history;
    }

    /**
     * @return The initial username
     */
    public String getInitialUsername() {
        return this.initialName;
    }

    /**
     * <i>Key</i> =&gt; The Java timestamp in milliseconds (changedToAt)<br>
     * <i>Value</i> =&gt; The username
     *
     * @return The history-TreeMap. Never null
     */
    public TreeMap<Long, String> getHistory() {
        return this.history;
    }
}

class MojangHistoryDeSerializer implements JsonSerializer<MojangHistory>, JsonDeserializer<MojangHistory> {
    @Override
    public MojangHistory deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        String initialName = null;
        TreeMap<Long, String> history = new TreeMap<>();

        for (JsonElement jElem : json.getAsJsonArray()) {

            JsonObject jObj = jElem.getAsJsonObject();

            String name = jObj.has("name") ? jObj.getAsJsonPrimitive("name").getAsString() : null;

            if (name != null) {

                long changedToAt = jObj.has("changedToAt") ? jObj.getAsJsonPrimitive("changedToAt").getAsLong() : -1;

                if (changedToAt == -1) {
                    initialName = name;
                } else {
                    history.put(changedToAt, name);
                }
            }
        }

        if (initialName != null) {
            return new MojangHistory(initialName, history);
        }

        return null;
    }

    @Override
    public JsonElement serialize(MojangHistory src, Type type, JsonSerializationContext context) {
        JsonArray result = new JsonArray();

        if (src.getInitialUsername() != null) {
            JsonObject initialName = new JsonObject();
            initialName.addProperty("name", src.getInitialUsername());
            result.add(initialName);
        }

        for (Entry<Long, String> set : src.getHistory().entrySet()) {
            JsonObject jObj = new JsonObject();
            jObj.addProperty("name", set.getValue());
            jObj.addProperty("changedToAt", set.getKey());
            result.add(jObj);
        }

        return result;
    }
}