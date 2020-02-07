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

import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;


/**
 * @author Arnaud Lecollaire
 */
public class SVGImageReaderSpi extends ImageReaderSpi {

    private final Class<? extends SVGImageReader> imageReaderClass;


    public SVGImageReaderSpi(final Class<? extends SVGImageReader> imageReaderClass) {
        super("dev-toolbox", "0.2.0",
                new String[] { "svg", "SVG" }, new String[] { "svg", "SVG" }, new String[] { "image/svg+xml" },
                imageReaderClass.getName(), new Class<?>[] { ImageInputStream.class },
                null, false, null, null, null, null, false, null, null, null, null);
        this.imageReaderClass = imageReaderClass;
    }

    @Override
    public String getDescription(final Locale locale) {
        // TODO add implementation name / version
        return "dev-toolbox SVG Reader v0.2.0";
    }

    @Override
    public boolean canDecodeInput(final Object input) throws IOException {
        if (! (input instanceof ImageInputStream)) {
            return false;
        }

        final ImageInputStream inputStream = (ImageInputStream) input;
        inputStream.mark();
        try {
            String line;
            while ((line = inputStream.readLine()) != null) {
                // remove processing instruction(s) from current line
                int xmlProcessingInstructionStart;
                do {
                    xmlProcessingInstructionStart = line.indexOf("<?");
                    if (xmlProcessingInstructionStart != -1) {
                        final int xmlProcessingInstructionEnd = line.indexOf("?>");
                        if (xmlProcessingInstructionEnd == -1) {
                            // processing instruction end is in another line, ignore this one
                            continue;
                        }
                        if (xmlProcessingInstructionEnd < xmlProcessingInstructionStart) {
                            // malformed XML
                            return false;
                        }
                        line = line.substring(xmlProcessingInstructionEnd + 2);
                    }
                } while (xmlProcessingInstructionStart != -1);

                // remove doctype definition, entity declarations, or comments
                int entityStart;
                do {
                    entityStart = line.indexOf("<!");
                    if (entityStart != -1) {
                        final int entityEnd = line.indexOf(">");
                        if (entityEnd == -1) {
                            // entity end is in another line, ignore this one
                            continue;
                        }
                        if (entityEnd < entityStart) {
                            // malformed XML
                            return false;
                        }
                        line = line.substring(entityEnd + 1);
                    }
                } while (entityStart != -1);

                final int rootTagStartIndex = line.indexOf('<');
                // ignore empty lines
                if (rootTagStartIndex == -1) {
                    continue;
                }
                if (line.length() < (rootTagStartIndex + 4)) {
                    return false;
                }
                return line.substring(rootTagStartIndex + 1, rootTagStartIndex + 4).equals("svg");
            }
            return false;
        } finally {
            inputStream.reset();
        }
    }

    @Override
    public ImageReader createReaderInstance(final Object extension) {
        try {
            return imageReaderClass.getConstructor(ImageReaderSpi.class).newInstance(this);
        } catch (final ReflectiveOperationException error) {
            // TODO Auto-generated catch block
            error.printStackTrace();
            return null;
        }
    }
}