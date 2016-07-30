package com.pascoapp.wba02_android.main;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.pascoapp.wba02_android.CoursesAdapter;
import com.pascoapp.wba02_android.R;

import trikita.anvil.RenderableView;

import static trikita.anvil.BaseDSL.text;
import static trikita.anvil.BaseDSL.withId;
import static trikita.anvil.BaseDSL.xml;
import static trikita.anvil.DSL.onClick;

/**
 * Created by peter on 7/29/16.
 */

public class MainScreen extends RenderableView {

    private int mCount = 0;
    private Context context;

    public MainScreen(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void view() {
        xml(R.layout.activity_main, () -> {
            withId(R.id.bottomBar, () -> {
                text(String.valueOf(mCount));
                onClick(v -> mCount++);
            });
        });
    }
}
