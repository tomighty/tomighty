# Tomighty #

A desktop timer app for the [Pomodoro Technique®](http://pomodorotechnique.com)
written in C++ on top of [Qt 5](http://qt-project.org).

It's designed to be:

* Simple
* Non-distracting
* Unintrusive
* Reasonably configurable
* Extensible (with plugins)
* Cross-platform

When the application starts, a little tomato icon is shown on the system tray. Its color indicates
the current state of the timer. By clicking on the tray icon the main application menu pops up.

For more information, please visit [www.tomighty.org](http://www.tomighty.org).


## Development ##

The source code in this branch is a full rewrite of the previous version which was written in Java.

The project follows the development model
[as presented by Vincent Driessen](http://nvie.com/posts/a-successful-git-branching-model/).


## Building ##

Requirements:

* [Qt 5](http://qt-project.org)
* A C++ compiler ([GCC](https://gcc.gnu.org) will do)

In the project's root directory, type:

```bash
mkdir build
cd build
qmake ../src/tomighty.pro
make
```


## License ##

Tomighty is licensed to you under the terms of the Apache License 2.0.
Read it here: https://www.apache.org/licenses/LICENSE-2.0.html


## Credits ##

The tomato icon was created by [José Campos](www.thenounproject.com/jcampos).

The clock icon was created by [Thomas Le Bas](www.thenounproject.com/tlb).


## Legal Information ##

[Pomodoro Technique®](http://pomodorotechnique.com) and [Pomodoro™](http://pomodorotechnique.com)
are registered and filed trademarks owned by Francesco Cirillo. Tomighty is not affiliated by,
associated with nor endorsed by Francesco Cirillo.
