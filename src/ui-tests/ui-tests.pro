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
  $$PWD/../core-mock \
  $$PWD/../ui

INCLUDEPATH += \
  $$PWD/../core \
  $$PWD/../core/impl \
  $$PWD/../core-mock \
  $$PWD/../ui \
  $$PWD/../ui/impl

win32:CONFIG(release, debug|release): LIBS += \
  -L$$OUT_PWD/../core/release/ -ltomighty-core \
  -L$$OUT_PWD/../core-mock/release/ -ltomighty-core-mock \
  -L$$OUT_PWD/../ui/release/ -ltomighty-ui

else:win32:CONFIG(debug, debug|release): LIBS += \
  -L$$OUT_PWD/../core/debug/ -ltomighty-core \
  -L$$OUT_PWD/../core-mock/debug/ -ltomighty-core-mock \
  -L$$OUT_PWD/../ui/debug/ -ltomighty-ui

else:unix: LIBS += \
  -L$$OUT_PWD/../core/ -ltomighty-core \
  -L$$OUT_PWD/../core-mock/ -ltomighty-core-mock \
  -L$$OUT_PWD/../ui/ -ltomighty-ui

HEADERS += \
    mock/MockTray.h \
    mock/MockTrayIconFiles.h \
    mock/MockSoundClip.h \
    mock/MockSoundNotifications.h \
    TrayControllerTest.h \
    SoundNotificationControllerTest.h

SOURCES += \
    mock/MockTray.cpp \
    mock/MockTrayIconFiles.cpp \
    mock/MockSoundClip.cpp \
    mock/MockSoundNotifications.cpp \
    TrayControllerTest.cpp \
    SoundNotificationControllerTest.cpp
