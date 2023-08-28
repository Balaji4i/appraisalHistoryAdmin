package view;

import com.view.utils.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.share.ADFContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class SearchTravelReq {
    public SearchTravelReq() {
    }

    public void onClickDelete(ActionEvent actionEvent) {
        ViewObject leaseHdrVO = ADFUtils.findIterator("IntroLtrHdr_VOIterator").getViewObject();
        DCBindingContainer bindings = ADFUtils.getDCBindingContainer();
        AttributeBinding personIdBinding = (AttributeBinding) bindings.getControlBinding("IntroLtrHdrId");
        String personId = personIdBinding.getInputValue() == null ? "0" : personIdBinding.getInputValue().toString();
        deleteLeaseReqHdr(personId);
        ADFUtils.findOperation("Commit").execute();
        leaseHdrVO.executeQuery();
    }

    public void deleteLeaseReqHdr(Object leaseReqHdrId) {
        ViewObject leaseHdrVO = ADFUtils.findIterator("IntroLtrHdr_VOIterator").getViewObject();
        ViewCriteria hdrVC = leaseHdrVO.createViewCriteria();
        ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
        hdrVcr.setAttribute("IntroLtrHdrId", leaseReqHdrId);
        hdrVC.addRow(hdrVcr);
        leaseHdrVO.applyViewCriteria(hdrVC);
        leaseHdrVO.executeQuery();
        if (leaseHdrVO.getEstimatedRowCount() > 0) {
            Row hdrRow = leaseHdrVO.first();
            Object hdrId = hdrRow.getAttribute("IntroLtrHdrId");
            System.err.println("Deleting LeaseReqHdrId = " + hdrId);
            deleteLeaseReqDtl(hdrId);
            hdrRow.remove();
            OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
        }
        leaseHdrVO.applyViewCriteria(null);

    }

    public void deleteLeaseReqDtl(Object leaseReqHdrId) {
        ViewObject leaseReqDtlVO = ADFUtils.findIterator("IntroLtrLines_VOIterator").getViewObject();
        ViewCriteria vc1 = leaseReqDtlVO.createViewCriteria();
        ViewCriteriaRow vcr = vc1.createViewCriteriaRow();
        vcr.setAttribute("IntroLtrHdrId", leaseReqHdrId);
        vc1.addRow(vcr);
        leaseReqDtlVO.applyViewCriteria(vc1);
        leaseReqDtlVO.executeQuery();
        if (leaseReqDtlVO.getEstimatedRowCount() > 0) {
            RowSetIterator rsDtl = leaseReqDtlVO.createRowSetIterator(null);
            while (rsDtl.hasNext()) {
                Row r = rsDtl.next();
                r.remove();
                // deleteLeaseReqDtl(leaseReqDtlId);now i put this, no need this, we r using loop
                OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
            }
        }
        leaseReqDtlVO.applyViewCriteria(null);
        leaseReqDtlVO.executeQuery();
    }


    public void onClickEdit(ActionEvent actionEvent) {
        Object obj = ADFContext.getCurrent()
                               .getPageFlowScope()
                               .get("headerId");
        ViewObject leaseHdrVO = ADFUtils.findIterator("IntroLtrHdr_VOIterator").getViewObject();
        ViewCriteria hdrVC = leaseHdrVO.createViewCriteria();
        ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
        hdrVcr.setAttribute("IntroLtrHdrId", obj);
        hdrVC.addRow(hdrVcr);
        leaseHdrVO.applyViewCriteria(hdrVC);
        leaseHdrVO.executeQuery();
    }
}
