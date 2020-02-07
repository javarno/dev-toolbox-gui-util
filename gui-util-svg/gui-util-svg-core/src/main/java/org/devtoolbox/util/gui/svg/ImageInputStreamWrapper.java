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
import java.io.InputStream;

import javax.imageio.stream.ImageInputStream;


public class ImageInputStreamWrapper extends InputStream {

    private final ImageInputStream wrappedStream;


    public ImageInputStreamWrapper(final ImageInputStream wrappedStream) {
        this.wrappedStream = wrappedStream;
    }

    @Override
    public int read() throws IOException {
        return wrappedStream.read();
    }

    @Override
    public int read(final byte b[]) throws IOException {
        return wrappedStream.read(b);
    }

    @Override
    public int read(final byte b[], final int off, final int len) throws IOException {
        return wrappedStream.read(b, off, len);
    }

    @Override
    public long skip(final long n) throws IOException {
        return wrappedStream.skipBytes(n);
    }

    @Override
    public void close() throws IOException {
        wrappedStream.close();
    }

    @Override
    public int available() throws IOException {
        return (int) wrappedStream.length();
    }
}