package com.example.angeles.encuestasuandes.ParaHacerRequest;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.angeles.encuestasuandes.Classes.CredentialManage;
import com.example.angeles.encuestasuandes.db.Usuario.Profile;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class NetworkManager implements Executor {
    public static final String BASE_URL = "http://167.99.236.252/";
    private static NetworkManager mInstance;
    private static Context mCtx;
    private static String token = "";
    private RequestQueue mRequestQueue;

    private NetworkManager(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
            CredentialManage credentialManage = CredentialManage.getInstance(context);
            token = credentialManage.getAuthToken();
        }
        return mInstance;
    }

    public void execute(Runnable r) {
        Thread t = new Thread(r);
        t.start();

    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public void PrintToken() {
        System.out.print(this.token);
    }

    public void login(String email, String password, final Response.Listener<JSONObject> responseListener,
                      Response.ErrorListener errorListener) throws JSONException {

        String url = BASE_URL + "/users/sign_in.json";

        JSONObject payload = new JSONObject();
        payload.put("email", email);
        payload.put("password", password);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, payload, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        token = response.optString("Authorization");
                        if (token != null) {
                            CredentialManage credentialManage = CredentialManage.getInstance(mCtx);
                            credentialManage.guardarCredenciales(email, password, token);
                        }

                        responseListener.onResponse(response);
                    }
                }, errorListener) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    jsonResponse.put("headers", new JSONObject(response.headers));
                    return Response.success(jsonResponse,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        mRequestQueue.add(jsonObjectRequest);
        PrintToken();
    }

    public void getBill(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, int desk) {
        String url = BASE_URL + "desks/bills/" + Integer.toString(desk);
        makeApiCall(Request.Method.GET, url, null, listener, errorListener);

    }

    public void killBill(Response.Listener<JSONObject> listener,
                         Response.ErrorListener errorListener, int bill) throws JSONException {
        String url = BASE_URL + "kill_bill";
        JSONObject obj = new JSONObject();
        obj.put("id", bill);
        makeApiCall(Request.Method.POST, url, obj, listener, errorListener);
    }

    public void getDesks(Response.Listener<JSONObject> listener,
                         Response.ErrorListener errorListener) {

        String url = BASE_URL + "desks";
        makeApiCall(Request.Method.GET, url, null, listener, errorListener);
    }

    public void getMyDesks(Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener) {
        String url = BASE_URL + "my_desks/?token=" + token;
        makeApiCall(Request.Method.GET, url, null, listener, errorListener);
    }

    public void getProducts(Response.Listener<JSONObject> listener,
                            Response.ErrorListener errorListener) {

        String url = BASE_URL + "products";
        makeApiCall(Request.Method.GET, url, null, listener, errorListener);
    }

    public void updateInterestedCategories(Response.Listener<JSONObject> listener,
                                           Response.ErrorListener errorListener, JSONObject payload) {

        String url = BASE_URL + "interest_categories/edit";
        makeApiCall(Request.Method.POST, url, payload, listener, errorListener);
    }


    public void activateEmail(Response.Listener<JSONObject> listener,
                              Response.ErrorListener errorListener, String email) {
        JSONObject payload = new JSONObject();
        try {
            payload.put("email", email);
        } catch (JSONException e) {

            e.printStackTrace();
        }
        String url = BASE_URL + "users/confirmations";
        makeApiCall(Request.Method.POST, url, payload, listener, errorListener);
    }

    public void getProfile(Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener) {
        String url = BASE_URL + "app_calls/get_profile_info.json";
        makeApiCall(Request.Method.GET, url, null, listener, errorListener);
    }

    public void getSurveys(Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener) {
        String url = BASE_URL + "app_calls/get_surveys.json";
        makeApiCall(Request.Method.GET, url, null, listener, errorListener);
    }

    public void getCategories(Response.Listener<JSONObject> listener,
                              Response.ErrorListener errorListener) {

        String url = BASE_URL + "/interest_categories";
        makeApiCall(Request.Method.GET, url, null, listener, errorListener);
    }

    public void createBill(Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener, ArrayList<Profile> products, int desk) throws JSONException {
        JSONObject payload = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < products.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("id", products.get(i));
            array.put(obj);
        }

        String url = BASE_URL + "create_bill";
        JSONObject obj = new JSONObject();
        obj.put("id", desk);
        obj.put("number", desk);
        payload.put("desk", obj);
        payload.put("products", array);
        payload.put("token", token);
        makeApiCall(Request.Method.POST, url, payload, listener, errorListener);
    }

    public void updateBill(Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener, ArrayList<Profile> products, int desk) throws JSONException {
        JSONObject payload = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < products.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("id", products.get(i));
            array.put(obj);
        }

        String url = BASE_URL + "update_bill";
        JSONObject obj = new JSONObject();
        obj.put("id", desk);
        obj.put("number", desk);
        payload.put("desk", obj);
        payload.put("products", array);
        payload.put("token", token);
        makeApiCall(Request.Method.POST, url, payload, listener, errorListener);
    }

    public void setToken(String data) {
        token = data;
    }

    private void makeApiCall(int method, String url, JSONObject payload, Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener) {

        JsonObjectArrayRequest jsonObjectRequest = new JsonObjectArrayRequest
                (method, url, payload, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        mRequestQueue.add(jsonObjectRequest);
    }
}