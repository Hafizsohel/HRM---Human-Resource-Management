package com.suffixit.hrm_suffix.view.Fragment;

        import android.os.Bundle;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.suffixit.hrm_suffix.Adapter.MyAdapter;
        import com.suffixit.hrm_suffix.R;

        import java.util.ArrayList;
        import java.util.List;

public class EmployeeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_employee, container, false);



        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        // Create a list of data you want to display (e.g., List<String>)
        List<String> dataList = new ArrayList<>();
        dataList.add("Item 1");

        // Initialize the adapter and set it to the RecyclerView
        MyAdapter adapter = new MyAdapter(requireContext(), dataList);
        recyclerView.setAdapter(adapter);

        return view;

    }
}