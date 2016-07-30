package com.pascoapp.wba02_android.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.CheckCurrentUser;
import com.pascoapp.wba02_android.CourseViewModel;
import com.pascoapp.wba02_android.CoursesAdapter;
import com.pascoapp.wba02_android.HelpActivity;
import com.pascoapp.wba02_android.Inbox.MessageListActivity;
import com.pascoapp.wba02_android.MainListItem;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.firebasePojos.Course;
import com.pascoapp.wba02_android.firebasePojos.Test;
import com.pascoapp.wba02_android.settings.SettingsActivity;
import com.pascoapp.wba02_android.takeTest.TestViewModel;

import java.util.ArrayList;
import java.util.List;

import trikita.anvil.Anvil;
import trikita.anvil.RenderableRecyclerViewAdapter;
import trikita.anvil.RenderableView;

import static trikita.anvil.BaseDSL.init;
import static trikita.anvil.BaseDSL.text;
import static trikita.anvil.BaseDSL.withId;
import static trikita.anvil.BaseDSL.xml;
import static trikita.anvil.DSL.onClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar loadingIndicator;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CoursesAdapter mAdapter;
    private View coordinatorLayoutView;
    private View emptyView;

    private DatabaseReference mCoursesRef;
    private int mCount = 0;
    private Toolbar toolbar;
    private List<MainListItem> screenItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //View view = new MainScreen(MainActivity.this);
        setContentView(new RenderableView(this) {
            @Override
            public void view() {
                xml(R.layout.activity_main, () -> {
                    withId(R.id.toolbar, () -> {
                        init(() -> {
                            toolbar = (Toolbar) Anvil.currentView();
                            setSupportActionBar(toolbar);
                            getSupportActionBar().setIcon(R.mipmap.ic_action_logo);
                        });
                    });

                    withId(R.id.coursesList, () -> {
                        init(() -> {
                            mRecyclerView = (RecyclerView) Anvil.currentView();
                            mRecyclerView.setHasFixedSize(true);
                            // Use a linear layout manager
                            mLayoutManager = new LinearLayoutManager(MainActivity.this);
                            mRecyclerView.setLayoutManager(mLayoutManager);

                             mAdapter = new CoursesAdapter(MainActivity.this, screenItems);
                            // mAdapter = new MyAdapter(screenItems);
                            // Set the adapter object to the RecyclerView
                            mRecyclerView.setAdapter(mAdapter);

                            fetchBoughtCourses();

                        });
                    });

                    withId(R.id.bottomBar, () -> {
                        text(String.valueOf(mCount));
                        onClick(v -> mCount++);
                    });
                });
            }
        });




//        coordinatorLayoutView = findViewById(R.id.snackbarPosition);
//        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
//        emptyView = (View) findViewById(R.id.empty_view);
//
//

//
//        Observer<List<Course>> printObserver = new Observer<List<Course>>() {
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError: " + e.getMessage());
//                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNext(List<Course> s) {
//                Log.d(TAG, "onNext: " + s);
//            }
//        };
//
//
//        Subscription courseSubscription = Course.fetchCourses().subscribe(printObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_inbox:
                startActivity(new Intent(MainActivity.this, MessageListActivity.class));
                break;
            case R.id.action_logout:
                logout();
                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.action_feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","pascoapp.wb@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback on Pasco Android App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Write your feedback here");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;
            case R.id.action_help:
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchBoughtCourses() {
        // TODO: Filter query by courses that student has bought for current duration
        mCoursesRef = FirebaseDatabase.getInstance().getReference().child("courses");
        Query coursesQuery = mCoursesRef.limitToFirst(9);
        coursesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                screenItems.clear();
                int i = 0;
                for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);
                    CourseViewModel courseViewModel = new CourseViewModel(course);
                    MainListItem mainListItem = new MainListItem(courseViewModel);
                    screenItems.add(i, mainListItem);
                    i++;
                }
                mAdapter.notifyDataSetChanged();

                List<MainListItem> courseList = screenItems;
                for (MainListItem item :
                        courseList) {
                    addTestsToCourse(item);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(coordinatorLayoutView,
                        databaseError.getCode() + " : " + databaseError.getMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void addTestsToCourse(final MainListItem courseListItem) {
        // TODO: Filter query by course current course
        DatabaseReference testsRef = FirebaseDatabase.getInstance().getReference().child("tests");
        testsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot testSnapshot: dataSnapshot.getChildren()) {
                    Test test = testSnapshot.getValue(Test.class);

                    TestViewModel testViewModel =
                            new TestViewModel(MainActivity.this, testSnapshot.getKey());

                    MainListItem mainListItem = new MainListItem(testViewModel);

                    int position = screenItems.indexOf(courseListItem) + 1;
                    Log.d(TAG, "onDataChange: " + position);
                    if (position != 0) screenItems.add(position, mainListItem);
                    position++;
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(coordinatorLayoutView,
                        databaseError.getCode() + " : " + databaseError.getMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public class MyAdapter extends RenderableRecyclerViewAdapter {
        public static final int TYPE_TEST = 1;
        public static final int TYPE_COURSE = 2;
        private List<MainListItem> items;

        public MyAdapter(List<MainListItem> items) {
            this.items = items;
        }

        @Override
        public int getItemViewType(int position) {
            MainListItem item = items.get(position);
            if (item.getTestViewModel() != null) {
                return TYPE_TEST;
            } else {
                return TYPE_COURSE;
            }
        }

        @Override
        public MountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return super.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(MountHolder h, int position) {
            super.onBindViewHolder(h, position);
        }

        @Override
        public void view(RecyclerView.ViewHolder holder) {
            int position = holder.getAdapterPosition();
            switch (getItemViewType(position)) {
                case TYPE_TEST:
                    xml(R.layout.test_item, () -> {
                        withId(R.id.testName, () -> {
                            TestViewModel testViewModel = screenItems.get(position).getTestViewModel();
                            text(testViewModel.getTestName());
                        });
                    });
                    break;
                case TYPE_COURSE:
                    xml(R.layout.course_item);
                default:
                    Log.wtf(TAG, "onCreateViewHolder: unidentified type");
                    Log.wtf(TAG, holder.toString());
                    Log.wtf(TAG, String.valueOf(position));
                    Toast.makeText(MainActivity.this, "Something wicked this way comes", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }



    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(MainActivity.this, CheckCurrentUser.class));
                        finish();
                    }
                });
    }

}
