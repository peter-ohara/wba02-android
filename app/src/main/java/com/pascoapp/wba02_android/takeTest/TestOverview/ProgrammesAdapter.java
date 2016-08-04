package com.pascoapp.wba02_android.takeTest.TestOverview;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.main.TestViewHolder;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.dataFetching.Programme;

import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 7/24/16.
 */

public class ProgrammesAdapter extends RecyclerView.Adapter<TestViewHolder> {

    private List<Programme> mProgrammes;
    private Store<Action, State> store;

    public ProgrammesAdapter(List<Programme> mProgrammes, Store<Action, State> store) {
        this.mProgrammes = mProgrammes;
        this.store = store;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.programme_item, parent, false);
        TestViewHolder holder = new TestViewHolder(v, store);
        return holder;
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        final Programme programme = mProgrammes.get(position);
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
