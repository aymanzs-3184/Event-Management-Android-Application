package com.example.assignment_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListCategory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CategoryRecyclerAdapter categoryRecyclerAdapter;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    public FragmentListCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListCategory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListCategory newInstance(String param1, String param2) {
        FragmentListCategory fragment = new FragmentListCategory();
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
        View v = inflater.inflate(R.layout.fragment_list_category, container, false);

        recyclerView = v.findViewById(R.id.category_recycler_view);

        recyclerView.setNestedScrollingEnabled(false);

// A Linear RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        layoutManager = new LinearLayoutManager(getActivity());
// Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
        recyclerView.setLayoutManager(layoutManager);

        categoryRecyclerAdapter = new CategoryRecyclerAdapter();


        ArrayList<Category> listCategories = new ArrayList<Category>();
        categoryRecyclerAdapter.setData(listCategories);
        recyclerView.setAdapter(categoryRecyclerAdapter);
        LoadAndSetDataFromSharedPreferences();

        return v;
    }


    public void LoadAndSetDataFromSharedPreferences()
    {
        SharedPreferences sP = getActivity().getSharedPreferences(SpKeys.SP_FILE_NAME, Context.MODE_PRIVATE);
        String categoryDBString = sP.getString(SpKeys.SP_CATEGORY_DB, "[]");
        Type type = new TypeToken<ArrayList<Category>>() {}.getType();
        Gson gson = new Gson();
        ArrayList<Category> listCategories = gson.fromJson(categoryDBString, type);
        categoryRecyclerAdapter.setData(listCategories);
        categoryRecyclerAdapter.notifyDataSetChanged();
    }

}