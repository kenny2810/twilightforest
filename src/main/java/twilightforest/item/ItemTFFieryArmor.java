package twilightforest.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;

import java.util.List;

public class ItemTFFieryArmor extends ItemArmor implements ModelRegisterCallback {

	public ItemTFFieryArmor(ItemArmor.ArmorMaterial par2EnumArmorMaterial, EntityEquipmentSlot armorType) {
		super(par2EnumArmorMaterial, 0, armorType);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.EPIC;
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, EntityEquipmentSlot slot, String layer) {
		if (slot == EntityEquipmentSlot.LEGS) {
			return TwilightForestMod.ARMOR_DIR + "fiery_2.png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "fiery_1.png";
		}
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		ItemStack istack = new ItemStack(par1, 1, 0);
		//istack.addEnchantment(TFEnchantment.fieryAura, 2);
		par3List.add(istack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(I18n.format(getUnlocalizedName() + ".tooltip"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped oldModel) {
		return TwilightForestMod.proxy.getFieryArmorModel(armorSlot);
	}
}
