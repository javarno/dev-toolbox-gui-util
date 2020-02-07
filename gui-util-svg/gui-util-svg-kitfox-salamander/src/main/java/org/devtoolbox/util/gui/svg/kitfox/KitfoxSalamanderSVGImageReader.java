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

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;

import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

import org.devtoolbox.util.gui.svg.ImageInputStreamWrapper;
import org.devtoolbox.util.gui.svg.SVGImageReader;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;


/**
 * SVG image reader implementation using kitfox salamander library.
 *
 * @author Arnaud Lecollaire
 * @see  <a href="http://svgsalamander.java.net/">kitfox salamander library</a>
 */
public class KitfoxSalamanderSVGImageReader extends SVGImageReader {


    public KitfoxSalamanderSVGImageReader(final ImageReaderSpi originatingProvider) {
        super(originatingProvider);
    }

    @Override
    protected BufferedImage readImage(final ImageInputStream inputStream, final int width, final int height) throws IOException {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = bufferedImage.createGraphics();

        try {
            final SVGUniverse universe = new SVGUniverse();
            final URI uri = universe.loadSVG(new ImageInputStreamWrapper(inputStream), "icon");
            final SVGDiagram diagram = universe.getDiagram(uri);
            diagram.setDeviceViewport(new Rectangle(0, 0, width, height));
            final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            final Graphics2D g = image.createGraphics();
            paintIcon(diagram, g, width, height);
            g.dispose();
        } catch (final Exception error) {
            // TODO Auto-generated catch block
            error.printStackTrace();
        } finally {
            graphics.dispose();
        }

        return bufferedImage;
    }

    public void paintIcon(final SVGDiagram diagram, final Graphics2D graphics, final int width, final int height) {
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        diagram.setIgnoringClipHeuristic(false);

        final Rectangle2D.Double rect = new Rectangle2D.Double();
        diagram.getViewRect(rect);

        final AffineTransform scaleXform = new AffineTransform();
        scaleXform.setToScale(width / rect.width, height / rect.height);
        graphics.transform(scaleXform);

        try
        {
            diagram.render(graphics);
        } catch (final SVGException e)
        {
            throw new RuntimeException(e);
        }
    }
}