package net.mcmodded.extraattributes.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcmodded.extraattributes.init.ExtraAttributesModAttributes;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class RegenAttributeProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player.level, event.player);
		}
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (((LivingEntity) entity).getAttribute(ExtraAttributesModAttributes.REGENTIMER.get()).getValue() > 0 && !world.isClientSide()) {
			if (Math.random() < ((LivingEntity) entity).getAttribute(ExtraAttributesModAttributes.REGENTIMER.get()).getValue() / 300) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) != 0) {
					if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)
							|| (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) == (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1))) {
						if (entity instanceof LivingEntity _entity)
							_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 1));
					}
				}
			}
		}
	}
}
