package io;

import ExceptionHandle.AllExceptions;
import gfx.SpriteLibrary;
import map.GameMap;

import java.io.*;
import java.net.URL;

public class MapIO {

    public static void save(GameMap map,String name) throws AllExceptions{
        //luam path ul pana la folderul de resurse
        final URL pathToResources = MapIO.class.getResource("/");
        //luam fisierul(sau folderul) din resurse cu numele maps
        File mapsFolder = new File(pathToResources.getFile() + "/maps/");

        //daca nu exista folderul , il cream
        if(!mapsFolder.exists()){
            mapsFolder.mkdir();
        }

        try(BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(mapsFolder.toString() + "/"+name))){//serializeaza ob si il scrie
            bufferedWriter.write(map.serialize());
        } catch (IOException e) {
            throw new AllExceptions("Nu s-a putut scrie in fisier",e);
        }
    }

    public static GameMap load(SpriteLibrary spriteLibrary,String name) throws AllExceptions{
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(MapIO.class.getResource("/maps/"+name).getFile()))) {
            GameMap map = new GameMap();
            //putemn citi doar cate o linie odata (cu BufferedReader)
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){ //daca line are valoare
                //"lipim" inapoi stringul si ii punem separatoare intre linii
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(line);
            }
            map.applySerializedData(stringBuilder.toString());
            map.reloadGraphics(spriteLibrary);
            return map;
        } catch (IOException e) {
            throw new AllExceptions("Nu s-a putut citi din fisier",e);
        }
    }
}
