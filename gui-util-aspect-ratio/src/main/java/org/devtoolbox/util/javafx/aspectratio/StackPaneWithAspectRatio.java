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
package org.devtoolbox.util.javafx.aspectratio;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.StackPane;


/**
 * @author Arnaud Lecollaire
 */
// TODO add tests, logs ?, javadoc ...
public class StackPaneWithAspectRatio extends StackPane {

    private final ObjectProperty<Orientation> orientation = new SimpleObjectProperty<>(Orientation.HORIZONTAL);
    private final DoubleProperty verticalAspectMultiplier = new SimpleDoubleProperty(1.0d);


    @Override
    protected double computePrefHeight(final double width) {
        if (width < 0) {
            return -1;
        } else {
            return width * verticalAspectMultiplier.get();
        }
    }

    @Override
    protected double computePrefWidth(final double height) {
        if (height < 0) {
            return -1;
        } else {
            return height / verticalAspectMultiplier.get();
        }
    }

    @Override
    public Orientation getContentBias() {
        return orientation.get();
    }

    public Orientation getOrientation() {
        return orientation.get();
    }

    public void setOrientation(final Orientation orientation) {
        this.orientation.set(orientation);
    }

    public double getVerticalAspectMultiplier() {
        return verticalAspectMultiplier.get();
    }

    public void setVerticalAspectMultiplier(final double verticalAspectMultiplier) {
        this.verticalAspectMultiplier.set(verticalAspectMultiplier);
    }
}