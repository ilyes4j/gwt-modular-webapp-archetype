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
#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.greeting;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import ${package}.GreetingResponse;
import ${package}.GreetingService;
import ${package}.GreetingServiceAsync;
import ${package}.greeting.GreetingView.GreetingEvent;

/**
 * In the ModelViewController {@link GreetingController} is responsible for
 * isolating the view layer {@link GreetingView} from the service layer
 * {@link GreetingService} and relaying messages between them.
 * 
 * @author Mohamed Ilyes Dimassi
 */
public class GreetingController implements GreetingEvent {

	public GreetingController(GreetingView view, GreetingServiceAsync service) {
		this.view = view;
		this.view.onGreetHandler(this);
		this.service = service;
	}

	/**
	 * Fired when the user clicks on the sendButton.
	 */
	public void onClick(ClickEvent event) {
		sendNameToServer();
	}

	/**
	 * Fired when the user types in the nameField.
	 */
	public void onKeyUp(KeyUpEvent event) {
		sendNameToServer();
	}

	@Override
	public void onGreet() {
		sendNameToServer();
	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	private void sendNameToServer() {

		service.greetServer(view.getText(), new AsyncCallback<GreetingResponse>() {
			public void onFailure(Throwable caught) {
				view.onError();
			}

			public void onSuccess(GreetingResponse result) {
				String greet = result.getGreeting();
				String info = result.getServerInfo();
				String agent = result.getUserAgent();
				view.onResponse(greet, info, agent);
			}
		});
	}

	private GreetingView view;

	private GreetingServiceAsync service;
}
