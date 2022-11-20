package net.easycloud.session.packet;

import net.easycloud.packet.list.JsonPacket;
import net.easycloud.session.CloudSession;

public interface SinglePacketListener {

    void listen(CloudSession session, JsonPacket<?> packet);

    int listenTo();

}
