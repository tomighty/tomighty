include(../common.pri)

TEMPLATE = lib
TARGET = tomighty-core

QT += core

SOURCES += \
    Clockwork.cpp \
    PomodoroEngine.cpp \
    Preferences.cpp \
    Interval.cpp \
    Timer.cpp \
    TimerSignalEmitter.cpp \
    impl/StandardClockwork.cpp \
    impl/StandardPomodoroEngine.cpp \
    impl/StandardTimer.cpp \
    impl/StandardPreferences.cpp

HEADERS += \
    Clockwork.h \
    Interval.h \
    IntervalType.h \
    PomodoroEngine.h \
    Preferences.h \
    Timer.h \
    TimerSignalEmitter.h \
    impl/StandardClockwork.h \
    impl/StandardPomodoroEngine.h \
    impl/StandardTimer.h \
    impl/StandardPreferences.h
