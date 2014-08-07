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

#include "MockTray.h"

using tmty::ui::MockTray;

MockTray::MockTray(QObject *parent) :
  Tray(parent)
{
  _isShowing = false;
  _isStopTimerActionEnabled = true;
  _isPomodoroCountResetActionEnabled = true;
  _isPomodoroActionEnabled = false;
  _isShortBreakActionEnabled = false;
  _isLongBreakActionEnabled = false;
  _remainingTimeText = QString("undefined");
  _icon = QString("undefined");
}

void MockTray::show()
{
  _isShowing = true;
}

void MockTray::enableStopTimerAction(bool enable)
{
  _isStopTimerActionEnabled = enable;
}

void MockTray::enablePomodoroCountResetAction(bool enable)
{
  _isPomodoroCountResetActionEnabled = enable;
}

void MockTray::enablePomodoroAction(bool enable)
{
  _isPomodoroActionEnabled = enable;
}

void MockTray::enableShortBreakAction(bool enable)
{
  _isShortBreakActionEnabled = enable;
}

void MockTray::enableLongBreakAction(bool enable)
{
  _isLongBreakActionEnabled = enable;
}

bool MockTray::isShowing()
{
  return _isShowing;
}

bool MockTray::isStopTimerActionEnabled()
{
  return _isStopTimerActionEnabled;
}

bool MockTray::isPomodoroCountResetActionEnabled()
{
  return _isPomodoroCountResetActionEnabled;
}

bool MockTray::isPomodoroActionEnabled()
{
  return _isPomodoroActionEnabled;
}

bool MockTray::isShortBreakActionEnabled()
{
  return _isShortBreakActionEnabled;
}

bool MockTray::isLongBreakActionEnabled()
{
  return _isLongBreakActionEnabled;
}

const QString &MockTray::remainingTimeText()
{
  return _remainingTimeText;
}

void MockTray::setRemainingTimeText(const QString &text)
{
  _remainingTimeText = text;
}

const QString &MockTray::icon()
{
  return _icon;
}

void MockTray::setIcon(const QString &iconFile)
{
  _icon = iconFile;
}
