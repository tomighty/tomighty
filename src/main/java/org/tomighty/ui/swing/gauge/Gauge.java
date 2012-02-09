/*
 * Copyright (c) 2010 Célio Cidral Junior
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.tomighty.ui.swing.gauge;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ui.UiStateChanged;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.UiState;
import org.tomighty.ui.swing.laf.GaugeButtonUI;
import org.tomighty.ui.state.pomodoro.Pomodoro;
import org.tomighty.ui.state.pomodoro.PomodoroFinished;

@SuppressWarnings("serial")
public class Gauge extends JPanel implements Subscriber<UiStateChanged> {

    private static final int NUMBER_OF_LIGHTS = 4;
    private static final Dimension BUTTON_SIZE = GaugeButtonUI.sizeFor(NUMBER_OF_LIGHTS);
    private static final Dimension GAUGE_SIZE = increase(4, BUTTON_SIZE);

    private static Dimension increase(int pixels, Dimension dimension) {
        return new Dimension(dimension.width + pixels, dimension.height + pixels);
    }

    private final GaugeButtonModel buttonModel = new GaugeButtonModel(NUMBER_OF_LIGHTS);

    @Inject
    public Gauge(Bus bus) {
        configureAppearance();

        add(createButton());

        bus.subscribe(this, UiStateChanged.class);
    }

    private void configureAppearance() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setOpaque(false);
        setPreferredSize(GAUGE_SIZE);
        setSize(GAUGE_SIZE);
    }

    private JButton createButton() {
        JButton button = new JButton();
        button.setModel(buttonModel);
        button.setUI(new GaugeButtonUI());
        button.setOpaque(false);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonModel.turnNextLightOn();
                turnAllLightsOffIfAllAreOn();
            }
        });
        return button;
    }

    @Override
	public void receive(UiStateChanged message) {
		UiState uiState = message.uiState();

        if(uiState instanceof PomodoroFinished)
            buttonModel.turnNextLightOn();

        else if(uiState instanceof Pomodoro)
            turnAllLightsOffIfAllAreOn();
	}

    private void turnAllLightsOffIfAllAreOn() {
        if(buttonModel.areAllLightsOn())
            buttonModel.turnAllLightsOff();
    }

}
