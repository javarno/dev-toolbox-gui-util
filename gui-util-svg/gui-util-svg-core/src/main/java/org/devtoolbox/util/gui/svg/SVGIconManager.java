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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.Icon;

import org.devtoolbox.util.resource.ResourceTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;


public class SVGIconManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SVGIconManager.class);

    private static final SVGIconManager INSTANCE = new SVGIconManager();
    private static final Map<SVGCacheKey, Image> CACHE = new HashMap<>();

    private SVGIconFactory iconFactory = null;


    public static SVGIconManager getInstance() {
        return INSTANCE;
    }

    protected SVGIconFactory getIconFactory() {
        if (iconFactory == null) {
            throw new IllegalStateException("There is no icon factory registered.");
        }
        return iconFactory;
    }

    public static void setIconFactory(final SVGIconFactory factory) {
        LOGGER.info("Initializing icon factory for SVG icon manager, using an instance of [{}].", factory.getClass().getName());
        INSTANCE.iconFactory = factory;
    }

    /**
     * Loads a svg file from classpath and converts it to an FX image using the given parameters.
     *
     * @param imagePath the path of the image to load inside the classpath
     * @param width the width of the FX image to return
     * @param height the height of the FX image to return
     * @return the requested image
     */
    public static Image getSVGImage(final String imagePath, final int width, final int height) throws IOException {
        Objects.requireNonNull(imagePath);
        final SVGCacheKey cacheKey = new SVGCacheKey(imagePath, width, height);
        Image cachedImage = CACHE.get(cacheKey);
        if (cachedImage == null) {
            final URL iconURL = ResourceTools.getClasspathResourceURL(imagePath);
            final Icon icon = getInstance().getIconFactory().getIcon(iconURL, width, height);
            final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D graphics = bufferedImage.createGraphics();
            try {
                icon.paintIcon(null, graphics, 0, 0);
            } finally {
                graphics.dispose();
            }
            final WritableImage fxImage = new WritableImage(width, height);
            SwingFXUtils.toFXImage(bufferedImage, fxImage);
            cachedImage = fxImage;
            CACHE.put(cacheKey, fxImage);
            cachedImage = fxImage;
        }
        return cachedImage;
    }

    public static ImageView getSVGImageView(final String path, final int width, final int height, final Consumer<? super IOException> errorHandler) {
        Image cachedImage;
        try {
            cachedImage = getSVGImage(path, width, height);
        } catch (final IOException error) {
            errorHandler.accept(error);
            return null;
        }
        return (cachedImage == null) ? null : new ImageView(cachedImage);
    }

    /**
     * Loads a svg file from classpath and converts it to an FX image using the given parameters.
     *
     * @param imagePath the path of the image to load inside the classpath
     * @param width the width of the FX image to return
     * @param height the height of the FX image to return
     * @param errorHandler handler for IOException
     * @return the requested image (or null if an I/O error occurs)
     */
    public static Image getSVGImage(final String imagePath, final int width, final int height, final Consumer<IOException> errorHandler) {
        try {
            return getSVGImage(imagePath, width, height);
        } catch (final IOException error) {
            errorHandler.accept(error);
            return null;
        }
    }
}