package net.zhuoweizhang.booktotxt;

import java.io.*;

import net.minecraft.server.*;

import org.bukkit.command.*;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BookToTxtPlugin extends JavaPlugin {
	public void onDisable() {
	}

	public void onEnable() {
		getDataFolder().mkdirs();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("booktotxt")) {
			Player player = (Player) sender;
			CraftItemStack stack = (CraftItemStack) player.getItemInHand();
			net.minecraft.server.ItemStack nmsStack = stack.getHandle();
			NBTTagCompound tag = nmsStack.getTag();
			File file = new File(getDataFolder(), args[0].replace("../", "").replace("..\\", "") + ".txt");
			PrintStream stream;
			try {
				stream = new PrintStream(file);
			} catch (Exception e) { return false; }
			stream.println(tag.getString("title"));
			stream.println("by " + tag.getString("author") + "\n");
			NBTTagList pagesTag = tag.getList("pages");
			for (int i = 0; i < pagesTag.size(); i++) {
				stream.println(((NBTTagString) pagesTag.get(i)).toString());
				stream.println();
			}
			stream.close();
			player.sendMessage("[BookToTxt] written book to " + file.getName());
			return true;
		}
		return false;
	}

}

