package by.epam.training.task03.run;

import by.epam.training.task03.entity.CallCenter;
import by.epam.training.task03.entity.Caller;
import org.apache.log4j.Logger;

import java.util.Random;


/**
 * Created by higgs on 14.04.15.
 */
public class Runner {

    private static org.apache.log4j.Logger log = Logger.getLogger(Runner.class);
    private static Random rand = new Random();

    public static void main(String[] args) throws InterruptedException {
        CallCenter callCenter = new CallCenter(5);
        Thread callCenterThread = new Thread(callCenter, "callCenter");
        callCenterThread.setDaemon(true);
        callCenterThread.start();
        log.info("Call center started!");

        ThreadGroup callers = new ThreadGroup("Callers");
        for (int i = 0; i < 20; i++) {
            new Thread(callers, new Caller("Caller-" + i, (1 + rand.nextInt(1000) % 4), (1 + rand.nextInt(1000) % 2), callCenter), "Caller-" + i).start();
            log.info("Thread Caller-" + i + " started!");
        }

        while (callers.activeCount() > 0) {
            Thread.sleep(100);
        }

    }

}
