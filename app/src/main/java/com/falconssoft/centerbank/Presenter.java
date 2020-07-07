package com.falconssoft.centerbank;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.falconssoft.centerbank.Models.LoginINFO;

import org.json.JSONException;
import org.json.JSONObject;

class Presenter {

    private SingUpActivity singUpActivity;
    private LogInActivity logInActivity;
    private Context context;
    private RequestQueue requestQueue;
    private LoginINFO user;// for login

    private JsonObjectRequest signUpRequest;
    String dd="http://falconssoft.net/ScanChecks/APIMethods.dll/";
    private String urlSignUp = dd+"RegisterUser?INFO=";

    private JsonObjectRequest loginRequest;
    private String urlLogin =  dd+"CheckUser?USERMOB=";

    private String getUranUp = "http://localhost:8081/RegisterUser?INFO={\"NATID\":\"220022\",\"FIRSTNM\":\"ALAA\"" +
            ",\"FATHERNM\":\"Salem\",\"GRANDNM\":\"M.\",\"FAMILYNM\":\"JF\",\"DOB\":\"19/05/1978\"" +
            ",\"GENDER\":\"0\",\"MOBILENO\":\"0798899716\",\"ADDRESS\":\"ADDRESSS\",\"EMIAL\":\"mail@Yahoo.com\",\"PASSWORD\":\"123\"}";

    public Presenter(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    // ******************************************************************************
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

    // ******************************************************************************
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
            if (response.toString().contains("\"StatusCode\":0,\"StatusDescreption\":\"OK\",\"INFO\"")){
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            logInActivity.goToTheMainPage(response.toString(), user);

        }
    }
}
