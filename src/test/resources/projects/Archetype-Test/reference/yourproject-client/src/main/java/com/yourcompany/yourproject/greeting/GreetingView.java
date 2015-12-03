/**
	The MIT License (MIT)

	Copyright (c) 2015 Mohamed Ilyes Dimassi

	Permission is hereby granted, free of charge, to any person obtaining a
	copy of this software and associated documentation files (the "Software"),
	to deal in the Software without restriction, including without limitation
	the rights to use, copy, modify, merge, publish, distribute, sublicense,
	and/or sell copies of the Software, and to permit persons to whom the Software
	is furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included
	in all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
	OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
	THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
	IN THE SOFTWARE.
 */
package com.yourcompany.yourproject.greeting;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.yourcompany.yourproject.FieldVerifier;
import com.yourcompany.yourproject.GreetingService;

/**
 * In the ModelViewController {@link GreetingView} contains only view related
 * logic. The most important thing to notice here is that this view does not
 * know about the {@link GreetingController} nor the {@link GreetingService}
 * which makes it perfectly independent and easily reusable for other projects.
 * 
 * @author Mohamed Ilyes Dimassi
 */
public class GreetingView {

	public GreetingView() {
		initUI();
	}

	/**
	 * Registering a handler for the name field and the send button.
	 * 
	 * @param handler
	 *          An instance implementing {@link GreetingEvent} to be invoked when
	 *          the enter key is typed and the name field has the focus or when
	 *          the send button is clicked.
	 */
	public void onGreetHandler(GreetingEvent handler) {

		// prevent empty assignments
		if (handler == null) {
			return;
		}

		this.greetingHandler = handler;
	}

	public String getText() {
		return nameField.getText();
	}

	public void onResponse(String greet, String info, String agent) {

		SafeHtmlBuilder builder = new SafeHtmlBuilder();
		builder.appendEscaped(greet);
		builder.appendHtmlConstant("<br><br>I am running ");
		builder.appendEscaped(info);
		builder.appendHtmlConstant(".<br><br>It looks like you are using:<br>");
		builder.appendEscaped(agent);

		dialogBox.setText("Remote Procedure Call");
		dialogBox.center();

		serverResponseLabel.removeStyleName(greetingCss.error());
		serverResponseLabel.setHTML(builder.toSafeHtml());

		closeButton.setFocus(true);
	}

	public void onError() {
		// Show the RPC error message to the user
		dialogBox.setText("Remote Procedure Call - Failure");
		dialogBox.center();

		serverResponseLabel.addStyleName(greetingCss.error());
		serverResponseLabel.setHTML(SERVER_ERROR);

		closeButton.setFocus(true);
	}

	/**
	 *
	 * {@link GreetingEvent} enforces separation between {@link GreetingView} and
	 * {@link GreetingController}. The controller does not need to know what
	 * specific UI Component event it needs to implement considering that this
	 * logic might change in the future which might cause the controller to break.
	 * It also does not need to care about the validation process involved before
	 * it actually decides to relay the call the service layer. The first level of
	 * validation is performed by the view itself and only if data is valid, it
	 * fires the {@link GreetingEvent} letting the controller know that it needs
	 * to send it to the service layer. If the view decides that another button
	 * causes the data to be sent to the server, the controller does not need to
	 * be updated since the contract between the view and the controller remains
	 * unchanged.
	 */
	public static interface GreetingEvent extends EventHandler {
		public void onGreet();
	}

	// First, we validate the input.
	private boolean validateInput() {
		errorLabel.setText("");
		String textToServer = nameField.getText();

		if (!FieldVerifier.isValidName(textToServer)) {
			errorLabel.setText("Please enter at least four characters");
			return false;
		}

		// Then, we send the input to the server.
		sendButton.setEnabled(false);
		textToServerLabel.setText(textToServer);
		serverResponseLabel.setText("");

		return true;
	}
	
	private void initUI() {

		if (greetingBundle == null) {
			greetingBundle = defaultBundle;
			greetingCss = greetingBundle.greetingCss();
			greetingCss.ensureInjected();
		}

		// Default implementation that does nothing to reduce checking.
		greetingHandler = new GreetingEvent() {
			@Override
			public void onGreet() {
			}
		};

		// add style names to widgets
		sendButton = new Button("Send");
		sendButton.addStyleName(greetingCss.sendButton());
		sendButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (validateInput() && greetingHandler != null) {
					greetingHandler.onGreet();
				}
			}
		});

		// Focus the cursor on the name field when the app loads
		nameField = new TextBox();
		nameField.setText("GWT User");
		nameField.setFocus(true);
		nameField.selectAll();
		nameField.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if (validateInput() && greetingHandler != null) {
						greetingHandler.onGreet();
					}
				}
			}
		});

		errorLabel = new Label();

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Create the popup dialog box
		dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);

		closeButton = new Button("Close");
		closeButton.addStyleName(greetingCss.close());

		textToServerLabel = new Label();
		serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
	}

	private Button sendButton;

	private TextBox nameField;

	private Label errorLabel;

	private Label textToServerLabel;

	private HTML serverResponseLabel;

	private DialogBox dialogBox;

	private Button closeButton;

	private GreetingEvent greetingHandler;

	private GreetingCss greetingCss;

	private GreetingBundle greetingBundle;

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private static final GreetingBundle defaultBundle = GWT
			.create(GreetingBundle.class);
}
