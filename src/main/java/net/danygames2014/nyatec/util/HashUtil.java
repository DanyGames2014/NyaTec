package net.danygames2014.nyatec.util;

import net.minecraft.item.ItemStack;

public class HashUtil {
    private static final long C1 = 0x9E3779B97F4A7C15L; // golden ratio constant

    public static long hashStack(ItemStack s) {
        if (s == null) return 0L;
        long h = ((long) s.itemId & 0xFFFFFFFFL)
                ^ (((long) s.count & 0xFFFFFFFFL) << 21)
                ^ (((long) s.getDamage() & 0xFFFFL) << 42);
        
        h ^= (h >>> 33);
        h *= C1;
        h ^= (h >>> 29);
        
        return h;
    }

    public static long hashInputs(ItemStack[] in) {
        long h = 0x1234ABCD5678EF90L; // random nonzero seed
        
        for (ItemStack s : in) {
            h ^= hashStack(s) + C1 + (h << 6) + (h >> 2);
        }

        // final avalanche
        h ^= (h >>> 27);
        h *= 0x94D049BB133111EBL;
        h ^= (h >>> 31);
        return h;
    }
}
