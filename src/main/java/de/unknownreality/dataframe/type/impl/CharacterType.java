package de.unknownreality.dataframe.type.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;

public class CharacterType extends ComparableType<Character> {
    @Override
    public Class<Character> getType() {
        return Character.class;
    }

    @Override
    public Character read(DataInputStream dis) throws IOException {
        return dis.readChar();
    }

    @Override
    public Character read(ByteBuffer buf) {
        return buf.getChar();
    }

    @Override
    public Character parse(String s) {
        return s.charAt(0);
    }

    @Override
    public void write(Writer writer, Character value) throws IOException {
        writer.write(toString(value));
    }

    @Override
    public String toString(Character value) {
        return Character.toString(value);
    }


    @Override
    public int write(DataOutputStream dos, Character value) throws IOException {
        dos.writeChar(value);
        return Character.BYTES;
    }
}
