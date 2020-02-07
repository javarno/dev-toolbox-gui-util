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
package org.devtoolbox.gui.util.file;

import java.io.File;
import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;


/**
 * @author Arnaud Lecollaire
 */
public class FileSelector extends BorderPane {

    private final ObjectProperty<Path> valueProperty = new SimpleObjectProperty<>();

    private final Label labelComponent = new Label("select file");
    private final TextField fileNameField = new TextField();
    private final Button browseButton = new Button("browse");

    private Window owner = null;


    public FileSelector() {
        super();
        final BorderPane value = new BorderPane();
        setCenter(value);
        value.setLeft(labelComponent);
        value.setCenter(fileNameField);
        setRight(browseButton);
        browseButton.setOnAction(event -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            if (valueProperty.get() != null) {
                final File initialFile = valueProperty.get().toFile();
                fileChooser.setInitialDirectory((initialFile.isDirectory()) ? initialFile : initialFile.getParentFile());
                fileChooser.setInitialFileName(initialFile.getName());
            }
            final File result = fileChooser.showOpenDialog(owner);
            if (result != null) {
                fileNameField.setText(result.getAbsolutePath());
            }
        });
        fileNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            final File newFile = new File(newValue);
            if (newFile.exists() && newFile.exists()) {
                valueProperty.set(newFile.toPath());
            } else {
                valueProperty.set(null);
            }
        });
    }

    public Window getOwner() {
        return owner;
    }

    public void setOwner(final Window owner) {
        this.owner = owner;
    }

    public String getLabel() {
        return labelComponent.getText();
    }

    public void setLabel(final String label) {
        labelComponent.setText(label);
    }

    public Path getValue() {
        return valueProperty.get();
    }

    public void setValue(final Path value) {
        fileNameField.setText(value.toFile().getAbsolutePath());
    }

    public void setTextValue(final String textValue) {
        fileNameField.setText(textValue);
    }

    public ObjectProperty<Path> valueProperty() {
        return valueProperty;
    }
}
