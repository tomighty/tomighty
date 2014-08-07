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
#include <QObject>

#include "impl/StandardClockwork.h"
#include "impl/StandardPomodoroEngine.h"
#include "impl/StandardPreferences.h"
#include "impl/StandardTimer.h"
#include "impl/StandardTray.h"
#include "impl/StandardTrayController.h"
#include "impl/StandardTrayIconFiles.h"

using tmty::Clockwork;
using tmty::PomodoroEngine;
using tmty::Preferences;
using tmty::StandardClockwork;
using tmty::StandardPomodoroEngine;
using tmty::StandardPreferences;
using tmty::StandardTimer;
using tmty::Timer;

using tmty::ui::StandardTray;
using tmty::ui::StandardTrayController;
using tmty::ui::StandardTrayIconFiles;
using tmty::ui::Tray;
using tmty::ui::TrayController;
using tmty::ui::TrayIconFiles;

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);

  Clockwork *clockwork = new StandardClockwork();
  Timer *timer = new StandardTimer(*clockwork);
  Preferences *preferences = new StandardPreferences();
  PomodoroEngine *pomodoroEngine = new StandardPomodoroEngine(*timer, *preferences);
  Tray *tray = new StandardTray();
  TrayIconFiles *trayIconFiles = new StandardTrayIconFiles();
  TrayController *trayController = new StandardTrayController(*tray, *trayIconFiles, *pomodoroEngine, *timer);

  trayController->showTrayIcon();

  return app.exec();
}
