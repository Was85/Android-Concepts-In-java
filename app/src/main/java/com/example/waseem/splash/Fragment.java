package com.example.waseem.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Waseem on 2/22/2018.
 */

public class Fragment extends android.app.Fragment {
    private listviewAdapter adapter;
    Bundle fragBundel;
    private HashMap<String, String> athkarRep;
    CollapsingToolbarLayout bar;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragBundel = getArguments();
        return inflater.inflate(R.layout.activity_list_of_the_athkar, container, false);

    }


    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bar = getActivity().findViewById(R.id.collaping);
        if (fragBundel.getSerializable("was") != null) {
            athkarRep = (HashMap<String, String>) fragBundel.getSerializable("was");
            String title = (String) fragBundel.get("title");
            bar.setTitle(title);

        }
        Toast.makeText(this.getActivity(), "nopooooo", Toast.LENGTH_SHORT).show();
        RecyclerView recView = getActivity().findViewById(R.id.recView);
        adapter = new listviewAdapter(athkarRep, getActivity());
        recView.setAdapter(adapter);
        DefaultItemAnimator a = new DefaultItemAnimator();
        recView.setItemAnimator(a);
        a.setAddDuration(500);
        a.setRemoveDuration(500);
        a.runPendingAnimations();
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
