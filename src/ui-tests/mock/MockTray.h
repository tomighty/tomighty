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

#ifndef MOCKTRAY_H
#define MOCKTRAY_H

#include "Tray.h"

namespace tmty
{
  namespace ui
  {
    class MockTray : public Tray
    {
        Q_OBJECT

      public:
        explicit MockTray(QObject *parent = 0);

        void show();
        void enableStopTimerAction(bool enable);
        void enablePomodoroCountResetAction(bool enable);
        void enablePomodoroAction(bool enable);
        void enableShortBreakAction(bool enable);
        void enableLongBreakAction(bool enable);
        void setRemainingTimeText(const QString &text);
        void setIcon(const QString &iconFile);

        bool isShowing();
        bool isStopTimerActionEnabled();
        bool isPomodoroCountResetActionEnabled();
        bool isPomodoroActionEnabled();
        bool isShortBreakActionEnabled();
        bool isLongBreakActionEnabled();
        const QString &remainingTimeText();
        const QString &icon();

      private:
        bool _isShowing;
        bool _isStopTimerActionEnabled;
        bool _isPomodoroCountResetActionEnabled;
        bool _isPomodoroActionEnabled;
        bool _isShortBreakActionEnabled;
        bool _isLongBreakActionEnabled;
        QString _remainingTimeText;
        QString _icon;
    };
  }
}

#endif // MOCKTRAY_H
