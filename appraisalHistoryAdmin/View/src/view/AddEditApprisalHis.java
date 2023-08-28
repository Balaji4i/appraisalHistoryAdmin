package view;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;
import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class AddEditApprisalHis {
    private RichPopup showPopup;
    private RichTable linetable;
    private RichInputText faerf;
    private RichInputText empNum;


    public AddEditApprisalHis() {
    }

    public void onClickCancel(ActionEvent actionEvent) {
        
        ViewObject quoteVO = ADFUtils.findIterator("ApprisalHisHdr_VOIterator").getViewObject();
        Row row = quoteVO.getCurrentRow();
        String Enumber = empNum.getValue().toString();
        System.out.println("backEmpNumber-----------------" +Enumber);
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Rollback").execute();
       ADFContext.getCurrent().getSessionScope().put("p_ENUM",Enumber);
        
// HdrVO.executeQuery();
    }

    public void onClickShowpopup(ActionEvent actionEvent) {
        System.out.println("-----------Inside PopUp--------------------");
        String LineId =  AdfFacesContext.getCurrentInstance().getPageFlowScope().get("LineId").toString();
        System.out.println("-----------pageflwScopeLineid--Get1----------------------"+LineId);
        ViewObject HdrVO = ADFUtils.findIterator("ApprisalHisLine_VO3Iterator").getViewObject();
        ViewCriteria viewCriteria = HdrVO.createViewCriteria();
         ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();
     //  Row hdrRow = HdrVO.getCurrentRow();
      // Object hdrId = hdrRow.getAttribute("AppraisalHistLineId");
         viewCriteriaRow.setAttribute("AppraisalHistLineId", LineId);
         viewCriteria.addRow(viewCriteriaRow);
         HdrVO.applyViewCriteria(viewCriteria);
          HdrVO.executeQuery();
          RichPopup.PopupHints hints = new RichPopup.PopupHints();
          this.getShowPopup().show(hints);
    }


    public void setShowPopup(RichPopup showPopup) {
        this.showPopup = showPopup;
    }

    public RichPopup getShowPopup() {
        return showPopup;
    }

    public void popUpCancel(ActionEvent actionEvent) {
        JSFUtils.hidePopup(showPopup);
        ViewObject HdrVO = ADFUtils.findIterator("ApprisalHisLine_VO3Iterator").getViewObject();
        HdrVO.applyViewCriteria(null);
        HdrVO.executeQuery();
    }

    

    public void setLinetable(RichTable linetable) {
        this.linetable = linetable;
    }

    public RichTable getLinetable() {
        return linetable;
    }


    public void setFaerf(RichInputText faerf) {
        this.faerf = faerf;
    }

    public RichInputText getFaerf() {
        return faerf;
    }

    public void setEmpNum(RichInputText empNum) {
        this.empNum = empNum;
    }

    public RichInputText getEmpNum() {
        return empNum;
    }
}
