package io.github.ferretFeet72.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import io.github.ferretFeet72.screens.ScreenEnum;
import io.github.ferretFeet72.screens.ScreenManager;
import io.github.ferretFeet72.settings.ISettings;
import io.github.ferretFeet72.utils.GameResources;

import java.util.Arrays;

public class SettingsUI {
    private final Table table;
    private ISettings.IActiveSettings activeSettings;

    public SettingsUI(Skin skin) {
        this.table = new Table();

        this.table.setSize(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.7f);
        this.table.setPosition(Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.15f);

        this.table.setBackground(skin.getDrawable("black"));
        table.setVisible(false);


// ####################################################
//        Get Settings Options and Actives
// ####################################################





        Json json = new Json();
        FileHandle file = Gdx.files.local("available-settings.json");
        JsonValue availSettings = json.fromJson(null, file);

        file = Gdx.files.local("game-settings.json");
        this.activeSettings = json.fromJson(ISettings.IActiveSettings.class, file);


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
        Label volumeLabel = new Label("Volume", skin, "white");
        Slider volumeSlider = new Slider(masterVolumeRange[0], masterVolumeRange[1], 0.01f, false, skin);
        volumeSlider.setValue(activeSettings.getAudio().masterVolume);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                saveVolume(volumeSlider.getValue());
            }
        });
        table.add(volumeLabel).pad(10);
        table.add(volumeSlider).pad(10);
        table.row();


//        Window Size
        Label resolutionLabel = new Label("Resolution", skin, "white");
        String activeResolutionString = activeSettings.getVideo().getResolution().width + "x" + activeSettings.getVideo().getResolution().height;
        SelectBox<String> resolutionBox = getStringSelectBox(skin, availResolutions, activeResolutionString);
        resolutionBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String selected = resolutionBox.getSelected();
                String[] parts = selected.split("x");
                int width = Integer.parseInt(parts[0]);
                int height = Integer.parseInt(parts[1]);

                Gdx.app.postRunnable(() -> {
                    GameResources.viewport.update(width, height, true);
//                    Gdx.graphics.setWindowedMode(width, height);

                    saveResolution(width, height);
                });

            }
        });
        table.add(resolutionLabel).pad(10);
        table.add(resolutionBox).pad(10);
        table.row();

//        Fullscreen toggle


//        Control Mapping



//        Close Settings
        TextButton closeButton = new TextButton("Close Settings", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.setVisible(false);
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
    private FileHandle getActiveSettingsFile() {
        return Gdx.files.local("game-settings.json");
    }

    private void saveVolume(float volume) {
        activeSettings.getAudio().masterVolume = volume;
        activeSettings.save();
    }

    private void saveResolution(int width, int height) {
        activeSettings.getVideo().getResolution().width = width;
        activeSettings.getVideo().getResolution().height = height;
        activeSettings.save();
    }

    private void saveFullScreen(boolean fullScreen) {
        activeSettings.getVideo().fullscreenEnabled = fullScreen;
        activeSettings.save();
    }


//    Load Data
    public void loadGameSettings() {
        FileHandle file = getActiveSettingsFile();

        JsonValue activeSettings = new JsonReader().parse(file);
        int width = activeSettings.get("video").get("resolution").get("width").asInt();
        int height = activeSettings.get("video").get("resolution").get("height").asInt();
        boolean fullscreenEnabled = activeSettings.get("video").get("fullscreenEnabled").asBoolean();

//        Set Resolution
        if (!fullscreenEnabled) {
            Gdx.graphics.setWindowedMode(width, height);
        } else {
//            Get Monitor and compatible display modes
            Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
            Graphics.DisplayMode[] displayMode = Gdx.graphics.getDisplayModes(currMonitor);
//            Find a display mode with a matching resolution
            Graphics.DisplayMode chosenMode = null;
            for (Graphics.DisplayMode mode : displayMode) {
                if (mode.width == width && mode.height == height) {
                    chosenMode = mode;
                    break;
                }
            }
            if (chosenMode != null) {
                Gdx.graphics.setFullscreenMode(chosenMode);
            } else {
//                Fallback
//                Should be beefed up
//                So that if full screen already
//                doesnt revert to windowed but
//                instead reverts to last good res
                Gdx.graphics.setWindowedMode(width, height);
            }
        }

        float  masterVolume = activeSettings.get("audio").get("masterVolume").asFloat();
    }


//    Public Functions
    public Table getRootActor() {
        return table;
    }

    public void setVisibility(boolean isVisible) {
        table.setVisible(isVisible);
    }

}
