package view;



import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimeZone;

public class ApprovalPayload {
    public ApprovalPayload() {
        super();
    }
    public static final String userName="oaopdtst";
    public static final String password="O_0Pst#819";
    
    //bUSINESS
    /***Cloud purpose un comment these section while deploying to TEST cloud **/    
    public static final String TRAVEL_REQ_WSDL = "http://oaosoatest-wls-1.sub08071802371.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/IntroductionLetterApproval/bpelprocess1_client_ep?WSDL";
   // public static final String TRAVEL_REQ_WSDL ="https://oaosoatest.oandoplc.com/soa-infra/services/default/IntroductionLetterApproval/bpelprocess1_client_ep?WSDL";
    
   /***Cloud purpose un comment these section while deploying to PROD cloud **/    
  // public static final String TRAVEL_REQ_WSDL = "http://oaosoaprd-wls-1.sub08071802370.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/IntroductionLetterApproval/bpelprocess1_client_ep?WSDL";
  
    public static final String TRAVEL_REQ_METHOD = "process";
   
    public static String businessTypeXMLData(String p_EmployeeName,String p_EmployeeNumber,String p_travelReq,String p_Country,String p_Email,String p_travelPurposeCode,String p_BusinessGroup,String p_Remarks,ArrayList p_Relationship, ArrayList p_FullName, ArrayList p_DateOfBirth,
                                               ArrayList p_PlaceOfBirth, ArrayList p_PassportNo, ArrayList p_PassportIssueDate,
                                            ArrayList p_PassportExpiryDate,ArrayList p_ProposalTravelDate,ArrayList p_ExpectedTravelDate)
                                              {
        String[] info=payloadHeader();
        String totalXml=null;
        String xmlData2="\n";
        System.err.println("Created time===>"+info[0]);
        System.err.println("User===========>"+info[1]);
        System.err.println("Password=======>"+info[2]);
        System.err.println("End time=======>"+info[3]);  
        String xmlData="<soapenv:Envelope xmlns:app=\"http://xmlns.oracle.com/introductionletterapproval\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + 
        "   <soapenv:Header/>\n" +  
        "   <soapenv:Body>\n" + 
        "      <app:processRequest>\n" + 
        "         <app:TravelReqNo>"+p_travelReq+"</app:TravelReqNo>\n" + 
        "         <app:EmployeeName>"+p_EmployeeName+"</app:EmployeeName>\n" +   
        "         <app:EmployeeNumber>"+p_EmployeeNumber+"</app:EmployeeNumber>\n" +              
        "         <app:CountryDestination>"+p_Country+"</app:CountryDestination>\n" + 
        "         <app:EmailAddress>"+p_Email+"</app:EmailAddress>\n" + 
        "         <app:TravelPurpose>"+p_travelPurposeCode+"</app:TravelPurpose>\n" + 
        "         <app:BusinessGroup>"+p_BusinessGroup+"</app:BusinessGroup>\n" + 
        "         <app:Remarks>"+p_Remarks+"</app:Remarks>\n" + 
        "         <!--1 or more repetitions:-->\n" ;
  for(int i=0;i<p_FullName.size();i++){ 
      String tempXml=
        "         <app:processLines>\n" + 
        "            <app:Relationship>"+p_Relationship.get(i)+"</app:Relationship>\n" + 
        "            <app:FullName>"+p_FullName.get(i)+"</app:FullName>\n" + 
        "            <app:DateOfBirth>"+p_DateOfBirth.get(i)+"</app:DateOfBirth>\n" + 
        "            <app:PlaceOfBirth>"+p_PlaceOfBirth.get(i)+"</app:PlaceOfBirth>\n" + 
        "            <app:PassportNo>"+p_PassportNo.get(i)+"</app:PassportNo>\n" + 
        "            <app:PassportIssueDate>"+p_PassportIssueDate.get(i)+"</app:PassportIssueDate>\n" + 
        "            <app:PassportExpiryDate>"+p_PassportExpiryDate.get(i)+"</app:PassportExpiryDate>\n" + 
        "            <app:ProposalTravelDate>"+p_ProposalTravelDate.get(i)+"</app:ProposalTravelDate>\n" + 
        "            <app:ExpectedTravelDate>"+p_ExpectedTravelDate.get(i)+"</app:ExpectedTravelDate>\n" + 
        "         </app:processLines>\n" ;
      xmlData2=xmlData2+"\n"+tempXml;
            }
        String xmlData3 =  "      </app:processRequest>\n" + 
        "   </soapenv:Body>\n" + 
        "</soapenv:Envelope>";
        totalXml= xmlData+xmlData2+"\n"+xmlData3;
        System.err.println("Totalxml"+totalXml);
        return totalXml;
    }
      
    public static String[] payloadHeader(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'"); //Hours:Minutes:Seconds
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.util.Date expDate ;
        expDate = new java.util.Date(t + (10 * 600000000));

    ////        date = new Date();
    //         dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    //         String expDate = dateFormat.format(DateUtils.addMinutes(date, 2));


        java.util.Date plusOne;
    //        String createdTS = dateFormat.format(date);
    //        String expiresTS = dateFormat.format(expDate);
    //        String username="praveen.c@4iapps.com";
    //        String password="Welcome#1234";
         String [] headerInfo=new String[4];
        headerInfo[0]=dateFormat.format(date);
        headerInfo[1]="oaopdtst";
        headerInfo[2]="O_0Pst#819";
        headerInfo[3]=dateFormat.format(expDate);
        return headerInfo;
    }
    
    public static String getUser(){
        
        return null;
    }
    
    
    

    
    
}
