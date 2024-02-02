package br.com.postech.netflixo.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "filmes")
public class NotaVideo {

    @Id
    private String objectId;
    private int id;
    @Field(value = "descricao")
    private int description;
    @Field(value = "nota")
    private int note;

    public NotaVideo() {}

    public NotaVideo(int id, int description, int note) {
        this.id = id;
        this.description = description;
        this.note = note;
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
