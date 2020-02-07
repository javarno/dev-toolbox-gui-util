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

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.devtoolbox.util.gui.svg.ImageInputStreamWrapper;
import org.devtoolbox.util.gui.svg.SVGImageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * SVG image reader implementation using batik library.
 *
 * @author Arnaud Lecollaire
 * @see <a href="http://xmlgraphics.apache.org/batik/">Apache batik library</a>
 */
// TODO improve error handling
public class BatikSVGImageReader extends SVGImageReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatikSVGImageReader.class);


    public BatikSVGImageReader(final ImageReaderSpi originatingProvider) {
        super(originatingProvider);
    }

    @Override
    protected BufferedImage readImage(final ImageInputStream inputStream, final int width, final int height) throws IOException {
        final BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
        transcoder.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, (float) width);
        transcoder.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, (float) height);
        try {
            transcoder.transcode(new TranscoderInput(new ImageInputStreamWrapper(inputStream)), null);
        } catch (final TranscoderException error) {
            LOGGER.error("Failed to read SVG image", error);
            return null;
        }
        return transcoder.getBufferedImage();
    }
}