package pt.neverstopthinking.canticosneocat.ui.Cantico;

public interface PlayerAdapter {
    void loadMedia(int resourceId);

    void release();

    boolean isPlaying();

    void play();

    void reset();

    void pause();

    void initializeProgressCallback();

    void seekTo(int position);
}
