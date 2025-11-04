package com.easeon.ss.logstripper;

import com.easeon.ss.core.game.EaseonItem;
import com.easeon.ss.core.game.EaseonSound;
import com.easeon.ss.core.util.system.EaseonLogger;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EaseonItemUseHandler {
    private final static EaseonLogger logger = EaseonLogger.of();

    public static ActionResult onUseItem(PlayerEntity player, World world, Hand hand) {
        if (world.isClient()) return ActionResult.PASS;
        if (hand != Hand.MAIN_HAND) {
            return ActionResult.PASS;
        }

        var mainHand = player.getMainHandStack();
        var offHand = player.getOffHandStack();

        // 도끼와 통나무 확인
        var mainIsAxe = mainHand.getItem() instanceof AxeItem;
        var offIsAxe = offHand.getItem() instanceof AxeItem;
        var mainIsLog = isStrippableLog(mainHand);
        var offIsLog = isStrippableLog(offHand);

        // 한 손에 도끼, 다른 손에 통나무가 있는지 확인
        if ((mainIsAxe && offIsLog) || (offIsAxe && mainIsLog)) {
            ItemStack axeStack = mainIsAxe ? mainHand : offHand;
            ItemStack logStack = mainIsLog ? mainHand : offHand;

            Block strippedLog = getStrippedLog(logStack.getItem());
            if (strippedLog == null) return ActionResult.PASS;

            EaseonItem.damage(player, axeStack);
            EaseonItem.giveOrDropItem(player, strippedLog.asItem());

            // 통나무 1개 소모
            logStack.decrement(1);
            EaseonSound.playAll(world, player, SoundEvents.ITEM_AXE_STRIP, SoundCategory.PLAYERS);
            if (mainIsAxe)
                player.swingHand(Hand.MAIN_HAND, true);
            else
                player.swingHand(Hand.OFF_HAND, true);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private static boolean isStrippableLog(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.OAK_LOG || item == Items.OAK_WOOD ||
                item == Items.BIRCH_LOG || item == Items.BIRCH_WOOD ||
                item == Items.ACACIA_LOG || item == Items.ACACIA_WOOD ||
                item == Items.MANGROVE_LOG || item == Items.MANGROVE_WOOD ||
                item == Items.SPRUCE_LOG || item == Items.SPRUCE_WOOD ||
                item == Items.JUNGLE_LOG || item == Items.JUNGLE_WOOD ||
                item == Items.DARK_OAK_LOG || item == Items.DARK_OAK_WOOD ||
                item == Items.PALE_OAK_LOG || item == Items.PALE_OAK_WOOD ||
                item == Items.CHERRY_LOG || item == Items.CHERRY_WOOD ||
                item == Items.CRIMSON_STEM || item == Items.WARPED_STEM ||
                item == Items.CRIMSON_HYPHAE || item == Items.WARPED_HYPHAE ||
                item == Items.BAMBOO_BLOCK;
    }

    private static Block getStrippedLog(Item logItem) {
        if (logItem == Items.OAK_LOG) return Blocks.STRIPPED_OAK_LOG;
        if (logItem == Items.SPRUCE_LOG) return Blocks.STRIPPED_SPRUCE_LOG;
        if (logItem == Items.BIRCH_LOG) return Blocks.STRIPPED_BIRCH_LOG;
        if (logItem == Items.JUNGLE_LOG) return Blocks.STRIPPED_JUNGLE_LOG;
        if (logItem == Items.ACACIA_LOG) return Blocks.STRIPPED_ACACIA_LOG;
        if (logItem == Items.DARK_OAK_LOG) return Blocks.STRIPPED_DARK_OAK_LOG;
        if (logItem == Items.MANGROVE_LOG) return Blocks.STRIPPED_MANGROVE_LOG;
        if (logItem == Items.CHERRY_LOG) return Blocks.STRIPPED_CHERRY_LOG;
        if (logItem == Items.CRIMSON_STEM) return Blocks.STRIPPED_CRIMSON_STEM;
        if (logItem == Items.WARPED_STEM) return Blocks.STRIPPED_WARPED_STEM;
        if (logItem == Items.PALE_OAK_LOG) return Blocks.STRIPPED_PALE_OAK_LOG;
        if (logItem == Items.BAMBOO_BLOCK) return Blocks.STRIPPED_BAMBOO_BLOCK;

        if (logItem == Items.OAK_WOOD) return Blocks.STRIPPED_OAK_WOOD;
        if (logItem == Items.SPRUCE_WOOD) return Blocks.STRIPPED_SPRUCE_WOOD;
        if (logItem == Items.BIRCH_WOOD) return Blocks.STRIPPED_BIRCH_WOOD;
        if (logItem == Items.JUNGLE_WOOD) return Blocks.STRIPPED_JUNGLE_WOOD;
        if (logItem == Items.ACACIA_WOOD) return Blocks.STRIPPED_ACACIA_WOOD;
        if (logItem == Items.DARK_OAK_WOOD) return Blocks.STRIPPED_DARK_OAK_WOOD;
        if (logItem == Items.MANGROVE_WOOD) return Blocks.STRIPPED_MANGROVE_WOOD;
        if (logItem == Items.CHERRY_WOOD) return Blocks.STRIPPED_CHERRY_WOOD;
        if (logItem == Items.PALE_OAK_WOOD) return Blocks.STRIPPED_PALE_OAK_WOOD;
        if (logItem == Items.CRIMSON_HYPHAE) return Blocks.STRIPPED_CRIMSON_HYPHAE;
        if (logItem == Items.WARPED_HYPHAE) return Blocks.STRIPPED_WARPED_HYPHAE;

        return null;
    }
}