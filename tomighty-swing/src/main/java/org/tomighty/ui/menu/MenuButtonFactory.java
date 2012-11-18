package org.tomighty.ui.menu;

import org.tomighty.ui.swing.laf.SexyArrowButtonUI;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuButtonFactory {

    @Inject
    private PopupMenuFactory popupMenuFactory;

    @Inject
    private SexyArrowButtonUI arrowButtonUI;

    public JButton create(Action[] actions) {
        final JPopupMenu menu = popupMenuFactory.create(actions);
        final JButton button = new JButton();
        button.setUI(arrowButtonUI);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.show(button, 0, button.getHeight());
            }
        });
        return button;
    }

}
