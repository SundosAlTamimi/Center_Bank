package com.falconssoft.centerbank;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.falconssoft.centerbank.Models.LoginINFO;
import com.falconssoft.centerbank.databinding.ActivityOwnerChequesBinding;
import com.falconssoft.centerbank.databinding.ActivityTrackingChequeBinding;
import com.falconssoft.centerbank.viewmodel.ChequeInfoVM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

class Presenter {

    private SingUpActivity singUpActivity;
    private LogInActivity logInActivity;
    private Context context;
    private RequestQueue requestQueue;
    private LoginINFO user;// for login
    private ActivityOwnerChequesBinding binding;
    private OwnerCheques ownerCheques;
    private TrackingCheque trackingCheque;
    private ActivityTrackingChequeBinding trackingChequeBinding;
    String URL = "http://falconssoft.net/ScanChecks/APIMethods.dll/";

    private JsonObjectRequest signUpRequest;
    private String urlSignUp = URL + "RegisterUser?INFO=";

    private JsonObjectRequest loginRequest;
    private String urlLogin = URL + "CheckUser?USERMOB=";

    private JsonObjectRequest trackingRequest;
    private String urlTracking = URL + "TrackCheck?ACCCODE=";//0014569990011000&IBANNO=\"\"&SERIALNO=\"\"&BANKNO=004&BRANCHNO=0099&CHECKNO=390105";

    private JsonObjectRequest ownerChequeRequest;
    private String urlOwnerCheque = URL + "GetOwnerChecks?MOBILENO=";

    private String getUranUp = "http://localhost:8081/RegisterUser?INFO={\"NATID\":\"220022\",\"FIRSTNM\":\"ALAA\"" +
            ",\"FATHERNM\":\"Salem\",\"GRANDNM\":\"M.\",\"FAMILYNM\":\"JF\",\"DOB\":\"19/05/1978\"" +
            ",\"GENDER\":\"0\",\"MOBILENO\":\"0798899716\",\"ADDRESS\":\"ADDRESSS\",\"EMIAL\":\"mail@Yahoo.com\",\"PASSWORD\":\"123\"}";

    public Presenter(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    // **************************************** trackingCheque **************************************
    public void trackingCheque(TrackingCheque context, ChequeInfoVM chequeInfoVM, ActivityTrackingChequeBinding binding) {
        trackingCheque = context;
        this.trackingChequeBinding = binding;
        trackingRequest = new JsonObjectRequest(Request.Method.GET, urlTracking + chequeInfoVM.getAccCode()
                + "&IBANNO=" + chequeInfoVM.getIbanNo() + "&SERIALNO=" + chequeInfoVM.getSerialNo()
                + "&BANKNO=" + chequeInfoVM.getBankNo() + "&BRANCHNO=" + chequeInfoVM.getBranchNo()
                + "&CHECKNO=" + chequeInfoVM.getChequeNo(),null, new TrackingRequestClass(), new TrackingRequestClass());

        Log.e("trackurl", urlTracking + chequeInfoVM.getAccCode()
                + "&IBANNO=" + chequeInfoVM.getIbanNo() + "&SERIALNO=" + chequeInfoVM.getSerialNo()
                + "&BANKNO=" + chequeInfoVM.getBankNo() + "&BRANCHNO=" + chequeInfoVM.getBranchNo()
                + "&CHECKNO=" + chequeInfoVM.getChequeNo());

        requestQueue.add(trackingRequest);
    }

    class TrackingRequestClass implements Response.Listener<JSONObject>, Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("presenter/", "trackingCheque/error/" + error.toString());
//            http://falconssoft.net/ScanChecks/APIMethods.dll/TrackCheck?
//            ACCCODE=2014569990011000
//            &IBANNO=123456789
//            &SERIALNO=09A8A74F513D4958
//            &BANKNO=004
//            &BRANCHNO=0099
//            &CHECKNO=390186

//            http://falconssoft.net/ScanChecks/APIMethods.dll/TrackCheck?
//            ACCCODE=2014569990011000
//            &IBANNO=%22123456789%22
//            &SERIALNO=%220A442902D76B49D0%22
//            &BANKNO=004
//            &BRANCHNO=0099
//            &CHECKNO=390186
        }

        @Override
        public void onResponse(JSONObject response) {

            Log.e("presenter/", "trackingCheque/" + response.toString());
            if (response.toString().contains("\"StatusCode\":0,\"StatusDescreption\":\"OK\",\"INFO\"")) {
                try {

//                    {"ROWID":"AABYLYAAPAAAADDAAG","BANKNO":"004","BANKNM":"Jordan Bank"
//                    ,"BRANCHNO":"0099","CHECKNO":"390105","ACCCODE":"0014569990011000"
//                    ,"IBANNO":"","CUSTOMERNM":"الصقور للبرمجيات","QRCODE":"","SERIALNO":"876BBDA3784049B3"
//                    ,"CHECKISSUEDATE":"7\/8\/2020 4:54:24 PM","CHECKDUEDATE":"8\/7\/2020"
//                    ,"TOCUSTOMERNM":"fghdtyfyugyu","AMTJD":"50","AMTFILS":"20"
//                    ,"AMTWORD":"خمسون ديناراً و 200 فلساً","TOCUSTOMERMOB":"0798858428"
//                    ,"TOCUSTOMERNATID":"1234569709","CHECKWRITEDATE":"7\/8\/2020 4:54:24 PM"
//                    ,"CHECKPICPATH":"\\\\?\\C:\\ScanChecks\\Images\\00400990014569990011000390105.txt"
//                    ,"TRANSSTATUS":"0","USERNO":"0798600143","ISCO":"0","ISFB":"0"
//                    ,"COMPANY":"","NOTE":"","TRANSTYPE":"0","RJCTREASON":""}
                    JSONArray jsonArray = response.getJSONArray("INFO");
                    List<ChequeInfoVM> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.e("presenter/", "trackingChequelength/" + jsonArray.length());
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ChequeInfoVM chequeInfo = new ChequeInfoVM();
                        chequeInfo.setRowId(jsonObject.getString("ROWID"));
                        chequeInfo.setBankNo(jsonObject.getString("BANKNO"));
                        chequeInfo.setBankName(jsonObject.getString("BANKNM"));
                        chequeInfo.setBranchNo(jsonObject.getString("BRANCHNO"));

                        chequeInfo.setChequeNo(jsonObject.getString("CHECKNO"));
                        chequeInfo.setAccCode(jsonObject.getString("ACCCODE"));
                        chequeInfo.setIbanNo(jsonObject.getString("IBANNO"));
                        chequeInfo.setQrCode(jsonObject.getString("QRCODE"));
                        chequeInfo.setSerialNo(jsonObject.getString("SERIALNO"));
                        chequeInfo.setCheckIssueDate(jsonObject.getString("CHECKISSUEDATE"));
                        chequeInfo.setCheckDueDate(jsonObject.getString("CHECKDUEDATE"));
                        chequeInfo.setOwnerName(jsonObject.getString("CUSTOMERNM"));
                        chequeInfo.setMoneyInDinar(jsonObject.getString("AMTJD"));
                        chequeInfo.setMoneyInFils(jsonObject.getString("AMTFILS"));
                        chequeInfo.setMoneyInWord(jsonObject.getString("AMTWORD"));
                        chequeInfo.setReceiverMobileNo(jsonObject.getString("TOCUSTOMERMOB"));
                        chequeInfo.setReceiverNationalID(jsonObject.getString("TOCUSTOMERNATID"));
                        chequeInfo.setCustomerWriteDate(jsonObject.getString("CHECKWRITEDATE"));
                        chequeInfo.setOwnerPhone(jsonObject.getString("USERNO"));
                        chequeInfo.setISCO(jsonObject.getString("ISCO"));
                        chequeInfo.setISBF(jsonObject.getString("ISFB"));
                        chequeInfo.setCompanyName(jsonObject.getString("COMPANY"));
                        chequeInfo.setNoteCheck(jsonObject.getString("NOTE"));
                        chequeInfo.setTransType(jsonObject.getString("TRANSTYPE"));

                        chequeInfo.setRejectReason(jsonObject.getString("RJCTREASON"));

//                        chequeInfo.setChequePIc(jsonObject.getString("CHECKPICPATH"));
//                        chequeInfo.setStatus(jsonObject.getString("TRANSSTATUS"));
//                        chequeInfo.setOwnerID(jsonObject.getString("OWNERNATID"));
                        chequeInfo.setReceiverName(jsonObject.getString("TOCUSTOMERNM"));

                        list.add(chequeInfo);
                    }

                    trackingCheque.fillAdapter(list, trackingChequeBinding);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (response.toString().contains("\"StatusCode\":28,\"StatusDescreption\":\"This User not have checks.\"")) {
//            Toast.makeText(singUpActivity, "No cheques found!", Toast.LENGTH_SHORT).show();
            }
            else if (response.toString().contains("\"StatusCode\":6,\"StatusDescreption\":\"Check Data not found\"")) {//{"StatusCode":6,"StatusDescreption":"Check Data not found"}

                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("**** Cheque Tracing ****")
                                .setContentText("Check Data not found")
                                .show();

            } }
    }

    // **************************************** owner Cheque **************************************
    public void ownerCheque(OwnerCheques context, String phone, ActivityOwnerChequesBinding binding) {
        ownerCheques = context;
        this.binding = binding;
        ownerChequeRequest = new JsonObjectRequest(Request.Method.GET, urlOwnerCheque + phone
                , null, new OwnerChequeRequestClass(), new OwnerChequeRequestClass());
        requestQueue.add(ownerChequeRequest);
    }

    class OwnerChequeRequestClass implements Response.Listener<JSONObject>, Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("presenter/", "ownerCheque/error/" + error.toString());

        }

        @Override
        public void onResponse(JSONObject response) {

            Log.e("presenter/", "ownerCheque/" + response.toString());
            if (response.toString().contains("\"StatusCode\":0,\"StatusDescreption\":\"OK\",\"INFO\"")) {
                try {
//                    {"ROWID":"AAAsRTAAuAAAADTAAA","BANKNO":"004","BANKNM":"","BRANCHNO":"0099"
//                    ,"CHECKNO":"390179","ACCCODE":"8014569990011000","IBANNO":"123456789"
//                    ,"CUSTOMERNM":"الصقور للبرمجيات","QRCODE":"390179;004;0099;8014569990011000"
//                    ,"SERIALNO":"3FB11DD1CD0A4E31","CHECKISSUEDATE":"15\/07\/2020 15:20:44"
//                    ,"CHECKDUEDATE":"","TOCUSTOMERNM":"","AMTJD":"","AMTFILS":"","AMTWORD":""
//                    ,"TOCUSTOMERMOB":"","TOCUSTOMERNATID":"","CHECKWRITEDATE":"","CHECKPICPATH":""
//                    ,"USERNO":"","ISCO":"","ISFB":"","COMPANY":"","NOTE":"","TRANSTYPE":""
//                    ,"INACTIVE":"","OWNERMOBNO":"0798899716","OWNERNATID":"2233445566"}
                    JSONArray jsonArray = response.getJSONArray("INFO");
                    List<ChequeInfoVM> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.e("presenter/", "ownerCheque/" + jsonArray.length());
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ChequeInfoVM chequeInfo = new ChequeInfoVM();
                        chequeInfo.setRowId(jsonObject.getString("ROWID"));
                        chequeInfo.setBankNo(jsonObject.getString("BANKNO"));
                        chequeInfo.setBankName(jsonObject.getString("BANKNM"));
                        chequeInfo.setBranchNo(jsonObject.getString("BRANCHNO"));

                        chequeInfo.setChequeNo(jsonObject.getString("CHECKNO"));
                        chequeInfo.setAccCode(jsonObject.getString("ACCCODE"));
                        chequeInfo.setIbanNo(jsonObject.getString("IBANNO"));

                        chequeInfo.setOwnerName(jsonObject.getString("CUSTOMERNM"));
                        chequeInfo.setOwnerPhone(jsonObject.getString("USERNO"));
                        chequeInfo.setOwnerID(jsonObject.getString("OWNERNATID"));

                        chequeInfo.setQrCode(jsonObject.getString("QRCODE"));

                        chequeInfo.setSerialNo(jsonObject.getString("SERIALNO"));
                        chequeInfo.setCheckIssueDate(jsonObject.getString("CHECKISSUEDATE"));

                        chequeInfo.setCheckDueDate(jsonObject.getString("CHECKDUEDATE"));
                        chequeInfo.setMoneyInDinar(jsonObject.getString("AMTJD"));
                        chequeInfo.setMoneyInFils(jsonObject.getString("AMTFILS"));
                        chequeInfo.setMoneyInWord(jsonObject.getString("AMTWORD"));

                        chequeInfo.setReceiverName(jsonObject.getString("TOCUSTOMERNM"));
                        chequeInfo.setReceiverMobileNo(jsonObject.getString("TOCUSTOMERMOB"));
                        chequeInfo.setReceiverNationalID(jsonObject.getString("TOCUSTOMERNATID"));

                        chequeInfo.setCustomerWriteDate(jsonObject.getString("CHECKWRITEDATE"));
                        chequeInfo.setChequePIc(jsonObject.getString("CHECKPICPATH"));

                        chequeInfo.setCompanyName(jsonObject.getString("COMPANY"));
                        chequeInfo.setNoteCheck(jsonObject.getString("NOTE"));
                        chequeInfo.setTransType(jsonObject.getString("TRANSTYPE"));

                        list.add(chequeInfo);
                    }

                    ownerCheques.fillAdapter(list, binding);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (response.toString().contains("\"StatusCode\":28,\"StatusDescreption\":\"This User not have checks.\""))
                ;
//            Toast.makeText(singUpActivity, "No cheques found!", Toast.LENGTH_SHORT).show();
        }
    }

    // *************************************** saveSignUpInfo ***************************************
    public void saveSignUpInfo(SingUpActivity singUpActivity, final LoginINFO loginINFO) {

        this.singUpActivity = singUpActivity;
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("NATID", loginINFO.getNationalID());
            jsonObject.put("FIRSTNM", loginINFO.getFirstName());
            jsonObject.put("FATHERNM", loginINFO.getSecondName());
            jsonObject.put("GRANDNM", loginINFO.getThirdName());
            jsonObject.put("FAMILYNM", loginINFO.getFourthName());
            jsonObject.put("DOB", "19/05/1978");
            jsonObject.put("GENDER", loginINFO.getGender());
            jsonObject.put("MOBILENO", loginINFO.getUsername());
            jsonObject.put("ADDRESS", loginINFO.getAddress());
            jsonObject.put("EMIAL", loginINFO.getEmail());
            jsonObject.put("PASSWORD", loginINFO.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("url", urlSignUp + jsonObject);
        signUpRequest = new JsonObjectRequest(Request.Method.GET, urlSignUp + jsonObject
                , null, new SignUpRequestClass(), new SignUpRequestClass())
//        {
//            @Override
//            public Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("INFO", jsonObject.toString());
//
////                params.put("NATID", "" + loginINFO.getNationalID());
////                params.put("FIRSTNM", loginINFO.getFirstName());
////                params.put("FATHERNM", loginINFO.getSecondName());
////                params.put("GRANDNM", loginINFO.getThirdName());
////                params.put("FAMILYNM", loginINFO.getFourthName());
////                params.put("DOB", loginINFO.getBirthDate());
////                params.put("GENDER", loginINFO.getGender());
////                params.put("MOBILENO", loginINFO.getUsername());
////                params.put("ADDRESS", loginINFO.getAddress());
////                params.put("EMIAL", loginINFO.getEmail());
////                params.put("PASSWORD", loginINFO.getPassword());
//                return params;
//            }
//        }
        ;
        requestQueue.add(signUpRequest);
    }

    class SignUpRequestClass implements Response.Listener<JSONObject>, Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("presenter/", "signup/error/" + error.toString());
            singUpActivity.goToLoginPage(error.toString());

        }

        @Override
        public void onResponse(JSONObject response) {

//            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
            Log.e("presenter/", "signup/" + response.toString());
            singUpActivity.goToLoginPage(response.toString());

        }
    }

    // **************************************** loginInfoCheck **************************************
    public void loginInfoCheck(LogInActivity logInActivity, LoginINFO loginINFO) {
        user = loginINFO;
        this.logInActivity = logInActivity;

        loginRequest = new JsonObjectRequest(Request.Method.GET, urlLogin + loginINFO.getUsername() + "&PASS=" + loginINFO.getPassword()
                , null, new LoginRequestClass(), new LoginRequestClass());
        requestQueue.add(loginRequest);
    }

    class LoginRequestClass implements Response.Listener<JSONObject>, Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("presenter/", "login/error/" + error.toString());
            logInActivity.goToTheMainPage(error.getMessage(), null);

        }

        @Override
        public void onResponse(JSONObject response) {

//            "INFO":[{"NATID":"1122334455","FIRSTNM":"abeer","FATHERNM":"ali","GRANDNM":"ahmad","FAMILYNM":"hiary"
//                    ,"DOB":"19\/05\/1978","GENDER":"0","MOBILENO":"0772095887","ADDRESS":"salt"
//                    ,"EMIAL":"hiary.abeer@yahoo.com","PASSWORD":"123","INACTIVE":"0","INDATE":"01\/07\/2020 12:34:40"}]

//            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
            Log.e("presenter/", "login/" + response.toString());
            if (response.toString().contains("\"StatusCode\":0,\"StatusDescreption\":\"OK\",\"INFO\"")) {
                try {
                    JSONObject jsonObject = response.getJSONArray("INFO").getJSONObject(0);
                    Log.e("jsonobject", jsonObject.getString("NATID") + jsonObject.getString("INACTIVE") + jsonObject.getString("INDATE"));
                    user.setNationalID(jsonObject.getString("NATID"));
                    user.setFirstName(jsonObject.getString("FIRSTNM"));
                    user.setSecondName(jsonObject.getString("FATHERNM"));
                    user.setThirdName(jsonObject.getString("GRANDNM"));
                    user.setFourthName(jsonObject.getString("FAMILYNM"));
                    user.setBirthDate(jsonObject.getString("DOB"));
                    user.setGender(jsonObject.getString("GENDER"));
                    user.setUsername(jsonObject.getString("MOBILENO"));
                    user.setAddress(jsonObject.getString("ADDRESS"));
                    user.setEmail(jsonObject.getString("EMIAL"));
                    user.setPassword(jsonObject.getString("PASSWORD"));
                    user.setInactive(jsonObject.getString("INACTIVE"));
                    user.setIndate(jsonObject.getString("INDATE"));

                    Log.e("remember/Active3", "" + user.getIsRemember() + user.getIsNowActive());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            logInActivity.goToTheMainPage(response.toString(), user);

        }
    }
}
