package au.com.normist.capital.domain.cap;

import java.io.Serializable;

public class CapBaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public String rowId;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }
}
