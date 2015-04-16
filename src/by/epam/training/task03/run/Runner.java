package by.epam.training.task03.run;

import by.epam.training.task03.entity.CallCenter;
import by.epam.training.task03.entity.Caller;

/**
 * Created by higgs on 14.04.15.
 */
public class Runner {
    public static void main(String[] args) {
        CallCenter callCenter = new CallCenter(5);
        Thread callCenterThread = new Thread(callCenter, "callCenter");
        callCenterThread.setDaemon(true);
        callCenterThread.start();

        ThreadGroup callers = new ThreadGroup("Callers");
        for(int i = 0; i < 9; i++) {
            new Thread(callers, new Caller("Caller-" + i, 5, 3000, callCenter),"Caller-" + i).start();
        }

    }

}
