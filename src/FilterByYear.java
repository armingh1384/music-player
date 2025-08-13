public class FilterByYear extends Filter {
    private int[] years;

    public FilterByYear(int... years) {
        this.years = years;
    }

    public boolean accept(Song song) {
        Double year = song.getReleaseYear();
        for (int y : years) {
            if (year == y) {
                return true;
            }
        }
        return false;
    }
}
