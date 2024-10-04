package utn.frc.bda;

import utn.frc.bda.entities.Album;
import utn.frc.bda.entities.Genre;
import utn.frc.bda.entities.Track;
import utn.frc.bda.repositories.TrackRepository;
import utn.frc.bda.services.ArchivoCSVService;
import utn.frc.bda.services.LoadToDBService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.String.format;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args ) {
        URL location = Main.class.getResource("/Tracks_Data.txt");
        ArchivoCSVService service = new ArchivoCSVService();
        LoadToDBService serviceDB = new LoadToDBService();
        List<Track> tracks;
        try {
            tracks = service.cargarTracksDeArchivo(location);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Requerimiento 2
        int cantTracks = tracks.size();
        System.out.println("La cantidad de tracks importados es: " + cantTracks);
        int cantTotalMs = tracks.stream()
                            .mapToInt(Track::getMiliseconds)
                            .sum();
        System.out.println("El tiempo total de horas, minutos y segundos de todos los tracks importados es:");
        System.out.printf("%dhs : %dmin : %dseg",
                            cantTotalMs / 3600000,
                            (cantTotalMs % 3600000) / 60000,
                            (cantTotalMs % 60000) / 1000);

        //Requerimiento 3
        File file = new File("Tracks_Genre.txt");
        try(PrintWriter printWriter = new PrintWriter(file)) {
            List<Genre> generosProcesados = new ArrayList<>(); //Genero una lista de generos ya procesados al archivo.
            printWriter.println(("Genero | Cantidad de tracks por genero "));
            for (Track track : tracks) { //Recorro cada uno de los tracks
                Genre genero = track.getGenre(); // Obtengo el genero para ese track en particular
                if (!generosProcesados.contains(genero)) { //Pregunto si el genero ya existe en la lista de generos procesados
                    long cantTracksByGenre = tracks.stream() //Si no es asi me fijo cuantas veces se repite ese genero recorriendo los tracks
                                                .filter(t -> t.getGenre().equals(genero))
                                                .count();
                    //Creo la nueva linea del archivo con el nombre del genero y la cantidad de tracks asociados a el.
                    printWriter.print(format("%s , %d \n",
                            track.getGenre().getName(),
                            cantTracksByGenre));
                    //Agrego el nuevo genero ya procesado al archivo
                    generosProcesados.add(genero);
                }
            }
        }   catch (FileNotFoundException e) {
            System.out.println("No se pudo crear el archivo");
            throw new RuntimeException(e);
        }

        //Requerimiento 4
        List<Album> albums = tracks.stream()
                            .map(track -> track.getAlbum())//Obtengo el objeto Album
                            .distinct()//verifico que sea distinto
                            .sorted(Comparator.comparing(Album::getName))//ordenado por nombre de Album
                            .toList(); //retorno la lista

        System.out.println("Album | Cantidad tracks | Duracion total album");
        for ( Album album : albums ){
            System.out.printf("%s | %d | %s \n",
                                album.getName(),
                                album.getTracks().size(),
                                album.getDuracionTotal());
        }


        //Requerimiento 5
        //serviceDB.addAll(tracks);

        //Consultas a DB
        //Consulta de todos los tracks
        TrackRepository trackRepository = new TrackRepository();
        //List<Track> tracksDb = trackRepository.findAll();

        //Track trackById = trackRepository.findById(7);


        //Consulta de tracks por album
        //List<Track> tracksByAlbum = trackRepository.findByAlbum("For Those About To Rock We Salute You");
        //tracksByAlbum.forEach(System.out::println);

        //Consulta de tracks por Genre
        /*List<Track> tracksByGenre = trackRepository.findByGenre("Blues");
        tracksByGenre.forEach(System.out::println);*/

        //Consulta de tracks por Artista
        List<Track> tracksByArtist = trackRepository.findByArtist("AC/DC");
        tracksByArtist.forEach(System.out::println);
    }


}
