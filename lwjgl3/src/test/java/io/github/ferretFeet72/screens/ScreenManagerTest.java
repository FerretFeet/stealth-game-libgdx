//package io.github.ferretFeet72.screens;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Screen;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class ScreenManagerTest {
//
//    private Game mockGame;
//    private ScreenManager screenManager;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        // Reset singleton using reflection since instance is private
//        var instanceField = ScreenManager.class.getDeclaredField("instance");
//        instanceField.setAccessible(true);
//        instanceField.set(null, null);
//
//        screenManager = ScreenManager.getInstance();
//
//        mockGame = mock(Game.class);
//        screenManager.init(mockGame);
//    }
//
//    @Test
//    void testSingletonInstance() {
//        ScreenManager instance1 = ScreenManager.getInstance();
//        ScreenManager instance2 = ScreenManager.getInstance();
//
//        assertSame(instance1, instance2, "ScreenManager should be singleton");
//    }
//
//    @Test
//    void testInitStoresGame() throws NoSuchFieldException, IllegalAccessException {
//        var gameField = ScreenManager.class.getDeclaredField("game");
//        gameField.setAccessible(true);
//        assertSame(mockGame, gameField.get(screenManager), "Game instance should be stored in ScreenManager");
//    }
//
//    @Test
//    void testShowScreenSetsNewScreenAndDisposesOld() {
//        // Mock previous screen
//        Screen mockOldScreen = mock(Screen.class);
//        when(mockGame.getScreen()).thenReturn(mockOldScreen);
//
//        // Spy the enum to return a mock screen
//        Screen mockNewScreen = mock(Screen.class);
//        ScreenEnum spyEnum = spy(ScreenEnum.MAIN_MENU);
//        doReturn(mockNewScreen).when(spyEnum).getScreen(any());
//
//        // Call method
//        screenManager.showScreen(spyEnum);
//
//        // Verify new screen is set
//        verify(mockGame).setScreen(mockNewScreen);
//
//        // Verify old screen is disposed
//        verify(mockOldScreen).dispose();
//    }
//
//    @Test
//    void testShowScreenNoPreviousScreen() {
//        when(mockGame.getScreen()).thenReturn(null);
//
//        Screen mockNewScreen = mock(Screen.class);
//        ScreenEnum spyEnum = spy(ScreenEnum.MAIN_MENU);
//        doReturn(mockNewScreen).when(spyEnum).getScreen(any());
//
//        // Call method
//        screenManager.showScreen(spyEnum);
//
//        verify(mockGame).setScreen(mockNewScreen);
//        // No previous screen, dispose should not be called
//    }
//}
