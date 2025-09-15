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
//    Get Settings File locations for use
    private final static String USER_DEFINED_SETTINGS = GameResources.usrSettingsLoc;
    private final static String DEFAULT_SETTINGS = GameResources.defSettingsLoc;

//    Class for deserializing active settings
//    Currently one is not needed for available settings
    public static class Active {
//        ==================
//    Define Translation variables
//    ==========================
        private Video video;
        private Audio audio;
        private Controls controls;

        public Active() {}

        public static class Video {
            private Resolution resolution;
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
    } //END Active

//        =======================
//        --- Read/Write to File --
//        =======================

    public static void saveSettings(Settings.Active settings) {
//        get file
        FileHandle file = Gdx.files.local(USER_DEFINED_SETTINGS);

//        Translate settings object to JSON
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);  // Force standard JSON with quotes
        String jsonSettings = json.prettyPrint(settings);

//        save to file, overwrite exisiting data
        file.writeString(jsonSettings, false);
    }

    public static Settings.Active getSettings() {
        String settings = "";
        Path userSettingsPath = Path.of(USER_DEFINED_SETTINGS);
        Path defSettingsPath = Path.of(DEFAULT_SETTINGS);
//        Get user defined settings, if not exists get defaults
        try {
            if (Files.exists(userSettingsPath)) {
//                User defined settings
                settings = Files.readString(userSettingsPath);
            } else {
//                Default Settings
                settings = Files.readString(defSettingsPath);
            }
        } catch (IOException e) {
//            File not found
            throw new RuntimeException(e);
        }
//        Translate from json to object
        Json json = new Json();
        return json.fromJson(Settings.Active.class, settings);
    }

}

