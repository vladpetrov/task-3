package by.epam.training.task03.entity;


import org.apache.log4j.Logger;

/**
 * Created by higgs on 15.04.15.
 */
public class Caller implements Runnable {
    private String callerID;
    private CallCenter callCenter;
    private Operator operator;
    private long waitSecondsPossibility;
    private long problemDiscussionSecondsTime;
    private boolean run;
    private static final long MILISECONDS_PER_SECOND = 1000;

    private static org.apache.log4j.Logger log = Logger.getLogger(Caller.class);

    public Caller(String callerID, long waitSecondsPossibility, long problemDiscussionSecondsTime, CallCenter callCenter) {
        this.run = true;
        this.callerID = callerID;
        this.waitSecondsPossibility = waitSecondsPossibility;
        this.problemDiscussionSecondsTime = problemDiscussionSecondsTime;
        this.callCenter = callCenter;
    }

    @Override
    public void run() {
        while (run) {
            try {
                makeCall();
            } catch (InterruptedException e) {
                log.error("Error when making a call");
            }
        }
        log.info(getCallerID() + " finished!");
    }


    public void makeCall() throws InterruptedException {
        if (callCenter.connectCaller(this)) {
            discussProblem();
        }
        endCall();
    }

    public void discussProblem() throws InterruptedException {
        Thread.sleep(getProblemDiscussionSecondsTime() * MILISECONDS_PER_SECOND);
    }

    public void endCall() throws InterruptedException {
        callCenter.endCall(getOperator());
        operator = null;
        run = false;
    }


    public long getProblemDiscussionSecondsTime() {
        return problemDiscussionSecondsTime;
    }

    public void setProblemDiscussionSecondsTime(long problemDiscussionSecondsTime) {
        this.problemDiscussionSecondsTime = problemDiscussionSecondsTime;
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

        if (problemDiscussionSecondsTime != caller.problemDiscussionSecondsTime) return false;
        if (run != caller.run) return false;
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
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (int) (waitSecondsPossibility ^ (waitSecondsPossibility >>> 32));
        result = 31 * result + (int) (problemDiscussionSecondsTime ^ (problemDiscussionSecondsTime >>> 32));
        result = 31 * result + (run ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Caller{");
        sb.append("callerID='").append(callerID).append('\'');
        sb.append(", callCenter=").append(callCenter);
        sb.append(", operator=").append(operator);
        sb.append(", waitSecondsPossibility=").append(waitSecondsPossibility);
        sb.append(", problemDiscussionSecondsTime=").append(problemDiscussionSecondsTime);
        sb.append(", run=").append(run);
        sb.append('}');
        return sb.toString();
    }

}
