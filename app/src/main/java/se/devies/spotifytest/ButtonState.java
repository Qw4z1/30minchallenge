package se.devies.spotifytest;

public enum ButtonState {
    GO(R.string.go),
    START(R.string.start),
    HOLD(R.string.hold);

    int text;

    ButtonState(int text) {
        this.text = text;
    }
}
