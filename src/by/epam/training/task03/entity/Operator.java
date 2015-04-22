package by.epam.training.task03.entity;

/**
 * Created by higgs on 14.04.15.
 */
public class Operator {

    private Caller caller;
    private String operatorID;

    public Operator(String operatorID) {
        this.operatorID = operatorID;
    }

    public Caller getCaller() {
        return caller;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public void setCaller(Caller caller) {
        this.caller = caller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operator operator = (Operator) o;

        if (caller != null ? !caller.equals(operator.caller) : operator.caller != null) return false;
        if (operatorID != null ? !operatorID.equals(operator.operatorID) : operator.operatorID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = caller != null ? caller.hashCode() : 0;
        result = 31 * result + (operatorID != null ? operatorID.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Operator{");
        sb.append("caller=").append(caller);
        sb.append(", operatorID='").append(operatorID).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
