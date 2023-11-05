package com.suffixit.hrm_suffix.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.ScrumModel;

import java.util.ArrayList;
import java.util.List;
/*

public class ScrumAdapter extends RecyclerView.Adapter<ScrumAdapter.ScrumViewHolder> {
    private List<ScrumModel> dataList;
    private OnButtonClickListener buttonClickListener;


 */
/*   public ScrumAdapter(List<ScrumModel> dataList) {
        this.dataList = dataList;
    }*//*


    public ScrumAdapter(List<ScrumModel> dataList, OnButtonClickListener buttonClickListener) {
        this.dataList = dataList;
        this.buttonClickListener = buttonClickListener;
    }

    @NonNull
    @Override
    public ScrumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scrum_list, parent, false);
        return new ScrumViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ScrumViewHolder holder, int position) {
        ScrumModel data = dataList.get(position);
        holder.txtID.setText(data.getUserId());
        holder.txtName.setText(data.getName());

        holder.btnPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.onButtonClick(scrum);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public interface OnButtonClickListener {
        void onButtonClick(ScrumModel scrum);
    }

    public class ScrumViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtName;
        ImageButton btnPresent;

        public ScrumViewHolder(View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtName=itemView.findViewById(R.id.txtName);
            btnPresent=itemView.findViewById(R.id.btnPresent);
        }
    }
}

*/

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.ScrumModel;
import java.util.List;

public class ScrumAdapter extends RecyclerView.Adapter<ScrumAdapter.ViewHolder> {
    private List<ScrumModel> scrumModelList;
    private OnButtonClickListener buttonClickListener;
    private List<ScrumModel> hiddenItems;


    public ScrumAdapter(List<ScrumModel> scrumModelList, OnButtonClickListener buttonClickListener) {
        this.scrumModelList = scrumModelList;
        this.buttonClickListener = buttonClickListener;
        this.hiddenItems = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scrum_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScrumModel scrum = scrumModelList.get(position);
        holder.txtName.setText(scrum.getName());
        holder.txtID.setText(scrum.getUserId());

        holder.btnPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonClickListener != null) {
                    buttonClickListener.onButtonClick(scrum, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return scrumModelList.size();
    }

    // Method to hide an item
    public void hideItem(int position) {
        if (position >= 0 && position < scrumModelList.size()) {
            ScrumModel scrum = scrumModelList.get(position);
            hiddenItems.add(scrum);
            notifyItemChanged(position);
        }
    }

    public interface OnButtonClickListener {
        void onButtonClick(ScrumModel scrum, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtID;
        public TextView txtName;
        public ImageButton btnPresent;

        public ViewHolder(View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtName = itemView.findViewById(R.id.txtName);
            btnPresent = itemView.findViewById(R.id.btnPresent);
        }
    }
}

