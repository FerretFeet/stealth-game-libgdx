package io.github.ferretFeet72.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;



public class MainMenuUI {
//    Parent will implement these methods
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

//        Add button to table
        table.add(startButton);
        table.row();

//        Open Settings Button
        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                assert listener != null;
                listener.onSettingsClicked();

                ;
            }
        });

//        Add Button To Table
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
