package ca.germuth.rubiks.activity;

import ca.germuth.rubiks.R;
import ca.germuth.rubiks.R.xml;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

public class SettingsActivity extends PreferenceActivity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        //PreferenceManager.setDefaultValues(this, R.layout.preferences, false);
    }
}
