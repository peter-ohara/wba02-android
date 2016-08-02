package com.pascoapp.wba02_android;

import org.junit.Test;

import trikita.jedux.Action;
import trikita.jedux.Store;

import static org.junit.Assert.assertEquals;

/**
 * Created by peter on 8/2/16.
 */

public class ActionsUnitTest {
    private Store<Action, State> store;

    public ActionsUnitTest() {
        State initialState = State.Default.build();
        store = new Store<Action, State>(new RootReducer(),
                initialState
        );
    }

    @Test
    public void showScreenAction() throws Exception {
        store.dispatch(Actions.showScreen(Screens.MAIN_SCREEN));
        assertEquals(store.getState().currentScreen(), Screens.MAIN_SCREEN);
    }
}
