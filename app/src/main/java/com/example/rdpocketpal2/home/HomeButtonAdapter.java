package com.example.rdpocketpal2.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.conversions.ConversionActivity;
import com.example.rdpocketpal2.predictiveequations.PredictiveEquationsActivity;
import com.example.rdpocketpal2.quickmethod.QuickMethodActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeButtonAdapter extends RecyclerView.Adapter<HomeButtonAdapter.HomeButtonViewHolder> {
    private HomeButton[] mButtons = {
            new HomeButton(R.drawable.ic_predictive_equations_purple_48dp, R.string.btn_predictive_equations
                    , PredictiveEquationsActivity.class),
            new HomeButton(R.drawable.ic_quick_method_purple_48dp, R.string.btn_quick_method
                    , QuickMethodActivity.class),
            new HomeButton(R.drawable.ic_anthropometrics_purple_48dp, R.string.btn_anthropometrics
                    , PredictiveEquationsActivity.class),
            new HomeButton(R.drawable.ic_conversions_purple_48dp, R.string.btn_conversions
                    , ConversionActivity.class)
    };

    private HomeButtonListener mListener;

    public HomeButtonAdapter(HomeButtonListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public HomeButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View homeButton = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_home_btn, parent, false);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new HomeButtonViewHolder(homeButton);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeButtonViewHolder holder, int position) {
        holder.mIcon.setImageResource(mButtons[position].getImageResourceId());
        holder.mText.setText(mButtons[position].getTextResourceId());
    }

    @Override
    public int getItemCount() {
        return mButtons.length;
    }

    public interface HomeButtonListener {
        void onClick(Class clazz);
    }

    public class HomeButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mIcon;
        private TextView mText;

        public HomeButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.btn_home_icon);
            mText = itemView.findViewById(R.id.btn_home_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(mButtons[this.getAdapterPosition()].getClazz());
        }
    }
}
