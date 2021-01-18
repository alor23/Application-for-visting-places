package com.example.ApplicationForVistingplaces;

import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.google.ar.sceneform.ux.ArFragment;

public class GameAr1 extends ArFragment {

    @Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup container, @Nullable android.os.Bundle savedInstanceState) {
        FrameLayout frameLayout =
                (FrameLayout) super.onCreateView(inflater, container, savedInstanceState);

        getPlaneDiscoveryController().hide();
        getPlaneDiscoveryController().setInstructionView(null);

        return frameLayout;
    }
}