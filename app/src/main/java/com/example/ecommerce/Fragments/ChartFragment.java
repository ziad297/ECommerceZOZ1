package com.example.ecommerce.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.StackedBarModel;


public class ChartFragment extends Fragment {

    TextView tvR, tvPython, tvCPP, tvJava;
   BarChart pieChart;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        pieChart = view.findViewById(R.id.piechart);
        setData();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setData()
    {

        String[] Colors = {"#FFA726","#66BB6A","#EF5350","#29B6F6","#E97777","#395144","#AA8B56","#F0EBCE","#40DFEF",
        "#B9F8D3","#FFFBE7","#E78EA9"};

        reference = FirebaseDatabase.getInstance().getReference().child("Orders");

        reference.child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int size = (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String s = snapshot.child("quantity").getValue().toString();
                    String s1 = String.valueOf(snapshot.child("productname").getValue());
                    System.out.println(s1);
                    System.out.println(s1);
                    System.out.println(s1);
                    System.out.println(s1);
                    System.out.println(s1);
                    System.out.println(s1);
                    System.out.println(s1);
                    System.out.println(s1);
                    System.out.println(s1);
                    System.out.println(s1);
                    pieChart. addBar(new BarModel(s1,Integer.parseInt(s) , Color.parseColor(Colors[size])));
                    size--;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        // Set the data and color to the pie chart
//        pieChart.addPieSlice(
//                new PieModel(
//                        "R",
//                        Integer.parseInt(tvR.getText().toString()),
//                        Color.parseColor("#FFA726")));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "Python",
//                        Integer.parseInt(tvPython.getText().toString()),
//                        Color.parseColor("#66BB6A")));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "C++",
//                        Integer.parseInt(tvCPP.getText().toString()),
//                        Color.parseColor("#EF5350")));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "Java",
//                        Integer.parseInt(tvJava.getText().toString()),
//                        Color.parseColor("#29B6F6")));
//
//        // To animate the pie chart
//        pieChart.startAnimation();
    }
}