package com.devapp.aesencryptionjava.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.devapp.aesencryptionjava.R;
import com.devapp.aesencryptionjava.model.Result;
import com.google.android.material.snackbar.Snackbar;

public class FragmentSuccess extends Fragment {
    public FragmentSuccess() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_success, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            Result data = (Result) getArguments().getSerializable("data");
            TextView tvResult = view.findViewById(R.id.tvResult);
            TextView tvTime = view.findViewById(R.id.tvTime);
            TextView btnCopy = view.findViewById(R.id.btnCopy);
            tvResult.setText(data.getData().trim());
            tvTime.setText(String.format("Time: %s", data.getTime()));
            btnCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipBoardManger = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("copy_text", data.getData());
                    clipBoardManger.setPrimaryClip(clipData);
                    showSnackbar("Copied");
                }
            });
        }

        super.onViewCreated(view, savedInstanceState);
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG);
        snackbar.setAction("OKAY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        snackbar.show();
    }
}
