package dev.jayox.partyGames.Utils;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.eclipse.sisu.inject.Legacy;

import javax.annotation.Nullable;

public class Message {
    public TextComponent colorText (String uncoloredText) {

        return LegacyComponentSerializer.legacyAmpersand().deserialize(uncoloredText);
    }

    public TextComponent colorText (String uncoloredText, boolean showPrefix) {

        return LegacyComponentSerializer.legacyAmpersand().deserialize(uncoloredText);
    }



    public String chatColor(String uncoloredText) {
        return uncoloredText.replace('&', '§');
    }
}
