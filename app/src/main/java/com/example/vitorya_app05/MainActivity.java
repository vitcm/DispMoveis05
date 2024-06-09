package com.example.vitorya_app05;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;
    private MediaPlayer mediaPlayer;

    private static final String[] PLANETS = {
            "mercurio", "venus", "terra", "marte",
            "jupiter", "saturno", "urano", "netuno"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector(() -> {
            Random random = new Random();
            String selectedPlanet = PLANETS[random.nextInt(PLANETS.length)];
            loadFragment(selectedPlanet);
            playSound(selectedPlanet);
        });

        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);

        loadFragment("terra");
        playSound("terra");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    private void loadFragment(String planetName) {
        PlanetaFragment fragment = PlanetaFragment.newInstance(planetName);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void playSound(String planetName) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        int soundResId = getResources().getIdentifier(planetName.toLowerCase(), "raw", getPackageName());
        if (soundResId != 0) {
            mediaPlayer = MediaPlayer.create(this, soundResId);
            mediaPlayer.start();
        }
    }
}
