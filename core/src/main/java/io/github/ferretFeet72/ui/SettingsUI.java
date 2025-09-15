package io.github.ferretFeet72.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import io.github.ferretFeet72.settings.Settings;
import io.github.ferretFeet72.utils.GameResources;

public class SettingsUI {

    private final Table table;
    private Settings.Active activeSettings;

    public SettingsUI(Skin skin) {
        this.activeSettings = Settings.getSettings();

        this.table = new Table();
//        this.table.pack();
        this.table.setSize(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f);

        this.table.setPosition((activeSettings.getVideo().getResolution().width - table.getWidth()) / 2,  (activeSettings.getVideo().getResolution().height - table.getHeight()) / 2);

//        this.table.setPosition(Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.15f);

        this.table.setBackground(skin.getDrawable("black"));
        table.setVisible(false);


// ####################################################
//        Get Settings Options and Actives
// ####################################################
//        active settings exists on parent
//        need to get values from available settings

//        get json tree for available settings
        Json json = new Json();
        FileHandle file = Gdx.files.internal("available-settings.json");
        JsonValue availSettings = json.fromJson(null, file);

//        Navigate to video settings
        JsonValue availVideoSettings = availSettings.get("video");
//        capture resolutions and fullscreen toggle
        String[] availResolutions = availVideoSettings.get("resolutions").asStringArray();

//        Navigate to audio
        JsonValue availAudioSettings = availSettings.get("audio");

        float[] masterVolumeRange = availAudioSettings.get("masterVolumeRange").asFloatArray();


// ####################################################
//        Create UI
// ####################################################

//        Volume Slider
//              create label and slider
        Label volumeLabel = new Label("Volume", skin, "white");
        Slider volumeSlider = new Slider(masterVolumeRange[0], masterVolumeRange[1], 0.01f, false, skin);
//        set starting value to last saved value
        volumeSlider.setValue(activeSettings.getAudio().masterVolume);
//        add listener to apply when changed
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                applyVolume(volumeSlider.getValue());
            }
        });
//        add to ui
        table.add(volumeLabel).pad(10);
        table.add(volumeSlider).pad(10);
        table.row();


//        Window Size
//        translate active settings resolution to available settings resolution format
        String activeResolutionString = activeSettings.getVideo().getResolution().width + "x" + activeSettings.getVideo().getResolution().height;
//        create label and selection box
        Label resolutionLabel = new Label("Resolution", skin, "white");
        SelectBox<String> resolutionBox = getStringSelectBox(skin, availResolutions, activeResolutionString);

//        add listener to apply when changed
        resolutionBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String selected = resolutionBox.getSelected();
                String[] parts = selected.split("x");
                int width = Integer.parseInt(parts[0]);
                int height = Integer.parseInt(parts[1]);

                    GameResources.viewport.update(width, height, true);
                    applyResolution(width, height);
            }
        });
//        add to ui
        table.add(resolutionLabel).pad(10);
        table.add(resolutionBox).pad(10);
        table.row();

//        Fullscreen toggle


//        Control Mapping



        // Apply Settings
        TextButton applyButton = new TextButton("Apply Settings", skin);
        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.saveSettings(activeSettings);
            }
        });
        table.add(applyButton).colspan(2).padTop(20);

//        Close Settings
        TextButton closeButton = new TextButton("Close Settings", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeTable();
            }
        });
        table.add(closeButton).colspan(2).padTop(20);

    }

    private SelectBox<String> getStringSelectBox(Skin skin, String[] options, String defaultValue) {
        SelectBox<String> resolutionBox = new SelectBox<>(skin);
        resolutionBox.setItems(options);
        resolutionBox.setSelected(options[0]);
        for (String option : options) {
            if (option.equals(defaultValue))
                resolutionBox.setSelected(defaultValue);
        }
        return resolutionBox;
    }

    //    Functions to save settings

    private void closeTable() {
//        refetch Settings, for if not changed
        Settings.Active savedSettings = Settings.getSettings();
        applyAll(savedSettings);
        table.setVisible(false);
        dispose();
    }

    private void applyAll(Settings.Active settings) {
        applyVolume(settings.getAudio().masterVolume);
        applyResolution(settings.getVideo().getResolution().width, settings.getVideo().getResolution().height);
        applyFullScreen(settings.getVideo().fullscreenEnabled);
    }

    private void applyVolume(float volume) {
        activeSettings.getAudio().masterVolume = volume;
    }

    private void applyResolution(int width, int height) {
        if (!activeSettings.getVideo().fullscreenEnabled) {
            System.out.println("Set Resolution");
            Gdx.graphics.setWindowedMode(width, height);
        }
        activeSettings.getVideo().getResolution().width = width;
        activeSettings.getVideo().getResolution().height = height;
        this.table.setSize((float) width / 2, (float) height / 2);
        this.table.setPosition((width - table.getWidth()) / 2, (height - table.getHeight()) / 2);
    }

    private void applyFullScreen(boolean fullScreen) {
        activeSettings.getVideo().fullscreenEnabled = fullScreen;
    }




//    Public Functions
    public Table getRootActor() {
        return table;
    }

    public void openSettings() {
        this.activeSettings = Settings.getSettings();
        setVisibility(true);
    }

    public void setVisibility(boolean isVisible) {
        table.setVisible(isVisible);
    }

    public void dispose() {
        this.table.clear();

    }

}
