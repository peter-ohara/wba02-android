package com.pascoapp.wba02_android.router;

/**
 * Created by peter on 8/6/16.
 */
public class Route {
    private Router.Screens screen;
    private Object payload;

    public Route(Router.Screens screen) {
        this.screen = screen;
    }

    public Route(Router.Screens screen, Object payload) {
        this.screen = screen;
        this.payload = payload;
    }

    public Router.Screens getScreen() {
        return screen;
    }

    public Object getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Route{" +
                "screen=" + screen +
                ", payload=" + payload +
                '}';
    }
}
