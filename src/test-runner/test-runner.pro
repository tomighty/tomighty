include(../common.pri)

TEMPLATE = app
TARGET = tomighty-test-runner
CONFIG   += console
CONFIG   -= app_bundle

QT       += testlib
QT       -= gui

DEFINES += SRCDIR=\\\"$$PWD/\\\"

DEPENDPATH += \
  $$PWD/../core-tests \
  $$PWD/../ui-tests

INCLUDEPATH += \
  $$PWD/../core-tests \
  $$PWD/../core-tests/impl \
  $$PWD/../ui-tests \
  $$PWD/../ui-tests/impl

SOURCES += \
    Main.cpp

win32:CONFIG(release, debug|release): LIBS += \
  -L$$OUT_PWD/../core/release/ -ltomighty-core \
  -L$$OUT_PWD/../ui/release/ -ltomighty-ui \
  -L$$OUT_PWD/../core-tests/release/ -ltomighty-core-tests \
  -L$$OUT_PWD/../ui-tests/release/ -ltomighty-ui-tests

else:win32:CONFIG(debug, debug|release): LIBS += \
  -L$$OUT_PWD/../core/debug/ -ltomighty-core \
  -L$$OUT_PWD/../ui/debug/ -ltomighty-ui \
  -L$$OUT_PWD/../core-tests/debug/ -ltomighty-core-tests \
  -L$$OUT_PWD/../ui-tests/debug/ -ltomighty-ui-tests

else:unix: LIBS += \
  -L$$OUT_PWD/../core/ -ltomighty-core \
  -L$$OUT_PWD/../ui/ -ltomighty-ui \
  -L$$OUT_PWD/../core-tests/ -ltomighty-core-tests \
  -L$$OUT_PWD/../ui-tests/ -ltomighty-ui-tests
