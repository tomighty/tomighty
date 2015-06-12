#ifndef MOCKSOUNDNOTIFICATIONS_H
#define MOCKSOUNDNOTIFICATIONS_H

#include "SoundNotifications.h"

namespace tmty
{
  namespace ui
  {
    class SoundClip;

    class MockSoundNotifications : public SoundNotifications
    {
      public:
        explicit MockSoundNotifications(SoundClip &timerSet, SoundClip &timeElapsing, SoundClip &timerExpired);

        SoundClip &timerSet();
        SoundClip &timeElapsing();
        SoundClip &timerExpired();

      private:
        SoundClip &_timerSet;
        SoundClip &_timeElapsing;
        SoundClip &_timerExpired;
    };
  }
}

#endif // MOCKSOUNDNOTIFICATIONS_H
