package database;

import ExceptionHandle.AllExceptions;
import io.Persistable;
import state.score.Score;
import state.score.ScoreList;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoadAndSaveScores {
    public static void save(ScoreList list) throws AllExceptions {
        List<String> scoreList = new ArrayList<>();
        String allScores = list.serialize();

        Connection c = null;
        Statement stmt = null;

        for (String s : allScores.split("\r?\n|\r")) {
            scoreList.add(s);
        }

        try {
            File file = new File("E:\\Cursuri\\An2\\Semestru2\\PAOO\\ProiectPAOO\\scoruri.db");
            if (file.exists()) {
                file.delete();
            }
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:scoruri.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql;
            int i = 0;

            sql = "CREATE TABLE HIGHSCORE " +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    " CLASS TEXT NOT NULL, " +
                    " DATE TEXT NOT NULL, " +
                    " SCORE INT)";
            stmt.executeUpdate(sql);

            for (String s : scoreList) {
                String[] parts = s.split("}");

                String clasa = parts[0];
                String data = parts[1];
                int score = Integer.parseInt(parts[2]);
                sql = String.format("INSERT INTO HIGHSCORE (ID,CLASS,DATE,SCORE) VALUES (%d, '%s', '%s',%d );", i, clasa, data, score);
                i++;
                stmt.executeUpdate(sql);
            }

            stmt.close();
            c.commit();
            c.close();

        } catch (ClassNotFoundException e) {
            throw new AllExceptions("Nu s-a gasit clasa org.sqlite.JDBC");
        } catch (SQLException e) { //separata de alte exceptii
        }
    }

    //vom returna o clasa T care va extinde Persistable, si ii spunem ca vom trimite in functie o clasa de ac tip cu ce returnam
    public static <T extends Persistable> T load(Class<T> clasa) throws AllExceptions{
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("/E:/Cursuri/An2/Semestru2/PAOO/ProiectPAOO/out/production/ProiectPAOO/scores.txt"))) {
            T persistable = clasa.getDeclaredConstructor().newInstance();

            //putemn citi doar cate o linie odata (cu BufferedReader)
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){ //daca line are valoare
                //"lipim" inapoi stringul si ii punem separatoare intre linii
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(line);
            }
            persistable.applySerializedData(stringBuilder.toString());
            return persistable;
        } catch (IOException e) {
            throw new AllExceptions("Nu s-a putut citi din fisier",e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new AllExceptions("Eroare la folosirea getDeclaredConstructor().newInstance",e);
        }
    }

    public static ScoreList loaad()throws AllExceptions{
        ScoreList scoreList = new ScoreList();
        Connection c;
        Statement stmt;

        try{
            Class.forName("org.sqlite.JDBC");
            c= DriverManager.getConnection("jdbc:sqlite:scoruri.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            StringBuilder finalString = new StringBuilder();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM HIGHSCORE;" );
            while ( rs.next() ) {
                StringBuilder line = new StringBuilder();
                String clasa = rs.getString("class");
                line.append(clasa);
                line.append("}");
                String data = rs.getString("date");
                line.append(data);
                line.append("}");
                int score = rs.getInt("score");
                line.append(score);
                finalString.append(System.lineSeparator());
                finalString.append(line);
            }
            rs.close();
            stmt.close();
            c.close();

            scoreList.applySerializedData(finalString.toString());
        } catch (ClassNotFoundException e) {
            throw new AllExceptions("Nu s-a gasit clasa org.sqlite.JDBC");
        } catch (SQLException e) { //separata de alte exceptii
            throw new RuntimeException(e);
        }
        return scoreList;
    }

}
