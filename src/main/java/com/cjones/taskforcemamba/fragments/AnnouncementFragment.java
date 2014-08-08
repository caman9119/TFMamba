package com.cjones.taskforcemamba.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cjones.taskforcemamba.R;
import com.cjones.taskforcemamba.adapter.RssReaderListAdapter;
import com.cjones.taskforcemamba.helper.RssFeedStructure;
import com.cjones.taskforcemamba.helper.XmlHandler;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AnnouncementFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AnnouncementFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView _rssFeedListView;
    private RssReaderListAdapter _adapter;
    List<RssFeedStructure> rssStr ;
    private FadingActionBarHelper mFadingHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnHeadlineSelectedListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment announcementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnnouncementFragment newInstance(String param1, String param2) {
        AnnouncementFragment fragment = new AnnouncementFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }
    public AnnouncementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private class RssFeedTask extends AsyncTask<String, Void, String> {
        // private String Content;
        private ProgressDialog Dialog;
        String response = "";

        @Override
        protected void onPreExecute() {
            Dialog = new ProgressDialog(getActivity());
            Dialog.setMessage("Rss Loading...");
            Dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                //String feed = "http://feeds.nytimes.com/nyt/rss/HomePage";

                String feed = "http://www.tfmamba.com/external.php?do=rss&type=newcontent&sectionid=155&days=120&count=10";
                XmlHandler rh = new XmlHandler();
                rssStr = rh.getLatestArticles(feed);
            } catch (Exception e) {
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            if(rssStr != null){
                _adapter = new RssReaderListAdapter(getActivity(),rssStr);
                _rssFeedListView.setAdapter(_adapter);
                Log.i("Task Force Mamba", "onPostExecute adapter = " + _adapter);
            }
            Dialog.dismiss();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null){

        } else {
            RssFeedTask rssTask = new RssFeedTask();
            rssTask.execute();
        }
        View v = inflater.inflate(R.layout.fragment_mediapage_list, null, false);
        _rssFeedListView = (ListView) v.findViewById(R.id.rssfeed_listview);

        return v;
//        _rssFeedListView = (ListView)getView().findViewById(R.id.rssfeed_listview);
//        return inflater.inflate(R.layout.fragment_mediapage_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
        public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the event to the host activity
        mListener.onArticleSelected(position);
    }
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnHeadlineSelectedListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnHeadlineSelectedListener");
//        }
    }

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


}
