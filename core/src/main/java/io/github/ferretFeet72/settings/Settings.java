package io.github.ferretFeet72.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import io.github.ferretFeet72.utils.GameResources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Settings {

    public static class Active {
        private static final String SETTINGS_FILE = "game-settings.json";

        public Video video;
        public Audio audio;
        public Controls controls;

        public Active() {

        }

        public static class Video {
            public Resolution resolution;
            public boolean fullscreenEnabled;

            public Resolution getResolution() {
                return resolution;
            }

            public void setResolution(Resolution resolution) {
                this.resolution = resolution;
            }


        }
        public static class Resolution {
            public int width;
            public int height;
        }
        public static class Audio {
            public float masterVolume;
        }

        public static class Controls {}



//        public static IActiveSettings load() {
//            /** Load settings from JSON, or return defaults if file missing */
//                FileHandle file = Gdx.files.local(SETTINGS_FILE);
//                Json json = new Json();
//
//                if (!file.exists()) {
//                    IActiveSettings defaults = new IActiveSettings();
//                    defaults.save();  // create the file
//                    return defaults;
//                }
//                return json.fromJson(IActiveSettings.class, file);
//
//        }

//        public void save() {
//            System.out.println("SAVING SETTINGS");
//            FileHandle file = Gdx.files.local(ISettings.IActiveSettings.SETTINGS_FILE);
//            Json json = new Json();
//            JsonValue root;
//
//            if  (!file.exists()) {
//
//                root = new JsonValue(JsonValue.ValueType.object);
//                root.addChild("settings", new JsonValue(JsonValue.ValueType.object));
//            } else {
//                root = json.fromJson(null, file);
//            }
//            System.out.println(root);
//            file.writeString(json.prettyPrint(root), false);
////            Gdx.files.local(SETTINGS_FILE).writeString(json.prettyPrint(root), false);
//        }

//        =======================
//        -- Getters Setters --
//        =======================
        public Controls getControls() {
            return controls;
        }

        public void setControls(Controls controls) {
            this.controls = controls;
        }

        public Audio getAudio() {
            return audio;
        }

        public void setAudio(Audio audio) {
            this.audio = audio;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }
}

//        =======================
//        --- Read/Write to File --
//        =======================

    public static void saveSettings(Settings.Active settings) {
        FileHandle file = Gdx.files.local(GameResources.usrSettingsLoc);
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);  // Force standard JSON with quotes
        String jsonSettings = json.prettyPrint(settings);
        file.writeString(jsonSettings, false);

    }

    public static Settings.Active getSettings() {
        String settings = "";
        try {
            settings = Files.readString(Path.of(GameResources.usrSettingsLoc));
            System.out.println("Content " + settings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!settings.isEmpty()) {
//            Fallback
        }
        Json json = new Json();
        return json.fromJson(Settings.Active.class, settings);
    }
//
//    public static Settings.Active applySettings(Settings.Active settings) {
//
//    }

    public static class IAvailSettings {

    }
}

