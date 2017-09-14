package com.example.projecttopaz;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecttopaz.events.AllPurposeEvent;

import org.w3c.dom.Text;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by xuan- on 20/08/2017.
 */

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @BindArray(R.array.temperatureUnits) String[] temperatureUnits;
    @BindArray(R.array.temperatureUnitsValues) String[] temperatureUnitsValues;
    @BindArray(R.array.pressureUnits) String[] pressureUnits;
    @BindArray(R.array.pressureUnitsValues) String[] pressureUnitsValues;
    @BindArray(R.array.speedUnits) String[] speedUnits;
    @BindArray(R.array.speedUnitsValues) String[] speedUnitsValues;
    @BindString(R.string.settings_about) String settingsAbout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
        ButterKnife.bind(this);


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("apiKey")){
            // TODO 1: Test if apikey is valid
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(new AllPurposeEvent("preferencesActivityEvent"));
    }

    @SuppressLint("ValidFragment")
    private class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs_main);

            Preference aboutButton = getPreferenceManager().findPreference(settingsAbout);
            if (aboutButton != null) {
                aboutButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        //Toast.makeText(SettingsActivity.this, "ABOUT", Toast.LENGTH_SHORT).show();
                        showAbout();
                        return true;
                    }
                });
            }
        }
    }


    private void showAbout(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.app_name);
        final WebView webView = new WebView(this);
        String about =
                "<p>An opensource weather app.</p>" +
                "<p>Developed by <a href='mailto:xnhoang@gmail.com'>Xuan-Nghia Hoang</a></p>" +
                "<p>Logo icon is made by <a href='https://smashicons.com/'>Smashicons</a> found on <a href='http://www.flaticon.com/'>Flaticons</a>. </p>" +
                "<p>Weather icons are <a href='https://erikflowers.github.io/weather-icons/'>Weather Icons</a>, by <a href='http://www.twitter.com/artill'>Lukas Bischoff</a> and <a href='http://www.twitter.com/Erik_UX'>Erik Flowers</a>, under the <a href='http://scripts.sil.org/OFL'>SIL OFL 1.1</a> licence."+
                "<p>Data provided by <a href='http://openweathermap.org/'>OpenWeatherMap</a>, under the <a href='http://creativecommons.org/licenses/by-sa/2.0/'>Creative Commons license</a>" ;
        webView.loadData(about, "text/html", "UTF-8");
        alert.setView(webView);
        alert.create();
        alert.show();
    }
}
