/*
 * MIT License
 *
 * Copyright © 2020 dev-toolbox.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.devtoolbox.util.gui.exception;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Utility class to display an error message with the stack trace.
 *
 * @author Arnaud Lecollaire
 */
public class ErrorDisplayer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorDisplayer.class);


	public static void showError(final Component owner, final String title,
			final String message, final Throwable error) {
	    LOGGER.error(message, error);
        displayErrorMessage(owner, title, new ErrorDetailsPanel(message, error));
	}

    private static void displayErrorMessage(final Component owner,
    		final String title, final ErrorDetailsPanel errorPanel) {
        if (SwingUtilities.isEventDispatchThread()) {
            openErrorFrame(owner, title, errorPanel);
            return;
        }
        try {
            SwingUtilities.invokeAndWait(() -> openErrorFrame(owner, title, errorPanel));
        } catch (final InterruptedException | InvocationTargetException error) {
            LOGGER.error("Failed to display error.", error);
        }
    }

    protected static void openErrorFrame(final Component owner,
            final String title, final ErrorDetailsPanel errorPanel) {
        JOptionPane.showMessageDialog(owner, errorPanel, title, JOptionPane.ERROR_MESSAGE);
    }
}