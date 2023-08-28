package view;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import model.vo.IntroLtrHdr_VORowImpl;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;


public class AddEditTravelReq {
    private RichInputDate dob;
    private RichInputText palceOfBirth;
    private RichInputText passportNo;
    private RichInputDate passportEndDate;
    private RichInputDate passportIssueDate;
    private RichTable lineTable;
    private RichPopup p1;
    private Date minDate = new Date();


    public AddEditTravelReq() {
    }

//    public void onClickSave(ActionEvent actionEvent) {
//        DCIteratorBinding line = ADFUtils.findIterator("IntroLtrLines_VOIterator");
//        DCIteratorBinding introHdrIter = ADFUtils.findIterator("IntroLtrHdr_VOIterator");
//        IntroLtrHdr_VORowImpl introHdrRow = (IntroLtrHdr_VORowImpl) introHdrIter.getCurrentRow();
//       
//        ViewObject leaseReqDtlVO = ADFUtils.findIterator("IntroLtrLines_VOIterator").getViewObject();
//
//        int a = 0;
//        int b = 0;
//        if (leaseReqDtlVO.getEstimatedRowCount() > 0) {
//            System.err.println("getEstimatedRowCount" + leaseReqDtlVO.getEstimatedRowCount());
//            RowSetIterator rsDtl = leaseReqDtlVO.createRowSetIterator(null);
//            while (rsDtl.hasNext()) {
//                Row r = rsDtl.next();
//                if (r.getAttribute("ProTravelDate") == null) {
//                    a++;
//
//                }
//                if (r.getAttribute("ExpReturnDate") == null) {
//                    b++;
//                }
//            }
//            if (a != 0 && b == 0) {
//                JSFUtils.addFacesErrorMessage("Please Enter the Proposed Travel Date ");
//            } else if (b != 0 && a == 0) {
//                JSFUtils.addFacesErrorMessage("Please Enter the Expected Return Date");
//            } else if (a != 0 && b != 0) {
//                JSFUtils.addFacesErrorMessage("Please Enter the Proposed Travel Date and Expected Return Date");
//            } else {
//                if (introHdrRow != null) {
//                    introHdrRow.setApprovedStatus("DRAFT");
//                }
//
//                OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
//                JSFUtils.addFacesInformationMessage("Information Saved Successfully");
//                line.executeQuery();
//            }
//
//        }
//
//
//    }

    public void onClickAdd(ActionEvent actionEvent) {

        DCBindingContainer bindings = ADFUtils.getDCBindingContainer();
        AttributeBinding personIdBinding = (AttributeBinding) bindings.getControlBinding("PersonId");
        AttributeBinding travelPurposeBinding = (AttributeBinding) bindings.getControlBinding("TravelPurposeCode");
        String personId = personIdBinding.getInputValue().toString();

        if (personId != null && !"".equalsIgnoreCase(personId)) {
            clearRows();
            String travelPurpose = travelPurposeBinding.getInputValue().toString();
            String[] travelPurposeArray = travelPurpose.split("-");
            if (travelPurposeArray[0].equalsIgnoreCase("PER")) {
                addSelfLineItem("Employee_View_ROVOIterator", personId);
                addDependentLineItem("Employee_Depnd_View_ROVOIterator", personId);
            } else {
                addSelfLineItem("Employee_View_ROVOIterator", personId);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lineTable);
    }

    public void addSelfLineItem(String iterator, String id) {
        if (id != null) {
            ViewObject LineVo = ADFUtils.findIterator("IntroLtrLines_VOIterator").getViewObject();
            ViewObject vo = ADFUtils.findIterator(iterator).getViewObject();
            ViewCriteria viewCriteria = vo.createViewCriteria();
            ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();
            viewCriteriaRow.setAttribute("PersonId", id);
            viewCriteria.addRow(viewCriteriaRow);
            vo.applyViewCriteria(viewCriteria);
            vo.executeQuery();
            Row lineRow = null;
            RowSetIterator rs = vo.createRowSetIterator(null);
            while (rs.hasNext()) {
                Row row = rs.next();
                if (LineVo.getCurrentRow() != null) {
                    if (row.getAttribute("PersonId").equals(LineVo.getCurrentRow().getAttribute("PersonId"))) {
                    } else {
                        ADFUtils.findOperation("CreateInsert1").execute();
                        lineRow = LineVo.getCurrentRow();

                        lineRow.setAttribute("ReleationTypeCode",
                                             iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ? null :
                                             row.getAttribute("ContactType"));
                        lineRow.setAttribute("EmployeeFullName", row.getAttribute("FullName"));
                        lineRow.setAttribute("PersonId",
                                             iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ?
                                             row.getAttribute("PersonId") : row.getAttribute("ContactPersonId"));
                        lineRow.setAttribute("DateOfBirth", row.getAttribute("DateOfBirth"));
                        lineRow.setAttribute("PlaceBirth", row.getAttribute("TownOfBirth"));
                        lineRow.setAttribute("PassportNo", row.getAttribute("PassportNumber"));
                        lineRow.setAttribute("PassportIssueDate", row.getAttribute("EffectiveStartDate"));
                        lineRow.setAttribute("PassportEndDate", row.getAttribute("EffectiveStartDate"));
                        lineRow.setAttribute("ProTravelDate", lineRow.getAttribute("ProTravelDate"));
                        lineRow.setAttribute("ExpReturnDate", lineRow.getAttribute("ExpReturnDate"));

                    }

                } else {
                    ADFUtils.findOperation("CreateInsert1").execute();
                    lineRow = LineVo.getCurrentRow();

                    lineRow.setAttribute("ReleationTypeCode",
                                         iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ? "SELF" :
                                         row.getAttribute("ContactType"));

                    lineRow.setAttribute("EmployeeFullName", row.getAttribute("FullName"));

                    lineRow.setAttribute(iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ? "EmployeeFullName" :
                                         "DependentFullName", row.getAttribute("FullName"));
                    lineRow.setAttribute("PersonId",
                                         iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ?
                                         row.getAttribute("PersonId") : row.getAttribute("ContactPersonId"));
                    lineRow.setAttribute("DateOfBirth", row.getAttribute("DateOfBirth"));
                    lineRow.setAttribute("PlaceBirth", row.getAttribute("TownOfBirth"));
                    lineRow.setAttribute("PassportNo", row.getAttribute("PassportNumber"));
                    lineRow.setAttribute("PassportIssueDate", row.getAttribute("EffectiveStartDate"));
                    lineRow.setAttribute("PassportEndDate", row.getAttribute("EffectiveStartDate"));
                    lineRow.setAttribute("ProTravelDate", lineRow.getAttribute("ProTravelDate"));
                    lineRow.setAttribute("ExpReturnDate", lineRow.getAttribute("ExpReturnDate"));
                }


            }

        }
    }


    public void addDependentLineItem(String iterator, String id) {
        if (id != null) {
            ViewObject LineVo = ADFUtils.findIterator("IntroLtrLines_VOIterator").getViewObject();
            ViewObject vo = ADFUtils.findIterator(iterator).getViewObject();
            ViewCriteria viewCriteria = vo.createViewCriteria();
            ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();
            viewCriteriaRow.setAttribute("PersonId", id);
            viewCriteria.addRow(viewCriteriaRow);
            vo.applyViewCriteria(viewCriteria);
            vo.executeQuery();
            Row lineRow = null;
            RowSetIterator rs = vo.createRowSetIterator(null);
            while (rs.hasNext()) {
                Row row = rs.next();
                if (LineVo.getCurrentRow() != null) {
                    if (row.getAttribute("ContactPersonId").equals(LineVo.getCurrentRow().getAttribute("PersonId"))) {
                    } else {
                        ADFUtils.findOperation("CreateInsert1").execute();
                        lineRow = LineVo.getCurrentRow();
                        lineRow.setAttribute("ReleationTypeCode",
                                             iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ? null :
                                             row.getAttribute("ContactType"));
                        lineRow.setAttribute(iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ?
                                             "EmployeeFullName" : "DependentFullName", row.getAttribute("FullName"));
                        lineRow.setAttribute("PersonId",
                                             iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ?
                                             row.getAttribute("PersonId") : row.getAttribute("ContactPersonId"));
                        lineRow.setAttribute("DateOfBirth", row.getAttribute("DateOfBirth"));
                        lineRow.setAttribute("PlaceBirth", row.getAttribute("TownOfBirth"));
                        lineRow.setAttribute("PassportNo", row.getAttribute("PassportNumber"));
                        lineRow.setAttribute("PassportIssueDate", row.getAttribute("EffectiveStartDate"));
                        lineRow.setAttribute("PassportEndDate", row.getAttribute("EffectiveStartDate"));
                        lineRow.setAttribute("ProTravelDate", lineRow.getAttribute("ProTravelDate"));
                        lineRow.setAttribute("ExpReturnDate", lineRow.getAttribute("ExpReturnDate"));
                    }

                } else {
                    ADFUtils.findOperation("CreateInsert1").execute();
                    lineRow = LineVo.getCurrentRow();
                    lineRow.setAttribute("ReleationTypeCode",
                                         iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ? null :
                                         row.getAttribute("ContactType"));

                    lineRow.setAttribute(iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ? "EmployeeFullName" :
                                         "DependentFullName", row.getAttribute("FullName"));
                    lineRow.setAttribute("PersonId",
                                         iterator.equalsIgnoreCase("Employee_View_ROVOIterator") ?
                                         row.getAttribute("PersonId") : row.getAttribute("ContactPersonId"));
                    lineRow.setAttribute("DateOfBirth", row.getAttribute("DateOfBirth"));
                    lineRow.setAttribute("PlaceBirth", row.getAttribute("TownOfBirth"));
                    lineRow.setAttribute("PassportNo", row.getAttribute("PassportNumber"));
                    lineRow.setAttribute("PassportIssueDate", row.getAttribute("EffectiveStartDate"));
                    lineRow.setAttribute("PassportEndDate", row.getAttribute("EffectiveStartDate"));
                    lineRow.setAttribute("ProTravelDate", lineRow.getAttribute("ProTravelDate"));
                    lineRow.setAttribute("ExpReturnDate", lineRow.getAttribute("ExpReturnDate"));
                }
            }

        }
    }

    public void relationTypeValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(dob);
        AdfFacesContext.getCurrentInstance().addPartialTarget(palceOfBirth);
        AdfFacesContext.getCurrentInstance().addPartialTarget(passportNo);
        AdfFacesContext.getCurrentInstance().addPartialTarget(passportEndDate);
        AdfFacesContext.getCurrentInstance().addPartialTarget(passportIssueDate);
    }

    public void setDob(RichInputDate dob) {
        this.dob = dob;
    }

    public RichInputDate getDob() {
        return dob;
    }

    public void setPalceOfBirth(RichInputText palceOfBirth) {
        this.palceOfBirth = palceOfBirth;
    }

    public RichInputText getPalceOfBirth() {
        return palceOfBirth;
    }

    public void setPassportNo(RichInputText passportNo) {
        this.passportNo = passportNo;
    }

    public RichInputText getPassportNo() {
        return passportNo;
    }

    public void setPassportEndDate(RichInputDate passportEndDate) {
        this.passportEndDate = passportEndDate;
    }

    public RichInputDate getPassportEndDate() {
        return passportEndDate;
    }

    public void setPassportIssueDate(RichInputDate passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public RichInputDate getPassportIssueDate() {
        return passportIssueDate;
    }

    public void onClickDelete(ActionEvent actionEvent) {
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Delete").execute();
        JSFUtils.addFacesInformationMessage("Deleted Successfully");
    }

    public void setLineTable(RichTable lineTable) {
        this.lineTable = lineTable;
    }

    public RichTable getLineTable() {
        return lineTable;
    }

    public void clearRows() {
        ViewObject LineVo = ADFUtils.findIterator("IntroLtrLines_VOIterator").getViewObject();
        Row[] rows = LineVo.getAllRowsInRange();
        Row row = null;
        for (int i = 0; i < rows.length; i++) {
            row = rows[i];
            row.remove();
        }
        LineVo.executeQuery();
    }


    public void valChangeTravelPurpose(ValueChangeEvent valueChangeEvent) {
        String travelPurposeBinding = "";
        travelPurposeBinding = (String) valueChangeEvent.getNewValue();       
        DCBindingContainer bindings = ADFUtils.getDCBindingContainer();
        AttributeBinding personIdBinding = (AttributeBinding) bindings.getControlBinding("PersonId");      
        String personId = personIdBinding.getInputValue().toString();

        if (personId != null && !"".equalsIgnoreCase(personId)) {
            clearRows();
            String[] travelPurposeArray = travelPurposeBinding.split("-");
            if (travelPurposeArray[0].equalsIgnoreCase("PER")) {
                addSelfLineItem("Employee_View_ROVOIterator", personId);
                addDependentLineItem("Employee_Depnd_View_ROVOIterator", personId);
            } else {
                addSelfLineItem("Employee_View_ROVOIterator", personId);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lineTable);
    }

    public void onClickCancel(ActionEvent actionEvent) {
        ViewObject HdrVO = ADFUtils.findIterator("IntroLtrHdr_VOIterator").getViewObject();
        HdrVO.applyViewCriteria(null);
        HdrVO.executeQuery();
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Rollback").execute();
        HdrVO.executeQuery();
    }

    public void onClickCancelPopup(ActionEvent actionEvent) {
        JSFUtils.hidePopup(p1);
    }


    public void setP1(RichPopup p1) {
        this.p1 = p1;
    }

    public RichPopup getP1() {
        return p1;
    }


    public void setOrgId() {
        Object obj = ADFContext.getCurrent()
                               .getSessionScope()
                               .get("personId");
        ViewObject LineVo =
            ADFUtils.getApplicationModuleForDataControl("Oando_AMDataControl").findViewObject("Employee_View_ROVO");
        ViewCriteria viewCriteria = LineVo.createViewCriteria();
        ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();
        viewCriteriaRow.setAttribute("PersonId", obj);
        viewCriteria.addRow(viewCriteriaRow);
        LineVo.applyViewCriteria(viewCriteria);
        LineVo.executeQuery();
        if (LineVo.getEstimatedRowCount() > 0) {
            Row row = LineVo.first();
            row.getAttribute("BusinessUnitId");
            Object orgObj = row.getAttribute("BusinessUnitId");
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("orgId", orgObj);
        }
    }

    public void onClickSubmit(ActionEvent actionEvent) {
        DCIteratorBinding line = ADFUtils.findIterator("IntroLtrLines_VOIterator");
        ViewObject leaseReqDtlVO = ADFUtils.findIterator("IntroLtrLines_VOIterator").getViewObject();
        boolean result = false;
        if (leaseReqDtlVO.getEstimatedRowCount() > 0) {
            int a = 0;
            int b = 0;
            int c = 0;
            int d = 0;
            int s = 0;
            System.err.println("getEstimatedRowCount" + leaseReqDtlVO.getEstimatedRowCount());
            RowSetIterator rsDtl = leaseReqDtlVO.createRowSetIterator(null);
            while (rsDtl.hasNext()) {
                Row r = rsDtl.next();
                if (r.getAttribute("ProTravelDate") == null) {
                    a++;

                }
                if (r.getAttribute("ExpReturnDate") == null) {
                    b++;
                }
                if (r.getAttribute("PassportNo") == null) {
                    c++;
                }
                if (r.getAttribute("PassportEndDate") != null) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new Date());                        
                        String curDate = sdf.format(cal.getTime());
                        Date CurrentDate = sdf.parse(curDate);
                        cal.add(Calendar.DATE, 180);
                        String newDate = sdf.format(cal.getTime());                       
                        Date cDay = sdf.parse(newDate);
                        String passExpDate1 = sdf.format(r.getAttribute("PassportEndDate"));
                        Date passExpDate = sdf.parse(passExpDate1);
                        if (passExpDate.compareTo(CurrentDate) > 0) {                           
                            if(passExpDate.compareTo(cDay) > 0){
                                System.out.println("Result : Allow");                               
                            } else{
                                System.out.println("result : Dont Allow");                             
                                d++;
                            }
                        } else{
                            System.out.println("result : Dont Allow");                          
                            s++;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
            if (a != 0) {
                JSFUtils.addFacesErrorMessage("Please Enter the Proposed Travel Date ");
                result = true;
            } else if (b != 0) {
                JSFUtils.addFacesErrorMessage("Please Enter the Expected Return Date");
                result = true;
            } else if (a != 0 && b != 0) {
                JSFUtils.addFacesErrorMessage("Please Enter the Proposed Travel Date and Expected Return Date");
                result = true;
            } else if (c != 0) {
                JSFUtils.addFacesErrorMessage("Please Enter Passport Number");
                result = true;
            } else if (d != 0) {
                JSFUtils.addFacesErrorMessage("Passport Expiry date is within 6 Months of Date of Application");
                result = true;
            } else if (s != 0) {
                JSFUtils.addFacesErrorMessage("Passport is Expired");
                result = true;
            } else {
               // OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
                line.executeQuery();
            }

        }

        if (!result) {
            DCIteratorBinding introHdrIter = ADFUtils.findIterator("IntroLtrHdr_VOIterator");
            IntroLtrHdr_VORowImpl introHdrRow = (IntroLtrHdr_VORowImpl) introHdrIter.getCurrentRow();

            String xmlData = null;
            String[] a = null;
            ArrayList<String> p_Relationship = new ArrayList<String>();
            ArrayList<String> p_FullName = new ArrayList<String>();
            ArrayList<String> p_DateOfBirth = new ArrayList<String>();
            ArrayList<String> p_PlaceOfBirth = new ArrayList<String>();
            ArrayList<String> p_PassportNo = new ArrayList<String>();
            ArrayList<String> p_PassportIssueDate = new ArrayList<String>();
            ArrayList<String> p_PassportExpiryDate = new ArrayList<String>();
            ArrayList<String> p_ProposalTravelDate = new ArrayList<String>();
            ArrayList<String> p_ExpectedTravelDate = new ArrayList<String>();
            String travelReqWSDL = null;
            travelReqWSDL = ApprovalPayload.TRAVEL_REQ_WSDL;
            ViewObject HdrVo = ADFUtils.findIterator("IntroLtrHdr_VOIterator").getViewObject();
            String p_EmployeeName =
                HdrVo.getCurrentRow().getAttribute("Trans_EmpName") == null ? " " :
                HdrVo.getCurrentRow()
                                                                                                             .getAttribute("Trans_EmpName")
                                                                                                             .toString();
            String p_EmployeeNumber =
                HdrVo.getCurrentRow().getAttribute("Trans_EmpNo") == null ? " " :
                HdrVo.getCurrentRow()
                                                                                                             .getAttribute("Trans_EmpNo")
                                                                                                             .toString();
            String p_travelReq = HdrVo.getCurrentRow().getAttribute("TravelRequestNo") == null ? " " : HdrVo.getCurrentRow()
                                                                                                            .getAttribute("TravelRequestNo")
                                                                                                            .toString();
            String p_Country = HdrVo.getCurrentRow().getAttribute("Trans_Country") == null ? " " : HdrVo.getCurrentRow()
                                                                                                        .getAttribute("Trans_Country")
                                                                                                        .toString();
            String p_Email = HdrVo.getCurrentRow().getAttribute("Trans_EmailId") == null ? " " : HdrVo.getCurrentRow()
                                                                                                      .getAttribute("Trans_EmailId")
                                                                                                      .toString();
            String p_travelPurposeCode =
                HdrVo.getCurrentRow().getAttribute("TravelPurposeCode") == null ? " " :
                HdrVo.getCurrentRow()
                                                                                                                      .getAttribute("TravelPurposeCode")
                                                                                                                      .toString();
            String p_BusinessGroup =
                HdrVo.getCurrentRow().getAttribute("Trans_BusinessGroup") == null ? " " :
                HdrVo.getCurrentRow()
                                                                                                                    .getAttribute("Trans_BusinessGroup")
                                                                                                                    .toString();
            String p_Remarks = HdrVo.getCurrentRow().getAttribute("Remarks") == null ? " " : HdrVo.getCurrentRow()
                                                                                                  .getAttribute("Remarks")
                                                                                                  .toString();

            ViewObject linesVo = ADFUtils.findIterator("IntroLtrLines_VOIterator").getViewObject();
            RowSetIterator rs = linesVo.createRowSetIterator(null);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            while (rs.hasNext()) {
                Row r = rs.next();
                p_Relationship.add(r.getAttribute("Relation") == null ? " " : r.getAttribute("Relation").toString());
                p_FullName.add(r.getAttribute("Name") == null ? " " : r.getAttribute("Name").toString());
                String strDate = "";
                if (r.getAttribute("DateOfBirth") != null) {
                    Date dob = (Date) r.getAttribute("DateOfBirth");
                    strDate = formatter.format(dob);
                }
                p_DateOfBirth.add(strDate);
                p_PlaceOfBirth.add(r.getAttribute("PlaceBirth") == null ? " " :
                                   r.getAttribute("PlaceBirth").toString());
                p_PassportNo.add(r.getAttribute("PassportNo") == null ? " " : r.getAttribute("PassportNo").toString());
                String passportIssueDate = "";
                if (r.getAttribute("PassportIssueDate") != null) {
                    Date passportIssue = (Date) r.getAttribute("PassportIssueDate");
                    passportIssueDate = formatter.format(passportIssue);
                }
                p_PassportIssueDate.add(passportIssueDate);
                String passportEndDate = "";
                if (r.getAttribute("PassportEndDate") != null) {
                    Date passportEnd = (Date) r.getAttribute("PassportEndDate");
                    passportEndDate = formatter.format(passportEnd);
                }
                p_PassportExpiryDate.add(passportEndDate);
                String ProTravelDate = "";
                if (r.getAttribute("ProTravelDate") != null) {
                    Date proTravel = (Date) r.getAttribute("ProTravelDate");
                    ProTravelDate = formatter.format(proTravel);

                }
                p_ProposalTravelDate.add(ProTravelDate);
                String expectedTravelDate = "";
                if (r.getAttribute("ExpReturnDate") != null) {
                    Date expectedTravel = (Date) r.getAttribute("ExpReturnDate");
                    expectedTravelDate = formatter.format(expectedTravel);

                }
                p_ExpectedTravelDate.add(expectedTravelDate);
                // p_PassportIssueDate.add(r.getAttribute("PassportIssueDate")==null?" ": r.getAttribute("PassportIssueDate").toString());
                // p_PassportExpiryDate.add(r.getAttribute("PassportEndDate")==null?" ": r.getAttribute("PassportEndDate").toString());
                //p_ProposalTravelDate.add(r.getAttribute("ProTravelDate")==null?" ": r.getAttribute("ReleationTypeCode").toString());
                // p_ExpectedTravelDate.add(r.getAttribute("ExpReturnDate")==null?" ": r.getAttribute("ReleationTypeCode").toString());
                System.err.println("linesName" + r.getAttribute("Name"));
            }
            rs.closeRowSetIterator();

            System.err.println("p_Relationship" + p_Relationship);
            System.err.println("p_FullName" + p_FullName);
            System.err.println("p_DateOfBirth" + p_DateOfBirth);
            System.err.println("p_PlaceOfBirth" + p_PlaceOfBirth);
            System.err.println("p_PassportNo" + p_PassportNo);
            System.err.println("p_PassportIssueDate" + p_PassportIssueDate);
            System.err.println("p_PassportExpiryDate" + p_PassportExpiryDate);
            System.err.println("p_ProposalTravelDate" + p_ProposalTravelDate);
            System.err.println("p_ExpectedTravelDate" + p_ExpectedTravelDate);
            xmlData =
                ApprovalPayload.businessTypeXMLData(p_EmployeeName, p_EmployeeNumber, p_travelReq, p_Country, p_Email,
                                                    p_travelPurposeCode, p_BusinessGroup, p_Remarks, p_Relationship,
                                                    p_FullName, p_DateOfBirth, p_PlaceOfBirth, p_PassportNo,
                                                    p_PassportIssueDate, p_PassportExpiryDate, p_ProposalTravelDate,
                                                    p_ExpectedTravelDate);
            System.err.println("xmlData =>" + xmlData);
            a = ApprovalProcess.invokeWsdl(xmlData, travelReqWSDL, ApprovalPayload.TRAVEL_REQ_METHOD);
            if (a[0] != null && a[0].equals("True")) {
                if (introHdrRow != null) {
                    introHdrRow.setApprovedStatus("SUBMITTED FOR APPROVAL");
                    OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
                }
                JSFUtils.addFacesInformationMessage("Introduction Letter Submitted Successfully");

            } else {
                JSFUtils.addFacesInformationMessage("Error");

            }

        }


    }

    public Date getMinDate() {
        try {
            Calendar cal = Calendar.getInstance();
            java.util.Date date = cal.getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = formatter.format(date);
            minDate = formatter.parse(currentDate);
            return formatter.parse(currentDate);
        } catch (Exception e) {
            return null;
        }
    }

    public Date getMaxDateVal() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        return cal.getTime();

    }


}
