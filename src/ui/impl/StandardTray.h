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

#ifndef STANDARDTRAY_H
#define STANDARDTRAY_H

#include "Tray.h"

class QAction;
class QMenu;
class QSystemTrayIcon;

namespace tmty
{
  namespace ui
  {
    class StandardTray : public Tray
    {
        Q_OBJECT

      public:
        explicit StandardTray(QObject *parent = 0);

        void show();
        void enableStopTimerAction(bool enable);
        void enablePomodoroCountResetAction(bool enable);
        void enablePomodoroAction(bool enable);
        void enableShortBreakAction(bool enable);
        void enableLongBreakAction(bool enable);
        void setRemainingTimeText(const QString &text);
        void setIcon(const QString &iconFile);

      private:
        QSystemTrayIcon *_systemTray;
        QMenu *_trayMenu;
        QAction *_remainingTimeAction;
        QAction *_stopTimerAction;
        QAction *_pomodoroCountResetAction;
        QAction *_pomodoroAction;
        QAction *_shortBreakAction;
        QAction *_longBreakAction;

        void buildSystemTray();
        void buildMenu();
        QAction *createTimerAction(QString text, QString iconFile);
        void enableTimerAction(QAction *action, bool enable);
    };
  }
}

#endif // STANDARDTRAY_H
