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

#include "MockTrayIconFiles.h"

using tmty::IntervalType;
using tmty::ui::MockTrayIconFiles;
using tmty::ui::TrayIconFiles;

MockTrayIconFiles::MockTrayIconFiles() :
  TrayIconFiles()
{
}

QString MockTrayIconFiles::idle() const
{
  if(_idle.isNull())
  {
    throw new std::runtime_error("idle icon is not set");
  }
  return _idle;
}

QString MockTrayIconFiles::forIntervalType(IntervalType intervalType) const
{
  if(!_intervalTypeIcons.contains(intervalType))
  {
    throw new std::runtime_error("no tray icon set for given interval type");
  }
  return _intervalTypeIcons[intervalType];
}

void MockTrayIconFiles::setIdleIcon(QString fictitiousIconFile)
{
  _idle = fictitiousIconFile;
}

void MockTrayIconFiles::setIconForIntervalType(IntervalType intervalType, QString fictitiousIconFile)
{
  _intervalTypeIcons[intervalType] = fictitiousIconFile;
}
