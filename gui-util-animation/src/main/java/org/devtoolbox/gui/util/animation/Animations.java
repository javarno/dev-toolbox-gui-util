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
package org.devtoolbox.gui.util.animation;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;


/**
 * @author Arnaud Lecollaire
 */
public class Animations {

    public static FadeTransition fadeOut(final Node nodeToFadeOut, final long delay) {
        final FadeTransition fadeTransition = new FadeTransition(Duration.millis(delay), nodeToFadeOut);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        return fadeTransition;
    }

    public static FadeTransition fadeIn(final Node nodeToFadeIn, final long delay) {
        final FadeTransition fadeTransition = new FadeTransition(Duration.millis(delay), nodeToFadeIn);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        return fadeTransition;
    }

    public static ScaleTransition scale(final Node target, final double delay, final double start, final double end) {
        final ScaleTransition scaleTransition = new ScaleTransition(new Duration(delay), target);
        scaleTransition.setFromX(start);
        scaleTransition.setFromY(start);
        scaleTransition.setToX(end);
        scaleTransition.setToY(end);
        return scaleTransition;
    }

    public static RotateTransition rotate(final Node target, final long delay, final double fromAngle, final double toAngle) {
        final RotateTransition rotateTransition = new RotateTransition(Duration.millis(delay), target);
        rotateTransition.setFromAngle(fromAngle);
        rotateTransition.setToAngle(toAngle);
        return rotateTransition;
    }

    public static PathTransition path(final Node target, final String content, final long delay) {
        final SVGPath path = new SVGPath();
        path.setContent(content);
        final PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(delay));
        pathTransition.setPath(path);
        pathTransition.setNode(target);
        return pathTransition;
    }

    public static void addEndStatusListener(final Animation animation, final AnimationEndListener listener) {
        animation.statusProperty().addListener(listener);
    }
}