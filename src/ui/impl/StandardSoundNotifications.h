#ifndef STANDARDSOUNDNOTIFICATIONS_H
#define STANDARDSOUNDNOTIFICATIONS_H

#include "SoundNotifications.h"

namespace tmty
{
  namespace ui
  {
    class StandardSoundNotifications : public SoundNotifications
    {
      public:
        explicit StandardSoundNotifications();

        SoundClip &timerSet();
        SoundClip &timeElapsing();
        SoundClip &timerExpired();

      private:
        SoundClip *_timerSet;
        SoundClip *_timeElapsing;
        SoundClip *_timerExpired;
    };
  }
}

#endif // STANDARDSOUNDNOTIFICATIONS_H
