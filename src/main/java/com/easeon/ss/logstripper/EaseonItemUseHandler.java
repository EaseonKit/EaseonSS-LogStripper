package com.easeon.ss.logstripper;

import com.easeon.ss.core.util.system.EaseonLogger;
import com.easeon.ss.core.wrapper.*;
import com.easeon.ss.logstripper.definitions.StrippedBlocks;
import net.minecraft.item.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;

public class EaseonItemUseHandler {
    private final static EaseonLogger logger = EaseonLogger.of();

    public static ActionResult onUseItem(EaseonWorld world, EaseonPlayer player, Hand hand) {
        if (world.isClient() || hand != Hand.MAIN_HAND)
            return ActionResult.PASS;

        final var main = player.getMainHandStack();
        final var off = player.getOffHandStack();

        EaseonItem tool;
        EaseonItem log;
        Hand swing = Hand.MAIN_HAND;
        if (main.of(AxeItem.class) && StrippedBlocks.of(off)) {
            tool = main;
            log = off;
        }
        else if (off.of(AxeItem.class) && StrippedBlocks.of(main)) {
            tool = off;
            log = main;
            swing = Hand.OFF_HAND;
        }
        else {
            return ActionResult.PASS;
        }

        final var reward = StrippedBlocks.get(log);

        tool.damage(player);
        player.giveOrDropItem(reward, 1);
        player.removeItem(log, 1);
        player.swingHand(swing);
        world.playSound(player.getPos(), SoundEvents.ITEM_AXE_STRIP, SoundCategory.PLAYERS, 1.0f);

        return ActionResult.SUCCESS;
    }
}