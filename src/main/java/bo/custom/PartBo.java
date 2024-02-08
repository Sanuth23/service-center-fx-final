package bo.custom;

import bo.SuperBo;
import dto.ItemDto;
import dto.PartDto;

import java.sql.SQLException;
import java.util.List;

public interface PartBo extends SuperBo {
    boolean savePart(PartDto dto) throws SQLException, ClassNotFoundException;
    boolean updatePart(PartDto dto) throws SQLException, ClassNotFoundException;
    boolean deletePart(String id) throws SQLException, ClassNotFoundException;
    List<PartDto> allParts() throws SQLException, ClassNotFoundException;
    PartDto getPart(String id) throws SQLException, ClassNotFoundException;
}
