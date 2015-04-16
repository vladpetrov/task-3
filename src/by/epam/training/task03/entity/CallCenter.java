package by.epam.training.task03.entity;

import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by higgs on 15.04.15.
 */
public class CallCenter implements Runnable {

    private BlockingQueue<Operator> operators;
    private BlockingQueue<Caller> callersQueue;
    private Lock lock = new ReentrantLock();
    private Condition freeOperator = lock.newCondition();
    private boolean run;

    private static org.apache.log4j.Logger log = Logger.getLogger(CallCenter.class);

    public CallCenter(int operatorsCount) {
        org.apache.log4j.BasicConfigurator.configure();
        this.run = true;
        this.operators = new LinkedBlockingQueue<>(operatorsCount);
        this.callersQueue = new LinkedBlockingQueue<>();
        for (int i = 0; i < operatorsCount; i++) {
            operators.add(new Operator("Operator-" + i));
        }
    }

    @Override
    public void run() {
        while (run) {
            lock.lock();
            try {
                freeOperator.await();
                while (callersQueue.size() > 0) {
                    Caller caller = callersQueue.poll();
                    Operator operator = operators.poll();
                    connectCaller(operator, caller);
                }
            } catch (InterruptedException e) {
                log.error("Wait for free operator!", e);
            } finally {
                lock.unlock();
            }
        }
    }

    public void processCall(Caller caller) throws InterruptedException {
        lock.lock();
        try {
            Operator operator = operators.poll();
            if(operator != null) {
                log.info("Connecting caller " + caller.getCallerID());
                connectCaller(operator, caller);
            } else {
                log.info("Caller " + caller.getCallerID() + " is waiting for free operator");
                callersQueue.put(caller);
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean connectCaller(Operator operator, Caller caller) throws InterruptedException{
        lock.lock();
        try {
            operator.setCaller(caller);
            caller.setOperator(operator);
            Thread.sleep(caller.getProblemDiscusionTime());
            caller.endCall();
            operator.endCall();
            if(callersQueue.contains(caller)) {
                callersQueue.remove(caller);
            }
            log.info("Caller" + caller.getCallerID() + " got consulteng from " + operator.getOperatorID());
            operators.put(operator);
            freeOperator.signal();

        } finally {
            lock.unlock();
        }


        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallCenter that = (CallCenter) o;
        if (callersQueue != null ? !callersQueue.equals(that.callersQueue) : that.callersQueue != null) return false;
        if (lock != null ? !lock.equals(that.lock) : that.lock != null) return false;
        if (log != null ? !log.equals(that.log) : that.log != null) return false;
        if (operators != null ? !operators.equals(that.operators) : that.operators != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = operators != null ? operators.hashCode() : 0;
        result = 31 * result + (callersQueue != null ? callersQueue.hashCode() : 0);
        result = 31 * result + (lock != null ? lock.hashCode() : 0);
        result = 31 * result + (log != null ? log.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CallCenter{");
        sb.append("operators=").append(operators);
        sb.append(", callersQueue=").append(callersQueue);
        sb.append(", lock=").append(lock);
        sb.append('}');
        return sb.toString();
    }

}
