public class FilterByAlbum extends Filter {
    private String[] Albums;

    public FilterByAlbum(String... Albums) {
        this.Albums = Albums;
    }

    public boolean accept(Song song) {
        String Album = song.getAlbum();
        for (String a: Albums) {
            if (Album.equals(a)) {
                return true;
            }
        }
        return false;
    }
}
