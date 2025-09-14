package io.github.ferretFeet72.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;





public class ISettings {

    public static class IActiveSettings {
        private static final String SETTINGS_FILE = "game-settings.json";
        public Video video;

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

        public Audio audio;
        public Controls controls;

        public static class Video {
            public Resolution getResolution() {
                return resolution;
            }

            public void setResolution(Resolution resolution) {
                this.resolution = resolution;
            }

            public Resolution resolution;
            public boolean fullscreenEnabled;
        }
        public static class Resolution {
            public int width;
            public int height;
        }
        public static class Audio {
            public float masterVolume;
        }

        public static class Controls {}

        public static IActiveSettings load() {
            /** Load settings from JSON, or return defaults if file missing */
                FileHandle file = Gdx.files.local(SETTINGS_FILE);
                Json json = new Json();

                if (!file.exists()) {
                    IActiveSettings defaults = new IActiveSettings();
                    defaults.save();  // create the file
                    return defaults;
                }
                return json.fromJson(IActiveSettings.class, file);

    }
        public void save() {
            FileHandle file = Gdx.files.local(ISettings.IActiveSettings.SETTINGS_FILE);
            Json json = new Json();
            file.writeString(json.prettyPrint(SETTINGS_FILE), false);
        }
}

    interface IAvailableSettings {

    }
}

