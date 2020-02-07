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
package org.devtoolbox.util.gui.svg.batik;

import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;

import org.devtoolbox.util.gui.svg.SVGIcon;
import org.devtoolbox.util.gui.svg.SVGIconFactory;
import org.devtoolbox.util.gui.svg.SVGIconManager;
import org.devtoolbox.util.gui.svg.SVGImageReader;


/**
 * SVG icon factory implementation using batik library.
 *
 * @author Arnaud Lecollaire
 * @see <a href="http://xmlgraphics.apache.org/batik/">Apache batik library</a>
 */
public class BatikSVGIconFactory implements SVGIconFactory {

    private static final BatikSVGIconFactory INSTANCE = new BatikSVGIconFactory();


    public static void initialize() {
        SVGIconManager.setIconFactory(INSTANCE);
    }

    public static BatikSVGIconFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Class<? extends SVGImageReader> getImageReaderClass() {
        return BatikSVGImageReader.class;
    }

    @Override
    public Icon getIcon(final URL imageURL, final int width, final int height) throws IOException {
        return new SVGIcon(imageURL, width, height);
    }
}
