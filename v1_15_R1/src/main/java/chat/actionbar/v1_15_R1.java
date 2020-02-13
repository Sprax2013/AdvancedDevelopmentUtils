package chat.actionbar;

import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutTitle;

public class v1_15_R1 {
    public static Object createActionBarPacket(String s) {
        return new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.ACTIONBAR,
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + s + "\"}")
        );
    }
}