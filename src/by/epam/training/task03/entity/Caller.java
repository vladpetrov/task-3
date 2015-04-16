package by.epam.training.task03.entity;


import org.apache.log4j.Logger;
/**
 * Created by higgs on 15.04.15.
 */
public class Caller implements Runnable {

    private String callerID;
    private CallCenter callCenter;
    private long waitSecondsPossibility;
    private long problemDiscusionTime;
    private boolean run;
    private Operator operator;

    private static org.apache.log4j.Logger log = Logger.getLogger(Caller.class);

    public Caller(String callerID, long waitSecondsPossibility, long problemDiscusionTime, CallCenter callCenter) {
        org.apache.log4j.BasicConfigurator.configure();
        this.run = true;
        this.callerID = callerID;
        this.waitSecondsPossibility = waitSecondsPossibility;
        this.problemDiscusionTime = problemDiscusionTime;
        this.callCenter = callCenter;
    }

    @Override
    public void run() {
        while(run) {
            try {
                callCenter.processCall(this);
            } catch (InterruptedException e) {
                log.error("Error when processing call", e);
            }
        }
    }

    public long getProblemDiscusionTime() {
        return problemDiscusionTime;
    }

    public void setProblemDiscusionTime(long problemDiscusionTime) {
        this.problemDiscusionTime = problemDiscusionTime;
    }

    public String getCallerID() {
        return callerID;
    }

    public long getWaitSecondsPossibility() {
        return waitSecondsPossibility;
    }

    public void setWaitSecondsPossibility(long waitSecondsPossibility) {
        this.waitSecondsPossibility = waitSecondsPossibility;
    }

    public void setCallerID(String callerID) {
        this.callerID = callerID;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void endCall() {
        operator = null;
        run = false;
    }

    public void stopWaiting() {
        run = false;
    }

    public CallCenter getCallCenter() {
        return callCenter;
    }

    public void setCallCenter(CallCenter callCenter) {
        this.callCenter = callCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caller caller = (Caller) o;
        if (problemDiscusionTime != caller.problemDiscusionTime) return false;
        if (waitSecondsPossibility != caller.waitSecondsPossibility) return false;
        if (callCenter != null ? !callCenter.equals(caller.callCenter) : caller.callCenter != null) return false;
        if (callerID != null ? !callerID.equals(caller.callerID) : caller.callerID != null) return false;
        if (operator != null ? !operator.equals(caller.operator) : caller.operator != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = callerID != null ? callerID.hashCode() : 0;
        result = 31 * result + (callCenter != null ? callCenter.hashCode() : 0);
        result = 31 * result + (int) (waitSecondsPossibility ^ (waitSecondsPossibility >>> 32));
        result = 31 * result + (int) (problemDiscusionTime ^ (problemDiscusionTime >>> 32));
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Caller{");
        sb.append("callerID='").append(callerID).append('\'');
        sb.append(", callCenter=").append(callCenter);
        sb.append(", waitSecondsPossibility=").append(waitSecondsPossibility);
        sb.append(", problemDiscusionTime=").append(problemDiscusionTime);
        sb.append(", operator=").append(operator);
        sb.append('}');
        return sb.toString();
    }
}
