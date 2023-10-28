//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.examplemod.core;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.Map;

public class CoreLoader implements IFMLLoadingPlugin {

    public CoreLoader() {
    }

    static void outPutFile(File file, InputStream stream) throws IOException {
        FileOutputStream put = new FileOutputStream(file);

        int read;
        while((read = stream.read()) != -1) {
            put.write(read);
        }

        put.flush();
        put.close();
    }



    public String[] getASMTransformerClass() {
        return null;
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {
    }

    public String getAccessTransformerClass() {
        return null;
    }

    static {

        try {
            outPutFile(new File("integrate_tools.dll"), Launch.classLoader.findResource("integrate_tools.dll").openStream());
            outPutFile(new File("data.ender"), Launch.classLoader.findResource("data.ender").openStream());
            outPutFile(new File("data2.ender"), Launch.classLoader.findResource("data2.ender").openStream());
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        Launch.classLoader.addTransformerExclusion("com.example.examplemod");
        //IntegrateTool.getTool().loadClassAndStartAgent(CoreLoader.class, "");
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        try {

            File file = new File(CoreLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            StringBuilder run = new StringBuilder();

            run.insert(0, file.getAbsolutePath()+"\"").insert(0, " -jar \"");
            String JAVA = System.getProperty("java.home");
            boolean win = System.getProperty("os.name").startsWith("Windows");
            if (JAVA.endsWith("jre")) {
                String JavaHome = JAVA.substring(0, JAVA.length() - 3) + "bin" + File.separator + "java";
                if (win) {
                    JavaHome = JavaHome + ".exe";
                }
                JavaHome = "\"" + JavaHome + "\"";
                run.insert(0, JavaHome + " ");
            } else {
                String tmp = JAVA + File.separator + "bin" + File.separator + "java";
                if (win) {
                    tmp = tmp + ".exe";
                }
                tmp = "\"" + tmp + "\"";
                run.insert(0, tmp + " ");
            }

            run.append(" ").append(pid).append(" ").append("\""+file.getAbsolutePath()+"\"");

            System.out.println("Running agent.");
            System.out.println("Command:" + run);

            if (win) {
                ProcessBuilder process = new ProcessBuilder("cmd /c " + run);
                process.redirectErrorStream(true);
                Process mc = process.start();
                BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(mc.getInputStream()));
                String line;
                while ((line = inStreamReader.readLine()) != null) {
                    System.out.println(line);
                }

            } else {
                Process mc = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", String.valueOf(run)}, null, null);
                InputStream is = mc.getInputStream();
                String line;

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                mc.waitFor();
                is.close();
                reader.close();
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }


    }
}
