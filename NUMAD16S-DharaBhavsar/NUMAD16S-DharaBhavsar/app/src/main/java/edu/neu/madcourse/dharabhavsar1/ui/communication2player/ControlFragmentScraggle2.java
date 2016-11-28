package edu.neu.madcourse.dharabhavsar1.ui.communication2player;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.madcourse.dharabhavsar1.ui.main.R;

public class ControlFragmentScraggle2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_control_scraggle2, container, false);
        final View pause = rootView.findViewById(R.id.button_pause);
        View quit = rootView.findViewById(R.id.button_quit);
        final View resume = rootView.findViewById(R.id.button_resume);
        /*View restart = rootView.findViewById(R.id.button_restart);*/
        final View muteToggle = rootView.findViewById(R.id.button_mute);
        final View unmuteToggle = rootView.findViewById(R.id.button_unmute);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScraggleGameActivity2) getActivity()).onQuitGame();
                getActivity().finish();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resume.setVisibility(View.VISIBLE);
                pause.setVisibility(View.INVISIBLE);
                muteToggle.setVisibility(View.INVISIBLE);
                unmuteToggle.setVisibility(View.INVISIBLE);
                ((ScraggleGameActivity2) getActivity()).onPauseGame();

            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.setVisibility(View.VISIBLE);
                resume.setVisibility(View.INVISIBLE);
                muteToggle.setVisibility(View.VISIBLE);
                unmuteToggle.setVisibility(View.INVISIBLE);
                ((ScraggleGameActivity2) getActivity()).onResumeGame();
            }
        });
        muteToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScraggleGameActivity2) getActivity()).toogleMute();
                muteToggle.setVisibility(View.INVISIBLE);
                unmuteToggle.setVisibility(View.VISIBLE);
            }
        });
        unmuteToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScraggleGameActivity2) getActivity()).toogleMute();
                unmuteToggle.setVisibility(View.INVISIBLE);
                muteToggle.setVisibility(View.VISIBLE);
            }
        });
        /*restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GameActivity) getActivity()).restartGame();
            }
        });*/
        return rootView;
    }

}
