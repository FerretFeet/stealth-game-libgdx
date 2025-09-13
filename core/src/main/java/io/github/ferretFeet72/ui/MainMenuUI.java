package io.github.ferretFeet72.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.ferretFeet72.screens.ScreenEnum;
import io.github.ferretFeet72.screens.ScreenManager;



public class MainMenuUI {
    public interface MainMenuListener {
        void onStartGameClicked();
        void onSettingsClicked();
    }

    private MainMenuListener listener;
    private final Table table;

    public MainMenuUI(Skin skin) {
        this.table = new Table();
        table.setFillParent(true);

//        Start Game Button
        TextButton startButton = new TextButton("Start Game", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                assert listener != null;
                listener.onStartGameClicked();
            }
        });

//        Open Settings
        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                assert listener != null;
                listener.onSettingsClicked();

                ;
            }
        });

//        Add Buttons To Table
        table.add(startButton);
        table.row();
        table.add(settingsButton);

    }

    public Table getRootActor() {
        return table;
    }

    // Method to set the listener
    public void setListener(MainMenuListener listener) {
        this.listener = listener;
    }

}
