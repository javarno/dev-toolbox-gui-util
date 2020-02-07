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

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Arnaud Lecollaire
 */
public abstract class SVGImageReader extends ImageReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SVGImageReader.class);

    private BufferedImage image;
    private Integer width = null;
    private Integer height = null;


    protected SVGImageReader(final ImageReaderSpi originatingProvider) {
        super(originatingProvider);
    }

    @Override
    public BufferedImage read(final int imageIndex, final ImageReadParam param) throws IOException {
        if (image == null) {
            if (input instanceof ImageInputStream) {
                image = readImage((ImageInputStream) input, width, height);
            } else {
                LOGGER.warn("Can not decode image, input is not an input stream");
            }
        }
        return image;
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(final int imageIndex) throws IOException {
        return Arrays.asList(ImageTypeSpecifier.createInterleaved(
                ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[] {0, 1, 2},
                DataBuffer.TYPE_BYTE, false, false)).iterator();
    }

    protected abstract BufferedImage readImage(final ImageInputStream inputStream, int width, int height) throws IOException;

    @Override
    public int getNumImages(final boolean allowSearch) throws IOException {
        return 1;
    }

    @Override
    public int getWidth(final int imageIndex) throws IOException {
        return width;
    }

    @Override
    public int getHeight(final int imageIndex) throws IOException {
        return height;
    }

    @Override
    public IIOMetadata getStreamMetadata() throws IOException {
        return null;
    }

    @Override
    public IIOMetadata getImageMetadata(final int imageIndex) throws IOException {
        return null;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setHeight(final int height) {
        this.height = height;
    }
}