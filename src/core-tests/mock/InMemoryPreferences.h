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

#ifndef INMEMORYPREFERENCES_H
#define INMEMORYPREFERENCES_H

#include "Preferences.h"

namespace tmty
{
  class InMemoryPreferences : public Preferences
  {
    public:
      explicit InMemoryPreferences(QObject *parent = 0);

      int intervalLengthInMinutes(IntervalType intervalType) const;
      void setIntervalLengthInMinutes(IntervalType intervalType, int minutes);

    private:
      QMap<IntervalType, int> _intervalLengths;
  };
}

#endif // INMEMORYPREFERENCES_H
