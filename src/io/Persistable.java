package io;

//interfata pt parsare proprie
//serializable nu este bun pt a salva pt ca putem obt usor "local class incompatible" -> adica doua serialVersionID diferite fiindca am pus alte componente in clasa ce se serializeaza
public interface Persistable {

    //pt a putea citi fisierul cu ochiul liber(nu vrem sa fie compresate etc. pt a le putea citi)
    String DELIMITER = ":";
    String SECTION_DELIMITER = System.lineSeparator() + "###"+System.lineSeparator();
    String LIST_DELIMITER = ", ";
    String COLUMN_DELIMITER=System.lineSeparator();

    String serialize();
    void applySerializedData(String serializedData);
}
