package de.sprax2013.advanced_dev_utils.mojang;

import com.google.gson.JsonArray;
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

/**
 * Holds a player's username plus any additional information about them (e.g.
 * skins).
 */
public class MojangProfile {
    private final UUID id;
    private final String name;

    private final TextureProperty texProp;

    public MojangProfile(UUID id, String name, TextureProperty texProp) {
        this.id = id;
        this.name = name;

        this.texProp = texProp;
    }

    /**
     * Gets the profile's UUID.
     *
     * @return The {@link UUID}
     */
    public UUID getUUID() {
        return this.id;
    }

    /**
     * Gets the profile's username.
     *
     * @return The username
     */
    public String getUsername() {
        return this.name;
    }

    /**
     * Gets the profile's texture-properties.
     *
     * @return {@link TextureProperty}-Object or null
     *
     * @see #hasTextureProp()
     */
    public TextureProperty getTextureProp() {
        return this.texProp;
    }

    /**
     * @return true, if {@link #getTextureProp()} is not <i>null</i>
     */
    public boolean hasTextureProp() {
        return this.texProp != null;
    }

    /*
     * Generates an JSON-String that should be identically to the one from the
     * external API.
     */
    @Override
    public String toString() {
        return MojangAPI.gson.toJson(this);
    }
}

class MojangProfileDeSerializer implements JsonSerializer<MojangProfile>, JsonDeserializer<MojangProfile> {
    @Override
    public MojangProfile deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jObj = (JsonObject) json;

        UUID id = jObj.has("id")
                ? UUID.fromString(UUIDUtils.addDashesToUUID(jObj.getAsJsonPrimitive("id").getAsString()))
                : null;
        String name = jObj.has("name") ? jObj.getAsJsonPrimitive("name").getAsString() : null;

        TextureProperty texProp = null;

        if (jObj.has("properties")) {
            texProp = context.deserialize(jObj.getAsJsonArray("properties"), TextureProperty.class);
        }

        return new MojangProfile(id, name, texProp);
    }

    @Override
    public JsonElement serialize(MojangProfile src, Type type, JsonSerializationContext context) {

        JsonObject result = new JsonObject();

        if (src.getUUID() != null) {
            result.addProperty("id", src.getUUID().toString().replace("-", ""));
        }

        if (src.getUsername() != null) {
            result.addProperty("name", src.getUsername());
        }

        if (src.getTextureProp() != null) {
            JsonArray jProps = new JsonArray();
            jProps.add(context.serialize(src.getTextureProp()));

            result.add("properties", jProps);
        }

        return result;
    }
}