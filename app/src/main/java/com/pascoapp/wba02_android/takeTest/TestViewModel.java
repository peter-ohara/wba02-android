package com.pascoapp.wba02_android.takeTest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pascoapp.wba02_android.firebasePojos.Programme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/23/16.
 */

public class TestViewModel {

    private Context context;

    public String courseCode = "ENGL 419";
    public String courseName = "Intro to Nouns";
    public String testName = "May 2015 Final";
    public String testDuration = "4h 50m";
    public String lecturerName = "Ms Zenia Osei";

    public TestViewModel() {
    }

    public View.OnClickListener onClickStart() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Starting Test...", Toast.LENGTH_LONG).show();
            }
        };
    }

    public Drawable getLecturerIcon() {
        // generate color based on a key (same key returns the same color), useful for list/grid views
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(lecturerName);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(
                        lecturerName.substring(0,1).toUpperCase(),
                        color
                );
        return drawable;
    }

    public List<Programme> getProgrammes() {
        List<Programme> programmes = new ArrayList<>();
        programmes.add(new Programme("Computer Science [B.Sc.]", "knust"));
        programmes.add(new Programme("Computer Engineering [B.Sc.]", "knust"));
        programmes.add(new Programme("Information Technology [B.Sc.]", "knust"));
        return programmes;
    }

    public List<String> getInstructions() {
        List<String> instructions = new ArrayList<>();
        instructions.add("Circle answers on both question and answer sheet");
        instructions.add("Leave 2 lines between answers to main question and one " +
                "line between sub questions answers on both question and answer sheet");
        instructions.add("Circle answers on both question and answer sheet");
        instructions.add("Leave 2 lines between answers to main question and one " +
                "line between sub questions answers on both question and answer sheet");
        instructions.add("Circle answers on both question and answer sheet");
        instructions.add("Leave 2 lines between answers to main question and one " +
                "line between sub questions answers on both question and answer sheet");
        instructions.add("Circle answers on both question and answer sheet");
        instructions.add("Leave 2 lines between answers to main question and one " +
                "line between sub questions answers on both question and answer sheet");
        return instructions;
    }
}
