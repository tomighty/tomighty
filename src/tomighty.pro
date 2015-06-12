include(common.pri)

TEMPLATE = subdirs
CONFIG += ordered
SUBDIRS = \
  core \
  core-mock \
  core-tests \
  ui \
  ui-tests \
  test-runner \
  app
