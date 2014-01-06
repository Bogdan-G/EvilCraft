package evilcraft.events;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import evilcraft.api.Helpers;
import evilcraft.enchantment.EnchantmentBreaking;
import evilcraft.enchantment.EnchantmentBreakingConfig;
import evilcraft.enchantment.EnchantmentLifeStealing;
import evilcraft.enchantment.EnchantmentLifeStealingConfig;
import evilcraft.enchantment.EnchantmentPoisonTip;
import evilcraft.enchantment.EnchantmentPoisonTipConfig;
import evilcraft.enchantment.EnchantmentUnusing;
import evilcraft.enchantment.EnchantmentUnusingConfig;

public class LivingAttackEventHook {

    @ForgeSubscribe(priority = EventPriority.NORMAL)
    public void onLivingAttack(LivingAttackEvent event) {
        stealLife(event);
        unusingEvent(event);
        breakingEvent(event);
        poisonTipEvent(event);
    }
    
    private void stealLife(LivingAttackEvent event) {
        if(event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getEntity();
            ItemStack itemStack = player.getCurrentEquippedItem();
            int enchantmentListID = Helpers.doesEnchantApply(itemStack, EnchantmentLifeStealingConfig._instance.ID);
            if(enchantmentListID > -1) {
                float damage = event.ammount;
                NBTTagList enchlist = itemStack.getEnchantmentTagList();
                int level = ((NBTTagCompound)enchlist.tagAt(enchantmentListID)).getShort("level");
                EnchantmentLifeStealing.stealLife(player, damage, level);
            }
        }
    }
    
    private void unusingEvent(LivingAttackEvent event) {
        if(event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getEntity();
            ItemStack itemStack = player.getCurrentEquippedItem();
            if(Helpers.doesEnchantApply(itemStack, EnchantmentUnusingConfig._instance.ID) > -1) {
                if(player != null
                        && EnchantmentUnusing.unuseTool(itemStack)) {
                    event.setCanceled(true);
                    player.stopUsingItem();
                }
            }
        }
    }
    
    private void breakingEvent(LivingAttackEvent event) {
        if(event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getEntity();
            ItemStack itemStack = player.getCurrentEquippedItem();
            int enchantmentListID = Helpers.doesEnchantApply(itemStack, EnchantmentBreakingConfig._instance.ID);
            if(enchantmentListID > -1) {
                EnchantmentBreaking.amplifyDamage(itemStack, enchantmentListID, new Random());
            }
        }
    }
    
    private void poisonTipEvent(LivingAttackEvent event) {
        if(event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getEntity();
            ItemStack itemStack = player.getCurrentEquippedItem();
            int enchantmentListID = Helpers.doesEnchantApply(itemStack, EnchantmentPoisonTipConfig._instance.ID);
            if(enchantmentListID > -1) {
                NBTTagList enchlist = itemStack.getEnchantmentTagList();
                int level = ((NBTTagCompound)enchlist.tagAt(enchantmentListID)).getShort("level");
                EnchantmentPoisonTip.poison((EntityLivingBase)event.entity, level);
            }
        }
    }
    
}