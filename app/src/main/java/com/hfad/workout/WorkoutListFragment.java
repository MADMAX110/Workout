package com.hfad.workout;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WorkoutListFragment extends ListFragment {

    static interface Listener{
        void itemClicked(long id);
    }

    private Listener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String[] names = new String[Workout.workouts.length];
        for (int i = 0; i < names.length; i++){
            //创建一个包含训练xian
            names[i] = Workout.workouts[i].getName();
        }
        //创建一个数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, names);
        //将数组适配器绑定到列表视图
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onAttach(Context context){
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    public void onListItemClick(ListView listview, View itemView, int position, long id){
        //调用活动中的itemClicked()方法，传入用户选择的训练项目的ID
        if (listener != null)listener.itemClicked(id);
    }
}