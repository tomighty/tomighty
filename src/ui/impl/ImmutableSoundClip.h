#ifndef IMMUTABLESOUNDCLIP_H
#define IMMUTABLESOUNDCLIP_H

#include "SoundClip.h"

class QSoundEffect;
class QString;

namespace tmty
{
  namespace ui {
    class ImmutableSoundClip : public SoundClip
    {
      public:
        explicit ImmutableSoundClip(const QString &filePath);

        void play();
        void loop();
        void stop();

      private:
        QSoundEffect *_sound;
    };
  }
}

#endif // IMMUTABLESOUNDCLIP_H
