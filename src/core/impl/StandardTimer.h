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

#ifndef STANDARDTIMER_H
#define STANDARDTIMER_H

#include "../Timer.h"

namespace tmty
{
  class Clockwork;
  class Interval;
  enum class IntervalType;

  class StandardTimer : public Timer
  {
      Q_OBJECT

    public:
      explicit StandardTimer(Clockwork &clockwork, QObject *parent = 0);

      void set(int seconds, IntervalType type);
      void interrupt();
      bool isRunning() const;

    private slots:
      void decreaseOneSecond();

    private:
      void stop();

      Clockwork &_clockwork;
      Interval *_interval;
      bool _isRunning;
      int _secondsLeft;
  };
}

#endif // STANDARDTIMER_H
