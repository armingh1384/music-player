public class ArtistsCollector extends DataCollector{
    public Object get(Song Song){
        return  Song.getArtist();

    }
}
