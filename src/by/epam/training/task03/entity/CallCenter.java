package by.epam.training.task03.entity;

import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
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
                log.info("Free operator!");
            } catch (InterruptedException e) {
                log.error("Wait for free operator!", e);
            } finally {
                lock.unlock();
            }
        }
    }

    public boolean connectCaller(Caller caller) throws InterruptedException {
        Operator operator = operators.poll(caller.getWaitSecondsPossibility(), TimeUnit.SECONDS);
        if (operator == null) {
            return false;
        }
        caller.setOperator(operator);
        log.info(caller.getCallerID() + " connected to " + operator.getOperatorID());
        return true;
    }

    public void endCall(Operator operator) throws InterruptedException {
        lock.lock();
        try {
            if (!(operator == null)) {
                operators.put(operator);
                freeOperator.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CallCenter that = (CallCenter) o;

        if (run != that.run) return false;
        if (callersQueue != null ? !callersQueue.equals(that.callersQueue) : that.callersQueue != null) return false;
        if (freeOperator != null ? !freeOperator.equals(that.freeOperator) : that.freeOperator != null) return false;
        if (lock != null ? !lock.equals(that.lock) : that.lock != null) return false;
        if (operators != null ? !operators.equals(that.operators) : that.operators != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = operators != null ? operators.hashCode() : 0;
        result = 31 * result + (callersQueue != null ? callersQueue.hashCode() : 0);
        result = 31 * result + (lock != null ? lock.hashCode() : 0);
        result = 31 * result + (freeOperator != null ? freeOperator.hashCode() : 0);
        result = 31 * result + (run ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CallCenter{");
        sb.append("operators=").append(operators);
        sb.append(", callersQueue=").append(callersQueue);
        sb.append(", lock=").append(lock);
        sb.append(", freeOperator=").append(freeOperator);
        sb.append(", run=").append(run);
        sb.append('}');
        return sb.toString();
    }

}
