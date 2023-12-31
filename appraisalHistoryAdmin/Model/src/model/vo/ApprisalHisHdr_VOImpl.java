package model.vo;

import java.math.BigDecimal;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 09 15:33:30 IST 2019
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ApprisalHisHdr_VOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public ApprisalHisHdr_VOImpl() {
    }

    /**
     * Returns the variable value for BV_ID.
     * @return variable value for BV_ID
     */
    public BigDecimal getBV_ID() {
        return (BigDecimal) ensureVariableManager().getVariableValue("BV_ID");
    }

    /**
     * Sets <code>value</code> for variable BV_ID.
     * @param value value to bind as BV_ID
     */
    public void setBV_ID(BigDecimal value) {
        ensureVariableManager().setVariableValue("BV_ID", value);
    }


    /**
     * Returns the variable value for B_Emp_Number.
     * @return variable value for B_Emp_Number
     */
    public BigDecimal getB_Emp_Number() {
        return (BigDecimal) ensureVariableManager().getVariableValue("B_Emp_Number");
    }

    /**
     * Sets <code>value</code> for variable B_Emp_Number.
     * @param value value to bind as B_Emp_Number
     */
    public void setB_Emp_Number(BigDecimal value) {
        ensureVariableManager().setVariableValue("B_Emp_Number", value);
    }

    /**
     * Returns the variable value for B_PlanName.
     * @return variable value for B_PlanName
     */
    public String getB_PlanName() {
        return (String) ensureVariableManager().getVariableValue("B_PlanName");
    }

    /**
     * Sets <code>value</code> for variable B_PlanName.
     * @param value value to bind as B_PlanName
     */
    public void setB_PlanName(String value) {
        ensureVariableManager().setVariableValue("B_PlanName", value);
    }
}

