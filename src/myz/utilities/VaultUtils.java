/**
 * 
 */
package myz.utilities;

import myz.MyZ;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author Jordan
 * 
 */
public class VaultUtils {

    public static Permission permission = null;

    public static boolean setupPermissions() {
        setupChat();
        RegisteredServiceProvider<Permission> permissionProvider = MyZ.instance.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null)
            permission = permissionProvider.getProvider();
        return permission != null;
    }

    public static Chat chat = null;

    public static boolean setupChat() {
        RegisteredServiceProvider<Chat> permissionProvider = MyZ.instance.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (permissionProvider != null)
            chat = permissionProvider.getProvider();
        return chat != null;
    }
}
