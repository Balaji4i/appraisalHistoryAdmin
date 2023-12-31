package model.vo;

import java.math.BigDecimal;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Oct 11 19:38:08 IST 2019
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ApprisalHisLine_VOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public ApprisalHisLine_VOImpl() {
    }

    /**
     * Returns the variable value for b_HistLineId.
     * @return variable value for b_HistLineId
     */
    public BigDecimal getpHistLineId() {
        return (BigDecimal) ensureVariableManager().getVariableValue("pHistLineId");
    }

    /**
     * Sets <code>value</code> for variable b_HistLineId.
     * @param value value to bind as b_HistLineId
     */
    public void setpHistLineId(BigDecimal value) {
        ensureVariableManager().setVariableValue("pHistLineId", value);
    }

    /**
     * Returns the variable value for B_Employee_number.
     * @return variable value for B_Employee_number
     */
    public BigDecimal getB_Employee_number() {
        return (BigDecimal) ensureVariableManager().getVariableValue("B_Employee_number");
    }

    /**
     * Sets <code>value</code> for variable B_Employee_number.
     * @param value value to bind as B_Employee_number
     */
    public void setB_Employee_number(BigDecimal value) {
        ensureVariableManager().setVariableValue("B_Employee_number", value);
    }

    /**
     * Returns the variable value for B_Plan_Name.
     * @return variable value for B_Plan_Name
     */
    public String getB_Plan_Name() {
        return (String) ensureVariableManager().getVariableValue("B_Plan_Name");
    }

    /**
     * Sets <code>value</code> for variable B_Plan_Name.
     * @param value value to bind as B_Plan_Name
     */
    public void setB_Plan_Name(String value) {
        ensureVariableManager().setVariableValue("B_Plan_Name", value);
    }
}

