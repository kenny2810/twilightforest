package twilightforest.block;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.enums.MagicWoodVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.client.particle.TFParticleType;
import twilightforest.item.TFItems;

import java.util.List;
import java.util.Random;

public class BlockTFMagicLeaves extends BlockLeaves implements ModelRegisterCallback {

	protected BlockTFMagicLeaves() {
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true)
				.withProperty(BlockTFMagicLog.VARIANT, MagicWoodVariant.TIME));
	}

	@Override
	public int getLightOpacity(IBlockState state) {
		return TFConfig.performance.leavesLightOpacity;
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockTFMagicLog.VARIANT, CHECK_DECAY, DECAYABLE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i |= state.getValue(BlockTFMagicLog.VARIANT).ordinal();

		if (!state.getValue(DECAYABLE)) {
			i |= 4;
		}

		if (state.getValue(CHECK_DECAY)) {
			i |= 8;
		}

		return i;
	}

	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta) {
		int variant = meta & 3;
		final MagicWoodVariant[] values = MagicWoodVariant.values();

		return getDefaultState()
				.withProperty(BlockTFMagicLog.VARIANT, values[variant % values.length])
				.withProperty(DECAYABLE, (meta & 4) == 0)
				.withProperty(CHECK_DECAY, (meta & 8) > 0);
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
		list.add(new ItemStack(this, 1, 2));
		list.add(new ItemStack(this, 1, 3));
	}

	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
		if (state.getValue(BlockTFMagicLog.VARIANT) == MagicWoodVariant.TRANS) {
			for (int i = 0; i < 1; ++i) {
				this.sparkleRunes(world, pos, random);
			}
		}
	}

	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		return BlockPlanks.EnumType.OAK;
	}

	private void sparkleRunes(World world, BlockPos pos, Random rand) {
		double offset = 0.0625D;

		EnumFacing side = EnumFacing.random(rand);
		double rx = pos.getX() + rand.nextFloat();
		double ry = pos.getY() + rand.nextFloat();
		double rz = pos.getZ() + rand.nextFloat();

		if (side == EnumFacing.DOWN && world.isAirBlock(pos.up())) {
			ry = pos.getY() + 1 + offset;
		}

		if (side == EnumFacing.UP && world.isAirBlock(pos.down())) {
			ry = pos.getY() + 0 - offset;
		}

		if (side == EnumFacing.NORTH && world.isAirBlock(pos.south())) {
			rz = pos.getZ() + 1 + offset;
		}

		if (side == EnumFacing.SOUTH && world.isAirBlock(pos.north())) {
			rz = pos.getZ() + 0 - offset;
		}

		if (side == EnumFacing.WEST && world.isAirBlock(pos.east())) {
			rx = pos.getX() + 1 + offset;
		}

		if (side == EnumFacing.EAST && world.isAirBlock(pos.west())) {
			rx = pos.getX() + 0 - offset;
		}

		if (rx < pos.getX() || rx > pos.getX() + 1 || ry < pos.getY() || ry > pos.getY() + 1 || rz < pos.getZ() || rz > pos.getZ() + 1) {
			TwilightForestMod.proxy.spawnParticle(world, TFParticleType.LEAF_RUNE, rx, ry, rz, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(BlockTFMagicLog.VARIANT).ordinal()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		IStateMapper stateMapper = new StateMap.Builder().ignore(CHECK_DECAY, DECAYABLE).build();
		ModelLoader.setCustomStateMapper(this, stateMapper);
		ModelUtils.registerToStateSingleVariant(this, BlockTFMagicLog.VARIANT, stateMapper);
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(BlockTFMagicLog.VARIANT).ordinal();
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {}
}
