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

import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.ClassName;

public interface GreetingCss extends CssResource {

	public String sendButton();

	/**
	 * When the selector name does not meet java naming rules it is possible to
	 * indicate the selector name using {@link ClassName}
	 * 
	 * @return selector name
	 */
	@ClassName("gwt-DialogBox")
	public String gwtDialogBox();

	public String dialogVPanel();

	/**
	 * Too long inconvenient selector name ? No problem !
	 * 
	 * @return selector name
	 */
	@ClassName("serverResponseLabelError")
	public String error();

	@ClassName("closeButton")
	public String close();
}
