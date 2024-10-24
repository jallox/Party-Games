package dev.jayox.partyGames.Utils;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.eclipse.sisu.inject.Legacy;

public class Message {
    public TextComponent colorText (String uncoloredText) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(uncoloredText);
    }
}
