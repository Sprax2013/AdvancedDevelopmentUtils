package de.sprax2013.advanced_dev_utils.spigot.particles;

import de.sprax2013.advanced_dev_utils.spigot.utils.BukkitServerUtils;
import org.bukkit.Location;

public class ParticleBuilder {
	private Particle particle;

	private Double x, y, z;
	private float offsetX = 0, offsetY = 0, offsetZ = 0;
	private int count = 1;

	private float particleData = 0F;
	private int data = 0;

	public ParticleBuilder(Particle particle) {
		this.particle = particle;
	}

	public ParticleBuilder setParticle(Particle particle) {
		this.particle = particle;

		return this;
	}

	public ParticleBuilder setCoords(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;

		return this;
	}

	public ParticleBuilder setCoords(Location loc) {
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();

		return this;
	}

	public ParticleBuilder setOffset(float offsetX, float offsetY, float offsetZ) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;

		return this;
	}

	public ParticleBuilder setCount(int count) {
		this.count = count;

		return this;
	}

	public ParticleBuilder setParticleData(float particleData) {
		this.particleData = particleData;

		return this;
	}

	public ParticleBuilder setData(int data) {
		this.data = data;

		return this;
	}

	public Object createPacket() {
		switch (BukkitServerUtils.getBukkitVersion()) {
		case "v1_8_R3":
			return new net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles(
					net.minecraft.server.v1_8_R3.EnumParticle.a(particle.id), false, x.floatValue(), y.floatValue(),
					z.floatValue(), offsetX, offsetY, offsetZ, particleData, count, data);
//		case "v1_12_R1":
//			return new net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles(
//					net.minecraft.server.v1_12_R1.EnumParticle.a(particle.id), false, x.floatValue(), y.floatValue(),
//					z.floatValue(), offsetX, offsetY, offsetZ, particleData, count, data);
		default:
			System.err.println("[!! WARNING !!] ParticleBuilder does not support Server-Version "
					+ BukkitServerUtils.getBukkitVersion());
			return null;
		}
	}
}