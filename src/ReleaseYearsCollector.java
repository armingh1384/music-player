public class ReleaseYearsCollector extends DataCollector{

    public  Object get(Song song){
        return song.getReleaseYear();
    }

}
