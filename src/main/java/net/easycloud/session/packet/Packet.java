package net.easycloud.session.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Packet {

    void write(DataOutputStream outputStream) throws IOException;

    void read(DataInputStream inputStream) throws IOException;

}
