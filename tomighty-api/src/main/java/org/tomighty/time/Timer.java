package org.tomighty.time;

import org.tomighty.Phase;

public interface Timer {

    void start(Time initialTime, Phase phase);

    void interrupt();

}
