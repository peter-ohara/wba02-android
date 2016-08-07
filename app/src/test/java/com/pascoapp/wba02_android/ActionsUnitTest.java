package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.router.Route;
import com.pascoapp.wba02_android.router.RouteActions;
import com.pascoapp.wba02_android.router.Router;

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
    public void showScreen() throws Exception {
        store.dispatch(RouteActions.showScreen(new Route(Router.Screens.MAIN_SCREEN)));
        assertEquals(store.getState().currentRoute().getScreen(), Router.Screens.MAIN_SCREEN);
        assertEquals(store.getState().currentRoute().getPayload(), null);
    }

}
