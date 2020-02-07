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

/**
 * @author Arnaud Lecollaire
 */
package org.devtoolbox.util.gui.svg;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;


/** @deprecated non-javafx support will be dropped in future releases, this class will be removed */
@Deprecated
public class SVGIcon implements Icon {

    private final Image image;
    private final int width;
    private final int height;


    public SVGIcon(final URL imageURL, final int width, final int height) throws IOException {
        this(readImage(imageURL, width, height), width, height);
    }

    public static BufferedImage readImage(final URL imageURL, final int width, final int height) throws IOException {
        try (InputStream istream = imageURL.openStream()) {
            final ImageInputStream stream = ImageIO.createImageInputStream(istream);
            if (stream == null) {
                throw new IllegalArgumentException("stream == null!");
            }
            try {
                return readImage(stream, width, height);
            } finally {
                istream.close();
            }
        }
    }

    public static BufferedImage readImage(final ImageInputStream stream, final int width, final int height) throws IOException {
        final Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
        if (! readers.hasNext()) {
            return null;
        }

        final ImageReader reader = readers.next();
        if (! (reader instanceof SVGImageReader)) {
            throw new IllegalStateException("reader is not a SVG reader");
        }
        final SVGImageReader svgReader = (SVGImageReader) reader;
        svgReader.setWidth(width);
        svgReader.setHeight(width);
        final ImageReadParam param = svgReader.getDefaultReadParam();
        svgReader.setInput(stream, true, true);
        try {
            return svgReader.read(0, param);
        } finally {
            reader.dispose();
        }
    }

    public SVGIcon(final Image image, final int width, final int height) {
        super();
        this.image = image;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
        g.drawImage(image, x, y, null);
    }
}