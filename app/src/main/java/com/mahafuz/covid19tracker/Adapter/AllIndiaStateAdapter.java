package com.mahafuz.covid19tracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mahafuz.covid19tracker.Interface.FragmentCall;
import com.mahafuz.covid19tracker.R;

public class AllIndiaStateAdapter extends RecyclerView.Adapter<AllIndiaStateAdapter.MyViewHolder> {
    FragmentCall fragmentCall;
    String[] indiaState;

    public AllIndiaStateAdapter(FragmentCall fragmentCall, String[] indiaState) {
        this.fragmentCall = fragmentCall;
        this.indiaState = indiaState;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_india_states_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final String currentState = indiaState[position];
        holder.stateName.setText(currentState);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentCall.indiaFragmentCall(position, currentState);
            }
        });
    }

    @Override
    public int getItemCount() {
        return indiaState.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView stateName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.state_list_linear_layout);
            this.stateName = itemView.findViewById(R.id.state_name);
        }
    }
}
