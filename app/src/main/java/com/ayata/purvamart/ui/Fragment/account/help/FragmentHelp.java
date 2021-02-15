package com.ayata.purvamart.ui.Fragment.account.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelHelp;
import com.ayata.purvamart.ui.Adapter.AdapterHelp;
import com.ayata.purvamart.utils.DividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentHelp extends Fragment implements AdapterHelp.HelpClickInterface {
    public static final String TAG = "FragmentHelp";
    RecyclerView recyclerView;
    AdapterHelp adapterHelp;
    List<ModelHelp> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        init(view);
        setValues();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapterHelp = new AdapterHelp(list, getContext());
        recyclerView.setAdapter(adapterHelp);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        dataPrepare();
        return view;
    }

    public void init(View view) {
        recyclerView = view.findViewById(R.id.recycle_container);
    }

    private void setValues() {
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Help and Support", false, false);
        list = new ArrayList<>();
        AdapterHelp.setListener(this);

    }

    private void dataPrepare() {
        list.add(new ModelHelp("My app isn’t working", "" +
                "CHECK YOUR INTERNET CONNECTION.\n" +
                "Here are some steps to help you make your app working again:\n" +
                "\n" +
                "Launch the recent applications menu.\n" +
                "Find Gaavaa in the list by scrolling up from the bottom.\n" +
                "Tap and hold on to Gaavaa and swipe it to the right.\n" +
                "Return to the app", ModelHelp.HelpType.HELP_CLOSE));

        list.add(new ModelHelp("Protecting your personal information", "Don't share your app password with anyone else" +
                "\nOne should not transact their money from public wifi but through their own mobile data", ModelHelp.HelpType.HELP_CLOSE));
        list.add(new ModelHelp("Communicating with customers", "...", ModelHelp.HelpType.HELP_CLOSE));

    }

    @Override
    public void helpListener(int position) {
        if (list.get(position).getHelpType() == ModelHelp.HelpType.HELP_OPEN) {
            list.get(position).setHelpType(ModelHelp.HelpType.HELP_CLOSE);
        } else {
            list.get(position).setHelpType(ModelHelp.HelpType.HELP_OPEN);
        }
        adapterHelp.notifyItemChanged(position);
    }
}
