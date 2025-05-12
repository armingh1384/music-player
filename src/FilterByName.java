public class FilterByName extends Filter {
    private String[] names;

    public FilterByName(String... names) {
        this.names = names;
    }

    public boolean accept(Song song) {
        String name = song.getName();
        for (String a : names) {
            if (name.equals(a)) {
                return true;
            }
        }
        return false;
    }
}
