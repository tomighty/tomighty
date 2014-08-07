include(../common.pri)

TEMPLATE = lib
TARGET = tomighty-core-tests
CONFIG   += console
CONFIG   -= app_bundle

QT       += testlib
QT       -= gui

DEFINES += SRCDIR=\\\"$$PWD/\\\"
DEPENDPATH += $$PWD/../core
INCLUDEPATH += $$PWD/../core $$PWD/../core/impl

win32:CONFIG(release, debug|release): LIBS += -L$$OUT_PWD/../core/release/ -ltomighty-core
else:win32:CONFIG(debug, debug|release): LIBS += -L$$OUT_PWD/../core/debug/ -ltomighty-core
else:unix: LIBS += -L$$OUT_PWD/../core/ -ltomighty-core

HEADERS += \
    TimerTest.h \
    PomodoroEngineTest.h \
    mock/ManualClockwork.h \
    mock/FakeTimer.h \
    mock/InMemoryPreferences.h

SOURCES += \
    TimerTest.cpp \
    PomodoroEngineTest.cpp \
    mock/ManualClockwork.cpp \
    mock/FakeTimer.cpp \
    mock/InMemoryPreferences.cpp
