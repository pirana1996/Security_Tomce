package com.sorsix.bookTradingClub.domain;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by jordancho on 18.7.2017.
 */
@Embeddable
public class FileEmbeddable {

    public FileEmbeddable(byte data[], String fileName, String contentType, int size) throws SQLException {
        this.data = data;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
    }

    public FileEmbeddable() {
    }

    @Basic(fetch = FetchType.LAZY)
    @Lob
    public byte[] data;

    public String fileName;

    public String contentType;

    public int size;

}

