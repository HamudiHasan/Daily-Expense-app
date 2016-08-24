package com.blackflag.whereismymoney.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackflag.whereismymoney.R;

import com.blackflag.whereismymoney.model.ExpanseTable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackFlag on 7/21/2016.
 */
public class ExpanseAdapter extends RecyclerView.Adapter<ExpanseAdapter.ExpanseHolder> {


    public List<ExpanseTable> expanses;

    public ExpanseAdapter(List<ExpanseTable> expanses) {
        this.expanses = expanses;
    }

    @Override
    public ExpanseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.expanse_item,parent,false);
        ExpanseHolder expanseHolder=new ExpanseHolder(v);
        return expanseHolder;
    }

    @Override
    public void onBindViewHolder(ExpanseHolder holder, int position) {

        holder.tvPurpose.setText(expanses.get(position).getPurpose());
        holder.tvAmount.setText(expanses.get(position).getAmount());
        if(expanses.get(position).getType().equals("Expanse"))
            holder.ivStatus.setImageResource(R.drawable.down_arrow);
        else holder.ivStatus.setImageResource(R.drawable.up_arrow);
        holder.tvDate.setText(expanses.get(position).getMonth()+" , "+expanses.get(position).getDay()+"\n"+expanses.get(position).getYear());



    }

    @Override
    public int getItemCount() {
        return expanses.size();
    }

    class ExpanseHolder extends RecyclerView.ViewHolder{

        TextView tvAmount,tvPurpose,tvDate;
        ImageView ivStatus;
        public ExpanseHolder(View itemView) {
            super(itemView);
            tvAmount=(TextView) itemView.findViewById(R.id.tvAmount);
            ivStatus=(ImageView) itemView.findViewById(R.id.ivStatus);
            tvPurpose=(TextView) itemView.findViewById(R.id.tvPurpose);
            tvDate=(TextView) itemView.findViewById(R.id.tvDate);

        }
    }

    public ExpanseAdapter() {
    }


}
