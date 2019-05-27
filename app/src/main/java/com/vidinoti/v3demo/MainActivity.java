package com.vidinoti.v3demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vidinoti.library3d.Callback;
import com.vidinoti.library3d.RequestManager;
import com.vidinoti.library3d.SimpleArActivity;
import com.vidinoti.library3d.Vidinoti3D;
import com.vidinoti.library3d.model.Asset3D;
import com.vidinoti.library3d.model.PageResult;

import java.util.List;

/**
 * Sample activity that shows how to retrieve the list of 3D models from V-Director.
 * It also shows 2 ways of starting the AR placement (1. start the AR view with the possibility
 * to add several models, 2. start the AR view with a specific 3D model).
 */
public class MainActivity extends AppCompatActivity {

    private Button mStartAllButton;
    private LinearLayout mLinearLayout;
    private List<Asset3D> mModels;
    private boolean mAndroidVersionSupported;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartAllButton = findViewById(R.id.start_all_button);
        mLinearLayout = findViewById(R.id.model_button_linear_layout);

        mStartAllButton.setOnClickListener(view -> SimpleArActivity.startActivity(MainActivity.this, null, mModels));

        mAndroidVersionSupported = Vidinoti3D.isAndroidVersionSupported();

        if (!mAndroidVersionSupported) {
            Toast.makeText(this, "Your Android version is not supported", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshModels();
    }

    private void refreshModels() {
        // Retrieve the list of models from the server
        RequestManager.list(new Callback<PageResult>() {
            @Override
            public void onSuccess(PageResult pageResult) {
                List<Asset3D> list = pageResult.getContent();
                setModels(list);
                if (list == null || list.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No model available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                setModels(null);
            }
        });
    }

    private void setModels(List<Asset3D> models) {
        mModels = models;
        if (models != null && !models.isEmpty()) {
            mStartAllButton.setEnabled(mAndroidVersionSupported);
            mLinearLayout.removeAllViews();
            for (Asset3D asset3D : models) {
                Button button = new Button(this);
                button.setText(asset3D.getName());
                button.setEnabled(mAndroidVersionSupported);
                button.setOnClickListener(view -> SimpleArActivity.startActivity(MainActivity.this, asset3D, null));
                mLinearLayout.addView(button);
            }
        } else {
            mStartAllButton.setEnabled(false);
            mLinearLayout.removeAllViews();
            mModels = null;
        }
    }
}
