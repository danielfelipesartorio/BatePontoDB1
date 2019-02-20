package br.com.db1.batepontodb1.secondui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.db1.batepontodb1.R;

public class MarkingsListAdapter extends RecyclerView.Adapter<MarkingsListAdapter.MyViewHolder> {
    private String[] markings;

    void setMarkings(String[] markings){
        this.markings = markings;
    }

    MarkingsListAdapter(String[] markings){
        this.markings=markings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.marking_layout,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.mTextViewMarking.setText(markings[i].substring(0,16));
        myViewHolder.mTextViewCompany.setText(markings[i].substring(17));
    }

    @Override
    public int getItemCount() {
        return markings.length ;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewMarking,mTextViewCompany;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewMarking = itemView.findViewById(R.id.markingTime);
            mTextViewCompany = itemView.findViewById(R.id.markingCompany);
        }
    }


}
