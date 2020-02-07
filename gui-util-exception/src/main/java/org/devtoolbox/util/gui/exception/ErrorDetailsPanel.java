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
import java.awt.CardLayout;
import java.awt.Dimension;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;


/**
 * Panel displaying a throwable's details.
 *
 * @author Arnaud Lecollaire
 */
/*
 * TODO set location of stack trace panel to corresponding caused by when a cause is selected
 * TODO add suppressed exceptions data
 */
public class ErrorDetailsPanel extends JPanel {

	private static final long serialVersionUID = -7920389408162464624L;

	private static final Dimension DETAIL_PANEL_SIZE = new Dimension(600, 450);
	private static final Dimension LIST_SIZE = new Dimension(400, 450);
	private static final MessageFormat MESSAGE_FORMAT = new MessageFormat("{0} (message : {1})");
	private static final MessageFormat SHORT_MESSAGE_FORMAT = new MessageFormat("{0}");


	public ErrorDetailsPanel(final Throwable error) {
		this(null, findCauses(error));
	}

	public ErrorDetailsPanel(final String message, final Throwable error) {
		this(message, findCauses(error));
	}

	public ErrorDetailsPanel(final String message, final Collection<? extends Throwable> errors) {
		super(new BorderLayout(0, ((errors != null) && (! errors.isEmpty())) ? 20 : 0));

		if (message != null) {
			add(new JLabel(message), BorderLayout.CENTER);
		}
		if ((errors == null) || errors.isEmpty()) {
			return;
		}

		if (errors.size() == 1) {
			final StackTracePanel detailsPanel = new StackTracePanel(errors.iterator().next());
			add(new JScrollPane(detailsPanel), BorderLayout.SOUTH);
			detailsPanel.setPreferredSize(DETAIL_PANEL_SIZE);
			setBorder(new TitledBorder("Details"));
			return;
		}

		final JPanel exceptionsPanel = new JPanel(new BorderLayout());
		add(exceptionsPanel, BorderLayout.SOUTH);
		exceptionsPanel.setBorder(new TitledBorder("Details"));

		final CardLayout exceptionsCardLayout = new CardLayout();
		final JPanel exceptionDetailContainer = new JPanel(exceptionsCardLayout);
		final Vector<String> exceptionSummaries = new Vector<String>();

		int exceptionIndex = 0;
		for (final Throwable exception : errors) {
			exceptionSummaries.add(
				((exceptionIndex == 0) ? "" : "Caused by : ")
				+ exception.getClass().getSimpleName()
				+ ((exception.getMessage() == null) ? "" : " : " + exception.getMessage()));
			exceptionDetailContainer.add(
					new JScrollPane(new StackTracePanel(exception)), Integer.toString(exceptionIndex++));
		}
		exceptionDetailContainer.setPreferredSize(DETAIL_PANEL_SIZE);

		final JList<String> exceptionSelector = new JList<String>(exceptionSummaries);
		exceptionSelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		exceptionsPanel.add(exceptionSelector, BorderLayout.WEST);
		exceptionsPanel.add(exceptionDetailContainer, BorderLayout.CENTER);
		exceptionSelector.getSelectionModel().addListSelectionListener(e -> exceptionsCardLayout.show(
		        exceptionDetailContainer, Integer.toString(exceptionSelector.getSelectedIndex())));
		exceptionSelector.setSelectedIndex(0);
		exceptionSelector.setPreferredSize(LIST_SIZE);
	}

	private static List<Throwable> findCauses(Throwable error) {
		final List<Throwable> causes = new ArrayList<Throwable>();
		do {
			causes.add(error);
			error = (error.getCause() == error) ? null : error.getCause();
		} while (error != null);
		return causes;
	}

	protected static String formatMessage(final Throwable error) {
		return (error.getMessage() == null) ?
			SHORT_MESSAGE_FORMAT.format(new Object[] { error.getClass().getName()}) :
			MESSAGE_FORMAT.format(new Object[] { error.getClass().getName(), error.getMessage()});
	}
}