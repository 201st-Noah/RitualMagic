package be.noah.ritual_magic.items.armor;

import be.noah.ritual_magic.client.DwarvenArmorRenderer;
import be.noah.ritual_magic.mana.ManaType;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.UUID;
import java.util.function.Consumer;

public class DwarvenArmor extends PlateArmor {

    private static final UUID MOVMENT_SLOWDOWN_UUID = UUID.fromString("11111111-2222-3333-4444-555555555556");
    private static final UUID[] ARMOR_UUIDS = new UUID[]{
            UUID.fromString("00000000-0000-0000-0000-000000000001"), // HEAD
            UUID.fromString("00000000-0000-0000-0000-000000000002"), // CHEST
            UUID.fromString("00000000-0000-0000-0000-000000000003"), // LEGS
            UUID.fromString("00000000-0000-0000-0000-000000000004")  // FEET
    };

    private static final UUID[] TOUGHNESS_UUIDS = new UUID[]{
            UUID.fromString("00000000-0000-0000-0000-000000000005"),
            UUID.fromString("00000000-0000-0000-0000-000000000006"),
            UUID.fromString("00000000-0000-0000-0000-000000000007"),
            UUID.fromString("00000000-0000-0000-0000-000000000008")
    };
    private static final UUID[] Knockback_UUIDS = new UUID[]{
            UUID.fromString("00000000-0000-0000-0000-000000000009"),
            UUID.fromString("00000000-0000-0000-0000-000000000010"),
            UUID.fromString("00000000-0000-0000-0000-000000000011"),
            UUID.fromString("00000000-0000-0000-0000-000000000012")
    };

    public DwarvenArmor(int platCount, ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(platCount, pMaterial, pType, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player) {
            if (!pLevel.isClientSide()) {

                // Helmet gives Saturation
                if (this.hasHelmet(player) && this.helmetLevel(player) >= 50) {
                    player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 1, 0, false, false));
                }
                // 10% Slower for each ArmorPiece
                int pieceCount = 0;
                if (this.hasHelmet(player)) pieceCount++;
                if (this.hasChestplate(player)) pieceCount++;
                if (this.hasLeggings(player)) pieceCount++;
                if (this.hasBoots(player)) pieceCount++;

                AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

                if (movementSpeed != null) {
                    if (movementSpeed.getModifier(MOVMENT_SLOWDOWN_UUID) != null) {
                        movementSpeed.removeModifier(MOVMENT_SLOWDOWN_UUID);
                    }

                    if (pieceCount > 0) {
                        double modifierValue = -0.15 * pieceCount;  // -20% per armor piece
                        AttributeModifier slowModifier = new AttributeModifier(
                                MOVMENT_SLOWDOWN_UUID,
                                "HeavyArmorSpeedDebuff",
                                modifierValue,
                                AttributeModifier.Operation.MULTIPLY_TOTAL
                        );
                        movementSpeed.addPermanentModifier(slowModifier);
                    }
                }
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    // Attribute BS
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        if (slot == this.getEquipmentSlot()) {
            double baseArmor = this.getDefense();
            double baseToughness = this.getToughness();
            double baseKnockback = this.getMaterial().getKnockbackResistance();

            int index = getSlotIndex(slot);

            builder.put(Attributes.ARMOR,
                    new AttributeModifier(ARMOR_UUIDS[index], "Armor modifier", baseArmor, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ARMOR_TOUGHNESS,
                    new AttributeModifier(TOUGHNESS_UUIDS[index], "Armor toughness modifier", baseToughness, AttributeModifier.Operation.ADDITION));

            builder.put(Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(Knockback_UUIDS[index], "Armor knockback modifier", baseKnockback, AttributeModifier.Operation.ADDITION));
        }

        return builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        if (slot == this.getEquipmentSlot()) {
            int level = getItemLevel(stack);  // NOW we can safely read level here

            double baseArmor = this.getDefense();
            double baseToughness = this.getToughness();
            double baseKnockback = this.getMaterial().getKnockbackResistance();

            double scaledArmor = baseArmor + (level * 0.2);
            double scaledToughness = baseToughness + (level * 0.2);
            double scaledKnockback = (baseKnockback * ((double) level /10));

            int index = getSlotIndex(slot);

            builder.put(Attributes.ARMOR,
                    new AttributeModifier(ARMOR_UUIDS[index], "Armor modifier", scaledArmor, AttributeModifier.Operation.ADDITION));

            builder.put(Attributes.ARMOR_TOUGHNESS,
                    new AttributeModifier(TOUGHNESS_UUIDS[index], "Armor toughness modifier", scaledToughness, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(Knockback_UUIDS[index], "Armor knockback modifier", scaledKnockback, AttributeModifier.Operation.ADDITION));
        }

        return builder.build();
    }

    private int getSlotIndex(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> 0;
            case CHEST -> 1;
            case LEGS -> 2;
            case FEET -> 3;
            default -> throw new IllegalArgumentException("Invalid armor slot: " + slot);
        };
    }

    @Override
    public ManaType getManaType() {return ManaType.DWARVEN;}

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new DwarvenArmorRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }
}
