include(../common.pri)

TEMPLATE = lib
TARGET = tomighty-core-tests
CONFIG   += console
CONFIG   -= app_bundle

QT       += testlib
QT       -= gui

DEFINES += SRCDIR=\\\"$$PWD/\\\"
DEPENDPATH += \
  $$PWD/../core \
  $$PWD/../core-mock

INCLUDEPATH += \
  $$PWD/../core \
  $$PWD/../core/impl \
  $$PWD/../core-mock

win32:CONFIG(release, debug|release): LIBS += \
  -L$$OUT_PWD/../core/release/ -ltomighty-core \
  -L$$OUT_PWD/../core-mock/release/ -ltomighty-core-mock

else:win32:CONFIG(debug, debug|release): LIBS += \
  -L$$OUT_PWD/../core/debug/ -ltomighty-core \
  -L$$OUT_PWD/../core-mock/debug/ -ltomighty-core-mock

else:unix: LIBS += \
  -L$$OUT_PWD/../core/ -ltomighty-core \
  -L$$OUT_PWD/../core-mock/ -ltomighty-core-mock

HEADERS += \
    TimerTest.h \
    PomodoroEngineTest.h

SOURCES += \
    TimerTest.cpp \
    PomodoroEngineTest.cpp
