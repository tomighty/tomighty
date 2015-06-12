#ifndef MOCKSOUNDCLIP_H
#define MOCKSOUNDCLIP_H

#include "SoundClip.h"

namespace tmty
{
  namespace ui
  {
    class MockSoundClip : public SoundClip
    {
      public:
        explicit MockSoundClip();

        void play();
        void loop();
        void stop();

        int timesPlayed();
        int timesStopped();
        bool isLooping();

      private:
        int _timesPlayed;
        int _timesStopped;
        bool _isLooping;
    };
  }
}

#endif // MOCKSOUNDCLIP_H
