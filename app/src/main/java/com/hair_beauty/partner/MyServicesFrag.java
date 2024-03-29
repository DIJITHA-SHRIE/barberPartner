package com.hair_beauty.partner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hair_beauty.partner.Adapter.MyServAdp;
import com.hair_beauty.partner.Model.Myservices_Mod;
import com.hair_beauty.partner.utility.ServerLinks;
import com.hair_beauty.partner.utility.SharedPreferenceClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyServicesFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyServicesFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyServicesFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<Myservices_Mod> myservices_modArrayList = new ArrayList<>();
    String saloon_name,saloon_id;
    SharedPreferenceClass sharedPreferenceClass;
    MyServAdp adapter;
    @BindView(R.id.rv_my_serv)
    RecyclerView rv_my_serv;

    public MyServicesFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyServicesFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static MyServicesFrag newInstance(String param1, String param2) {
        MyServicesFrag fragment = new MyServicesFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_services, container, false);
        ButterKnife.bind(this,view);
        sharedPreferenceClass = new  SharedPreferenceClass(getActivity());
        saloon_name =sharedPreferenceClass.getValue_string("BARBER_NAME");
        saloon_id=sharedPreferenceClass.getValue_string("BARBERID");

        getmyServicesData();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

 /*   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void getmyServicesData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.MY_SERVICES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response_my_ser",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if(message.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i = 0; i<jsonArray.length();i++){
                            JSONObject row = jsonArray.getJSONObject(i);
                            myservices_modArrayList.add(new Myservices_Mod(row.getString("pac_name"),
                                    row.getString("cat_name"),row.getString("price"),row.getString("times") ));
                        }
                    }
                    adapter = new MyServAdp(myservices_modArrayList,getActivity());
                    LinearLayoutManager lm = new LinearLayoutManager(getActivity());

                    rv_my_serv.setLayoutManager(lm);
                    rv_my_serv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token <token>");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Log.i("SaloonName",saloon_id);
                params.put("owned_by", saloon_id); //B7982
                //  params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
