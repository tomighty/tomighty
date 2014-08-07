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

#include <QString>

#include "IntervalType.h"
#include "StandardTrayIconFiles.h"

using tmty::IntervalType;
using tmty::ui::StandardTrayIconFiles;
using tmty::ui::TrayIconFiles;

StandardTrayIconFiles::StandardTrayIconFiles() :
  TrayIconFiles()
{
}

QString StandardTrayIconFiles::idle() const
{
  return ":/images/icons/tray/idle.png";
}

QString StandardTrayIconFiles::forIntervalType(IntervalType intervalType) const
{
  switch(intervalType)
  {
    case IntervalType::POMODORO: return ":/images/icons/tray/pomodoro.png";
    case IntervalType::SHORT_BREAK: return ":/images/icons/tray/short-break.png";
    default: return ":/images/icons/tray/long-break.png";
  }
}
