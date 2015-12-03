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
package com.yourcompany.yourproject;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.yourcompany.yourproject.greeting.GreetingController;
import com.yourcompany.yourproject.greeting.GreetingView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Mohamed Ilyes Dimassi
 */
public class App implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		GreetingView view = new GreetingView();
		new GreetingController(view, service);
	}

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private static final GreetingServiceAsync service = GWT.create(GreetingService.class);
}