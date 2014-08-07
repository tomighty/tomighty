include(../common.pri)

TEMPLATE = lib
TARGET = tomighty-ui

QT += widgets

win32:CONFIG(release, debug|release): LIBS += -L$$OUT_PWD/../core/release/ -ltomighty-core
else:win32:CONFIG(debug, debug|release): LIBS += -L$$OUT_PWD/../core/debug/ -ltomighty-core
else:unix: LIBS += -L$$OUT_PWD/../core/ -ltomighty-core

INCLUDEPATH += $$PWD/../core $$PWD/../core/impl
DEPENDPATH += $$PWD/../core
DEFINES += SRCDIR=\\\"$$PWD/\\\"

HEADERS += \
    Tray.h \
    TrayController.h \
    impl/StandardTrayController.h \
    impl/StandardTray.h \
    TrayIconFiles.h \
    impl/StandardTrayIconFiles.h

SOURCES += \
    Tray.cpp \
    TrayController.cpp \
    impl/StandardTrayController.cpp \
    impl/StandardTray.cpp \
    TrayIconFiles.cpp \
    impl/StandardTrayIconFiles.cpp

RESOURCES = resources/tomighty.qrc
