package pe.jota.joketeller;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JokeFragment extends Fragment {

    public JokeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_joke, container, false);
        Intent intent = getActivity().getIntent();
        String jokeString = intent.getStringExtra(JokeActivity.PARAM_JOKE);
        TextView jokeText = (TextView)root.findViewById(R.id.joke_textview);

        if (jokeString != null && !jokeString.equals("")) {
            jokeText.setText(jokeString);
        }
        return root;
    }
}
