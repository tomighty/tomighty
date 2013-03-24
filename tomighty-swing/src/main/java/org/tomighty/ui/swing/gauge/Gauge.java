/*
 * Copyright (c) 2010-2012 Célio Cidral Junior.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.tomighty.ui.swing.gauge;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.inject.Inject;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ui.UiStateChanged;
import org.tomighty.config.Options;
import org.tomighty.ui.UiState;
import org.tomighty.ui.state.pomodoro.Pomodoro;
import org.tomighty.ui.state.pomodoro.PomodoroFinished;
import org.tomighty.ui.swing.laf.GaugeButtonUI;
import org.tomighty.ui.util.Geometry;

@SuppressWarnings("serial")
public class Gauge extends JPanel implements Subscriber<UiStateChanged> {

    private GaugeButtonModel buttonModel;
    private Options options;

    @Inject
    public Gauge(Bus bus, Options options) {
        this.options = options;

        createButtonModel();

        configureAppearance();

        add(createButton());

        bus.subscribe(this, UiStateChanged.class);
    }

    private void createButtonModel() {
        buttonModel = new GaugeButtonModel(getNumberOfLights());
    }

    private void configureAppearance() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setOpaque(false);
        setPreferredSize(getGaugeSize());
        setSize(getGaugeSize());
    }

    private Dimension getGaugeSize() {
        return Geometry.increase(4, getButtonSize());
    }

    private Dimension getButtonSize() {
        return GaugeButtonUI.sizeFor(getNumberOfLights());
    }

    private int getNumberOfLights() {
        return options.miscellaneous().numberOfPomodoros();
    }

    private JButton createButton() {
        JButton button = new JButton();
        button.setModel(buttonModel);
        button.setUI(new GaugeButtonUI());
        button.setOpaque(false);
        button.setPreferredSize(getButtonSize());
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
