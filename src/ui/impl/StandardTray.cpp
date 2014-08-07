/*
 * Copyright (c) 2010-2014 Célio Cidral Junior.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

#include <QApplication>
#include <QMenu>
#include <QObject>
#include <QSystemTrayIcon>

#include "StandardTray.h"

using tmty::ui::StandardTray;
using tmty::ui::Tray;

StandardTray::StandardTray(QObject *parent) :
  Tray(parent)
{
  _trayMenu = new QMenu();
  _systemTray = new QSystemTrayIcon();

  buildMenu();
  buildSystemTray();
}

void StandardTray::show()
{
  _systemTray->show();
}

void StandardTray::enableStopTimerAction(bool enable)
{
  _stopTimerAction->setEnabled(enable);
}

void StandardTray::enablePomodoroCountResetAction(bool enable)
{
  _pomodoroCountResetAction->setEnabled(enable);
}

void StandardTray::enablePomodoroAction(bool enable)
{
  enableTimerAction(_pomodoroAction, enable);
}

void StandardTray::enableShortBreakAction(bool enable)
{
  enableTimerAction(_shortBreakAction, enable);
}

void StandardTray::enableLongBreakAction(bool enable)
{
  enableTimerAction(_longBreakAction, enable);
}

void StandardTray::setRemainingTimeText(const QString &text)
{
  _remainingTimeAction->setText(text);
}

void StandardTray::setIcon(const QString &iconFile)
{
  _systemTray->setIcon(QIcon(iconFile));
}

void StandardTray::buildSystemTray()
{
  _systemTray->setContextMenu(_trayMenu);
}

void StandardTray::buildMenu()
{
  _remainingTimeAction = _trayMenu->addAction(QIcon(":/images/icons/menu/clock.png"), "");
  _stopTimerAction = _trayMenu->addAction(QIcon(":/images/icons/menu/stop.png"), tr("Stop"));
  _trayMenu->addSeparator();
  QAction *pomodoroCountAction = _trayMenu->addAction(tr("No pomodoro completed"));
  _pomodoroCountResetAction = _trayMenu->addAction(tr("Reset count"));
  _trayMenu->addSeparator();
  _pomodoroAction = createTimerAction(tr("Pomodoro"), QString(":/images/icons/menu/pomodoro.png"));
  _shortBreakAction = createTimerAction(tr("Short break"), QString(":/images/icons/menu/short-break.png"));
  _longBreakAction = createTimerAction(tr("Long break"), QString(":/images/icons/menu/long-break.png"));
  _trayMenu->addSeparator();
  _trayMenu->addAction(tr("Preferences..."));
  _trayMenu->addAction(tr("About Tomighty"));
  _trayMenu->addSeparator();
  QAction *quitAction = _trayMenu->addAction(tr("Quit"));

  _remainingTimeAction->setEnabled(false);
  pomodoroCountAction->setEnabled(false);

  quitAction->setShortcut(QKeySequence::Quit);

  connect(_stopTimerAction, SIGNAL(triggered()), this, SIGNAL(stopTimerClicked()));
  connect(_pomodoroAction, SIGNAL(triggered()), this, SIGNAL(pomodoroClicked()));
  connect(_shortBreakAction, SIGNAL(triggered()), this, SIGNAL(shortBreakClicked()));
  connect(_longBreakAction, SIGNAL(triggered()), this, SIGNAL(longBreakClicked()));
  connect(quitAction, SIGNAL(triggered()), this, SIGNAL(quitClicked()));
}

QAction *StandardTray::createTimerAction(QString text, QString iconFile)
{
  QAction *action = _trayMenu->addAction(QIcon(iconFile), text);
  action->setCheckable(true);
  return action;
}

void StandardTray::enableTimerAction(QAction *action, bool enable)
{
  action->setEnabled(enable);
  action->setChecked(!enable);
}
