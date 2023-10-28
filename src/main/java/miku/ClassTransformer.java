package miku;

import com.example.examplemod.core.CoreTransformer;
import mods.tzdxxy.ender.io.EnderFile;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

public class ClassTransformer implements ClassFileTransformer {
    private static final Map<String, byte[]> classcodes;

    static {
        classcodes = new HashMap<>();
        try {
            File dataFile = new File("data2.ender");
            if (dataFile.exists()) {
                EnderFile list = new EnderFile(dataFile);
                classcodes.putAll(list.datas);
                list.close();
            }
        } catch (Throwable var2) {
            var2.printStackTrace();
        }
    }
    @Override
    public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) {
        if (classcodes.containsKey(s.replace("/", "."))) {
            return classcodes.get(s.replace("/", "."));
        } else {
            if (classLoader instanceof LaunchClassLoader) {
                bytes = CoreTransformer.core.transform(s.replace("/", "."), s.replace("/", "."), bytes);
            }

            return bytes;
        }
    }
}
