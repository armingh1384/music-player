public  class PlayList {
    private int size;
    private Song[] songs;
    private int control = 0;

    public PlayList(int size) {
        this.size = size;
        this.songs = new Song[size];
    }


    public Song[] getSongs() {
        return songs;
    }

    public int getNumberOfSongs() {
        int count = 0;
        for (Song song : songs) {
            if (song != null)
                count++;
        }
        return count;
    }

    public boolean addSong(Song song) {
        if (control >= size)
            return false;
        songs[control++] = song;
        return true;
    }

    public boolean removeSong(Song song) {
        for (int i = 0; i < control; i++) {
            if (songs[i] != null && songs[i].equals(song)) {

                for (int j = i; j < control - 1; j++) {
                    songs[j] = songs[j + 1];
                }
                songs[--control] = null;
                return true;
            }
        }
        return false;
    }

    public PlayList filter(Filter filter) {
        PlayList filteredPlayList = new PlayList(size);
        for (Song song : songs) {
            if (song != null && filter.accept(song)) {
                filteredPlayList.addSong(song);
            }
        }
        return filteredPlayList;
    }

    public Object[] collectData(DataCollector collector) {
        Object[] data = new Object[getNumberOfSongs()];
        int index = 0;
        for (Song song : songs) {
            if (song != null) {
                data[index++] = collector.get(song);
            }
        }

        return data;
    }


}