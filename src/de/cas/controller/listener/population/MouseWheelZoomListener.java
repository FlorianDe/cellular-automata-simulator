package de.cas.controller.listener.population;

import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import de.cas.controller.IAutomatonController;

public class MouseWheelZoomListener implements MouseWheelListener {

    protected IAutomatonController controller;

    public MouseWheelZoomListener(IAutomatonController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
            int notches = e.getWheelRotation();

            if (notches < 0) {
                this.controller.getPopulationModel().zoomIn();
            } else {
                this.controller.getPopulationModel().zoomOut();
            }
        }
    }
}

