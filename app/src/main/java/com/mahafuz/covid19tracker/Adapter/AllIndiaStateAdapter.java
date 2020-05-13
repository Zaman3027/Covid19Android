package com.mahafuz.covid19tracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mahafuz.covid19tracker.Interface.FragmentCall;
import com.mahafuz.covid19tracker.Model.SateWiseModel;
import com.mahafuz.covid19tracker.R;

import java.util.List;

public class AllIndiaStateAdapter extends RecyclerView.Adapter<AllIndiaStateAdapter.MyViewHolder> {
    CardView parentList;
    FragmentCall fragmentCall;
    List<SateWiseModel> sateWiseModelList;
    TextView listText;

    public AllIndiaStateAdapter(FragmentCall fragmentCall, List<SateWiseModel> sateWiseModelList) {
        this.fragmentCall = fragmentCall;
        this.sateWiseModelList = sateWiseModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.alli_india_list_expandable, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        listText.setText(sateWiseModelList.get(position).getState());
        parentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentCall.indiaFragmentCall(position, sateWiseModelList.get(position).getStatecode());
            }
        });
    }

    @Override
    public int getItemCount() {
        return sateWiseModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parentList = itemView.findViewById(R.id.parentListExp);
            listText = itemView.findViewById(R.id.listText);
        }
    }
}
