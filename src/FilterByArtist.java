public class FilterByArtist extends Filter {
    private String[] artists;

    public FilterByArtist(String... artists) {
        this.artists = artists;
    }

    public boolean accept(Song music) {
        String artist = music.getArtist();
        for (String a : artists) {
            if (artist.equals(a)) {
                return true;
            }
        }
        return false;
    }
}
