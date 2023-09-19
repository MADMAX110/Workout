package com.hfad.workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements WorkoutListFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void itemClicked(long id) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null){
            WorkoutDetailFragment details = new WorkoutDetailFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            details.setWorkoutId(id);
            //每次用户单击一个训练项目时，要把这个片段替换为它的一个新实例
            //这是WorkoutDetailFragment的一个新实例，它会显示用户选择的那个训练项目的详细信息。
            ft.replace(R.id.fragment_container, details);
            //设置片段淡入淡出
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //将这个事务增加到后退堆栈
            ft.addToBackStack(null);
            ft.commit();
        }else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, (int)id);
            startActivity(intent);
        }
    }
}