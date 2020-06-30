package com.falconssoft.centerbank;

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

import java.util.HashMap;
import java.util.Map;

class Presenter {

    private SingUpActivity singUpActivity;

    private RequestQueue requestQueue;
    private JsonObjectRequest signUpRequest;
    private String urlSignUp = "http://10.0.0.16:8081/RegisterUser?INFO={\"NATID\":\"220022\",\"FIRSTNM\":\"ALAA\",\"FATHERNM\":\"Salem\"\n" +
            ",\"GRANDNM\":\"M.\",\"FAMILYNM\":\"JF\",\"DOB\":\"19/05/1978\",\"GENDER\":\"0\"\n" +
            ",\"MOBILENO\":\"0798899716\",\"ADDRESS\":\"ADDRESSS\",\"EMIAL\":\"mail@Yahoo.com\",\"PASSWORD\":\"123\"}";

    public Presenter(SingUpActivity singUpActivity) {
        this.singUpActivity = singUpActivity;
        this.requestQueue = Volley.newRequestQueue(singUpActivity.getBaseContext());
    }

    // ******************************************************************************
    public void saveSignUpInfo(final LoginINFO loginINFO) {

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("NATID", loginINFO.getNationalID());
            jsonObject.put("FIRSTNM", loginINFO.getFirstName());
            jsonObject.put("FATHERNM", loginINFO.getSecondName());
            jsonObject.put("GRANDNM", loginINFO.getThirdName());
            jsonObject.put("FAMILYNM", loginINFO.getFourthName());
            jsonObject.put("DOB", loginINFO.getBirthDate());
            jsonObject.put("GENDER", loginINFO.getGender());
            jsonObject.put("MOBILENO", loginINFO.getUsername());
            jsonObject.put("ADDRESS", loginINFO.getAddress());
            jsonObject.put("EMIAL", loginINFO.getEmail());
            jsonObject.put("PASSWORD", loginINFO.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("url", urlSignUp + jsonObject);
        signUpRequest = new JsonObjectRequest(Request.Method.GET, urlSignUp
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
            singUpActivity.hideDialog();

        }

        @Override
        public void onResponse(JSONObject response) {

//            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
            Log.e("presenter/", "signup/" + response.toString());
            if (response.toString().contains("{\"StatusCode\":0,\"StatusDescreption\":\"OK\"}"))
                Toast.makeText(singUpActivity, "Saved Successfully", Toast.LENGTH_SHORT).show();
            else if (response.toString().contains("{\"StatusCode\" : 4,\"StatusDescreption\":\"Error in Saving Check Temp.\" }"))
                Toast.makeText(singUpActivity, "Not Saved !", Toast.LENGTH_SHORT).show();
            else if (response.toString().contains("{\"StatusCode\" : 9,\"StatusDescreption\":\"Error in saving User.\" }"))
                Toast.makeText(singUpActivity, "PLease check sent Info first!", Toast.LENGTH_SHORT).show();

            singUpActivity.hideDialog();
        }
    }
}
