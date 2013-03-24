package org.tomighty.ui.options;

import java.awt.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.JFormattedTextField;

import org.tomighty.config.Options;
import org.tomighty.i18n.Messages;

@SuppressWarnings("serial")
public class Miscellaneous extends OptionPanel implements OptionGroup {

    @Inject private Options options;
    @Inject private Messages messages;

    private JFormattedTextField numberOfPomodoros;

    @PostConstruct
    public void initialize() {
        numberOfPomodoros = addField("Number of Pomodoros");
    }

    private JFormattedTextField addField(String name) {
        return new OptionCommons(messages).addStandardOption(this, name, "pomodoros");
    }

    @Override
    public String name() {
        return messages.get("Miscellaneous");
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void readConfiguration() {
        numberOfPomodoros.setValue(options.miscellaneous().numberOfPomodoros());
    }

    @Override
    public void saveConfiguration() {
        options.miscellaneous().numberOfPomodoros(valueOf(numberOfPomodoros));
    }

    private int valueOf(JFormattedTextField field) {
        return new OptionCommons(messages).valueOf(field);
    }
}
