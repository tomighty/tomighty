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

#ifndef TRAY_H
#define TRAY_H

#include <QObject>

namespace tmty
{
  namespace ui
  {
    class Tray : public QObject
    {
        Q_OBJECT

      public:
        explicit Tray(QObject *parent = 0);

        virtual void show() = 0;
        virtual void enableStopTimerAction(bool enable) = 0;
        virtual void enablePomodoroCountResetAction(bool enable) = 0;
        virtual void enablePomodoroAction(bool enable) = 0;
        virtual void enableShortBreakAction(bool enable) = 0;
        virtual void enableLongBreakAction(bool enable) = 0;
        virtual void setRemainingTimeText(const QString &text) = 0;
        virtual void setIcon(const QString &iconFile) = 0;

      signals:
        void pomodoroClicked();
        void shortBreakClicked();
        void longBreakClicked();
        void stopTimerClicked();
        void quitClicked();
    };
  }
}

#endif // TRAY_H
