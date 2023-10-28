package net.minecraft.launchwrapper;

public interface IClassTransformer {
    byte[] transform(String name, String name2, byte[] bytecode);
}
