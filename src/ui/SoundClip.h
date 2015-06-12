#ifndef SOUNDCLIP_H
#define SOUNDCLIP_H

namespace tmty
{
  namespace ui
  {
    class SoundClip
    {
      public:
        SoundClip();

        virtual void play() = 0;
        virtual void loop() = 0;
        virtual void stop() = 0;
    };
  }
}

#endif // SOUNDCLIP_H
