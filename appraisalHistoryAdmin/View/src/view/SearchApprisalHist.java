package view;

import com.view.utils.ADFUtils;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.adf.view.rich.component.rich.nav.RichLink;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class SearchApprisalHist {


    private RichSelectOneChoice empNum;
    private RichSelectOneChoice planName;
    private RichOutputText tstENum;
    private RichLink planNameTest;
    private RichSelectOneChoice pname;
    private RichOutputText date;
   

    public SearchApprisalHist() {
    }
    

    public void onClickEdit(ActionEvent actionEvent) {
//        Object obj = ADFContext.getCurrent()
//                               .getPageFlowScope()
//                               .get("headerId");

      ViewObject apprHisROVO = ADFUtils.findIterator("AppraisalHistHdr_V_ROVO1Iterator").getViewObject();
      String pName =  apprHisROVO.getCurrentRow().getAttribute("PlanName")==null?"":apprHisROVO.getCurrentRow().getAttribute("PlanName").toString();
       String eNum =  apprHisROVO.getCurrentRow().getAttribute("EmployeeNumber")==null?"":apprHisROVO.getCurrentRow().getAttribute("EmployeeNumber").toString();    
       System.out.println("---------------------------------------------ENumber-----navigate--------" +eNum);
       System.out.println("---------------------------------------------PlanName----navigate----" +pName);
        String eNumm =  AdfFacesContext.getCurrentInstance().getPageFlowScope().get("EmployeeNumber1")==null?"":AdfFacesContext.getCurrentInstance().getPageFlowScope().get("EmployeeNumber1").toString();
        String pNamee = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PlanName1")==null?"": AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PlanName1").toString();
        
        System.out.println("---------------------------------------------ENumber-----pageflowscope--------" +eNumm);
        System.out.println("---------------------------------------------PlanName----pageflowscope----" +pNamee);
        
    
        ViewCriteria hdrVC = apprHisROVO.createViewCriteria();
        ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
        hdrVcr.setAttribute("PlanName", pNamee);
        hdrVcr.setAttribute("EmployeeNumber", eNumm);
        hdrVC.addRow(hdrVcr);
        apprHisROVO.applyViewCriteria(hdrVC);
        apprHisROVO.executeQuery();
        
        
    }


    public void onClickSearch(ActionEvent actionEvent) {
        // Add event code here...
              String eNum = empNum.getValue()==null?"":empNum.getValue().toString();
              String pName = planName.getValue()==null?"":planName.getValue().toString();
              ViewObject apprHisROVO = ADFUtils.findIterator("AppraisalHistHdr_V_ROVO1Iterator").getViewObject();
               ViewCriteria hdrVC = apprHisROVO.createViewCriteria();
               ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
               hdrVcr.setAttribute("EmployeeNumber", eNum);
               hdrVcr.setAttribute("PlanName", pName);
               hdrVC.addRow(hdrVcr);
               apprHisROVO.applyViewCriteria(hdrVC);
               apprHisROVO.executeQuery();
//             apprHisROVO.first().setAttribute("EmpNumTrans", eNum);
//             apprHisROVO.first().setAttribute("PlanNameTrans", pName);
               
        System.out.println("---------------------------------------------ENumber----search-------" +eNum);
        System.out.println("---------------------------------------------PlanName----search-------" +pName);
    }

    public void setEmpNum(RichSelectOneChoice empNum) {
        this.empNum = empNum;
    }

    public RichSelectOneChoice getEmpNum() {
        return empNum;
    }

    public void setPlanName(RichSelectOneChoice planName) {
        this.planName = planName;
    }

    public RichSelectOneChoice getPlanName() {
        return planName;
    }

   

    public void setTstENum(RichOutputText tstENum) {
        this.tstENum = tstENum;
    }

    public RichOutputText getTstENum() {
        return tstENum;
    }

    public void setPlanNameTest(RichLink planNameTest) {
        this.planNameTest = planNameTest;
    }

    public RichLink getPlanNameTest() {
        return planNameTest;
    }

   
    public void onClickReset(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject apprHisROVO = ADFUtils.findIterator("ApprisalHisHdr_VOIterator").getViewObject();
               System.out.println("--------------1---------------" +apprHisROVO.getEstimatedRowCount());
               apprHisROVO.executeQuery();
               System.out.println("-------------2----------------" +apprHisROVO.getEstimatedRowCount());
               apprHisROVO.first().setAttribute("EmpNumTrans", null);
               apprHisROVO.first().setAttribute("PlanNameTrans", null);
         
    }

    public void valueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!= null){
        ADFContext.getCurrent().getSessionScope().put("p_ENUM","");
        }
    }
}
