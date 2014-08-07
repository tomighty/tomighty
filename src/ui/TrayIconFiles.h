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

#ifndef TRAYICONFILES_H
#define TRAYICONFILES_H

class QString;

namespace tmty
{
  enum class IntervalType;

  namespace ui
  {
    class TrayIconFiles
    {
      public:
        explicit TrayIconFiles();
        virtual QString idle() const = 0;
        virtual QString forIntervalType(IntervalType intervalType) const = 0;
    };
  }
}

#endif // TRAYICONFILES_H
