package io.github.ferretFeet72.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.ferretFeet72.screens.ScreenEnum;
import io.github.ferretFeet72.screens.ScreenManager;

public class SettingsUI {
    private final Table table;

    public SettingsUI(Skin skin) {
        this.table = new Table();
        table.setFillParent(true);
        this.table.setBackground(skin.getDrawable("black"));
        table.setVisible(false);

//        Volume Slider
        Label volumeLabel = new Label("Volume", skin);
        Slider volumeSlider = new Slider(0, 100, 0.1f, false, skin);


//        Window Size


//        Control Mapping



//        Close Settings
        TextButton settingsButton = new TextButton("Close Settings", skin);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.setVisible(false);
            }
        });

//        Add Buttons To Table
        table.add(volumeLabel).padRight(10).left();
        table.add(volumeSlider).width(200).padBottom(20).center();
        table.row();
        table.add(settingsButton).colspan(2).padTop(20);

    }

    public Table getRootActor() {
        return table;
    }

    public void setVisibility(boolean isVisible) {
        table.setVisible(isVisible);
    }

}
