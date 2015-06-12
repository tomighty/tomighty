include(../common.pri)

TEMPLATE = lib
TARGET = tomighty-core-mock
CONFIG   += console
CONFIG   -= app_bundle

QT       -= gui

DEFINES += SRCDIR=\\\"$$PWD/\\\"
DEPENDPATH += $$PWD/../core
INCLUDEPATH += $$PWD/../core $$PWD/../core/impl

win32:CONFIG(release, debug|release): LIBS += -L$$OUT_PWD/../core/release/ -ltomighty-core
else:win32:CONFIG(debug, debug|release): LIBS += -L$$OUT_PWD/../core/debug/ -ltomighty-core
else:unix: LIBS += -L$$OUT_PWD/../core/ -ltomighty-core

HEADERS += \
    ManualClockwork.h \
    FakeTimer.h \
    InMemoryPreferences.h \
    MockPomodoroEngine.h \
    MockTimerSignalEmitter.h

SOURCES += \
    ManualClockwork.cpp \
    FakeTimer.cpp \
    InMemoryPreferences.cpp \
    MockPomodoroEngine.cpp \
    MockTimerSignalEmitter.cpp
