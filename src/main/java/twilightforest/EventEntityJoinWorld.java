package twilightforest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import twilightforest.item.TFItems;

/**
 * Created on 12/10/2018.
 */

// This is totally not a joke class. Please take seriously....
public class EventEntityJoinWorld {

    ItemStack seekerBow = new ItemStack(TFItems.seeker_bow);
    ItemStack sheildWand = new ItemStack(TFItems.shield_scepter);
    ItemStack megaSword = new ItemStack(TFItems.knightmetal_sword);
    ItemStack headPiece = new ItemStack(TFItems.fiery_helmet);
    ItemStack chestPiece = new ItemStack(TFItems.fiery_chestplate);
    ItemStack legPiece = new ItemStack(TFItems.fiery_leggings);
    ItemStack footPiece = new ItemStack(TFItems.fiery_boots);

    private String id = "3def5bdc-df07-4df8-8978-4ab883cf95b5";

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getEntity().getEntityWorld().isRemote) {
            if(event.getEntity() != null){
                if (event.getEntity() instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) event.getEntity();

                    if (player.getUniqueID().toString().equals(id)) {
                        player.addItemStackToInventory(seekerBow);
                        player.addItemStackToInventory(sheildWand);
                        player.addItemStackToInventory(megaSword);
                        player.addItemStackToInventory(headPiece);
                        player.addItemStackToInventory(chestPiece);
                        player.addItemStackToInventory(legPiece);
                        player.addItemStackToInventory(footPiece);
                    }
                }

            }
        }
    }
}
//Seriously, delete this class. Do not accept the PR. Blame Killer Demon