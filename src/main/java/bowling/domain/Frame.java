package bowling.domain;

import java.util.List;

public interface Frame {
    boolean playing();

    Frame play(int knockedPinsCount);

    Frame play(KnockedPins knockedPins);

    Frame next();

    Frame last();

    List<Pitch> pitches();
}
