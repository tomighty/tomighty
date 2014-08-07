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

#include <stdexcept>

#include "ManualClockwork.h"

using tmty::ManualClockwork;

ManualClockwork::ManualClockwork(QObject *parent) : Clockwork(parent)
{
  _isRunning = false;
}

void ManualClockwork::resume()
{
  if(_isRunning)
    throw std::runtime_error("clockwork is already running");

  _isRunning = true;
}

void ManualClockwork::pause()
{
  if(!_isRunning)
    throw std::runtime_error("clockwork is not running");

  _isRunning = false;
}

bool ManualClockwork::isRunning() const
{
  return _isRunning;
}

void ManualClockwork::moveOneSecond()
{
  if(!_isRunning)
    throw std::runtime_error("clockwork is not running");

  emit secondElapsed();
}
