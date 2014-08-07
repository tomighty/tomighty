include(common.pri)

TEMPLATE = subdirs
CONFIG += ordered
SUBDIRS = \
  core \
  core-tests \
  ui \
  ui-tests \
  test-runner \
  app
