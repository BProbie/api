package com.probie.Tools;

import java.io.*;
import java.util.Base64;
import com.probie.Interface.ISerializeBase64;

public class SerializeBase64 implements ISerializeBase64 {

    private static SerializeBase64 INSTANCE = new SerializeBase64();

    public static SerializeBase64 getInstance() {
        return INSTANCE;
    }

    @Override
    public Object enSerializeToBase64(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(object);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    @Override
    public Object deSerializeFromBase64(Object object) {
        byte[] bytes = Base64.getDecoder().decode(object.toString());
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

}