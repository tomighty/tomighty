include(../common.pri)

TEMPLATE = app
TARGET = tomighty

QT += widgets

DEFINES += SRCDIR=\\\"$$PWD/\\\"

DEPENDPATH += \
  $$PWD/../core \
  $$PWD/../ui

INCLUDEPATH += \
  $$PWD/../core \
  $$PWD/../ui

SOURCES += \
    Main.cpp

win32:CONFIG(release, debug|release): LIBS += \
  -L$$OUT_PWD/../core/release/ -ltomighty-core \
  -L$$OUT_PWD/../ui/release/ -ltomighty-ui

else:win32:CONFIG(debug, debug|release): LIBS += \
  -L$$OUT_PWD/../core/debug/ -ltomighty-core \
  -L$$OUT_PWD/../ui/debug/ -ltomighty-ui

else:unix: LIBS += \
  -L$$OUT_PWD/../core/ -ltomighty-core \
  -L$$OUT_PWD/../ui/ -ltomighty-ui
