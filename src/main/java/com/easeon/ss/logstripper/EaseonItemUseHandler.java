package com.easeon.ss.logstripper;

import com.easeon.ss.core.util.system.EaseonLogger;
import com.easeon.ss.core.wrapper.EaseonItem;
import com.easeon.ss.core.wrapper.EaseonPlayer;
import com.easeon.ss.core.wrapper.EaseonWorld;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EaseonItemUseHandler {
    private final static EaseonLogger logger = EaseonLogger.of();

    public static ActionResult onUseItem(ServerPlayerEntity playerEntity, World mcWorld, Hand hand) {
        var world = new EaseonWorld(mcWorld);
        if (world.isClient()) return ActionResult.PASS;
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;

        var player = new EaseonPlayer(playerEntity);

        var mainHand = player.getMainHandStack();
        var offHand = player.getOffHandStack();

        // 도끼와 통나무 확인
        var mainIsAxe = mainHand.of(AxeItem.class);
        var offIsAxe = offHand.of(AxeItem.class);
        var mainIsLog = getStrippedLog(mainHand);
        var offIsLog = getStrippedLog(offHand);

        // 한 손에 도끼, 다른 손에 통나무가 있는지 확인
        if ((mainIsAxe && offIsLog != null) || (offIsAxe && mainIsLog != null)) {
            var axeStack = mainIsAxe ? mainHand : offHand;
            var logStack = mainIsAxe ? offHand : mainHand;
            var strippedLog = mainIsAxe ? offIsLog : mainIsLog;

            axeStack.damage(player);
            player.giveOrDropItem(strippedLog, 1);
            player.removeItem(logStack, 1);
            world.playSound(player.getPos(), SoundEvents.ITEM_AXE_STRIP, SoundCategory.PLAYERS, 1.0f);
            if (mainIsAxe)
                player.swingHand(Hand.MAIN_HAND);
            else
                player.swingHand(Hand.OFF_HAND);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private static EaseonItem getStrippedLog(EaseonItem item) {
        Block stripped = null;

        if (item.of(Items.OAK_LOG)) stripped = Blocks.STRIPPED_OAK_LOG;
        else if (item.of(Items.SPRUCE_LOG)) stripped = Blocks.STRIPPED_SPRUCE_LOG;
        else if (item.of(Items.BIRCH_LOG)) stripped = Blocks.STRIPPED_BIRCH_LOG;
        else if (item.of(Items.JUNGLE_LOG)) stripped = Blocks.STRIPPED_JUNGLE_LOG;
        else if (item.of(Items.ACACIA_LOG)) stripped = Blocks.STRIPPED_ACACIA_LOG;
        else if (item.of(Items.DARK_OAK_LOG)) stripped = Blocks.STRIPPED_DARK_OAK_LOG;
        else if (item.of(Items.MANGROVE_LOG)) stripped = Blocks.STRIPPED_MANGROVE_LOG;
        else if (item.of(Items.CHERRY_LOG)) stripped = Blocks.STRIPPED_CHERRY_LOG;
        else if (item.of(Items.CRIMSON_STEM)) stripped = Blocks.STRIPPED_CRIMSON_STEM;
        else if (item.of(Items.WARPED_STEM)) stripped = Blocks.STRIPPED_WARPED_STEM;
        else if (item.of(Items.PALE_OAK_LOG)) stripped = Blocks.STRIPPED_PALE_OAK_LOG;
        else if (item.of(Items.BAMBOO_BLOCK)) stripped = Blocks.STRIPPED_BAMBOO_BLOCK;

        else if (item.of(Items.OAK_WOOD)) stripped = Blocks.STRIPPED_OAK_WOOD;
        else if (item.of(Items.SPRUCE_WOOD)) stripped = Blocks.STRIPPED_SPRUCE_WOOD;
        else if (item.of(Items.BIRCH_WOOD)) stripped = Blocks.STRIPPED_BIRCH_WOOD;
        else if (item.of(Items.JUNGLE_WOOD)) stripped = Blocks.STRIPPED_JUNGLE_WOOD;
        else if (item.of(Items.ACACIA_WOOD)) stripped = Blocks.STRIPPED_ACACIA_WOOD;
        else if (item.of(Items.DARK_OAK_WOOD)) stripped = Blocks.STRIPPED_DARK_OAK_WOOD;
        else if (item.of(Items.MANGROVE_WOOD)) stripped = Blocks.STRIPPED_MANGROVE_WOOD;
        else if (item.of(Items.CHERRY_WOOD)) stripped = Blocks.STRIPPED_CHERRY_WOOD;
        else if (item.of(Items.PALE_OAK_WOOD)) stripped = Blocks.STRIPPED_PALE_OAK_WOOD;
        else if (item.of(Items.CRIMSON_HYPHAE)) stripped = Blocks.STRIPPED_CRIMSON_HYPHAE;
        else if (item.of(Items.WARPED_HYPHAE)) stripped = Blocks.STRIPPED_WARPED_HYPHAE;

        return stripped != null ? new EaseonItem(new ItemStack(stripped.asItem())) : null;
    }
}