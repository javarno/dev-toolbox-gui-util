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

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * Panel displaying a throwable's message and stack trace.
 *
 * @author Arnaud Lecollaire
 */
// TODO add caused by stack trace(s)
public class StackTracePanel extends JPanel {

	private static final long serialVersionUID = 3421066249029966769L;


	public StackTracePanel(final Throwable exception) {
		super(new BorderLayout(0, 5));

		final StringBuilder exceptionStringBuilder = new StringBuilder("<html><ul>");
		for (final StackTraceElement element : exception.getStackTrace()) {
			exceptionStringBuilder.append("<li>at " + element + "</li>");
		}
		exceptionStringBuilder.append("</ul></html>");

		final JScrollPane scrollPane = new JScrollPane(new JLabel(exceptionStringBuilder.toString()));
		scrollPane.setBorder(null);
		add(new JLabel(exception.getMessage()), BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}
}