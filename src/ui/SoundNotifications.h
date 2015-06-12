#ifndef SOUNDNOTIFICATIONS_H
#define SOUNDNOTIFICATIONS_H

namespace tmty
{
  namespace ui
  {
    class SoundClip;

    class SoundNotifications
    {
      public:
        explicit SoundNotifications();

        virtual SoundClip &timerSet() = 0;
        virtual SoundClip &timeElapsing() = 0;
        virtual SoundClip &timerExpired() = 0;
    };
  }
}

#endif // SOUNDNOTIFICATIONS_H
