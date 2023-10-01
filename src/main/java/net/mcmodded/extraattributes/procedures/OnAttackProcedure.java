package net.mcmodded.extraattributes.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber
public class OnAttackProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		Entity entity = event.getEntity();
		if (event != null && entity != null) {
			execute(event, entity.getLevel(), entity, event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((sourceentity instanceof Player || sourceentity instanceof LivingEntity) && !(entity == sourceentity) && (entity instanceof Player || entity instanceof LivingEntity)) {
			if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:bleedchance"))) != null && entity instanceof LivingEntity
					&& ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:bleeddamage"))) != null) {
				if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:bleedchance"))).getValue() / 100) {
					if (entity instanceof LivingEntity _entity)
						_entity.hurt(new DamageSource(_entity.level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)) {
							@Override
							public Component getLocalizedDeathMessage(LivingEntity _msgEntity) {
								String _translatekey = "death.attack." + "bleed";
								if (this.getEntity() == null && this.getDirectEntity() == null) {
									return _msgEntity.getKillCredit() != null
											? Component.translatable(_translatekey + ".player", _msgEntity.getDisplayName(), _msgEntity.getKillCredit().getDisplayName())
											: Component.translatable(_translatekey, _msgEntity.getDisplayName());
								} else {
									Component _component = this.getEntity() == null ? this.getDirectEntity().getDisplayName() : this.getEntity().getDisplayName();
									ItemStack _itemstack = ItemStack.EMPTY;
									if (this.getEntity() instanceof LivingEntity _livingentity)
										_itemstack = _livingentity.getMainHandItem();
									return !_itemstack.isEmpty() && _itemstack.hasCustomHoverName()
											? Component.translatable(_translatekey + ".item", _msgEntity.getDisplayName(), _component, _itemstack.getDisplayName())
											: Component.translatable(_translatekey, _msgEntity.getDisplayName(), _component);
								}
							}
						}, (float) (((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 100)
								* ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:bleeddamage"))).getValue()));
					if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealchance"))) != null && entity instanceof LivingEntity
							&& ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealdamage"))) != null) {
						if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealchance"))).getValue() / 100) {
							if (sourceentity instanceof LivingEntity _entity)
								_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 100)
										* ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:bleeddamage"))).getValue()));
						}
					}
				}
			}
			if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealchance"))) != null && entity instanceof LivingEntity
					&& ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealdamage"))) != null) {
				if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealchance"))).getValue() / 100) {
					if (sourceentity instanceof LivingEntity _entity)
						_entity.setHealth(
								(float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (((LivingEntity) sourceentity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getValue() / 100)
										* ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealdamage"))).getValue()));
				}
			}
			if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:critchance"))) != null && entity instanceof LivingEntity
					&& ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:critdamage"))) != null) {
				if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:critchance"))).getValue() / 100) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.CRIT, (entity.getX()), (entity.getY()), (entity.getZ()), 1, 1, 2, 1, 1);
					if (entity instanceof LivingEntity _entity)
						_entity.hurt(new DamageSource(_entity.level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)) {
							@Override
							public Component getLocalizedDeathMessage(LivingEntity _msgEntity) {
								String _translatekey = "death.attack." + "critdamage";
								if (this.getEntity() == null && this.getDirectEntity() == null) {
									return _msgEntity.getKillCredit() != null
											? Component.translatable(_translatekey + ".player", _msgEntity.getDisplayName(), _msgEntity.getKillCredit().getDisplayName())
											: Component.translatable(_translatekey, _msgEntity.getDisplayName());
								} else {
									Component _component = this.getEntity() == null ? this.getDirectEntity().getDisplayName() : this.getEntity().getDisplayName();
									ItemStack _itemstack = ItemStack.EMPTY;
									if (this.getEntity() instanceof LivingEntity _livingentity)
										_itemstack = _livingentity.getMainHandItem();
									return !_itemstack.isEmpty() && _itemstack.hasCustomHoverName()
											? Component.translatable(_translatekey + ".item", _msgEntity.getDisplayName(), _component, _itemstack.getDisplayName())
											: Component.translatable(_translatekey, _msgEntity.getDisplayName(), _component);
								}
							}
						}, (float) (((LivingEntity) sourceentity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getValue()
								* (((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:critdamage"))).getValue() / 100)));
					if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealchance"))) != null && entity instanceof LivingEntity
							&& ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealdamage"))) != null) {
						if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealchance"))).getValue() / 100) {
							if (sourceentity instanceof LivingEntity _entity)
								_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
										+ (((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:critdamage"))).getValue() / 100)
												* ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealdamage"))).getValue()));
						}
					}
				}
			}
			if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:stunchance"))) != null) {
				if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:stunchance"))).getValue() / 100) {
					if (entity instanceof LivingEntity _entity && !_entity.level.isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 9));
					if (entity instanceof LivingEntity _entity && !_entity.level.isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 9));
					if (entity instanceof LivingEntity _entity && !_entity.level.isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 9));
				}
			}
			if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:freezechance"))) != null && entity instanceof LivingEntity
					&& ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:freezedamage"))) != null) {
				if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:freezechance"))).getValue() / 100) {
					entity.setTicksFrozen((int) (entity.getTicksFrozen() + ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:freezedamage"))).getValue() * 20));
				}
			}
			if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:burnchance"))) != null && entity instanceof LivingEntity
					&& ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:burndamage"))) != null) {
				if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:burnchance"))).getValue() / 100) {
					entity.setSecondsOnFire((int) (((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:burndamage"))).getValue() * 20));
				}
			}
			if (entity instanceof LivingEntity && ((LivingEntity) entity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:reflectchance"))) != null && entity instanceof LivingEntity
					&& ((LivingEntity) entity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:reflectdamage"))) != null) {
				if (Math.random() < ((LivingEntity) entity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:reflectchance"))).getValue() / 100) {
					if (sourceentity instanceof LivingEntity _entity)
						_entity.hurt(new DamageSource(_entity.level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)) {
							@Override
							public Component getLocalizedDeathMessage(LivingEntity _msgEntity) {
								String _translatekey = "death.attack." + "reflect";
								if (this.getEntity() == null && this.getDirectEntity() == null) {
									return _msgEntity.getKillCredit() != null
											? Component.translatable(_translatekey + ".player", _msgEntity.getDisplayName(), _msgEntity.getKillCredit().getDisplayName())
											: Component.translatable(_translatekey, _msgEntity.getDisplayName());
								} else {
									Component _component = this.getEntity() == null ? this.getDirectEntity().getDisplayName() : this.getEntity().getDisplayName();
									ItemStack _itemstack = ItemStack.EMPTY;
									if (this.getEntity() instanceof LivingEntity _livingentity)
										_itemstack = _livingentity.getMainHandItem();
									return !_itemstack.isEmpty() && _itemstack.hasCustomHoverName()
											? Component.translatable(_translatekey + ".item", _msgEntity.getDisplayName(), _component, _itemstack.getDisplayName())
											: Component.translatable(_translatekey, _msgEntity.getDisplayName(), _component);
								}
							}
						}, (float) ((((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getValue() / 100)
								* ((LivingEntity) entity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:reflectdamage"))).getValue()));
				}
			}
			if (entity instanceof LivingEntity && ((LivingEntity) entity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:panicchance"))) != null) {
				if (Math.random() < ((LivingEntity) entity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:panicchance"))).getValue() / 100) {
					if (sourceentity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal("You panic and attempt to run away"), true);
					if (Math.random() < 0.5) {
						if (Math.random() < 0.5) {
							sourceentity.setDeltaMovement(new Vec3(0.5, 0, 0));
							if (Math.random() < 0.5) {
								if (event != null && event.isCancelable()) {
									event.setCanceled(true);
								}
							}
						} else {
							sourceentity.setDeltaMovement(new Vec3((-0.5), 0, 0));
							if (Math.random() < 0.5) {
								if (event != null && event.isCancelable()) {
									event.setCanceled(true);
								}
							}
						}
					} else {
						if (Math.random() < 0.5) {
							sourceentity.setDeltaMovement(new Vec3(0, 0, 1));
							if (Math.random() < 0.5) {
								if (event != null && event.isCancelable()) {
									event.setCanceled(true);
								}
							}
						} else {
							sourceentity.setDeltaMovement(new Vec3(0, 0, (-1)));
							if (Math.random() < 0.5) {
								if (event != null && event.isCancelable()) {
									event.setCanceled(true);
								}
							}
						}
					}
				}
			}
			if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lightningchance"))) != null && entity instanceof LivingEntity
					&& ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lightningdamage"))) != null) {
				if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lightningchance"))).getValue() / 100) {
					if (world instanceof ServerLevel _level) {
						LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
						entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())));
						entityToSpawn.setVisualOnly(true);
						_level.addFreshEntity(entityToSpawn);
					}
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.LIGHTNING_BOLT)),
							(float) ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lightningdamage"))).getValue());
					if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealchance"))) != null && entity instanceof LivingEntity
							&& ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealdamage"))) != null) {
						if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealchance"))).getValue() / 100) {
							if (sourceentity instanceof LivingEntity _entity)
								_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
										+ (((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lightningdamage"))).getValue() / 100)
												* ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealdamage"))).getValue()));
						}
					}
				}
			}
			if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:sweepchance"))) != null && entity instanceof LivingEntity
					&& ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:sweepchance"))) != null) {
				if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:sweepchance"))).getValue() / 100) {
					entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
					{
						final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
								.collect(Collectors.toList());
						for (Entity entityiterator : _entfound) {
							if (!(entityiterator == sourceentity)) {
								if (entity instanceof ServerPlayer || entity instanceof Player || entity instanceof LivingEntity) {
									entityiterator.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
									if (world instanceof ServerLevel _level)
										_level.sendParticles(ParticleTypes.SWEEP_ATTACK, (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 1, 1, 2, 1, 1);
									if (entityiterator instanceof LivingEntity _entity)
										_entity.hurt(new DamageSource(_entity.level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)) {
											@Override
											public Component getLocalizedDeathMessage(LivingEntity _msgEntity) {
												String _translatekey = "death.attack." + "SWEEP";
												if (this.getEntity() == null && this.getDirectEntity() == null) {
													return _msgEntity.getKillCredit() != null
															? Component.translatable(_translatekey + ".player", _msgEntity.getDisplayName(), _msgEntity.getKillCredit().getDisplayName())
															: Component.translatable(_translatekey, _msgEntity.getDisplayName());
												} else {
													Component _component = this.getEntity() == null ? this.getDirectEntity().getDisplayName() : this.getEntity().getDisplayName();
													ItemStack _itemstack = ItemStack.EMPTY;
													if (this.getEntity() instanceof LivingEntity _livingentity)
														_itemstack = _livingentity.getMainHandItem();
													return !_itemstack.isEmpty() && _itemstack.hasCustomHoverName()
															? Component.translatable(_translatekey + ".item", _msgEntity.getDisplayName(), _component, _itemstack.getDisplayName())
															: Component.translatable(_translatekey, _msgEntity.getDisplayName(), _component);
												}
											}
										}, (float) (((LivingEntity) sourceentity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getValue()
												* (((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:sweepdamage"))).getValue() / 100)));
									if (entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealchance"))) != null
											&& entity instanceof LivingEntity && ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealdamage"))) != null) {
										if (Math.random() < ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealchance"))).getValue() / 100) {
											if (sourceentity instanceof LivingEntity _entity)
												_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
														+ (((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:sweepdamage"))).getValue() / 100)
																* ((LivingEntity) sourceentity).getAttribute(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("extra_attributes:lifestealdamage"))).getValue()));
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
