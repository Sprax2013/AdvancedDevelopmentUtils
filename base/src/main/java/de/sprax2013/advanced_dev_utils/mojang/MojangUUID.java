package de.sprax2013.advanced_dev_utils.mojang;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.sprax2013.advanced_dev_utils.misc.UUIDUtils;

import java.lang.reflect.Type;
import java.util.UUID;

public class MojangUUID {
    private final UUID id;
    private final String name;

    private final boolean legacy;
    private final boolean demo;

    public MojangUUID(UUID uuid, String name, boolean legacy, boolean demo) {
        this.id = uuid;
        this.name = name;

        this.legacy = legacy;
        this.demo = demo;
    }

    /**
     * @return The {@link UUID}
     */
    public UUID getUUID() {
        return this.id;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return this.name;
    }

    /**
     * @return True, if profile was not migrated to mojang.com
     */
    public boolean isLegacy() {
        return this.legacy;
    }

    /**
     * @return True, if account is unpaid
     */
    public boolean isDemo() {
        return this.demo;
    }
}

class MojangUUIDDeSerializer implements JsonSerializer<MojangUUID>, JsonDeserializer<MojangUUID> {
    @Override
    public MojangUUID deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jObj = (JsonObject) json;

        UUID id = jObj.has("id")
                ? UUID.fromString(UUIDUtils.addDashesToUUID(jObj.getAsJsonPrimitive("id").getAsString()))
                : null;

        String name = jObj.has("name") ? jObj.getAsJsonPrimitive("name").getAsString() : null;

        boolean legacy = jObj.has("legacy") ? jObj.getAsJsonPrimitive("legacy").getAsBoolean() : false;
        boolean demo = jObj.has("demo") ? jObj.getAsJsonPrimitive("demo").getAsBoolean() : false;

        if (id != null && name != null) {
            return new MojangUUID(id, name, legacy, demo);
        }

        return null;
    }

    @Override
    public JsonElement serialize(MojangUUID src, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        if (src.getUUID() != null) {
            result.addProperty("id", src.getUUID().toString().replace("-", ""));
        }

        if (src.getUsername() != null) {
            result.addProperty("name", src.getUsername());
        }

        if (src.isLegacy()) {
            result.addProperty("legacy", true);
        }

        if (src.isDemo()) {
            result.addProperty("demo", true);
        }

        return result;
    }
}