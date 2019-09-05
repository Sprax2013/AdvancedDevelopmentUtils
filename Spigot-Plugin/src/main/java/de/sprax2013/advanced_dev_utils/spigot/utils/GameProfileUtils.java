package de.sprax2013.advanced_dev_utils.spigot.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.sprax2013.advanced_dev_utils.mojang.MojangAPI;
import de.sprax2013.advanced_dev_utils.mojang.MojangProfile;

public class GameProfileUtils {
	public static GameProfile getGameProfile(Player p) {
		try {
			return (GameProfile) NMSClassUtils.getNMSClass("org.bukkit.craftbukkit.?.entity.CraftPlayer")
					.getMethod("getProfile").invoke(p);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static GameProfile toGameProfile(UUID uuid) {
		return toGameProfile(MojangAPI.getProfile(uuid));
	}

	public static GameProfile toGameProfile(MojangProfile profile) {
		if (profile != null) {
			GameProfile result = new GameProfile(profile.getUUID(), profile.getUsername());

			if (profile.hasTextureProp()) {
				result.getProperties().put("textures", new Property("textures", profile.getTextureProp().getValue(),
						profile.getTextureProp().getSignature()));
			}

			return result;
		}

		return null;
	}

	public static String getSkinURL(GameProfile gp) {
		try {
			return new JsonParser()
					.parse(new String(Base64.getDecoder()
							.decode(gp.getProperties().get("textures").iterator().next().getValue())))
					.getAsJsonObject().get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url")
					.getAsString();
		} catch (@SuppressWarnings("unused") NullPointerException noSkinData) {
			return null;
		}
	}

	public static String getCapeURL(GameProfile gp) {
		try {
			return new JsonParser()
					.parse(new String(Base64.getDecoder()
							.decode(gp.getProperties().get("textures").iterator().next().getValue())))
					.getAsJsonObject().get("textures").getAsJsonObject().get("CAPE").getAsJsonObject().get("url")
					.getAsString();
		} catch (@SuppressWarnings("unused") NullPointerException noSkinData) {
			return null;
		}
	}

	public static String getTextureValue(GameProfile gp) {
		try {
			return gp.getProperties().get("textures").iterator().next().getValue();
		} catch (@SuppressWarnings("unused") NullPointerException noSkinData) {
			return null;
		}
	}

	public static String getTextureSignature(GameProfile gp) {
		try {
			return gp.getProperties().get("textures").iterator().next().getSignature();
		} catch (@SuppressWarnings("unused") NullPointerException noSkinData) {
			return null;
		}
	}
}