/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcmodded.extraattributes.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

import net.mcmodded.extraattributes.ExtraAttributesMod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExtraAttributesModAttributes {
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ExtraAttributesMod.MODID);
	public static final RegistryObject<Attribute> BLEEDDAMAGE = ATTRIBUTES.register("bleeddamage", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".bleeddamage", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> BLEEDCHANCE = ATTRIBUTES.register("bleedchance", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".bleedchance", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> LIFESTEALCHANCE = ATTRIBUTES.register("lifestealchance", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".lifestealchance", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> LIFESTEALDAMAGE = ATTRIBUTES.register("lifestealdamage", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".lifestealdamage", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> STUNCHANCE = ATTRIBUTES.register("stunchance", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".stunchance", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> PANICCHANCE = ATTRIBUTES.register("panicchance", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".panicchance", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> REFLECTCHANCE = ATTRIBUTES.register("reflectchance", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".reflectchance", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> REFLECTDAMAGE = ATTRIBUTES.register("reflectdamage", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".reflectdamage", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> FREEZECHANCE = ATTRIBUTES.register("freezechance", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".freezechance", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> FREEZEDAMAGE = ATTRIBUTES.register("freezedamage", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".freezedamage", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> BURNCHANCE = ATTRIBUTES.register("burnchance", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".burnchance", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> BURNDAMAGE = ATTRIBUTES.register("burndamage", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".burndamage", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> CRITCHANCE = ATTRIBUTES.register("critchance", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".critchance", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> CRITDAMAGE = ATTRIBUTES.register("critdamage", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".critdamage", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> LIGHTNINGCHANCE = ATTRIBUTES.register("lightningchance", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".lightningchance", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> LIGHTNINGDAMAGE = ATTRIBUTES.register("lightningdamage", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".lightningdamage", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> SWEEPCHANCE = ATTRIBUTES.register("sweepchance", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".sweepchance", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> SWEEPDAMAGE = ATTRIBUTES.register("sweepdamage", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".sweepdamage", 0, 0, 100)).setSyncable(true));
	public static final RegistryObject<Attribute> REGENTIMER = ATTRIBUTES.register("regentimer", () -> (new RangedAttribute("attribute." + ExtraAttributesMod.MODID + ".regentimer", 0, 0, 256)).setSyncable(true));

	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			ATTRIBUTES.register(FMLJavaModLoadingContext.get().getModEventBus());
		});
	}

	@SubscribeEvent
	public static void addAttributes(EntityAttributeModificationEvent event) {
		List<EntityType<? extends LivingEntity>> entityTypes = event.getTypes();
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, BLEEDDAMAGE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, BLEEDCHANCE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, LIFESTEALCHANCE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, LIFESTEALDAMAGE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, STUNCHANCE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, PANICCHANCE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, REFLECTCHANCE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, REFLECTDAMAGE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, FREEZECHANCE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, FREEZEDAMAGE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, BURNCHANCE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, BURNDAMAGE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, CRITCHANCE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, CRITDAMAGE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, LIGHTNINGCHANCE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, LIGHTNINGDAMAGE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, SWEEPCHANCE.get());
			}
		});
		entityTypes.forEach((e) -> {
			Class<? extends Entity> baseClass = e.getBaseClass();
			if (baseClass.isAssignableFrom(Monster.class)) {
				event.add(e, SWEEPDAMAGE.get());
			}
		});
		event.add(EntityType.PLAYER, REGENTIMER.get());
	}
}
