package com.pascoapp.wba02_android.router;

import com.pascoapp.wba02_android.Actions;

import java.util.Map;

import trikita.jedux.Action;

/**
 * Created by peter on 8/7/16.
 */

public class RouteActions {

    public static Action showScreen(Route route) {
        return new Action<>(Actions.ActionType.SHOW_SCREEN,
                route);
    }

    public static Action setRouteResolutions(Map<String, Object> resolutions) {
        return new Action<>(Actions.ActionType.SET_ROUTE_RESOLUTIONS,
                resolutions);
    }
}
