package utn.frc.bda.services;
import utn.frc.bda.entities.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArchivoCSVService {
    public List<Track> cargarTracksDeArchivo(URL location) throws URISyntaxException, IOException {
        //Listas para verificar si el objeto ya está creado o no
        List<Album> albums = new ArrayList<>();
        List<Artist> artists = new ArrayList<>();
        List<Track> tracks = new ArrayList<>();
        List<Composer> composers = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        List<MediaType> mediaTypes = new ArrayList<>();

       Files.lines(Paths.get(location.toURI()))
                .skip(1)
                .forEach(line -> { //Recorro linea por linea el archivo y le voy pasando las lineas y las listas por cada vuelta al metodo crearTrack
                    Track track = crearOObtenerTrack(line, albums, artists, composers, genres, mediaTypes);
                    tracks.add(track); //Una vez que creó el track lo agrega
                });
       return tracks;
    }

    private Track crearOObtenerTrack(String line, List<Album> albums, List<Artist> artists, List<Composer> composers, List<Genre> genres, List<MediaType> mediaTypes) {
        String[] valores = line.split("\\|");
        int trackId = Integer.parseInt(valores[0]);
        String nameTrack = valores[1];
        String nameAlbum = valores[2];
        String artistNames = valores[3];
        List<Artist> artistsList = crearOObtenerArtists(artistNames, artists);
        String composerNames = valores[4];
        List<Composer> composersList = crearOObtenerComposers(composerNames, composers);
        int miliseconds = Integer.parseInt(valores[5]);
        String nameGenre = valores[6];
        String nameMediaType = valores[7];


        //Album -- Si no existe en la lista del albums significa que el objeto no esta creado por lo tanto lo creamos
        //y lo agregamos a la lista
        Album album = null;
        for ( Album a : albums ){
            if ( a.getName().equals(nameAlbum) ){
                album = a;
                album.setTotalMiliseconds(album.getTotalMiliseconds()+miliseconds);
                break;
            }
        }
        //Vemos si no existía..
        if (album == null){
            album = new Album(nameAlbum, miliseconds);
            albums.add(album);
        }

        //Genre Si no existe en la lista del genres significa que el objeto no esta creado por lo tanto lo creamos
        //y lo agregamos a la lista
        Genre genre = null;
        for ( Genre g : genres ){
            if( g.getName().equals(nameGenre)){
                genre = g;
                break;
            }
        }
        //Vemos si no existia
        if (genre == null){
            genre = new Genre(nameGenre);
            genres.add(genre);
        }

        //MediaTypes Si no existe en la lista del mediaTypes significa que el objeto no esta creado por lo tanto lo creamos
        //y lo agregamos a la lista
        MediaType mediaType = null;
        for ( MediaType mt : mediaTypes ){
            if( mt.getName().equals(nameMediaType)){
                mediaType = mt;
                break;
            }
        }
        //Vemos si no existia
        if ( mediaType == null){
            mediaType = new MediaType(nameMediaType);
            mediaTypes.add(mediaType);
        }
        //Creamos el track
        Track track = new Track(trackId, nameTrack, miliseconds, genre, mediaType, album, artistsList, composersList);
        return track;
    }

    private List<Composer> crearOObtenerComposers(String composerNames, List<Composer> composers) {
        String[] valores = composerNames.split(",");
        List<Composer> composersByTrack = new ArrayList<>();//Lista que se retorna con los compositores del actual track
        for (String composerName : valores) {
            Composer composer = null;
            boolean existe = composers.stream() //lista con todos los compositores creados. Si no existe el actual lo creamos y lo agregamos a la lista.
                    .anyMatch(c -> c.getName().equals(composerName));
            if(!existe){
                composer = new Composer(composerName);
                composers.add(composer);
            } else{ //Si el compositor actual ya existe en la lista
                for ( Composer c : composers ){ //Recorremos esa lista y cuando lo encuentre lo asignamos al composer sin crearlo
                    if ( c.getName().equals(composerName) ){
                        composer = c;
                    }
                }
            }
            composersByTrack.add(composer); //Sea que creamos el objeto o no, de todas formas lo guardamos en la
            //lista de compositores del actual track para luego retornarla.
        }
        return composersByTrack;
    }


    private List<Artist> crearOObtenerArtists(String namesArtists, List<Artist> artists) {
        String[] valores = namesArtists.split(",");
        List<Artist> artistsByTrack = new ArrayList<>();
        for (String artistName : valores) {
            Artist artist = null;
            boolean existe = artists.stream()
                    .anyMatch(a -> a.getName().equals(artistName));
            if(!existe){
                artist = new Artist(artistName);
                artists.add(artist);
            } else{
                for ( Artist a : artists ){
                    if ( a.getName().equals(artistName) ){
                        artist = a;
                    }
                }
            }
            artistsByTrack.add(artist);
        }
        return artistsByTrack;
    }
}
