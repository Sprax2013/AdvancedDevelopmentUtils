package de.sprax2013.advanced_dev_utils.mojang;

import com.google.gson.*;
import de.sprax2013.advanced_dev_utils.misc.UUIDUtils;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.UUID;

public class TextureProperty {
    private final long timestamp;

    private final UUID profileID;
    private final String profileName;

    private final boolean signatureRequired;

    private final String skinURL, capeURL;
    private final boolean slim;

    private final String value, signature;

    private final boolean defaultSteve;

    public TextureProperty(long timestamp, UUID profileID, String profileName, boolean signatureRequired,
                           String skinURL, String capeURL, boolean slim, String value, String signature) {
        this.timestamp = timestamp;

        this.profileID = profileID;
        this.profileName = profileName;

        this.signatureRequired = signatureRequired;

        this.skinURL = skinURL;
        this.capeURL = capeURL;
        this.slim = slim;

        this.value = value;
        this.signature = signature;

        this.defaultSteve = profileID.hashCode() % 2 == 0;
    }

    /**
     * The timestamp is sometimes in the past (probably due to cached results?).
     *
     * @return The timestamp
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * @return The profile's {@link UUID}
     */
    public UUID getProfileUUID() {
        return this.profileID;
    }

    /**
     * @return The profile's username
     */
    public String getProfileUsername() {
        return this.profileName;
    }

    /**
     * @return true, if the signature is required
     */
    public boolean isSignatureRequired() {
        return this.signatureRequired;
    }

    /**
     * @return The Skin-URL or null
     */
    public String getSkinURL() {
        return this.skinURL;
    }

    /**
     * @return true, if {@link #getSkinURL()} is not <i>null</i>
     */
    public boolean hasSkinURL() {
        return this.skinURL != null;
    }

    /**
     * @return The Cape-URL or null
     */
    public String getCapeURL() {
        return this.capeURL;
    }

    /**
     * @return true, if {@link #hasCapeURL()} is not <i>null</i>
     */
    public boolean hasCapeURL() {
        return this.capeURL != null;
    }

    /**
     * @return true, if if the profile's Skin-Model is slim
     */
    public boolean isSlim() {
        return this.slim;
    }

    /**
     * The "value" Base64 string for the "textures" object
     *
     * @return The value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * A Base64 string containing signed data using Yggdrasil's private key
     *
     * @return The signature or null
     */
    public String getSignature() {
        return this.signature;
    }

    /**
     * The default skin depends on the Java hashCode of their UUID. Steve is used
     * for even hashes.
     *
     * @return True, if default skin is/would be Steve
     */
    public boolean isDefaultSteve() {
        return this.defaultSteve;
    }
}

class TexturePropertyDeSerializer implements JsonSerializer<TextureProperty>, JsonDeserializer<TextureProperty> {
    @Override
    public TextureProperty deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonArray jArr = (JsonArray) json;

        for (JsonElement jElem : jArr) {
            JsonObject jObj = jElem.getAsJsonObject();

            if (jObj.has("name") && jObj.getAsJsonPrimitive("name").getAsString().equalsIgnoreCase("textures")) {
                String value = jObj.has("value") ? jObj.getAsJsonPrimitive("value").getAsString() : null;

                if (value != null) {
                    String signature = jObj.has("signature") ? jObj.getAsJsonPrimitive("signature").getAsString()
                            : null;

                    JsonObject jValue = new JsonParser().parse(new String(Base64.getDecoder().decode(value)))
                            .getAsJsonObject();

                    long timestamp = jValue.getAsJsonPrimitive("timestamp").getAsLong();

                    UUID profileID = UUID.fromString(
                            UUIDUtils.addDashesToUUID(jValue.getAsJsonPrimitive("profileId").getAsString()));
                    String profileName = jValue.getAsJsonPrimitive("profileName").getAsString();

                    boolean signatureRequired = jValue.getAsJsonPrimitive("signatureRequired").getAsBoolean();

                    String skinURL = null, capeURL = null;

                    boolean slim = false;

                    if (jValue.has("textures")) {
                        if (jValue.getAsJsonObject("textures").has("SKIN")) {
                            JsonObject skinObj = jValue.getAsJsonObject("textures").getAsJsonObject("SKIN");

                            if (skinObj.has("url")) {
                                skinURL = skinObj.getAsJsonPrimitive("url").getAsString();
                            }

                            if (skinObj.has("metadata") && !skinObj.get("metadata").isJsonNull()) {
                                JsonObject skinMetaObj = skinObj.getAsJsonObject("metadata");

                                if (skinMetaObj.has("model")) {
                                    slim = skinMetaObj.getAsJsonPrimitive("model").getAsString()
                                            .equalsIgnoreCase("slim");
                                }
                            }
                        }

                        if (jValue.getAsJsonObject("textures").has("CAPE")) {
                            JsonObject capeObj = jValue.getAsJsonObject("textures").getAsJsonObject("CAPE");

                            if (capeObj.has("url")) {
                                capeURL = capeObj.getAsJsonPrimitive("url").getAsString();
                            }
                        }
                    }

                    return new TextureProperty(timestamp, profileID, profileName, signatureRequired, skinURL, capeURL,
                            slim, value, signature);
                }
            }
        }

        return null;
    }

    @Override
    public JsonElement serialize(TextureProperty src, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("name", "textures");

        if (src.getValue() != null) {
            JsonObject jValue = new JsonObject();

            jValue.addProperty("timestamp", src.getTimestamp());

            if (src.getProfileUUID() != null) {
                jValue.addProperty("profileId", src.getProfileUUID().toString().replace("-", ""));
            }

            if (src.getProfileUsername() != null) {
                jValue.addProperty("profileName", src.getProfileUsername());
            }

            if (src.getSignature() != null) {
                jValue.addProperty("signatureRequired", true);
            }

            if (src.getSkinURL() != null || src.getCapeURL() != null) {
                JsonObject jTextures = new JsonObject();

                if (src.getSkinURL() != null) {
                    JsonObject jSkin = new JsonObject();
                    jSkin.addProperty("url", src.getSkinURL());

                    if (src.isSlim()) {
                        JsonObject jSkinMeta = new JsonObject();
                        jSkinMeta.addProperty("model", "slim");

                        jSkin.add("metadata", jSkinMeta);
                    }

                    jTextures.add("SKIN", jSkin);
                }

                if (src.getCapeURL() != null) {
                    JsonObject jCape = new JsonObject();
                    jCape.addProperty("url", src.getCapeURL());

                    jTextures.add("CAPE", jCape);
                }

                jValue.add("textures", jTextures);
            }

            result.addProperty("value",
                    Base64.getEncoder().encodeToString(jValue.toString().getBytes(Charset.forName("UTF-8"))));
        }

        if (src.getSignature() != null) {
            result.addProperty("signature", src.getSignature());
        }

        return result;
    }
}