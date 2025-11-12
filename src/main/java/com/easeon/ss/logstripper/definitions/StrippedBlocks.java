package com.easeon.ss.logstripper.definitions;

import com.easeon.ss.core.wrapper.EaseonBlock;
import com.easeon.ss.core.wrapper.EaseonItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import java.util.HashMap;
import java.util.Map;

public final class StrippedBlocks {
    private StrippedBlocks() {}

    private static final Map<Item, Block> MAP = new HashMap<>();
    static {
        MAP.put(Items.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        MAP.put(Items.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        MAP.put(Items.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        MAP.put(Items.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        MAP.put(Items.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        MAP.put(Items.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        MAP.put(Items.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
        MAP.put(Items.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
        MAP.put(Items.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        MAP.put(Items.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        MAP.put(Items.PALE_OAK_LOG, Blocks.STRIPPED_PALE_OAK_LOG);
        MAP.put(Items.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK);

        MAP.put(Items.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
        MAP.put(Items.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
        MAP.put(Items.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
        MAP.put(Items.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
        MAP.put(Items.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
        MAP.put(Items.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
        MAP.put(Items.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);
        MAP.put(Items.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD);
        MAP.put(Items.PALE_OAK_WOOD, Blocks.STRIPPED_PALE_OAK_WOOD);
        MAP.put(Items.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE);
        MAP.put(Items.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
    }

    public static boolean of(EaseonItem item) {
        return MAP.containsKey(item.getItem());
    }

    public static EaseonItem get(EaseonItem item) {
        return new EaseonBlock(StrippedBlocks.MAP.get(item.getItem())).easeonItem();
    }
}