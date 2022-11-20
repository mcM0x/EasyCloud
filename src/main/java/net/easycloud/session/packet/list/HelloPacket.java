package net.easycloud.session.packet.list;

import net.easycloud.session.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HelloPacket implements Packet {

    private String txt;

    public HelloPacket() {

    }

    @Override
    public void write(DataOutputStream outputStream) throws IOException {
        String output = "hello";
        outputStream.writeInt(output.length());
        outputStream.write(output.getBytes());
    }

    @Override
    public void read(DataInputStream inputStream) throws IOException {
        int len = inputStream.readInt();
        byte[] bytes = new byte[len];
        inputStream.readFully(bytes, 0, bytes.length);
        this.txt = new String(bytes);
    }
}
