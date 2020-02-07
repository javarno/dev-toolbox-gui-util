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
package org.devtoolbox.util.gui.svg.kitfox;

import java.awt.Dimension;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.kitfox.svg.app.beans.SVGIcon;


/**
 * SVG iconimplementation using kitfox salamander library.
 *
 * @author Arnaud Lecollaire
 * @see  <a href="http://svgsalamander.java.net/">kitfox salamander library</a>
 */
public class KitfoxSalamanderSVGIcon extends SVGIcon {


    public KitfoxSalamanderSVGIcon(final URL imageURL, final int width, final int height) throws IOException {
        super();
        setPreferredSize(new Dimension(width, height));
        setAntiAlias(true);
        setScaleToFit(true);
        try {
            setSvgURI(imageURL.toURI());
        } catch (final URISyntaxException error) {
            // TODO Auto-generated catch block
            error.printStackTrace();
        }
    }
}