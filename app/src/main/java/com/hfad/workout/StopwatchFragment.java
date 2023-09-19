package com.hfad.workout;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class StopwatchFragment extends Fragment implements View.OnClickListener {

    private int seconds = 0;//记录已经过去的秒数
    private boolean running;//秒表是否正常运行
    //记录onStop之前秒表是否在运行，这样就知道是否需要恢复运行
    private boolean wasRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            //保存wasRunning变量的状态
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
    }

    @Nullable
    @Override
    //片段改为在onCreateView()方法中设置片段的布局
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        runTimer(layout);

        Button startButton = (Button)layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        Button stopButton = (Button)layout.findViewById(R.id.stop_button);
        startButton.setOnClickListener(this);
        Button resetButton = (Button)layout.findViewById(R.id.reset_button);
        startButton.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_button)onClickStart();
        else if (view.getId() == R.id.stop_button)onClickStop();
        else if (view.getId() == R.id.reset_button)onClickReset();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //启动秒表
    public void onClickStart() {
        running = true;
    }

    //停止秒表
    public void onClickStop() {
        running = false;
    }

    //单击reset按钮时会调用这个方法
    public void onClickReset() {
        running = false;
        seconds = 0;
    }

    private void runTimer(View view) {
        //得到文本视图(片段不能为直接使用findViewById，使用view参数调用findViewById)
        final TextView timeView = (TextView) view.findViewById(R.id.time_view);
        //创建一个新地Handler
        final Handler handler = new Handler();
        //调用post()方法，传入一个新的Runnable。post()方法会立即运行代码
        handler.post(new Runnable() {
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds % 60;
                //设置显示格式
                String time = String.format(Locale.getDefault(), "%d:%02d%02d", hours, minutes, secs);
                //设置文本视图
                timeView.setText(time);
                if (running) {
                    ++seconds;
                }

                //在1000ms后再次提交并运行Runnable中的代码，会反复调用
                handler.postDelayed(this, 1000);
            }
        });
    }
}