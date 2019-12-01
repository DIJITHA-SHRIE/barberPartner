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

import com.hair_beauty.partner.Adapter.TodayAppt_adp;
import com.hair_beauty.partner.Model.Myservices_Mod;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyAppointment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyAppointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAppointment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private ArrayList<Myservices_Mod> todayArray ;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.rv_today_appt)
    RecyclerView rv_today_appt;

    public MyAppointment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param todayArray Parameter 2.
     * @return A new instance of fragment MyAppointment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAppointment newInstance(String param1, ArrayList<Myservices_Mod> todayArray) {
        MyAppointment fragment = new MyAppointment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putParcelableArrayList(ARG_PARAM2, todayArray);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            todayArray = getArguments().getParcelableArrayList(ARG_PARAM2);
            Log.i("TodayArray",todayArray.size()+"");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_appointment, container, false);
        ButterKnife.bind(this,view);
        TodayAppt_adp adapter = new TodayAppt_adp(todayArray,getActivity());
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv_today_appt.setLayoutManager(lm);
        rv_today_appt.setAdapter(adapter);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
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
}
