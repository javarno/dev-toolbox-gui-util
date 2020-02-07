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

import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;

import org.devtoolbox.util.gui.svg.SVGIconFactory;
import org.devtoolbox.util.gui.svg.SVGIconManager;
import org.devtoolbox.util.gui.svg.SVGImageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * SVG icon factory implementation using kitfox salamander library.
 *
 * @author Arnaud Lecollaire
 * @see  <a href="http://svgsalamander.java.net/">kitfox salamander library</a>
 */
public class KitfoxSalamanderSVGIconFactory implements SVGIconFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(KitfoxSalamanderSVGIconFactory.class);
    private static final KitfoxSalamanderSVGIconFactory INSTANCE = new KitfoxSalamanderSVGIconFactory();


    public static KitfoxSalamanderSVGIconFactory getInstance() {
        return INSTANCE;
    }

    public static void initialize() {
        LOGGER.info("Initializing kitfox salamander SVG icon factory ...");
        SVGIconManager.setIconFactory(INSTANCE);
    }

    @Override
    public Class<? extends SVGImageReader> getImageReaderClass() {
        return KitfoxSalamanderSVGImageReader.class;
    }

    @Override
    public Icon getIcon(final URL imageURL, final int width, final int height) throws IOException {
        return new KitfoxSalamanderSVGIcon(imageURL, width, height);
    }
}