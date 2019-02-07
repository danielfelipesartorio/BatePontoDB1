package br.com.db1.batepontodb1.mainui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.db1.batepontodb1.R;

public class MarkingsListAdapter extends RecyclerView.Adapter<MarkingsListAdapter.MyViewHolder> {
    private String[] markings;

    public MarkingsListAdapter (String[] markings){
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
        myViewHolder.mTextViewMarking.setText(markings[i]);

    }

    @Override
    public int getItemCount() {
        return markings.length ;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewMarking;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewMarking = itemView.findViewById(R.id.markingTime);
        }
    }
}
