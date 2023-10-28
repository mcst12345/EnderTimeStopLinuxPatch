package miku;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class agent {


    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("The fuck? Pid of target process isn't provided?");
        }
        String pid = args[0];
        String file = args[1];

        System.out.println("Target:" + pid);
        System.out.println("Agent file:" + file);

        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(file);
    }

    public static void agentmain(String arg, Instrumentation inst) {


        try {
            inst.addTransformer(new ClassTransformer());
        } catch (Throwable t){
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }
}
