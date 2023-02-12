<!--
  - MIT License
  -
  - Copyright © 2020-2023 dev-toolbox.org
  -
  - Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
  - (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish,
  - distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
  - following conditions:
  -
  - The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
  -
  - THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
  - MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
  - CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
  - OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
  -->

dev-toolbox-gui-util
====================

Parent pom for dev-toolbox.org GUI utility projects.

- gui-util-action : Implementation of a background task for JavaFX that performs all task listeners notifications in the JavaFX platform thread.
- gui-util-animation : Utility methods for java FX animations.
- gui-util-aspect-ratio : JavaFX components with aspect ratio automatic handling.
- gui-util-exception : JavaFX components displaying java exception.
- gui-util-file-field : JavaFX component for file selection.
- gui-util-svg : Abstraction and implementations for SVG image handling.
- gui-util-tab : Utility classes to easily associate a FXML file to a tab.
- gui-util-task-console : JavaFX component displaying actions status and logs.

history
-------
- v1.9.0 2023/02/12 : upgrade to dev-toolbox-util-task v0.10.0
- v1.8.0 2023/02/03 : java 17
- v1.7.2 2021/05/13 : upgraded all gui-util projects to use the same version of dev-toolbox-gui-util
- v1.7.1 2020/04/21 : upgrade javafx to 14.0.2.1, junit-jupiter to 5.7.0, surefire and failsafe plugins to 3.0.0-M5, moved gui-action and gui-animation to their own repository
- v1.7.0 2020/04/23 : java 14
- previous versions : history lost :)