include(../common.pri)

TEMPLATE = lib
TARGET = tomighty-ui-tests
CONFIG   += console
CONFIG   -= app_bundle

QT       += testlib
QT       -= gui

DEFINES += SRCDIR=\\\"$$PWD/\\\"

DEPENDPATH += \
  $$PWD/../core \
  $$PWD/../ui

INCLUDEPATH += \
  $$PWD/../core \
  $$PWD/../core/impl \
  $$PWD/../ui \
  $$PWD/../ui/impl

win32:CONFIG(release, debug|release): LIBS += \
  -L$$OUT_PWD/../core/release/ -ltomighty-core \
  -L$$OUT_PWD/../ui/release/ -ltomighty-ui

else:win32:CONFIG(debug, debug|release): LIBS += \
  -L$$OUT_PWD/../core/debug/ -ltomighty-core \
  -L$$OUT_PWD/../ui/debug/ -ltomighty-ui

else:unix: LIBS += \
  -L$$OUT_PWD/../core/ -ltomighty-core \
  -L$$OUT_PWD/../ui/ -ltomighty-ui

HEADERS += \
  TrayControllerTest.h \
  mock/MockTray.h \
    mock/MockPomodoroEngine.h \
    mock/MockTimerSignalEmitter.h \
    mock/MockTrayIconFiles.h

SOURCES += \
  TrayControllerTest.cpp \
  mock/MockTray.cpp \
    mock/MockPomodoroEngine.cpp \
    mock/MockTimerSignalEmitter.cpp \
    mock/MockTrayIconFiles.cpp
