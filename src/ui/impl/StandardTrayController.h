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

#ifndef STANDARDTRAYCONTROLLER_H
#define STANDARDTRAYCONTROLLER_H

#include "TrayController.h"

namespace tmty
{
  class Interval;
  class PomodoroEngine;
  class TimerSignalEmitter;

  namespace ui
  {
    class Tray;
    class TrayIconFiles;

    class StandardTrayController : public TrayController
    {
        Q_OBJECT

      public:
        explicit StandardTrayController(
          Tray &tray,
          const TrayIconFiles &trayIconFiles,
          PomodoroEngine &pomodoroEngine,
          TimerSignalEmitter &timerSignalEmitter,
          QObject *parent = 0
        );

        void showTrayIcon();

      private slots:
        void startPomodoro();
        void startShortBreak();
        void startLongBreak();
        void stopTimer();

        void timerSet(const Interval &);
        void timerStopped();
        void secondElapsed(int);

      private:
        void updateRemainingTime(int secondsLeft);
        void switchToIdleState();

        Tray &_tray;
        const TrayIconFiles &_trayIconFiles;
        PomodoroEngine &_pomodoroEngine;
        TimerSignalEmitter &_timerSignalEmitter;
    };
  }
}


#endif // STANDARDTRAYCONTROLLER_H
