package com.pascoapp.wba02_android.takeTest.TestOverview;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pascoapp.wba02_android.BR;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import com.pascoapp.wba02_android.BindingHolder;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.firebasePojos.Programme;

import java.util.List;

/**
 * Created by peter on 7/24/16.
 */

public class ProgrammesAdapter extends RecyclerView.Adapter<BindingHolder> {

    private List<Programme> mProgrammes;

    public ProgrammesAdapter(List<Programme> mProgrammes) {
        this.mProgrammes = mProgrammes;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.programme_item, parent, false);
        BindingHolder holder = new BindingHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final Programme programme = mProgrammes.get(position);
        holder.getBinding().setVariable(BR.programmeIcon, getProgrammeIcon(programme));
        holder.getBinding().setVariable(BR.programmeName, programme.getName());
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProgrammes.size();
    }

    public Drawable getProgrammeIcon(Programme programme) {
        // generate color based on a key (same key returns the same color), useful for list/grid views
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(programme.getName());

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(
                        programme.getName().substring(0,1).toUpperCase(),
                        color
                );
        return drawable;
    }

}
